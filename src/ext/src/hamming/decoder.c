#include "hamming.h"

#include "../bitarr/bitarr.h"
#include "../buffer/buffer.h"

#include <errno.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void correct(void *block, uint32_t block_size, uint32_t exponent, void *masks);

void unpack(void *buffer, buffered_writer *bw, uint32_t block_size);

char *decode(char *path, char *dest, uint64_t block_size, uint64_t exponent,
             int correction) {
  buffered_reader reader;
  buffered_writer writer;

  init_buffered_reader(&reader, path);
  if (!reader.file) {
    return strerror(errno);
  }
  init_buffered_writer(&writer, dest);

  void *masks = init_masks();

  uint32_t block_size_bytes = block_size / 8;
  uint64_t file_size, n_blocks;

  read_bytes(&reader, &n_blocks, sizeof(uint64_t));
  read_bytes(&reader, &file_size, sizeof(uint64_t));

  set_max_size(&writer, file_size);

  uint8_t aux[block_size];
  void *block;
  for (int i = 0; i < n_blocks; i++) {
    bit_slice slice = read(&reader, block_size);

    if (slice.size < block_size) {
      uint64_t remaining = block_size - slice.size;
      move(slice.base, aux, slice.bit_offset, 0, slice.size);

      uint64_t passed = slice.size;
      slice = read(&reader, remaining);
      move(slice.base, aux, slice.bit_offset, passed, slice.size);
      block = aux;
    } else {
      block = slice.base;
    }

    if (correction) {
      correct(block, block_size_bytes, exponent, masks);
    }

    unpack(block, &writer, block_size);
  }

  free_buffered_writer(&writer);
  free_buffered_reader(&reader);

  return NULL;
}

void correct(void *block, uint32_t block_size_bytes, uint32_t exponent,
             void *masks) {
  uint32_t sindrome = 0, i;

  // calculates the block syndrome
  for (i = 0; i < exponent; i++) {
    sindrome |= (masked_parity(block, (void *)(masks + i * MAX_BLOCK_SIZE),
                               block_size_bytes)
                 << i);
  }

  // Checks if the parity of the blocks needs correction
  if (parity(block, block_size_bytes)) {
    if (sindrome != 0) {
      flip_bit(block, sindrome - 1);
    }
  }
}

void unpack(void *block, buffered_writer *bw, uint32_t block_size) {
  int remaining = block_size - 2, offset = 2;
  bit_slice slice;
  slice.base = block;
  slice.size = 1;
  slice.bit_offset = 2;

  while (remaining > 0) {
    put_slice(bw, slice);

    offset += slice.size + 1;
    slice.base = block + offset / 8;
    slice.bit_offset = offset % 8;
    slice.size = (slice.size << 1) + 1;

    remaining -= slice.size + 1;
  }
}

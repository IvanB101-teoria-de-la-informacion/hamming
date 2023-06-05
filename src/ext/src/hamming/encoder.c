#include "hamming.h"

#include "../bitarr/bitarr.h"
#include "../buffer/buffer.h"

#include <errno.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void pack(buffered_reader *br, void *block, uint32_t block_size);

void protect(void *block, uint32_t block_size_bytes, uint32_t exponent,
             void *masks);

char *encode(char *path, char *dest, uint64_t block_size, uint64_t exponent) {
  buffered_reader reader;
  buffered_writer writer;

  init_buffered_reader(&reader, path);
  if (!reader.file) {
    return strerror(errno);
  }
  init_buffered_writer(&writer, dest);

  void *masks = init_masks();

  uint32_t info_bits, buff_offset, block_size_bytes = block_size / 8;
  uint64_t n_blocks;

  info_bits = block_size - exponent - 1;
  n_blocks = reader.file_size / info_bits;
  if (reader.file_size % info_bits) {
    n_blocks++;
  }

  put_bytes(&writer, (uint8_t *)&n_blocks, sizeof(uint64_t));
  put_bytes(&writer, (uint8_t *)&reader.file_size, sizeof(uint64_t));

  for (int i = 0; i < n_blocks; i++) {
    void *block = take_slice(&writer, block_size_bytes).base;

    pack(&reader, block, block_size);

    protect(block, block_size_bytes, exponent, masks);
  }

  free_buffered_reader(&reader);
  free_buffered_writer(&writer);

  return NULL;
}

/**
 * Sets or resets control bits of the block to protect it
 * @param block pointer to the block to be protected
 * @param block_size size of the block to be protected, in bytes
 * @param exponent to which to elevate 2 to obtain block_size
 * @param masks array of masks to check parity of diferent groups of bits
 */
void protect(void *block, uint32_t block_size_bytes, uint32_t exponent,
             void *masks) {
  int i, j = 1;
  // Control bits for hamming
  for (i = 0; i < exponent; i++) {
    if (masked_parity(block, (void *)(masks + i * MAX_BLOCK_SIZE),
                      block_size_bytes)) {
      flip_bit(block, j - 1);
    }

    j <<= 1;
  }

  // Parity check for entire block
  if (parity(block, block_size_bytes)) {
    flip_bit(block, j - 1);
  }
}

/**
 * Puts informations bits in block, leaving space for protection bits.
 */
void pack(buffered_reader *br, void *block, uint32_t block_size) {
  int remaining = block_size - 2, start_to = 2, size = 1;
  bit_slice slice;

  while (remaining > 0) {
    if (!(slice = read(br, size)).base) {
      return;
    }
    if (slice.size < size) {
      uint64_t diff = size - slice.size;

      move(slice.base, block, slice.bit_offset, start_to, slice.size);
      slice = read(br, diff);

      start_to += slice.size;

      if (!(slice = read(br, diff)).base) {
        return;
      }
    }

    move(slice.base, block, slice.bit_offset, start_to, slice.size);

    remaining -= size + 1;
    start_to += size + 1;
    size = (size << 1) + 1;
  }
}

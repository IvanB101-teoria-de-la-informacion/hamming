#include "buffer.h"

#include "../bitarr/bitarr.h"
#include "../common/common.h"

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

void init_buffered_writer(buffered_writer *bw, char *path) {
  bw->file = fopen(path, "wb");
  bw->buffer = (uint8_t *)malloc(BUFFER_SIZE);
  bw->index = 0;
  bw->size = 0;
  bw->written_bits = 0;
}

void set_max_size(buffered_writer *bw, uint64_t max) { bw->size = max; }

void free_buffered_writer(buffered_writer *bw) {
  uint64_t bytes_in_buffer = bw->index / 8 + bw->index % 8;
  fwrite(bw->buffer, 1, bytes_in_buffer, bw->file);

  fclose(bw->file);
  free((void *)bw->buffer);
}

byte_slice take_slice(buffered_writer *bw, uint64_t size) {
  uint64_t available = BUFFER_SIZE - bw->index / 8;

  byte_slice slice;
  slice.size = size;

  if (available < size) {
    fwrite(bw->buffer, 1, bw->index / 8, bw->file);
    bw->written_bits += bw->index;
    bw->index = 0;
  }

  slice.base = bw->buffer + bw->index / 8;

  bw->index += size * 8;
  bw->written_bits += size * 8;

  return slice;
}

void put_slice(buffered_writer *bw, bit_slice slice) {
  uint64_t available = (uint64_t)BUFFER_SIZE * 8 - bw->index;

  if (bw->size) {
    uint64_t remaining = bw->size - bw->written_bits;
    if (!remaining) {
      return;
    }
    slice.size = min(slice.size, remaining);
  }

  if (available < slice.size) {
    move(slice.base, bw->buffer, slice.bit_offset, bw->index, available);

    slice.base += available / 8;
    slice.bit_offset = (slice.bit_offset + available) % 8;
    slice.size -= available;

    fwrite(bw->buffer, 1, BUFFER_SIZE, bw->file);
    bw->written_bits += bw->index;
    bw->index = 0;
  }

  move(slice.base, bw->buffer, slice.bit_offset, bw->index, slice.size);
  bw->index += slice.size;
  bw->written_bits += slice.size;
}

void put_bytes(buffered_writer *bw, void *buffer, uint64_t size) {
  uint64_t available = BUFFER_SIZE - bw->index / 8, start_from = 0;

  if (available < size) {
    move(buffer, bw->buffer, start_from, bw->index, available * 8);

    fwrite(bw->buffer, 1, bw->index / 8, bw->file);
    bw->index = 0;

    size -= available;
    start_from = available * 8;
  }

  move(buffer, bw->buffer, start_from, bw->index, size * 8);
  bw->index += size * 8;
}

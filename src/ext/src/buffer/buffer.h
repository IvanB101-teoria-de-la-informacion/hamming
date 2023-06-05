#ifndef BUFFER
#define BUFFER
// Buffers of 256 MBs
#define BUFFER_SIZE 256 * 1024 * 1024

#include <stdint.h>
#include <stdio.h>

typedef struct {
  FILE *file;
  uint64_t file_size;
  uint64_t read_bits;
  uint8_t *buffer;
  uint64_t index;
  uint64_t last;
} buffered_reader;

typedef struct {
  FILE *file;
  uint64_t size;
  uint64_t written_bits;
  uint8_t *buffer;
  uint64_t index;
} buffered_writer;

typedef struct {
  uint8_t *base;
  uint8_t bit_offset;
  uint64_t size;
} bit_slice;

typedef struct {
  uint8_t *base;
  uint64_t size;
} byte_slice;

void init_buffered_reader(buffered_reader *br, char *path);

bit_slice read(buffered_reader *br, uint64_t amount);

void rewind_reader(buffered_reader *br);

void free_buffered_reader(buffered_reader *br);

int read_bytes(buffered_reader *br, void *buffer, uint64_t size);

void init_buffered_writer(buffered_writer *bw, char *path);

void set_max_size(buffered_writer *bw, uint64_t max);

void free_buffered_writer(buffered_writer *bw);

byte_slice take_slice(buffered_writer *bw, uint64_t size);

void put_slice(buffered_writer *bw, bit_slice slice);

void put_bytes(buffered_writer *bw, void *buffer, uint64_t size);

#endif

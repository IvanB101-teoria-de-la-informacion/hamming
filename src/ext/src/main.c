#include "buffer/buffer.h"
#include "common/common.h"
#include "hamming/hamming.h"
#include "huffman/huffman.h"
#include "noise/generator.h"

#include <math.h>
#include <stdint.h>
#include <stdio.h>

int main() {
  encode("../../../test/todo.txt", "../../../test/todo.HA1", 32, 5);
  decode("../../../test/todo.HA1", "../../../test/todo.DC1", 32, 5, 1);

  return 0;
}

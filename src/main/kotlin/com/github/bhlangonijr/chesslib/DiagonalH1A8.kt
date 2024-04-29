/*
 * Copyright 2017 Ben-Hur Carlos Vieira Langoni Junior
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.bhlangonijr.chesslib

/**
 * The representation of all possible *left-pointing* diagonals in a board. A diagonal is identified by two edge
 * squares and defines all the squares of the same color in between, the two edge squares included. A
 * *left-pointing* diagonal is tilted to the left, that is, the file of the edge square on the greater rank is
 * lower or equal than the file of the other edge square.
 *
 *
 * Note that corners `A1` and `H8` are special cases of diagonals of length 1.
 */
enum class DiagonalH1A8 {
    /**
     * The `A1-A1` diagonal.
     */
    A1_A1,

    /**
     * The `B1-A2` diagonal.
     */
    B1_A2,

    /**
     * The `C1-A3` diagonal.
     */
    C1_A3,

    /**
     * The `D1-A4` diagonal.
     */
    D1_A4,

    /**
     * The `E1-A5` diagonal.
     */
    E1_A5,

    /**
     * The `F1-A6` diagonal.
     */
    F1_A6,

    /**
     * The `G1-A7` diagonal.
     */
    G1_A7,

    /**
     * The `H1-A8` diagonal.
     */
    H1_A8,

    /**
     * The `B8-H2` diagonal.
     */
    B8_H2,

    /**
     * The `C8-H3` diagonal.
     */
    C8_H3,

    /**
     * The `D8-H4` diagonal.
     */
    D8_H4,

    /**
     * The `E8-H5` diagonal.
     */
    E8_H5,

    /**
     * The `F8-H6` diagonal.
     */
    F8_H6,

    /**
     * The `G8-H7` diagonal.
     */
    G8_H7,

    /**
     * The `H8-H8` diagonal.
     */
    H8_H8
}

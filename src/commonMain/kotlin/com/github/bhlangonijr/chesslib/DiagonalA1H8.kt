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
 * The representation of all possible *right-pointing* diagonals in a board. A diagonal is identified by two edge
 * squares and defines all the squares of the same color in between, the two edge squares included. A
 * *right-pointing* diagonal is tilted to the right, that is, the file of the edge square on the greater rank is
 * greater or equal than the file of the other edge square.
 *
 *
 * Note that corners `H1` and `A8` are special cases of diagonals of length 1.
 */
enum class DiagonalA1H8 {
    /**
     * The `A8-A8` diagonal.
     */
    A8_A8,

    /**
     * The `B8-A7` diagonal.
     */
    B8_A7,

    /**
     * The `C8-A6` diagonal.
     */
    C8_A6,

    /**
     * The `D8-A5` diagonal.
     */
    D8_A5,

    /**
     * The `E8-A4` diagonal.
     */
    E8_A4,

    /**
     * The `F8-A3` diagonal.
     */
    F8_A3,

    /**
     * The `G8-A2` diagonal.
     */
    G8_A2,

    /**
     * The `H8-A1` diagonal.
     */
    H8_A1,

    /**
     * The `B1-H7` diagonal.
     */
    B1_H7,

    /**
     * The `C1-H6` diagonal.
     */
    C1_H6,

    /**
     * The `D1-H5` diagonal.
     */
    D1_H5,

    /**
     * The `E1-H4` diagonal.
     */
    E1_H4,

    /**
     * The `F1-H3` diagonal.
     */
    F1_H3,

    /**
     * The `G1-H2` diagonal.
     */
    G1_H2,

    /**
     * The `H1-H1` diagonal.
     */
    H1_H1
}

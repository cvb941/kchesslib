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
 * The files in a board. A *file* is a column in the chessboard, and it is identified as a letter from `A` to
 * `H`.
 *
 *
 * Each value defines a single file, except for the special value [File.NONE] which represents no file.
 */
enum class File(
    /**
     * Returns the letter that identifies the file in chess notations.
     *
     * @return the letter used to represent the file
     */
    val notation: String
) {
    /**
     * The `A` file.
     */
    FILE_A("A"),

    /**
     * The `B` file.
     */
    FILE_B("B"),

    /**
     * The `C` file.
     */
    FILE_C("C"),

    /**
     * The `D` file.
     */
    FILE_D("D"),

    /**
     * The `E` file.
     */
    FILE_E("E"),

    /**
     * The `F` file.
     */
    FILE_F("F"),

    /**
     * The `G` file.
     */
    FILE_G("G"),

    /**
     * The `H` file.
     */
    FILE_H("H"),

    /**
     * Special value that represents no file in particular.
     */
    NONE("");

    /**
     * Returns the name of the file.
     *
     * @return the name of the file
     */
    fun value(): String {
        return name
    }

    companion object {
        val allFiles: Array<File> = entries.toTypedArray()

        /**
         * Returns a file given its name.
         *
         *
         * Same as invoking [File.valueOf].
         *
         * @param v name of the file
         * @return the file with the specified name
         * @throws IllegalArgumentException if the name does not correspond to any file
         */
        fun fromValue(v: String?): File {
            return valueOf(v!!)
        }
    }
}

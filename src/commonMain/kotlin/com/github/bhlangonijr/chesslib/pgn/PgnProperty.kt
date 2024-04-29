package com.github.bhlangonijr.chesslib.pgn

import com.github.bhlangonijr.chesslib.util.StringUtil
import java.util.regex.Pattern

/**
 * The definition of a Portable Game Notation (PGN) property, also known as *tag*.
 */
class PgnProperty {
    /**
     * The name of the PGN property.
     */
    var name: String? = null

    /**
     * The value of the PGN property.
     */
    var value: String? = null

    /**
     * Constructs an empty PGN property.
     */
    constructor()

    /**
     * Constructs an PGN property formed by a tag pair.
     *
     * @param name  the name of the property
     * @param value the value of the property
     */
    constructor(name: String?, value: String?) {
        this.name = name
        this.value = value
    }

    companion object {
        /**
         * The Byte Order Mark (BOM) that defines a big-endian representation of bytes.
         */
        const val UTF8_BOM: String = "\uFEFF"

        private val propertyPattern: Pattern = Pattern.compile("\\[.* \".*\"\\]")

        /**
         * Checks if the line of text contains a PGN property.
         *
         * @param line the line of text to check
         * @return `true` if the line is a PGN property
         */
        fun isProperty(line: String): Boolean {
            return propertyPattern.matcher(line).matches()
        }

        /**
         * Parses a line of text that contains a PGN property.
         *
         * @param line the line of text that includes the PGN property
         * @return the PGN property extracted from the line of text
         */
        fun parsePgnProperty(line: String): PgnProperty? {
            try {
                var l = line.replace("[", "")
                l = l.replace("]", "")
                l = l.replace("\"", "")

                return PgnProperty(
                    StringUtil.beforeSequence(l, " "),
                    StringUtil.afterSequence(l, " ")
                )
            } catch (e: Exception) {
                // do nothing
            }

            return null
        }
    }
}
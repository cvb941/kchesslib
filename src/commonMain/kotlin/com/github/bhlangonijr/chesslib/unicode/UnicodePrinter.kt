package com.github.bhlangonijr.chesslib.unicode

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Piece
import kotlinx.io.Sink
import kotlinx.io.writeString
import kotlin.jvm.JvmOverloads

/**
 * A printer class for conveniently printing boards using Unicode chess symbols in a reliable and consistent way.
 */
class UnicodePrinter
/**
 * Construct a printer using `System.out` as a print stream.
 *
 *
 * Same as invoking `new UnicodePrinter(System.out)`.
 *
 * @see UnicodePrinter.UnicodePrinter
 */ @JvmOverloads constructor(private val printStream: Sink) {
    /**
     * Construct a printer using the specified print stream.
     *
     * @param printStream the print stream where to output the board
     */

    /**
     * Prints the board using Unicode chess symbols.
     *
     * @param board the board to print
     */
    fun print(board: Board) {
        var row = 0
        for (p in board.boardToArray()) {
            if (p == Piece.NONE) {
                printStream.writeString(" ")
            } else {
                printStream.writeString(p.fanSymbol)
            }
            if (++row % 8 == 0) {
                printStream.writeString("\n")
            }
        }
    }
}

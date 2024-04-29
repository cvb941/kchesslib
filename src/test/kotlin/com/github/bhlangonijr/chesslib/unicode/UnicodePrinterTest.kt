package com.github.bhlangonijr.chesslib.unicode

import com.github.bhlangonijr.chesslib.Board
import com.github.bhlangonijr.chesslib.Square
import com.github.bhlangonijr.chesslib.move.Move
import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

class UnicodePrinterTest {
    @Test
    fun testPrintBoard() {
        val baos = ByteArrayOutputStream(256)
        val printer = UnicodePrinter(PrintStream(baos))

        val board = Board()
        board.doMove(Move(Square.E2, Square.E4))
        board.doMove(Move(Square.E7, Square.E5))
        printer.print(board)

        val repr = baos.toString(StandardCharsets.UTF_8)
        Assert.assertEquals(
            "Should be a white rook at pos 0\n$repr",
            '\u2656'.code.toLong(),
            repr[0].code.toLong()
        )
        Assert.assertEquals(
            "Should be a black rook at pos 63\n$repr",
            '\u265C'.code.toLong(),
            repr.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[7][7].code.toLong())
    }
}

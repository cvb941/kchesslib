package com.github.bhlangonijr.chesslib.util

/**
 * A utility class that is used to generate pseudorandom numbers bases on XOR and bit shifts operations.
 * [Xorshift random generators](https://en.wikipedia.org/wiki/Xorshift) are simple and efficient software
 * implementations of number generators.
 */
class XorShiftRandom
/**
 * Constructs a new random number generator.
 */ @JvmOverloads constructor(private var seed: Long = System.nanoTime()) {
    /**
     * Constructs a new random number generator using a custom long seed.
     *
     * @param seed the initial seed
     */

    /**
     * Returns the next pseudorandom long value from this random number generator's sequence.
     *
     * @return the next pseudorandom long value
     */
    fun nextLong(): Long {
        seed = seed xor (seed ushr 12)
        seed = seed xor (seed shl 25)
        seed = seed xor (seed ushr 27)
        return seed * 0x2545F4914F6CDD1DL
    }
}

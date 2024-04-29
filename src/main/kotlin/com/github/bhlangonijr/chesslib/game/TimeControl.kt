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
package com.github.bhlangonijr.chesslib.game

/**
 * A generic chess time control used in a game or a chess event. Depending on its type, the time control can be defined
 * by different components. It is also possible to convert the time control to and from its PGN representation.
 */
class TimeControl {
    /**
     * Returns the moves-per-time sub-time controls of this time controls.
     *
     * @return the moves-per-time time controls
     */
    val movePerTime: MutableList<MovePerTime> = ArrayList()
    /**
     * Returns the type of the time control.
     *
     * @return the time control type
     */
    /**
     * Sets the type of the time control.
     *
     * @param timeControlType the time control type to set
     */
    var timeControlType: TimeControlType? = null
    /**
     * Returns the half moves component of the time control.
     *
     * @return the number of half moves
     */
    /**
     * Sets the half moves component of the time control.
     *
     * @param halfMoves the number of half moves to set
     */
    var halfMoves: Int = 0
    private var milliseconds: Long = 0
    /**
     * Returns the time increment component of the time control, expressed in milliseconds.
     *
     * @return the time increment, in milliseconds
     */
    /**
     * Sets the time increment component of the time control, in milliseconds.
     *
     * @param increment the time increment to set
     */
    var increment: Long = 0
    /**
     * Returns the depth component of the time control.
     *
     * @return the depth
     */
    /**
     * Sets the depth component of the time control.
     *
     * @param depth the depth to set
     */
    var depth: Int = 0
    /**
     * Returns the nodes component of the time control.
     *
     * @return the nodes
     */
    /**
     * Sets the nodes component of the time control.
     *
     * @param nodes the nodes to set
     */
    var nodes: Long = 0

    /**
     * Returns the time component of the time control, expressed in milliseconds.
     *
     * @return the amount of time, in milliseconds
     */
    fun getMilliseconds(): Long {
        return milliseconds
    }

    /**
     * Sets the time component of the time control, in milliseconds.
     *
     * @param milliseconds the amount of time to set
     */
    fun setMilliseconds(milliseconds: Long) {
        this.milliseconds = milliseconds
    }

    /**
     * Returns the PGN notation that represents this time control. The format of the returned value depends on its type.
     *
     * @return the PGN representation of this time control
     */
    fun toPGNString(): String {
        if (timeControlType == TimeControlType.UNKNOW) {
            return "?"
        }
        val s = StringBuilder()
        if (halfMoves > 0) {
            s.append(halfMoves)
            s.append("/")
            s.append(getMilliseconds() / 1000)
        } else if (getMilliseconds() >= 0) {
            s.append(getMilliseconds() / 1000)
        }
        if (increment > 0) {
            s.append("+")
            s.append(increment / 1000)
        }
        if (movePerTime.size > 0) {
            for (mt in movePerTime) {
                s.append(":")
                s.append(mt.toPGNString())
            }
        }
        return s.toString()
    }

    /**
     * Returns a string representation of this time control.
     *
     * @return a string representation of this time control
     */
    override fun toString(): String {
        if (timeControlType == TimeControlType.UNKNOW) {
            return "Custom..."
        }
        val s = StringBuilder()
        if (halfMoves > 0) {
            s.append(halfMoves)
            s.append(" Moves / ")
            s.append(getMilliseconds() / 1000)
            s.append(" Sec")
        } else if (getMilliseconds() >= 0) {
            s.append(getMilliseconds() / 1000 / 60)
            s.append(" Min")
        }
        if (increment > 0) {
            s.append(" + ")
            s.append(increment / 1000)
            s.append(" Sec")
        }
        if (movePerTime.size > 0) {
            for (mt in movePerTime) {
                s.append(" : ")
                s.append(mt.toString())
            }
        }
        return s.toString()
    }

    companion object {
        /**
         * Parses a string that defines a time control and returns its representation.
         *
         *
         * Different formats are supported, as specified in [TimeControlType].
         *
         * @param s the string to parse
         * @return the parsed time control
         */
        fun parseFromString(s: String): TimeControl {
            var s = s
            val tc = TimeControl()
            s = s.replace("|", "+")
            if (s == "?" || s == "-") {
                tc.timeControlType = TimeControlType.UNKNOW
                return tc
            }

            if (s.indexOf(":") >= 0) {
                for (field in s.split(":")) {
                    parseTC(field, tc)
                }
            } else {
                parseTC(s, tc)
            }

            return tc
        }

        private fun parseTC(s: String, tc: TimeControl) {
            if (s.indexOf("/") >= 0) {
                tc.timeControlType = TimeControlType.MOVES_PER_TIME
                parseMT(s, tc)
            } else if (s.indexOf("+") >= 0) {
                tc.timeControlType = TimeControlType.TIME_BONUS
                parseTM(s, tc)
            } else {
                tc.timeControlType = TimeControlType.TIME_BONUS
                tc.milliseconds = (s.toInt() * 1000).toLong()
            }
        }

        private fun parseTM(s: String, tc: TimeControl) {
            val tm = s.split("\\+")
            tc.increment = (tm[1].toInt() * 1000).toLong()
            if (tm[0].indexOf("/") >= 0) {
                parseMT(tm[0], tc)
            } else {
                tc.setMilliseconds((tm[0].toInt() * 1000).toLong())
            }
        }

        private fun parseMT(s: String, tc: TimeControl) {
            val tm = s.split("/")
            val moves = tm[0].toInt()
            val milliseconds = tm[1].toInt() * 1000
            if (tc.halfMoves == 0) {
                tc.halfMoves = moves
                if (tm[1].indexOf("+") >= 0) {
                    parseTM(tm[1], tc)
                } else {
                    tc.setMilliseconds(milliseconds.toLong())
                }
            } else {
                tc.movePerTime.add(MovePerTime(moves, milliseconds.toLong()))
            }
        }
    }
}

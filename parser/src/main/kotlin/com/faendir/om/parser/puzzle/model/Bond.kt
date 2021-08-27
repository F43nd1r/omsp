package com.faendir.om.parser.puzzle.model

import com.faendir.om.parser.util.ZERO
import kotlin.experimental.and

data class Bond(var type: Set<BondType>, var start: HexIndex, var end: HexIndex)

enum class BondType(internal val flag: Byte) {
    NORMAL(0x1),
    RED(0x2),
    BLACK(0x4),
    YELLOW(0x8);

    companion object {

        fun fromBits(bits: Byte): Set<BondType> = values().filter { (bits and it.flag) != Byte.ZERO }.toSet()
    }
}

fun Set<BondType>.asBits() : Byte = map { it.flag }.sum().toByte()
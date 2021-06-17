package com.faendir.om.parser.puzzle.model

enum class Atom(internal val id: Byte) {
    SALT(0x1),
    AIR(0x2),
    EARTH(0x3),
    FIRE(0x4),
    WATER(0x5),
    QUICKSILVER(0x6),
    GOLD(0x7),
    SILVER(0x8),
    COPPER(0x9),
    IRON(0xa),
    TIN(0xb),
    LEAD(0xc),
    VITAE(0xd),
    MORS(0xe),
    REPEAT(0xf),
    QUINTESSENCE(0x10);

    companion object {
        fun fromByte(byte: Byte): Atom? {
            return values().find { it.id == byte }
        }
    }
}
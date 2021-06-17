package com.faendir.om.parser.puzzle.model

enum class Tool(internal val mask: Long) {
    ARM(0x00000001),
    MULTI_ARM(0x00000002),
    PISTON(0x00000004),
    TRACK(0x00000008),
    BONDER(0x00000100),
    UNBONDER(0x00000200),
    MULTI_BONDER(0x00000400),
    TRIPLEX_BONDER(0x00000800),
    CALCIFICATION(0x00001000),
    DUPLICATION(0x00002000),
    PROJECTION(0x00004000),
    PURIFICATION(0x00008000),
    ANIMISMUS(0x00010000),
    DISPOSAL(0x00020000),
    QUINTESSENCE_GLYPHS(0x00040000),
    INSTRUCTION_GRAB_AND_TURN(0x00400000),
    INSTRUCTION_DROP(0x00800000),
    INSTRUCTION_RESET(0x01000000),
    INSTRUCTION_REPEAT(0x02000000),
    INSTRUCTION_PIVOT(0x04000000),
    VAN_BERLOS_WHEEL(0x10000000);

    companion object {
        fun fromBits(bits: Long): Set<Tool> = values().filter { (bits and it.mask) != 0L}.toSet()
    }
}

fun Set<Tool>.asBits() : Long = map { it.mask }.sum()
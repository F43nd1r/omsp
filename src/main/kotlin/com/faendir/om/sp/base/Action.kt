package com.faendir.om.sp.base

enum class Action(val key: Char) {
    ROTATE_CLOCKWISE('R'),
    ROTATE_COUNTERCLOCKWISE('r'),
    EXTEND('E'),
    RETRACT('e'),
    GRAB('G'),
    DROP('g'),
    PIVOT_CLOCKWISE('P'),
    PIVOT_COUNTERCLOCKWISE('p'),
    FORWARD('A'),
    BACK('a'),
    REPEAT('C'),
    RESET('X'),
    NOOP('O'),
    EMPTY(' ');

    companion object {
        fun fromChar(key: Char): Action? {
            return values().find { it.key == key }
        }
    }
}
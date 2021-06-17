package com.faendir.om.parser.solution.model

data class Position(val x: Int, val y: Int)

infix fun Int.to(other: Int) : Position = Position(this, other)
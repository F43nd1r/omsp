package com.faendir.omsp.part

import com.faendir.omsp.base.Action

abstract class Part {
    abstract val name: String
    abstract var position: Position
    open var number: Int = 0
    open var rotation: Int = 0
    open var size: Int = 1
    open var steps: List<Action> = emptyList()
    open var index: Int = 0
}

typealias Position = Pair<Int, Int>
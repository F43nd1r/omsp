package com.faendir.omsp.solution

import com.faendir.omsp.part.Part

data class SolvedSolution(
    override var puzzle: String,
    override var name: String,
    var cycles: Int,
    var cost: Int,
    var area: Int,
    var instructions: Int,
    override var parts: List<Part> = emptyList()
) : Solution
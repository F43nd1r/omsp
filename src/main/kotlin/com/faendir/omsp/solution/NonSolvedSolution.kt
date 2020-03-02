package com.faendir.omsp.solution

import com.faendir.omsp.part.Part

data class NonSolvedSolution(
    override var puzzle: String,
    override var name: String,
    override var parts: List<Part> = emptyList()
) : Solution
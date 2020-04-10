package com.faendir.om.parser.solution.model

import com.faendir.om.parser.solution.model.part.Part

data class NonSolvedSolution(
    override var puzzle: String,
    override var name: String,
    override var parts: List<Part> = emptyList()
) : Solution
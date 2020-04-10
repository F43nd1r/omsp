package com.faendir.om.parser.solution.model

import com.faendir.om.parser.solution.model.part.Part

interface Solution {
    var puzzle: String
    var name: String
    var parts: List<Part>
}
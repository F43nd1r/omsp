package com.faendir.omsp.solution

import com.faendir.omsp.part.Part

interface Solution {
    var puzzle: String
    var name: String
    var parts: List<Part>
}
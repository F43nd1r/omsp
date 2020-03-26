package com.faendir.om.sp.solution

import com.faendir.om.sp.part.Part

interface Solution {
    var puzzle: String
    var name: String
    var parts: List<Part>
}
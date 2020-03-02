package com.faendir.om.sp

import com.faendir.om.sp.solution.NonSolvedSolution
import com.faendir.om.sp.solution.SolvedSolution
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import java.io.ByteArrayOutputStream
import java.io.InputStream

internal class SolutionParserTest {

    private fun getSolvedStream() : InputStream = javaClass.classLoader.getResourceAsStream("test-puzzle-c790345819993536-1.solution")!!
    private fun getNonSolvedStream() : InputStream = javaClass.classLoader.getResourceAsStream("test-puzzle-c790345819993536-2.solution")!!

    @org.junit.jupiter.api.Test
    fun parse() {
        val solution1 = SolutionParser.parse(getSolvedStream())
        assertThat(solution1, instanceOf(SolvedSolution::class.java))

        val solution2 = SolutionParser.parse(getNonSolvedStream())
        assertThat(solution2, instanceOf(NonSolvedSolution::class.java))
        //TODO: Assert solution content
    }

    @org.junit.jupiter.api.Test
    fun write() {
        val compare = getNonSolvedStream().readBytes()

        val solution1 = SolutionParser.parse(getSolvedStream())
        solution1.name = "NONSOLVED SOLUTION"
        val out1 = ByteArrayOutputStream()
        SolutionParser.write(solution1, out1)
        assertThat(out1.toByteArray(), equalTo(compare))

        val solution2 = SolutionParser.parse(getNonSolvedStream())
        val out2 = ByteArrayOutputStream()
        SolutionParser.write(solution2, out2)
        assertThat(out2.toByteArray(), equalTo(compare))
    }
}
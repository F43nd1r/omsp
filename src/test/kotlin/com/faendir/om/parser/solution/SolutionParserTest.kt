package com.faendir.om.parser.solution

import com.faendir.om.parser.solution.model.NonSolvedSolution
import com.faendir.om.parser.solution.model.SolvedSolution
import kotlinx.io.core.readBytes
import kotlinx.io.streams.asInput
import kotlinx.io.streams.asOutput
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.Test
import java.io.ByteArrayOutputStream

internal class SolutionParserTest {

    private fun getSolvedStream() = javaClass.classLoader.getResourceAsStream("test-puzzle-c790345819993536-1.solution")!!.asInput()
    private fun getNonSolvedStream()  = javaClass.classLoader.getResourceAsStream("test-puzzle-c790345819993536-2.solution")!!.asInput()

    @Test
    fun parse() {
        val solution1 = SolutionParser.parse(getSolvedStream())
        assertThat(solution1, instanceOf(SolvedSolution::class.java))

        val solution2 = SolutionParser.parse(getNonSolvedStream())
        assertThat(solution2, instanceOf(NonSolvedSolution::class.java))
        //TODO: Assert solution content
    }

    @Test
    fun write() {
        val compare = getNonSolvedStream().readBytes()

        val solution1 = SolutionParser.parse(getSolvedStream())
        solution1.name = "NONSOLVED SOLUTION"
        val out1 = ByteArrayOutputStream()
        SolutionParser.write(solution1, out1.asOutput())
        assertThat(out1.toByteArray(), equalTo(compare))

        val solution2 = SolutionParser.parse(getNonSolvedStream())
        val out2 = ByteArrayOutputStream()
        SolutionParser.write(solution2, out2.asOutput())
        assertThat(out2.toByteArray(), equalTo(compare))
    }
}
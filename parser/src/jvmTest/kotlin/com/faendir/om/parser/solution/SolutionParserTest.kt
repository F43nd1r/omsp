package com.faendir.om.parser.solution

import com.faendir.om.parser.solution.model.NonSolvedSolution
import com.faendir.om.parser.solution.model.SolvedSolution
import okio.buffer
import okio.sink
import okio.source
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream

internal class SolutionParserTest {

    private fun resource(name: String) = javaClass.classLoader.getResourceAsStream(name)!!.source().buffer()

    private fun getSolvedStream() = resource("test-puzzle-c790345819993536-1.solution")
    private fun getNonSolvedStream()  = resource("test-puzzle-c790345819993536-2.solution")

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
        val compare = getNonSolvedStream().readByteArray()

        val solution1 = SolutionParser.parse(getSolvedStream())
        solution1.name = "NONSOLVED SOLUTION"
        val out1 = ByteArrayOutputStream()
        SolutionParser.write(solution1, out1.sink().buffer())
        assertThat(out1.toByteArray(), equalTo(compare))

        val solution2 = SolutionParser.parse(getNonSolvedStream())
        val out2 = ByteArrayOutputStream()
        SolutionParser.write(solution2, out2.sink().buffer())
        assertThat(out2.toByteArray(), equalTo(compare))
    }

    @Test
    fun `should rewrite solution name correctly`() {
        val tournament = SolutionParser.parse(resource("RADIO_RECEIVERS_tournament.solution"))
        val steamBytes = resource("RADIO_RECEIVERS_steam.solution").readByteArray()

        tournament.puzzle = "w2788067896"

        val out1 = ByteArrayOutputStream()
        SolutionParser.write(tournament, out1.sink().buffer(), writeSolved = true)
        assertThat(out1.toByteArray(), equalTo(steamBytes))

    }
}
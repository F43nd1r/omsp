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
    private fun getDLCStream()  = resource("sample-c737537958035598-1.solution")

    @Test
    fun `should parse solved solution`() {
        val solution = SolutionParser.parse(getSolvedStream())
        assertThat(solution, instanceOf(SolvedSolution::class.java))
        //TODO: Assert solution content
    }

    @Test
    fun `should parse non-solved solution`() {
        val solution = SolutionParser.parse(getNonSolvedStream())
        assertThat(solution, instanceOf(NonSolvedSolution::class.java))
    }

    @Test
    fun `should parse DLC solution`() {
        val solution = SolutionParser.parse(getDLCStream())
        assertThat(solution, instanceOf(SolvedSolution::class.java))
    }

    @Test
    fun `should write solved solution as non-solved by default`() {
        val compare = getNonSolvedStream().readByteArray()

        val solution = SolutionParser.parse(getSolvedStream())
        solution.name = "NONSOLVED SOLUTION"
        val out = ByteArrayOutputStream()
        SolutionParser.write(solution, out.sink().buffer())
        assertThat(out.toByteArray(), equalTo(compare))
    }

    @Test
    fun `should write non-solved solution identically`() {
        val compare = getNonSolvedStream().readByteArray()

        val solution = SolutionParser.parse(getNonSolvedStream())
        val out = ByteArrayOutputStream()
        SolutionParser.write(solution, out.sink().buffer())
        assertThat(out.toByteArray(), equalTo(compare))
    }

    @Test
    fun `should write DLC solution identically`() {
        val compare = getDLCStream().readByteArray()

        val solution = SolutionParser.parse(getDLCStream())
        val out = ByteArrayOutputStream()
        SolutionParser.write(solution, out.sink().buffer(), writeSolved = true)
        assertThat(out.toByteArray(), equalTo(compare))
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
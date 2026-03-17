package com.faendir.om.parser.puzzle

import com.faendir.om.parser.puzzle.model.Puzzle
import okio.buffer
import okio.sink
import okio.source
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test
import java.io.ByteArrayOutputStream

internal class PuzzleParserTest {

    private fun getProductionStream() = javaClass.classLoader.getResourceAsStream("test_production.puzzle")!!.source().buffer()
    private fun getDLCStream() = javaClass.classLoader.getResourceAsStream("c737537958035598.puzzle")!!.source().buffer()

    @Test
    fun `should parse production puzzle`() {
        val puzzle = PuzzleParser.parse(getProductionStream())
        MatcherAssert.assertThat(puzzle, Matchers.instanceOf(Puzzle::class.java))
        //TODO: Assert puzzle content
    }

    @Test
    fun `should parse DLC puzzle`() {
        val puzzle = PuzzleParser.parse(getDLCStream())
        MatcherAssert.assertThat(puzzle, Matchers.instanceOf(Puzzle::class.java))
    }

    @Test
    fun `should write production puzzle identically`() {
        val compare = getProductionStream().readByteArray()

        val puzzle = PuzzleParser.parse(getProductionStream())
        val out = ByteArrayOutputStream()
        PuzzleParser.write(puzzle, out.sink().buffer())
        MatcherAssert.assertThat(out.toByteArray(), Matchers.equalTo(compare))
    }

    @Test
    fun `should write DLC puzzle identically`() {
        val compare = getDLCStream().readByteArray()

        val puzzle = PuzzleParser.parse(getDLCStream())
        val out = ByteArrayOutputStream()
        PuzzleParser.write(puzzle, out.sink().buffer())
        MatcherAssert.assertThat(out.toByteArray(), Matchers.equalTo(compare))
    }
}
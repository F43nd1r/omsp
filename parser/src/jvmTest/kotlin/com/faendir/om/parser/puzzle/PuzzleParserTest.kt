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
    fun parse() {
        val puzzle = PuzzleParser.parse(getProductionStream())
        MatcherAssert.assertThat(puzzle, Matchers.instanceOf(Puzzle::class.java))

        val puzzle2 = PuzzleParser.parse(getDLCStream())
        MatcherAssert.assertThat(puzzle2, Matchers.instanceOf(Puzzle::class.java))
        //TODO: Assert puzzle content
    }

    @Test
    fun write() {
        val compare = getProductionStream().readByteArray()

        val puzzle = PuzzleParser.parse(getProductionStream())
        val out = ByteArrayOutputStream()
        PuzzleParser.write(puzzle, out.sink().buffer())
        MatcherAssert.assertThat(out.toByteArray(), Matchers.equalTo(compare))

        val compare2 = getDLCStream().readByteArray()

        val puzzle2 = PuzzleParser.parse(getDLCStream())
        val out2 = ByteArrayOutputStream()
        PuzzleParser.write(puzzle2, out2.sink().buffer())
        MatcherAssert.assertThat(out2.toByteArray(), Matchers.equalTo(compare2))
    }
}
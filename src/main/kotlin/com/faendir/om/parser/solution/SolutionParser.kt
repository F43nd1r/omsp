package com.faendir.om.parser.solution

import com.faendir.om.parser.csharp.CSharpBinaryReader
import com.faendir.om.parser.csharp.CSharpBinaryWriter
import com.faendir.om.parser.solution.model.*
import com.faendir.om.parser.solution.model.part.*
import kotlinx.io.core.Input
import kotlinx.io.core.Output
import kotlinx.io.core.use

object SolutionParser {

    fun parse(input: Input): Solution {
        CSharpBinaryReader(input).use { reader ->
            if (reader.readInt() != 7) throw IllegalArgumentException("Input is not an Opus Magnum solution.")
            val puzzle = reader.readString()
            val name = reader.readString()
            val solution = if (reader.readInt() == 0) {
                NonSolvedSolution(puzzle, name)
            } else {
                reader.readInt()
                val cycles = reader.readInt()
                reader.readInt()
                val cost = reader.readInt()
                if (reader.readInt() != 2) throw IllegalArgumentException("Malformed solution.")
                val area = reader.readInt()
                if (reader.readInt() != 3) throw IllegalArgumentException("Malformed solution.")
                val instructions = reader.readInt()
                SolvedSolution(puzzle, name, cycles, cost, area, instructions)
            }
            solution.parts = reader.readList {
                val partName = readString()
                if (readByte() != 1.toByte()) throw IllegalArgumentException("Malformed solution.")
                val position = readPosition()
                val size = readInt()
                val rotation = readInt()
                val index = readInt()
                var step = 0
                val steps = (0 until readInt()).flatMap {
                    val pos = readInt()
                    val action = Action.fromChar(readChar())!!
                    val result = ((step until pos).map { Action.EMPTY }) + action
                    step = pos + 1
                    result
                }
                val trackPositions = if (partName == "track") readList { readPosition() } else null

                val number = readInt() + 1
                val pipeDetails = if (partName == "pipe") readInt() to readList { readPosition() } else null
                ArmType.fromString(partName)?.let { Arm(number, position, rotation, size, steps, it) }
                        ?: pipeDetails?.let { Conduit(position, rotation, it.first, it.second) }
                        ?: trackPositions?.let { Track(position, it) }
                        ?: IOType.fromString(partName)?.let { IO(index, position, rotation, it) }
                        ?: GlyphType.fromString(partName)?.let { Glyph(position, rotation, it) }
                        ?: throw IllegalArgumentException("$partName is not a valid part type.")
            }
            return solution
        }
    }

    private fun CSharpBinaryReader.readPosition() = Position(readInt(), readInt())

    fun write(solution: Solution, output: Output) {
        CSharpBinaryWriter(output).use { writer ->
            writer.write(7)
            writer.write(solution.puzzle)
            writer.write(solution.name)
            // always write solutions as non-verified
            writer.write(0)
            writer.write(solution.parts) { part ->
                write(part.name)
                write(1.toByte())
                write(part.position)
                write(part.size)
                write(part.rotation)
                write(part.index)
                write(part.steps.count { it != Action.EMPTY })
                var step = 0
                part.steps.forEach { action ->
                    if (action != Action.EMPTY) {
                        write(step)
                        write(action.key)
                    }
                    step++
                }
                if (part is Track) {
                    write(part.positions) { write(it) }
                }
                write(part.number - 1)
                if (part is Conduit) {
                    write(part.id)
                    write(part.positions) { write(it) }
                }
            }
        }
    }

    private fun CSharpBinaryWriter.write(position: Position) {
        write(position.x)
        write(position.y)
    }
}
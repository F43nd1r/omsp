package com.faendir.om.sp

import com.faendir.om.sp.base.Action
import com.faendir.om.sp.csharp.CSharpBinaryReader
import com.faendir.om.sp.csharp.CSharpBinaryWriter
import com.faendir.om.sp.part.*
import com.faendir.om.sp.solution.NonSolvedSolution
import com.faendir.om.sp.solution.Solution
import com.faendir.om.sp.solution.SolvedSolution
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
            val partCount = reader.readInt()
            solution.parts = (0 until partCount).map {
                val partName = reader.readString()
                if (reader.readByte() != 1.toByte()) throw IllegalArgumentException("Malformed solution.")
                val position : Position = reader.readInt() to reader.readInt()
                val size = reader.readInt()
                val rotation = reader.readInt()
                val index = reader.readInt()
                val stepCount = reader.readInt()
                var step = 0
                val steps = (0 until stepCount).flatMap {
                    val pos = reader.readInt()
                    val action = Action.fromChar(reader.readChar())!!
                    val result = ((step until pos).map { Action.EMPTY }) + action
                    step = pos + 1
                    result
                }
                val trackPositions = if (partName == "track") (0 until reader.readInt()).map { reader.readInt() to reader.readInt() } else null

                val number = reader.readInt() + 1
                val pipeDetails =  if(partName == "pipe") reader.readInt() to (0 until reader.readInt()).map { reader.readInt() to reader.readInt() } else null
                ArmType.fromString(partName)?.let { Arm(number, position, rotation, size, steps, it) }
                        ?: pipeDetails?.let { Conduit(position, it.first, it.second) }
                        ?: trackPositions?.let { Track(position, it) }
                        ?: IOType.fromString(partName)?.let { IO(index, position, rotation, it) }
                        ?: GlyphType.fromString(partName)?.let { Glyph(position, rotation, it) }
                        ?: throw IllegalArgumentException("$partName is not a valid part type.")
            }
            return solution
        }
    }

    fun write(solution: Solution, output: Output) {
        CSharpBinaryWriter(output).use { writer ->
            writer.write(7)
            writer.write(solution.puzzle)
            writer.write(solution.name)
            // always write solutions as non-verified
            writer.write(0)
            writer.write(solution.parts.size)
            solution.parts.forEach { part ->
                writer.write(part.name)
                writer.write(1.toByte())
                writer.write(part.position.x)
                writer.write(part.position.y)
                writer.write(part.size)
                writer.write(part.rotation)
                writer.write(part.index)
                writer.write(part.steps.count { it != Action.EMPTY })
                var step = 0
                part.steps.forEach { action ->
                    if (action != Action.EMPTY) {
                        writer.write(step)
                        writer.write(action.key)
                    }
                    step++
                }
                if (part is Track) {
                    writer.write(part.positions.size)
                    part.positions.forEach {
                        writer.write(it.x)
                        writer.write(it.y)
                    }
                }
                writer.write(part.number - 1)
                if(part is Conduit) {
                    writer.write(part.id)
                    writer.write(part.positions.size)
                    part.positions.forEach {
                        writer.write(it.x)
                        writer.write(it.y)
                    }
                }
            }
        }
    }
}
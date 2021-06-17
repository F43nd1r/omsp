package com.faendir.om.parser.puzzle

import com.faendir.om.parser.csharp.CSharpBinaryReader
import com.faendir.om.parser.csharp.CSharpBinaryWriter
import com.faendir.om.parser.puzzle.model.*
import okio.BufferedSink
import okio.BufferedSource
import okio.use

object PuzzleParser {

    fun parse(input: BufferedSource): Puzzle {
        CSharpBinaryReader(input).use { reader ->
            if (reader.readInt() != 3) throw IllegalArgumentException("Input is not an Opus Magnum puzzle.")
            val name = reader.readString()
            val creator = reader.readLong()
            val tools = Tool.fromBits(reader.readLong())
            val inputs = reader.readList { readMolecule() }
            val outputs = reader.readList { readMolecule() }
            val outputScale = reader.readInt()
            val productionInfo = if (reader.readBoolean()) {
                val shrinkLeft = reader.readBoolean()
                val shrinkRight = reader.readBoolean()
                val isolateInputsFromOutputs = reader.readBoolean()
                val cabinets = reader.readList { Cabinet(readHexIndex(), CabinetType.fromString(readString())!!) }
                val conduits = reader.readList { Conduit(readHexIndex(), readHexIndex(), readList { readHexIndex() }) }
                val vials = reader.readList { Vial(readHexIndex(), if (readBoolean()) VialStyle.TOP else VialStyle.BOTTOM, readInt()) }
                ProductionInfo(shrinkLeft, shrinkRight, isolateInputsFromOutputs, cabinets, conduits, vials)
            } else {
                null
            }
            return Puzzle(name, creator, tools, inputs, outputs, outputScale, productionInfo)
        }
    }

    private fun CSharpBinaryReader.readMolecule(): Molecule {
        val atoms = readList { Atom.fromByte(readByte())!! to readHexIndex() }
        val bonds = readList { Bond(BondType.fromBits(readByte()), readHexIndex(), readHexIndex()) }
        return Molecule(atoms, bonds)
    }

    private fun CSharpBinaryReader.readHexIndex() = HexIndex(readByte(), readByte())


    fun write(puzzle: Puzzle, output: BufferedSink) {
        CSharpBinaryWriter(output).use { writer ->
            writer.write(3)
            writer.write(puzzle.name)
            writer.write(puzzle.creator)
            writer.write(puzzle.tools.asBits())
            writer.write(puzzle.inputs) { writeMolecule(it) }
            writer.write(puzzle.outputs) { writeMolecule(it) }
            writer.write(puzzle.outputScale)
            writer.write(puzzle.productionInfo != null)
            puzzle.productionInfo?.let { info ->
                writer.write(info.shrinkLeft)
                writer.write(info.shrinkRight)
                writer.write(info.isolateInputsFromOutputs)
                writer.write(info.cabinets) {
                    write(it.hexIndex)
                    write(it.type.id)
                }
                writer.write(info.conduits) {
                    write(it.hexIndexA)
                    write(it.hexIndexB)
                    write(it.offsets) { hexIndex -> write(hexIndex) }
                }
                writer.write(info.vials) {
                    write(it.hexIndex)
                    write(it.style == VialStyle.TOP)
                    write(it.count)
                }
            }
        }
    }

    private fun CSharpBinaryWriter.writeMolecule(molecule: Molecule) {
        write(molecule.atoms) {
            write(it.first.id)
            write(it.second)
        }
        write(molecule.bonds) {
            write(it.type.asBits())
            write(it.start)
            write(it.end)
        }
    }

    private fun CSharpBinaryWriter.write(h: HexIndex) {
        write(h.x)
        write(h.y)
    }
}
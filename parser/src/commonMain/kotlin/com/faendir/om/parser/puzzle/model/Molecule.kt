package com.faendir.om.parser.puzzle.model

data class Molecule(var atoms: List<Pair<Atom, HexIndex>>, var bonds: List<Bond>) {

}
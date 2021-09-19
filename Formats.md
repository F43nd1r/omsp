# File formats

reverse engineered, and just as far as I understand it

Data Types:
- INT: 32-Bit Integer (little endian)
- LONG: 64-Bit Integer (little endian)
- BYTE: just a standard byte
- CHAR: byte interpreted as character
- BOOLEAN: byte interpreted as boolean (0 for false)
- STRING: UTF-8 Bytes prefixed with length as variable-length Integer (little endian), see also [C# Binary Reader](https://docs.microsoft.com/en-us/dotnet/api/system.io.binaryreader.readstring?view=net-5.0)
- POSITION: `INT` x followed by `INT` y
- BYTE_POSITION: `BYTE` x followed by `BYTE` y
- LIST: list length as `INT` followed by content

## Solution file format
```
INT: solution format, currently 7
STRING: puzzle file name
STRING: solution name
INT: 1 for solved, 0 for not solved
if solved {
    INT: always 0
    INT: cycles
    INT: always 1
    INT: cost
    INT: always 2
    INT: area
    INT: always 3
    INT: instructions
}
LIST: parts {
    STRING: part name (arm1, arm2, arm3, arm6, piston, baron)
    BYTE: always 1
    POSITION: part position
    INT: arm size
    INT: part rotation
    INT: input/output index
    LIST: instructions {
        INT: position
        CHAR: action (R, r, E, e, G, g, P, p, A, a, c, x, O)
    }
    if part is track {
        LIST: track parts {
            POSITION: relative track part position
        }
    }
    INT: arm number (as shown in game - 1)
    if part is conduit {
        INT: conduit id (start at 100 I think?)
        LIST: conduit parts {
            POSITION: relative conduit part position
        }
    }
}
```

## Puzzle file format

```
INT: puzzle format, currently 3
STRING: puzzle name
LONG: creator id (steam id?)
LONG: bitfield for allowed glyphs
LIST: inputs {
    LIST: atoms {
        BYTE: type
        BYTE_POSITION: atom position
    }
    LIST: bonds {
        BYTE: type
        BYTE_POSITION: from
        BYTE_POSITION: to
    }
}
LIST: outputs {
    LIST: atoms {
        BYTE: type
        BYTE_POSITION: atom position
    }
    LIST: bonds {
        BYTE: type
        BYTE_POSITION: from
        BYTE_POSITION: to
    }
}
INT: output scaling (multiplied by 6 in game)
BOOLEAN: is production
if is production {
     BOOLEAN: shrink left, has something to do with the background I think?
     BOOLEAN: shrink right
     BOOLEAN: force different chambers for inputs and outputs
     LIST: chambers {
        BYTE_POSITION: chamber position
        STRING: chamber type (Small, SmallWide, SmallWider, Medium, MediumWide, Large)
     }
     LIST: conduits {
        BYTE_POSITION: Conduit position
        BYTE_POSITION: Other conduit position
        List: conduit parts {
            BYTE_POSITION: relative part position
        }
     }
     LIST: vials {
        BYTE_POSITION: vial position
        BOOLEAN: true for top style, false for bottom style
        INT: type (0-3)
     }
}
```

glyph type bitfield:
```
2^0: arm
2^1: multiarm
2^2: piston
2^3: track
2^7: bonder
2^8: unbonder
2^9: multibonder
2^10: triplex bonder
2^11: calcification
2^12: duplication
2^13: projection
2^14: purification
2^15: animismus
2^16: disposal
2^17: quintessence glyphs
2^22: grab and rotation
2^23: drop
2^24: reset
2^25: repeat
2^26: pivot
2^28: berlos wheel
```

atom type:
```
1: salt
2: air
3: earth
4: fire
5: water
6: quicksilver
7: gold
8: silver
9: copper
10: iron
11: tin
12: lead
13: vitae
14: mors
15: repeat (infinites)
16: quintessence
```

bond type bitfield:
```
2^0: normal
2^1: red
2^2: black
2^3: yellow
```
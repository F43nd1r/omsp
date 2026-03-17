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


### Understanding positions:
All possible `BYTE_POSITION`s:
```
                fc04    fd04    fe04    ff04    0004
            fc03    fd03    fe03    ff03    0003    0103
        fc02    fd02    fe02    ff02    0002    0102    0202 
    fc01    fd01    fe01    ff01    0001    0101    0201    0301 
fc00    fd00    fe00    ff00    0000    0100    0200    0300    0400
    fdff    feff    ffff    00ff    01ff    02ff    03ff    04ff
        fefe    fffe    00fe    01fe    02fe    03fe    04fe
            fffd    00fd    01fd    02fd    03fd    04fd
                00fc    01fc    02fc    03fc    04fc
```
`POSITION`s follow the same format but with integers instead of bytes.

## Solution file format
```
INT: solution format, currently 7
STRING: puzzle file name
STRING: solution name
INT: 4 for solved, 0 for not solved (probably number of metrics in file)
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
        CHAR: action (see below)
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

instructions:
```
R: rotate clockwise
r: rotate counterclockwise
E: extend
e: retract
G: grab
g: drop
P: pivot clockwise
p: pivot counterclockwise
A: move +
a: move -
c: repeat
x: reset
O: noop
```


## Puzzle file format

```
INT: puzzle format, currently 3
STRING: puzzle name
LONG: creator id (steam id)
LONG: bitfield for allowed things (see below)
LIST: inputs {
    LIST: atoms {
        BYTE: type (see below)
        BYTE_POSITION: atom position
    }
    LIST: bonds {
        BYTE: type (see below)
        BYTE_POSITION: from
        BYTE_POSITION: to
    }
}
LIST: outputs (same as inputs) {
    LIST: atoms {
        BYTE: type (see below)
        BYTE_POSITION: atom position
    }
    LIST: bonds {
        BYTE: type (see below)
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

allowed things type bitfield:
```
2^0: arm
2^1: multiarm
2^2: piston
2^3: track
2^8: bonder
2^9: unbonder
2^10: multibonder
2^11: triplex bonder
2^12: calcification
2^13: duplication
2^14: projection
2^15: purification
2^16: animismus
2^17: disposal
2^18: quintessence glyphs
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

# Sources

 - [Puzzle file spec v3](https://steamcommunity.com/sharedfiles/filedetails/?id=1185668197)
 - [C# BinaryReader](https://docs.microsoft.com/en-us/dotnet/api/system.io.binaryreader?view=net-5.0)
 - [fazzone's solution parser](https://github.com/fazzone/opus/blob/master/blobs/src/blobs/codec/solution.cljc) (caution, also reverse-engineered. They were wrong about a few fields)
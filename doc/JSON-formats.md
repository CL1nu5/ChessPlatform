# JSON formats:

## Chess board
<hr>

Format:
```json
[
  {
    "piece": "(pawn, rook, ...)",
    "colour": "(white, black)",
    "position": "(a2, d3, h8, ...)"
  },
  {
    "piece": "(pawn, rook, ...)",
    "colour": "(white, black)",
    "position": "(a2, d3, h8, ...)"
  }
]
```
Example:
```json
[
  {
    "piece": "pawn",
    "colour": "white",
    "position": "a2"
  },
  {
    "piece": "pawn",
    "colour": "white",
    "position": "b2"
  }
]
```
## Move
<hr>

Format:
```json
[
  {
    "moving-piece": "2,4 or 1,4, ...",
    "captured-piece": "2,4 or 1,4, ...",
    "postponed-position": "2,4 or 1,4, ...",
    "connected-move": 
    {
      "moving-piece": "2,4 or 1,4, ...",
      "captured-piece": "",
      "postponed-position": "2,4 or 1,4, ...",
      "connected-move": ""
    }
  }
]
```

## Team
<hr>

```json
[
  {
    "colour": "(white, black)"
  }
]

```
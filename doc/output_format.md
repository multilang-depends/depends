# Sample output:

    {
      "schemaVersion": "1.0",
      "name": "sample-sdsm",
      "variables": [
        "A.java",
        "B.java"
      ],
      "cells": [
        {
          "src": 0,
          "dest": 1,
          "values": {
            "Call": 1,
            "Import": 2,
            "Create": 3
          }
        }
      ]
    }
      
# Important concept

Dependency could be see as a square matrix. The variables are the column/row variables, and
cells are the matrix cell. 
 
## Variables
*Variables* are all files/methods or any variables under analysis.

## Cells
*Cells* contains all the relations of variable pair. The number is the *variable* index (0-based).

For each cell (src->dest pair), the relations are outputted in *values*.

Each value contains the *dependency type* and *weight*. 



 
# Compilers Notes

## Important Changes
* Left Factoring - upload a txt file saying the productions that have been changed and why (Left recursive)
    * Typelist
    * Ifstat
    * Expr
    * Returnstat
    * Bool
    * Fact
    * Term
    *(Any rule that starts with itself)

## Questions
[ X ] **`Integer.parse("10101.1")` - does it trunkate or error?**

    It will error.

[ X ] **Ask dan about == and != for reals - how many digits should we check for**

    Check Grammar Spec

[  ] **Can you init arrays inline? E.g.** 

	arrays:
		comp3290: marks is array[10] of mark

    Waiting

[  ] **When copying whole arrays, should it be a shallow or deep copy?** 

    Waiting



[  ] **Are Booleans initialised to anything?** 

    Waiting


## Facts
### General
* Real variables will ONLY store real values (similarly for other primitives)
* Variables must be defined before use
* Named typed equivalence - order of var, name of var, types of var. Then Student A = Teacher B 
* Handling negative exponents: `x^-2 === x^(0-2) === 1/x^(0+2)`

### Structs
* No functions are allowed in structures
* No Arrays allowed in structs (Only primitives)
* If declaring a single struct, it must be defined as an array of size 1

### Arrays
* Structs must be declared in arrays
* Arrays must only contain structs, cannot contain primitives
* Arrays start at 0
* You can copy whole arrays, but not individual elements 
    * `array1 = array2` only if they are the same type

### Looping Structures
#### For Loops
* For loops are actually while loops
* For loop variables used must be declared previously 
* `for(<init>+); <exitcondition>+)` - Can have multiple initialisers and exit conditions

#### Repeat Loops
* Repeat loops can have optional conditions

### Functions
* Structs CAN be used INSIDE functions, but they cannot be passed to a function or returned from a function
* Returns can return a variable or an expression
* Cannot have nested functions


## Things to keep in mind for future parts
### Syntactics


### Semantics
* Divide by zero check
* Integer too large check
* Attempting parse to primitives (floats and ints)
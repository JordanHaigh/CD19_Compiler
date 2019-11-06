package CD19.CodeGen;

/**
 * OpCodes Enum. Keeps log of all opcode types in the code gen section.
 * Has methods that map opcode name to value
 *
 * @author Jordan Haigh c3256730
 * @since 6/11/19
 */
public enum OpCodes {
    HALT(0), //Stop execution
    NOOP(1), //Do nothing
    TRAP(2), //Stop Execution – Abort
    ZERO(3), //Push the INTG zero onto the stack
    FALSE(4), //Push the BOOL false onto the stack
    TRUE(5), //Push the BOOL true onto the stack


    TYPE(7), //1 x arithmetic  - swap types INTG >> FLOT, FLOT>>INTG
    ITYPE(8),// 1 x arithmetic -  set type for TOS to INTG
    FTYPE(9), // 1 x arithmetic - set type for TOS to FLOT

    ADD(11), //2 x arithmetic  - add them, push the result
    SUB(12), //2 x arithmetic -  subtract first popped from second, push
    MUL(13), //2 x arithmetic -  multiply them, push the result
    DIV(14), //2 x arithmetic -  divide second popped by first, push
    REM(15), //2 x Integer -  second popped MOD first, push

    POW(16), //1 x arithmetic; 1 x Integer  - raise second popped to power of first, push
    CHS(17), //1 x arithmetic - push negative of operand popped
    ABS(18), //1 x arithmetic -  push absolute value of operand popped

    GT(21), //1 x arithmetic -  if TOS > 0, push true else false
    GE(22), //1 x arithmetic -  if TOS >= 0, push true else false
    LT(23), //1 x arithmetic - if TOS < 0, push true else false
    LE(24), //1 x arithmetic - if TOS <= 0, push true else false

    EQ(25), //1 x Integer if TOS == 0, push true else false
                 //1 x Float if | TOS | < 0.000001, push true else false
    NE(26), //1 x Integer if TOS != 0, push true else false
                // 1 x Float if | TOS | > 0.000001, push true else false

    AND(31), //2 x Boolean  - if (popped) (b1 & b2), push true else false
    OR(32), //2 x Boolean - if (b1 | b2), push true else false
    XOR(33), //2 x Boolean - if (b1 exclusive-or b2), push true else false
    NOT(34), //1 x Boolean - push logical negation of popped item


    BT(35), //1 bool. 1 addr  - if boolean is true then place address into pc
    BF(36), //1 bool, 1 addr -  if boolean is false then place address into pc
    BR(37), //1 x Address - place address into pc


    L(40), //1 x Address - pop address, push the value at this address
    LB(41), //1 x Instr Byte push 8-bits, sign extended to 64, as INTG
    LH(42), //2 x Instr Byte push 16-bits, sign extended to 64, as INTG
    ST(43), //1 arith/bool | 1 addr - store value (first) popped at address popped

    STEP(51), //step the SP one word (tagged UNDF)
    ALLOC(52), //1 x Integer - step the sp by this many (popped) words
    ARRAY(53), //1 int, 1 addr - construct descriptor and step sp by size
    INDEX(54), //1 int, 1 desc - construct address of elt (int) in array (addr)
    SIZE(55), //1 descriptor - pop descriptor, extract & push array size
    DUP(56), //push a duplicate of the top item on the stack


    READF(60), //input floating pt value, push to stack
    READI(61), //input integer value, push to stack
    VALPR(62), //1 x arithmetic -  print a space and then the popped value
    STRPR(63), //1 x Address - print string const at popped address
    CHRPR(64), //1 x Address -  print character const at popped address
    NEWLN(65), //terminate the current line of outpu
    SPACE(66), //print a single space character

    RVAL(70), //1 x arith/bool pop value, store in function return position
    RETN(71), //pop the proc environment, return to caller
    JS2(72), //pop the proc environment, return to caller


    /*
    * These form a family of LV (Load Value) instructions. 0/1/2 gives the base
     register number. The instruction code is followed by a 4-byte offset.
     Base Reg plus offset give an address, the value at this address is pushed.
    * */
    LV0(80),
    LV1(81),
    LV2(82),

    /*
    * These form a family of LA (Load Address) instructions. 0/1/2 gives the
    base register number. The instruction code is followed by a 4-byte offset.
    Base Reg plus offset give an address, and this address is pushed.*/
    LA0(90),
    LA1(91),
    LA2(92);



    private int value;
    private OpCodes(int value) {this.value = value; }
    public int getValue(){return value;}
}

// FILE. . . . . /home/hak/hlt/src/hlt/language/jaccapps/aql/sources/BuiltIns.java
// EDIT BY . . . Hassan Ait-Kaci
// ON MACHINE. . Hp-Dv7
// STARTED ON. . Wed Oct 17 23:32:58 2012

/**
 * This code defines built-in functions for the AQL language.
 * 
 * @version     Last modified on Wed Oct 17 23:37:01 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

package hlt.aql;

import hlt.language.design.kernel.Constant;
import hlt.language.design.kernel.Global;
import hlt.language.design.types.*;
import hlt.language.design.instructions.*;

public class BuiltIns
{
  BuiltIns (Tables tables)
    {
      _initialize(tables);
      Constant.initialize(tables);
    }    
        
  /**
   * Initialization of built-in operators.
   */
  private final void _initialize (Tables tables)
    {
      /////////////////////////////////////////////////////////////////////////////

      Symbol s;
      TypeParameter tvar = new TypeParameter();
      Type[] tvars = { tvar,tvar };
      SetType set = new SetType(tvar);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("wrapInt");
      // wrapInt : int -> boxed_int
      tables.defineBuiltIn(s,Type.INT(),Type.BOXED_INT(),Instruction.I_TO_O);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("unwrapInt");
      // unwrapInt : boxed_int ->int
      tables.defineBuiltIn(s,Type.BOXED_INT(),Type.INT(),Instruction.O_TO_I);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("wrapReal");
      // wrapReal : real -> boxed_real
      tables.defineBuiltIn(s,Type.REAL(),Type.BOXED_REAL(),Instruction.R_TO_O);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("unwrapReal");
      // unwrapReal : boxed_real ->real
      tables.defineBuiltIn(s,Type.BOXED_REAL(),Type.REAL(),Instruction.O_TO_R);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("toInt");
      // toInt : real -> int
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Instruction.R_TO_I);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("toReal");
      // toReal : int -> real
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Instruction.I_TO_R);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("noop2",true);      // no currying allowed
      // noop2 : void,void -> void
      tables.defineBuiltIn(s,Type.VOID,Type.VOID,Type.VOID,Instruction.NO_OP);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("+",true);      // no currying allowed for '+'
      // + : int -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Instruction.NO_OP);
      // + : real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Instruction.NO_OP);
      // + : int,int   -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.INT(),Instruction.ADD_II);
      // + : int,real   -> real
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.REAL(),Instruction.ADD_IR);
      // + : real,int   -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.REAL(),Instruction.ADD_RI);
      // + : real,real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.REAL(),Instruction.ADD_RR);
      // + : A,string -> string
      tables.defineBuiltIn(s,tvar,Type.STRING,Type.STRING,Instruction.DUMMY_STRCON);
      // + : A, {A} -> {A}
      tables.defineBuiltIn(s,tvar,set,set,Instruction.DUMMY_SET_ADD);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("-",true);      // no currying allowed for '-'
      // - : int -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Instruction.MINUS_I);
      // - : real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Instruction.MINUS_R);
      // - : int,int   -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.INT(),Instruction.SUB_II);
      // - : int,real  -> real
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.REAL(),Instruction.SUB_IR);
      // - : real,int  -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.REAL(),Instruction.SUB_RI);
      // - : real,real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.REAL(),Instruction.SUB_RR);
      // - : A, {A} -> {A}
      tables.defineBuiltIn(s,tvar,set,set,Instruction.DUMMY_SET_RMV);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("*");
      // * : int,int   -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.INT(),Instruction.MUL_II);
      // * : int,real  -> real
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.REAL(),Instruction.MUL_IR);
      // * : real,int - > real
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.REAL(),Instruction.MUL_RI);
      // * : real,real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.REAL(),Instruction.MUL_RR);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("/");
      // / : int,int   -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.INT(),Instruction.DIV_II);
      // / : int,real  -> real
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.REAL(),Instruction.DIV_IR);
      // / : real,int  -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.REAL(),Instruction.DIV_RI);
      // / : real,real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.REAL(),Instruction.DIV_RR);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("%");
      // % : int,int -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.INT(),Instruction.MODULO);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("abs");
      // abs : int -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Instruction.ABS_I);

      s = tables.symbol("abs");
      // abs : real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Instruction.ABS_R);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("min");
      // min : int,int   -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.INT(),Instruction.MIN_II);
      // min : int,real  -> real
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.REAL(),Instruction.MIN_IR);
      // min : real,int  -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.REAL(),Instruction.MIN_RI);
      // min : real,real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.REAL(),Instruction.MIN_RR);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("max");
      // max : int,int   -> int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.INT(),Instruction.MAX_II);
      // max : int,real  -> real
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.REAL(),Instruction.MAX_IR);
      // max : real,int  -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.REAL(),Instruction.MAX_RI);
      // max : real,real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.REAL(),Instruction.MAX_RR);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.defineEqualitySymbol("==");
      tables.defineBuiltIn(s,tvars,Type.BOOLEAN(),Instruction.DUMMY_EQU);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("!=");
      tables.defineBuiltIn(s,tvars,Type.BOOLEAN(),Instruction.DUMMY_NEQ);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("&&");
      // && : boolean,boolean -> boolean
      tables.defineBuiltIn(s,Type.BOOLEAN(),Type.BOOLEAN(),Type.BOOLEAN(),Instruction.DUMMY_AND);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("||");
      // || : boolean,boolean -> boolean
      tables.defineBuiltIn(s,Type.BOOLEAN(),Type.BOOLEAN(),Type.BOOLEAN(),Instruction.DUMMY_OR);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("!");
      // ! : boolean -> boolean
      tables.defineBuiltIn(s,Type.BOOLEAN(),Type.BOOLEAN(),Instruction.NOT);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("<");
      // < : int,int   -> boolean
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.BOOLEAN(),Instruction.LST_II);
      // < : int,real  -> boolean
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.BOOLEAN(),Instruction.LST_IR);
      // < : real,int  -> boolean
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.BOOLEAN(),Instruction.LST_RI);
      // < : real,real -> boolean
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.BOOLEAN(),Instruction.LST_RR);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("<=");
      // <= : int,int   -> boolean
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.BOOLEAN(),Instruction.LTE_II);
      // <= : int,real  -> boolean
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.BOOLEAN(),Instruction.LTE_IR);
      // <= : real,int  -> boolean
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.BOOLEAN(),Instruction.LTE_RI);
      // <= : real,real -> boolean
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.BOOLEAN(),Instruction.LTE_RR);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol(">");
      // > : int,int   -> boolean
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.BOOLEAN(),Instruction.GRT_II);
      // > : int,real  -> boolean
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.BOOLEAN(),Instruction.GRT_IR);
      // > : real,int  -> boolean
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.BOOLEAN(),Instruction.GRT_RI);
      // > : real,real -> boolean
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.BOOLEAN(),Instruction.GRT_RR);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol(">=");
      // >= : int,int   -> boolean
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.BOOLEAN(),Instruction.GTE_II);
      // >= : int,real  -> boolean
      tables.defineBuiltIn(s,Type.INT(),Type.REAL(),Type.BOOLEAN(),Instruction.GTE_IR);
      // >= : real,int  -> boolean
      tables.defineBuiltIn(s,Type.REAL(),Type.INT(),Type.BOOLEAN(),Instruction.GTE_RI);
      // >= : real,real -> boolean
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.BOOLEAN(),Instruction.GTE_RR);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("sqrt");
      // sqrt : real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Instruction.SQRT);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("**");
      // ** : real,real -> real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.REAL(),Instruction.POWER);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("random");
      // random : real
      tables.defineBuiltIn(s,Type.REAL(),Instruction.RANDOM);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("..");
      // .. : int,int   -> int .. int
      tables.defineBuiltIn(s,Type.INT(),Type.INT(),Type.INT_RANGE,Instruction.PUSH_INT_RNG);
      // .. : real,real   -> real .. real
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL(),Type.REAL_RANGE,Instruction.PUSH_REAL_RNG);

      s = tables.symbol("size");
      // size : int .. int -> int
      tables.defineBuiltIn(s,Type.INT_RANGE,Type.INT(),Instruction.INDEXABLE_SIZE);

      s = tables.symbol("lower");
      // lower : int .. int -> int
      tables.defineBuiltIn(s,Type.INT_RANGE,Type.INT(),Instruction.INT_RNG_LB);
      // lower : real .. real -> real
      tables.defineBuiltIn(s,Type.REAL_RANGE,Type.REAL(),Instruction.REAL_RNG_LB);

      s = tables.symbol("upper");
      // upper : int .. int -> int
      tables.defineBuiltIn(s,Type.INT_RANGE,Type.INT(),Instruction.INT_RNG_LB);
      // upper : real .. real -> real
      tables.defineBuiltIn(s,Type.REAL_RANGE,Type.REAL(),Instruction.REAL_RNG_UB);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("in");
      // int : int, int .. int -> boolean
      tables.defineBuiltIn(s,Type.INT(),Type.INT_RANGE,Type.BOOLEAN(),Instruction.BELONGS_I);
      // in : real, real .. real -> boolean
      tables.defineBuiltIn(s,Type.REAL(),Type.REAL_RANGE,Type.BOOLEAN(),Instruction.BELONGS_R);
      s = tables.symbol("in");
      // in : A, {A} -> boolean
      tables.defineBuiltIn(s,tvar,set,Type.BOOLEAN(),Instruction.DUMMY_BELONGS);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("ord");
      // ord : {A}, A -> int
      tables.defineBuiltIn(s,set,tvar,Type.INT(),Instruction.DUMMY_ORD);

      s = tables.symbol("first");
      // first : {A} -> A
      tables.defineBuiltIn(s,set,tvar,Instruction.DUMMY_FIRST);

      s = tables.symbol("last");
      // last : {A} -> A
      tables.defineBuiltIn(s,set,tvar,Instruction.DUMMY_LAST);

      s = tables.symbol("next");
      // next : {A}, A -> A
      tables.defineBuiltIn(s,set,tvar,tvar,Instruction.DUMMY_NEXT);

      s = tables.symbol("nextc");
      // nextc : {A}, A -> A
      tables.defineBuiltIn(s,set,tvar,tvar,Instruction.DUMMY_NEXT_C);

      s = tables.symbol("prev");
      // prev : {A}, A -> A
      tables.defineBuiltIn(s,set,tvar,tvar,Instruction.DUMMY_PREV);

      s = tables.symbol("prevc");
      // prevc : {A}, A -> A
      tables.defineBuiltIn(s,set,tvar,tvar,Instruction.DUMMY_PREV_C);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("copy");
      // copy : {A} -> {A}
      tables.defineBuiltIn(s,set,set,Instruction.SET_COPY);

      s = tables.symbol("size");
      // size : {A} -> int
      tables.defineBuiltIn(s,set,Type.INT(),Instruction.INDEXABLE_SIZE);

      s = tables.symbol("C=");
      // C : {A}, {A} -> boolean
      tables.defineBuiltIn(s,set,set,Type.BOOLEAN(),Instruction.SUBSET);

      s = tables.symbol("U");
      // U : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.UNION);

      s = tables.symbol("I");
      // I : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.INTER);

      s = tables.symbol("^");
      // ^ : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.XNION);

      s = tables.symbol("-");
      // - : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.SDIFF);

      s = tables.symbol("U=");
      // U= : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.D_UNION_1);

      s = tables.symbol("I=");
      // I= : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.D_INTER_1);

      s = tables.symbol("^=");
      // ^= : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.D_XNION_1);

      s = tables.symbol("=U");
      // U= : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.D_UNION_2);

      s = tables.symbol("=I");
      // I= : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.D_INTER_2);

      s = tables.symbol("=^");
      // ^= : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.D_XNION_2);

      s = tables.symbol("-=");
      // -= : {A}, {A} -> {A}
      tables.defineBuiltIn(s,set,set,set,Instruction.D_SDIFF);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("write"); 
      // write : A -> void
      tables.defineBuiltIn(s,tvar,Type.VOID,Instruction.DUMMY_WRITE);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("size");
      // length : A[]   -> int
      tables.defineBuiltIn(s,new ArrayType(),Type.INT(),Instruction.DUMMY_SIZE);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("toArray");
      // toArray : A[B]   -> A[int]
      tables.defineBuiltIn(s,new ArrayType(tvar),new ArrayType(tvar,Type.INT()),Instruction.MAP_TO_ARRAY);

      /////////////////////////////////////////////////////////////////////////////

      s = tables.symbol("indexable");
      // indexable : A[B]   -> B
      tables.defineBuiltIn(s,new ArrayType(new TypeParameter(),tvar),tvar,Instruction.GET_INDEXABLE);

      /////////////////////////////////////////////////////////////////////////////
   }
}

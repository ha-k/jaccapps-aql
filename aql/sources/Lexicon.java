// FILE. . . . . /home/hak/hlt/src/hlt/language/jaccapps/aql/sources/Lexicon.java
// EDIT BY . . . Hassan Ait-Kaci
// ON MACHINE. . Hp-Dv7
// STARTED ON. . Wed Oct 17 23:51:02 2012

/**
 * This is an auxiliary file for the <a
 * href="../docs/AQLDoc/AQL.html">AQL language grammar</a> and defines
 * its tokenizer's lexical units, reserved word, and utility methods as
 * a class (see <a
 * href="../docs/AQLDoc/Tokenizer.html">Tokenizer.java</a>).
 * 
 * @version     Last modified on Thu Oct 18 00:48:02 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

package hlt.aql;

import java.util.HashSet;

class Lexicon
{
  /**
   * Returns <tt>true</tt> iff the specified string starts with a
   * letter, and every other character is alphanumeric or underscore.
   */
  public final static boolean isIdentifier (String s)
    {
      if (!Character.isLetter(s.charAt(0)))
        return false;

      char c;
      for (int i=1; i<s.length(); i++)
          {
            c = s.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_')
              return false;
          }

      return true;
    }       

  /**
   * The following is a store for reserved words.
   */
  public static final HashSet reservedWords = new HashSet(30);   

  /**
   * Returns <tt>true</tt> iff the specified string is a
   * reserved word.
   */
  public final static boolean isReservedWord (String s)
    {
      return reservedWords.contains(s);
    }

  /**
   * Defines the specified string as a reserved word.
   */
  static final void kw (String word)
    {
      reservedWords.add(word);
    }
        
  /**
   * Initializes the table of reserved words.
   */
  static
    {
      kw("alias");
      kw("as");
      kw("bag");
      kw("boolean");
      kw("char");
      kw("class");
      kw("define");
      kw("do");
      kw("else");
      kw("false");
      kw("forall");
      kw("foreach");
      kw("function");
      kw("hom");
      kw("if");
      kw("in");
      kw("int");
      kw("let");
      kw("list");
      kw("method");
      kw("name");
      kw("new");
      kw("null");
      kw("operator");
      kw("real");
      kw("return");
      kw("set");
      kw("signature");
      kw("static");
      kw("string");
      kw("structure");
      kw("then");
      kw("true");
      kw("type");
      kw("value");
      kw("void");
      kw("while");
    }   

  /**
   * The following is a store for operator specifiers.
   */
  public static final HashSet specifiers = new HashSet(7);

  /**
   * Returns <tt>true</tt> iff the specified string is an
   * operator specifier.
   */
  public final static boolean isSpecifier (String s)
    {
      return specifiers.contains(s);
    }

  /**
   * Defines the specified string as a specifier.
   */
  static final void sp (String word)
    {
      specifiers.add(word);
    }
        
  /**
   * Initializes the table of specifiers.
   */
  static
    {
      sp("fx");         // unary  prefix  non-associative
      sp("fy");         // unary  prefix  right-associative
      sp("xf");         // unary  postfix non-associative
      sp("yf");         // unary  postfix left-associative
      sp("xfx");        // binary infix   non-associative
      sp("xfy");        // binary infix   right-associative
      sp("yfx");        // binary infix   left-associative
    }
}

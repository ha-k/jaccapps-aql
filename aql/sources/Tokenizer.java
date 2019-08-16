// FILE. . . . . /home/hak/hlt/src/hlt/language/jaccapps/aql/sources/Tokenizer.java
// EDIT BY . . . Hassan Ait-Kaci
// ON MACHINE. . Hp-Dv7
// STARTED ON. . Thu Oct 18 00:41:40 2012

/**
 * This is the tokenizer for the <a href="../docs/AQLDoc/AQL.html">AQL
 * language grammar</a>. It uses the lexical units defined in <a
 * href="../docs/AQLDoc/Lexicon.html">Lexicon.java</a>).
 * 
 * @version     Last modified on Wed Apr 24 08:57:32 2013 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

package hlt.aql;

import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.io.StringReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

import hlt.language.io.CircularInclusionException;
import hlt.language.io.IncludeReader;
import hlt.language.io.StreamTokenizer;
import hlt.language.io.IO;

import hlt.language.tools.Misc;

import hlt.language.syntax.*;

public class Tokenizer extends AbstractTokenizer
{
  int lineno;
  IncludeReader reader;
  StreamTokenizer st;
  boolean prompt;

  public final void setInteractive ()
    {
      setReader(new InputStreamReader(System.in));
      prompt = true;
      prompt();
    }

  public final int lineNumber()
    {
      return lineno;
    }

  public final void setReader (Reader rd)
    {
      reader = rd instanceof IncludeReader ? (IncludeReader)rd : new IncludeReader(rd);
      st = new StreamTokenizer(reader);
      st.ordinaryChars("+-.(){}[]<>;,?:#@");
      st.setType('.',st.NUMERIC);
      st.quoteChar('\'');
      st.quoteChar('"');
      st.wordChar('_');
    }

  public final Reader getReader ()
    {
      return reader;
    }

  final void setFile (String file)
    {
      reader.setFile(file);
    }

  public final void include (String file)
    throws FileNotFoundException, CircularInclusionException
    {
      reader.include(file);
    }

  public final void setPrompt (boolean flag)
    {
      prompt = flag;
    }

  private final boolean promptIsNeeded ()
    {
      boolean isWaiting = prompt;
      try
        {
          isWaiting &= !reader.ready();
        }
      catch (IOException e)
        {
          e.printStackTrace();
        }
      return isWaiting && reader.depth() <= 1;
    }

  public final void prompt ()
    {
      if (promptIsNeeded())
        System.out.print(promptString());
    }

  public final String promptString ()
    {
      return Misc.numberString(lineno+1,3,'*') + " " + promptLabel + "> ";
    }

  String promptLabel = "AQL";  

  public final void setPrompt (String p)
    {
      promptLabel = p;
    }

  final ParseNode locate (ParseNode node)
    {
      return ((ParseNode)node
	      .setStart(st.tokenStart())
	      .setEnd(st.tokenEnd())).setFile(reader.getFile());
    }

  final ParseNode integer ()
    {
      return locate(GenericParser.numberToken("INT",(int)st.nval));
    }

  final ParseNode real ()
    {
      return locate(GenericParser.numberToken("REAL",st.nval));
    }

  final ParseNode string ()
    {
      return locate(GenericParser.symbolToken("STRING",st.sval));
    }

  final ParseNode character ()
    {
      return locate(GenericParser.symbolToken("CHAR",st.sval));
    }

  final ParseNode specifier ()
    {
      return locate(GenericParser.symbolToken("SPECIFIER",st.sval));
    }

  final ParseNode id (String symbol)
    {
      return locate(GenericParser.symbolToken("ID",symbol));
    }

  final ParseNode dynamic (String s)
    {
      return locate(DynamicParser.literalToken(s));
    }

  final ParseNode dynamic (int c)
    {
      return locate(DynamicParser.literalToken(String.valueOf((char)c)));
    }

  final ParseNode literal (String s)
    {
      return locate(GenericParser.literalToken(s));
    }

  final ParseNode literal (int c)
    {
      return locate(GenericParser.literalToken(String.valueOf((char)c)));
    }

  final boolean isNotEndOfSymbol (int c)
    {
      return st.isOrdinaryChar(c)
        && !(st.isWhitespaceChar(c)
             || c == '(' || c == ')'
             || c == '[' || c == ']'
             || c == '{' || c == '}'
             || c == '<' || c == '>'
             || c == ':'
             || c == ';'
             || c == ',');
    }

  public final ParseNode nextToken () throws IOException
    {
      ParseNode t = null;
      int tk = st.nextToken();
      lineno = st.lineno();

      switch (tk)
        {
        case StreamTokenizer.TT_SPECIAL:
          return nextToken();
        case StreamTokenizer.TT_EOF:
          t = locate(GenericParser.eoi());
          break;
        case StreamTokenizer.TT_NUMBER:
          if (st.isInteger)
            t = integer();
          else
            t = real();
          break;
        case '"':
          t = string();
          break;
        case '\'':    // NB: this does NOT check the contents between single quotes
          t = character();
          break;
        case StreamTokenizer.TT_WORD:
          if (Lexicon.isSpecifier(st.sval))
            {
              t = specifier();
              break;
            }
          if (Lexicon.isReservedWord(st.sval))
            {
	      if (st.sval == "strip")
		t = dynamic("strip");
	      else
		if (st.sval == "set")
		  t = dynamic("set");
		else
		  if (st.sval == "bag")
		    t = dynamic("bag");
		  else
		    if (st.sval == "list")
		      t = dynamic("list");
		    else
		      t = literal(st.sval);
              break;
            }
          if (st.sval == "U" || st.sval == "I" || st.sval == "C")
            {
              if (st.peek() == '=')
                {
                  t = id(st.sval+"=");
                  st.skipChar(true);
                }
              else
                t = id(st.sval); 
            }
          else
            t = id(st.sval);
          break;
        case '(': case ')': case '{': case '}':
        case ';': case ',': case '?': case '@':
        case '[': case '$': 
          t = literal(tk);
          break;
        case '#':
          if (st.peek() == '[')
            {
              t = literal("#[");
              st.skipChar(true);
            }
          else
            t = literal("#");
          break;
        case ']':
          if (st.peek() == '#')
            {
              t = literal("]#");
              st.skipChar(true);
            }
          else
            t = literal("]");
          break;
        case ':':
          if (st.peek() == '=')
            {
              t = literal(":=");
              st.skipChar(true);
            }
          else
            t = literal(":");
          break;
        case '*':
          if (st.peek() == '*')
            {
              t = id("**");
              st.skipChar(true);
            }
          else
            t = id("*");
          break;
        case '-':
          switch (st.peek())
            {
            case '>':
              t = literal("->");
              st.skipChar(true);
              break;
            case '=':
              t = id("-=");
              st.skipChar(true);
              break;
            default:
              t = dynamic("-");
            }
          break;
        case '^':
          if (st.peek() == '=')
            {
              t = id("^=");
              st.skipChar(true);
            }
          else
            t = dynamic("^");
          break;
        case '=':
          if (st.peek() == '=' || st.peek() == 'U' || st.peek() == 'I' || st.peek() == '^')
            {
              t = dynamic("="+(char)st.peek());
              st.skipChar(true);
            }
          else
            t = literal("=");
          break;
        case '<':
          switch (st.peek())
            {
            case '=':         
              t = dynamic("<=");
              st.skipChar(true);
              break;
            case '-':         
              t = literal("<-");
              st.skipChar(true);
              break;
            default:
              t = dynamic("<");
            }
          break;
        case '>':
          if (st.peek() == '=')
            {
              t = dynamic(">=");
              st.skipChar(true);
            }
          else
            t = dynamic(">");
          break;
        case '!':
          if (st.peek() == '=')
            {
              t = dynamic("!=");
              st.skipChar(true);
            }
          else
            t = dynamic("!");
          break;
        case '&':
          if (st.peek() == '&')
            {
              t = dynamic("&&");
              st.skipChar(true);
            }
          else
            t = dynamic("&");
          break;
        case '|':
          if (st.peek() == '|')
            {
              t = dynamic("||");
              st.skipChar(true);
            }
          else
            t = dynamic("|");
          break;
        default: // read the longest possible token and return it as a DYNAMIC token
          String op = String.valueOf((char)tk);
          while (isNotEndOfSymbol(tk = st.peek()))
            {
              op += String.valueOf((char)tk);
              st.skipChar(true);
            }
          t = dynamic(op);
        }
//       System.out.println("Read Token: "+t.nodeInfo());
      return t;
    }
}


// FILE. . . . . /home/hak/hlt/src/hlt/language/jaccapps/aql/sources/Main.java
// EDIT BY . . . Hassan Ait-Kaci
// ON MACHINE. . Hp-Dv7
// STARTED ON. . Wed Oct 17 20:53:56 2012

/**
 * @version     Last modified on Thu Oct 18 04:08:07 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

package hlt.aql;

import java.io.File;
import java.io.IOException;

import hlt.language.util.Error;

public class Main
{
  final static void welcome ()
    {
      System.out.println();
      System.out.println("********************************************************");
      System.out.println("***                                                  ***");
      System.out.println("*** Welcome to HLT's AQL --- Prototype Version  0.00 ***");
      System.out.println("***                                                  ***");
      System.out.println("********************************************************");
      System.out.println();
      System.out.println("*** Interactive mode - type #help; for known pragmas...");
      System.out.println();
    }

  final static void exit ()
    {
      System.out.println();
      System.out.println();
      System.out.println("********************************************************");
      System.out.println("*** Exiting HLT's AQL Prototype v0.00 -- Good Bye... ***");
      System.out.println("********************************************************");
      System.out.println();
      System.exit(0);
    }

  final public static void main (String args[])
    {
      Tokenizer tokenizer = new Tokenizer();
      Parser parser = new Parser(tokenizer);

      welcome();

      try
        {
          for (int i=0; i<args.length; i++)
            {
              parser.displayLine("*** Loading file: "+args[i]);
              parser.parse(args[i]);
            }
          tokenizer.setInteractive();
          parser.parse();
        }
      catch (IOException e)
        {
          System.err.println(new Error().setLabel("IO Error: ")
                             .setMsg(e.getMessage()+" aborting")
                             .setSee("..."));
        }
      catch (Throwable e)
        {
          parser.errorManager().reportError(new Error().setLabel("Fatal Error: ")
                                            .setMsg("aborting")
                                            .setSee("..."));
          e.printStackTrace();
        }
      finally
        {
          exit();
        }
    }
}


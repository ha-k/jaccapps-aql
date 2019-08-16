// FILE. . . . . /home/hak/hlt/src/hlt/language/jaccapps/aql/sources/Bench.java
// EDIT BY . . . Hassan Ait-Kaci
// ON MACHINE. . Hp-Dv7
// STARTED ON. . Wed Oct 17 23:30:20 2012

/**
 * This code is to bench the AQL system on test files for all its constructs.
 * 
 * @version     Last modified on Thu Oct 18 03:36:50 2012 by hak
 * @author      <a href="mailto:hak@acm.org">Hassan A&iuml;t-Kaci</a>
 * @copyright   &copy; <a href="http://www.hassan-ait-kaci.net/">by the author</a>
 */

package hlt.aql;

import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.IOException;

import hlt.language.io.FileTools;
import hlt.language.util.Error;
import hlt.language.tools.Misc;

public class Bench
{
  static Tokenizer tokenizer = new Tokenizer();
  static Parser parser = new Parser(tokenizer);
  static long totalTime = 0L;
  static long runningTime = 0L;

  final public static void main (String args[])
    {
      tokenizer.setPrompt(false);
      for (int i=0; i < args.length; i++) run(args[i]);
      System.out.println(Misc.repeat(70,'*'));
      System.out.println("*** Ran "+args.length+" files; total time: "+totalTime+" ms");
    }

  final static void run (String file)
    {
      try
        {
          System.out.print("*** Running file "+file);
          PrintStream curr =
	    new PrintStream(new FileOutputStream(FileTools.prefix(file)+".curr"),true);
          parser.displayManager.setOutputStream(curr);
          parser.errorManager().setErrorStream(curr);
	  runningTime = System.currentTimeMillis();
          parser.parse(file);
	  runningTime = System.currentTimeMillis() - runningTime;
	  totalTime += runningTime;
          System.out.println("\t["+runningTime+" ms]");
          parser.tables.reset();
        }
      catch (IOException e)
        {
          System.err.println(new Error().setLabel("IO Error: ")
                                        .setMsg(e.getMessage()+" aborting")
                                        .setSee("..."));
        }
      catch (Exception e)
        {
          parser.errorManager().reportError(new Error().setLabel("Fatal Error: ")
                                                       .setMsg("aborting")
                                                       .setSee("..."));
          e.printStackTrace();
        }
    }      
}



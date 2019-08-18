NB: not all of this makes sense nor is high-priority.


0. Fix collections and redo benches!!!

1. Finish the hyperdocumentor: fix Jacc so that an additional option to
   jacc -doc (say -hdoc DIR) may specify where the <GRM>Doc directory is
   created (in . by default). Same thing adding a -root FILE option
   (default index.html). All this is in hlt.language.syntax.Documentor.java.

2. Annotate the AQL grammar to generate as full (and useful)
   a documentation as possible.

3. Update Jacc's documentation to document Jacc's documentation
   mode. [I reread this - and, yes!, it does make sense!... -hak]

4. Define AQL as a subclass of hlt.language.tools.Command
   with appropriate options.

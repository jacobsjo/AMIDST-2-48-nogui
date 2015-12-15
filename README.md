AMIDST 2^48-nogui, a Minecraft stronghold finder and duplication tool.
======

How tu use it:
--------------
-As AMIDST 2^48-nogui has no gui (thats ovious isn't it?), you have to specify the minecraft jar file using the -mcjar command line Parameter. Aditionally a -output file has to be specified.
-This Tool has 2 modes:
  1. if called with:
     '''
       -seed <baseseed> -x <Chunk X Pos> -z <Chunk Z Pos>
     '''
     it checks the next 1000000 Seeds with seed mod 2^48 = baseseed and writes all seeds that have a Stronghold at these Positions into the specified output file and into the console log.
  2. if called with:
     '''
       -seedfile <path>
     '''
     it reads the specified seedfile and does the same as written in 1., but for only 20000 seeds and stops as soon as it finds the first Stronghold. That means that in the seedfile file
     there will be a maximum of 1 lines in the output file. The seedfile has to have one ore more line of the following layout:
     '''
     <baseseed> <n> <ChunkX> <ChunkZ>
     '''
     (Don't wory if there is more than one line with the same baseseed directly next to each other the script dosn't run multiple times for that seed ;) )

How to compile :
----------------
-Start Eclipse
-Open build.xml
-Click Run>External tools>Run as>Ant build
-If there is an error with JAVA_HOME, go to Run>External tools>External tools configurations..., click on JRE, then on Installed JREs, click Add, select your JDK folder and apply changes. Tell me if you know a better way to fix this issue.

======

AMIDST stands for Advanced Minecraft Interface and Data/Structure Tracking

License and warranty
--------------------

AMIDST comes with ABSOLUTELY NO WARRANTY. It is free software, and you are
welcome to redistribute it under certain conditions. See LICENSE.txt for more
details on both of these points.

# submissions-exoplanets
Batch processing example using executors and flink.

Build steps -

1. There are 2 Maven profiles - executors, flink.
2. Either can be used to generate the jar with dependencies for the respective implementation.

Notes -
1. The output jar is prefixed by the respective keyword.
2. Needs JDK 1.8 to run the jars.

Running Executors jar -
1. java - jar <<jar file name>> <<data source>> <<number of threads in pool>>
2. Both arguments are optional. (Arg 2 can be specified only if arg 1 is specified).
3. Data source can be either an http URL, or a full path to local file.
    
Running Flink jar -
1. This jar can be submitted as a flink job to the flink cluster.
2. flink run <<jar file name>> --input=<<data source>> --output=<<data sink>>
3. input and output are optional
4. There are 3 files that are generated if output is specified, else raw datasets are dumped on the cluster stdout.

Note - For a minimal flink local setup -
1. Download apache flink.
2. Start the local cluster 'bin\start-cluster'
3. Submit flink job 'bin\flink run -p <<parallelism>> <<jar file name>> --input=<<data source>> --output=<<data sink>>


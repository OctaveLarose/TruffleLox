#!/bin/bash
pushd /home/octavel/PhD_Programs/my_programs/TruffleLox > /dev/null

#ant -Dinputfile=$1 run

/home/octavel/PhD_Programs/git_repos/graalvm-ce-java17-22.1.0/bin/java -Dtruffle.class.path.append=build/classes --add-opens=org.graalvm.truffle/com.oracle.truffle.api=ALL-UNNAMED --add-opens=org.graalvm.truffle/com.oracle.truffle.api.nodes=ALL-UNNAMED --add-opens=org.graalvm.truffle/com.oracle.truffle.api.dsl=ALL-UNNAMED -classpath /home/octavel/PhD_Programs/my_programs/TruffleLox/build/classes:/home/octavel/PhD_Programs/my_programs/TruffleLox/libs/graal-sdk.jar:/home/octavel/PhD_Programs/my_programs/TruffleLox/libs/graal.jar:/home/octavel/PhD_Programs/my_programs/TruffleLox/libs/hamcrest-core-1.3.jar:/home/octavel/PhD_Programs/my_programs/TruffleLox/libs/junit-4.12.jar:/home/octavel/PhD_Programs/my_programs/TruffleLox/libs/truffle-api.jar:/home/octavel/PhD_Programs/my_programs/TruffleLox/libs/truffle-dsl-processor.jar lox.GodClass /home/octavel/PhD_Programs/git_repos/craftinginterpreters/$1

popd > /dev/null

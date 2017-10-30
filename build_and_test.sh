#! /usr/bin/bash
gradle build
cd cob_spec
java -jar fitnesse.jar -c  "HttpTestSuite.ResponseTestSuite.SimpleGet?test&format=text"
java -jar fitnesse.jar -c  "HttpTestSuite.ResponseTestSuite.FourOhFour?test&format=text"
java -jar fitnesse.jar -c  "HttpTestSuite.ResponseTestSuite.SimplePut?test&format=text"
java -jar fitnesse.jar -c  "HttpTestSuite.ResponseTestSuite.SimpleHead?test&format=text"
java -jar fitnesse.jar -c  "HttpTestSuite.ResponseTestSuite.FourEightTeen?test&format=text"
cd -

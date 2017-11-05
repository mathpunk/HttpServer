task :default => :run_passing do
  puts "Running passing tests"
end

task :run_next => :run_passing do
  Dir.chdir('cob_spec') do
    # next feature goes here
  end
end

task :build do
  sh "gradle build"
end

task :run_passing => :build do
  Dir.chdir('cob_spec') do
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimpleGet?test&format=text\" |grep -i failure"
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.FourOhFour?test&format=text\" |grep -i failure"
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimplePut?test&format=text\" |grep -i failure"
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimpleHead?test&format=text\" |grep -i failure"
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.FourEightTeen?test&format=text\" |grep -i failure"
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.MethodNotAllowed?test&format=text\" |grep -i failure"
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.RedirectPath?test&format=text\" |grep -i failure"
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimpleOption?test&format=text\" |grep -i failure"
  end
end

task :run_passing_verbose => :build do
  Dir.chdir('cob_spec') do
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimpleGet?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.FourOhFour?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimplePut?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimpleHead?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.FourEightTeen?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.MethodNotAllowed?test&format=text\""
  end
end

task :serve => :build do
  sh "java -jar build/libs/HttpServer-1.0-SNAPSHOT.jar -p 5000 -d cob_spec/public"
end




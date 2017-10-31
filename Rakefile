task :build do
  sh "gradle build"
end

task :run_passing => :build do
  Dir.chdir('cob_spec') do
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimpleGet?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.FourOhFour?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimplePut?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.SimpleHead?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.FourEightTeen?test&format=text\""
    sh "java -jar fitnesse.jar -c  \"HttpTestSuite.ResponseTestSuite.MethodNotAllowed?test&format=text\""
  end
end

task :default => :run_passing do
  puts "Running passing tests"
end

task :run_next => :build do
  puts "Testing next feature"
  # insert next test here
end


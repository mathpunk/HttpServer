task :build do
  sh "gradle build"
end

task :serve => :build do
  sh "java -jar build/libs/HttpServer-1.0-SNAPSHOT.jar -p 5000 -d cob_spec/public"
end

task :run_passing => :build do
  features_passing = [
    "CookieData",
    "ParameterDecode",
    "FileContents",
    "SimplePost",
    "SimpleOption",
    "RedirectPath",
    "MethodNotAllowed",
    "FourEightTeen",
    "FourOhFour",
    "SimplePut",
    "SimpleHead",
    "SimpleGet",
  ]
  Dir.chdir('cob_spec') do
    features_passing.each do |feature|
      sh "java -jar fitnesse.jar -c \"HttpTestSuite.ResponseTestSuite.#{feature}?test&format=text\""
    end
    sh "java -jar fitnesse.jar -c \"HttpTestSuite.SimultaneousTestSuite.TimeToComplete?test&format=text\""
  end
end

task :run_simultaneous => :build do
end

task :run_next => :build do
  features_pending = [
    "DirectoryListing",
    "DirectoryLinks",
    "BasicAuth",
    "ImageContent",
    "MediaTypes",
    "PartialContent",
    "PatchWithEtag",
    "PostGetPutGetDeleteGet"]
  Dir.chdir('cob_spec') do
    sh "java -jar fitnesse.jar -c \"HttpTestSuite.ResponseTestSuite.#{features_pending.first}?test&format=text\""
  end
end

task :run_regressed => :build do
  Dir.chdir('cob_spec') do
    # feature = nil
    # sh "java -jar fitnesse.jar -c \"HttpTestSuite.ResponseTestSuite.#{feature}?test&format=text\""
  end
end

task :run_all => [:run_passing, :run_regressed, :run_next] do
  "Running all tests"
end

task :default => :run_next do
  "Next story(s)"
end

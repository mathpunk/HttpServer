task :build do
  sh "gradle build"
end

task :serve => :build do
  sh "java -jar build/libs/HttpServer-1.0-SNAPSHOT.jar -p 5000 -d cob_spec/public"
end

task :run_passing => :build do
  features = ["SimpleGet", "SimplePut", "FourOhFour", "SimpleHead", "FourEightTeen", "RedirectPath", "SimpleOption", "MethodNotAllowed", "SimplePost", "ParameterDecode"]
  Dir.chdir('cob_spec') do
    features.each do |feature|
      sh "java -jar fitnesse.jar -c \"HttpTestSuite.ResponseTestSuite.#{feature}?test&format=text\""
    end
    sh "java -jar fitnesse.jar -c \"HttpTestSuite.SimultaneousTestSuite.TimeToComplete?test&format=text\""
  end
end

task :run_next => :build do
  features_pending = [
    "FileContents",
    "BasicAuth",
    "CookieData",
    "DirectoryLinks",
    "DirectoryListing",
    "ImageContent",
    "MediaTypes",
    "PartialContent",
    "PatchWithEtag",
    "PostGetPutGetDeleteGet"]
  Dir.chdir('cob_spec') do
    sh "java -jar fitnesse.jar -c \"HttpTestSuite.ResponseTestSuite.#{features_pending.first}?test&format=text\""
  end
end

task :run_all => [:run_passing, :run_next] do
  "Running all tests"
end

task :default => :run_next do
  "Next story(s)"
end

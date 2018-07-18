cd\
cd C:\GradleTutorials\ScriptDirectedGui\build\libs
start /wait /min java -jar ScriptDirectedGui-1.0.jar copy-run %1 %2

cd\
cd C:\GradleTutorials\ScriptDirectedGui
gradlew clean build
pause

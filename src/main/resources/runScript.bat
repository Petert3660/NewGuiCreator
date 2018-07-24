cd\
cd GradleTutorials\TestGuiRunner

set myDirName = ".\build\libs"

if exist myDirName (
    cd build\libs
    del *.*
    cd ..\..
)

call gradlew clean build
cd build\libs
java -jar TestGuiRunner.jar
cd ..\..\..\NewGuiCreator\src\main\resources


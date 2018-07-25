cd\
cd GradleTutorials\TestGuiRunner

set myDirName = ".\build\libs"

if exist myDirName (
    cd build\libs
    del *.*
    cd ..\..
)

call gradlew clean build

cp c:\GradleTutorials\TestGuiRunner\build\libs\TestGuiRunner.jar c:\PTConsultancy\LocalTestEnvironment\TestGuiRunner
cd\
cd c:\PTConsultancy\LocalTestEnvironment\TestGuiRunner
java -jar TestGuiRunner.jar

cd ..\..\..\NewGuiCreator\src\main\resources


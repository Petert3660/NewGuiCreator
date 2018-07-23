cd\
cd GradleTutorials\TestGuiRunner

set myDirName = ".\build\libs"

if exist myDirName (
    cd build\libs
    del *.*
    cd ..\..
)

call gradlew clean build
cd myDirName
java -jar
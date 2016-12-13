CD %~dp0

CALL setenv.bat

SET /A pstatus=0
SET ClassDir=%cd%\classes
SET SourcePath_Prefix=\src\sg\edu\nus\iss
SET FullSourcePath_Prefix=%cd%\src\sg\edu\nus\iss
SET CLASSPATH=.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar;%cd%\classses;%cd%\src;%CLASSPATH%;

SET CUR_DIR=%cd%

IF EXIST "%ClassDir%" RMDIR /Q /S "%ClassDir%" 1>NUL 2>&1

MKDIR "%ClassDir%"

FOR /R "%FullSourcePath_Prefix%" %%s in (.) do (
	ECHO %%s
	javac -d "%cd%\classes" -cp classes -sourcepath src "%%s"\*.java
) 
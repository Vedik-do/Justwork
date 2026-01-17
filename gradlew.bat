@echo off
setlocal
set DIR=%~dp0
set CLASSPATH=%DIR%gradle\wrapper\gradle-wrapper.jar

if defined JAVA_HOME (
  set JAVA_EXE=%JAVA_HOME%\bin\java.exe
) else (
  set JAVA_EXE=java.exe
)

"%JAVA_EXE%" %GRADLE_OPTS% -Dorg.gradle.appname=Gradle -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
endlocal

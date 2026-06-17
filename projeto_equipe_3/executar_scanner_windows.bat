@echo off
cd /d "%~dp0"
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src\exception\*.java src\model\*.java src\service\*.java src\util\*.java src\view\*.java src\main\*.java
java -cp bin main.Main scanner
pause

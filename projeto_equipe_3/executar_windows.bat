@echo off
cd /d "%~dp0"
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src\exception\*.java src\model\*.java src\service\*.java src\util\*.java src\view\*.java src\main\*.java
if %errorlevel% neq 0 (
    echo.
    echo Erro ao compilar o projeto.
    pause
    exit /b %errorlevel%
)
java -cp bin main.Main
pause

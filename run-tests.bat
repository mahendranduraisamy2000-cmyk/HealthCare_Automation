@echo off
REM Usage:  run-tests.bat            -> runs tags from TestRunnerClass
REM         run-tests.bat @ATC001    -> runs only the given tag
cd /d "%~dp0"

if "%~1"=="" (
    call mvn clean test
) else (
    call mvn clean test "-Dcucumber.filter.tags=%~1"
)

echo.
echo ===== Generating Allure report =====
call ".allure\allure-2.30.0\bin\allure.bat" generate target\allure-results --clean --single-file -o target\allure-report

echo.
echo Report: %~dp0target\allure-report\index.html
start "" "target\allure-report\index.html"

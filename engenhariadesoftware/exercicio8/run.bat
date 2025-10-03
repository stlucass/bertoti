@echo off
cd /d %~dp0
mvn -q clean javafx:run
pause

call runcrud.bat
echo RUNCRUD END
if "%ERRORLEVEL%" == "0" goto gettasks
echo.
echo RUNCRUD.BAT has errors - breaking work
goto fail

:gettasks
start http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.
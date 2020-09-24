call runcrud.bat
if "%ERRORLEVEL%" == "0" goto runbrowser
echo.
echo ---Cannot run runcrud.bat
goto fail

:runbrowser
cd "C:\Program Files (x86)\Mozilla Firefox\"
start firefox.exe -new-window "http://localhost:8080/crud/v1/task/getTasks"
exit
if "%ERRORLEVEL%" == "0" goto end
echo.
echo ---Cannot run firefox.exe
goto fail

:fail
echo.
echo ---There were errors

:end
echo.
echo ---Work is finished
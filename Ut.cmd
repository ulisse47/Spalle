rem
rem Trasferisce il file Maya.data nelle altre directory
rem
@echo off

set fileSP=%USERPROFILE%\AppData\Roaming\.spalle\dev\
set fileIM=%USERPROFILE%\AppData\Roaming\.impalcatograticciosuite\dev\

if not exist %fileSP% goto :Errdev
if not exist %fileIM% goto :Errdev

set fileSP=%USERPROFILE%\AppData\Roaming\.spalle\dev\Maya.data
set fileIM=%USERPROFILE%\AppData\Roaming\.impalcatograticciosuite\dev\Maya.data

if exist %fileSP% ( call :Chek %fileSP% %fileIM% "Spalle")


if exist %fileIM% ( call :Chek %fileIM% %fileSP% "Impalcato")

pause
goto :eof

:Chek

if %~z1  equ 0 ( 
       echo %3 Manca la chiave
) else ( 
	echo %3 Chive disponibile
	copy %1 %2
)
exit /b

:Errdev

Echo Direcotory inesistente
goto :eof

:eof


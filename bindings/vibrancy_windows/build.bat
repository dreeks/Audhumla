@ECHO OFF
g++ -c -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -o vibrancy_windows.o xyz_dreeks_audhumla_jni_JNIVibrancyWindows.c
g++ -shared -o vibrancy_windows.dll xyz_dreeks_audhumla_jni_JNIVibrancyWindows.c vibrancy_windows.def -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32"
pause
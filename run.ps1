$env:Path = "C:\msys64\ucrt64\bin;$env:Path"
$env:COB_CONFIG_DIR = "C:\msys64\ucrt64\share\gnucobol\config"
$JAVA_HOME = "C:\Program Files\Java\jdk-17"

Write-Host "--- 🏦 COMPILANDO CAJARIO CORE ---" -ForegroundColor Cyan

# 1. Java
& javac -h src/main/cobol -d bin src/main/java/com/cajario/bridge/CobolBridge.java src/main/java/com/cajario/api/CajeroAutomatico.java

# 2. DLL (Integrando Includes de Java y COBOL)
$INC_J = "$JAVA_HOME\include"
$INC_W = "$JAVA_HOME\include\win32"

& cobc -z -free -O2 `
    -I"$INC_J" -I"$INC_W" -I"src/main/cobol" `
    src/main/cobol/cajario_core.cbl `
    src/main/cobol/bridge.c `
    -o lib/cajario_native.dll

# 3. Ejecutar
Write-Host "--- EJECUTANDO CAJERO ---" -ForegroundColor Green
$LIB_PROP = "-Djava.library.path=lib"
& java $LIB_PROP -cp bin com.cajario.api.CajeroAutomatico
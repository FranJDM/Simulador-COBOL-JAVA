cat <<EOF > README.md
# CajaRio Core 🏦

Sistema de integración bancaria que conecta una interfaz de usuario en **Java** con un núcleo de procesamiento de transacciones en **COBOL** mediante **JNI (Java Native Interface)** y **C**.

## 🛠 Requisitos técnicos
- **Entorno:** MSYS2 (Terminal UCRT64)
- **Compiladores:** GnuCOBOL 3.x, GCC (UCRT64)
- **Java:** JDK 17 o superior

## 🚀 Compilación y Ejecución (MSYS2)

1. **Configurar entorno:**
   \`\`\`bash
   export JAVA_HOME="/c/Program Files/Java/jdk-17"
   export PATH="\$JAVA_HOME/bin:\$PATH"
   export COB_CONFIG_DIR="/ucrt64/share/gnucobol/config"
   \`\`\`

2. **Compilar e Interconectar:**
   \`\`\`bash
   # Generar clases y cabeceras JNI
   javac -h src/main/cobol -d bin src/main/java/com/cajario/bridge/CobolBridge.java src/main/java/com/cajario/api/CajeroAutomatico.java

   # Compilar lógica COBOL y enlazar con Puente C
   cobc -c -free -O2 src/main/cobol/cajario_core.cbl -o src/main/cobol/cajario_core.o
   gcc -shared -I"\$JAVA_HOME/include" -I"\$JAVA_HOME/include/win32" -I"src/main/cobol" \\
       src/main/cobol/bridge.c src/main/cobol/cajario_core.o \\
       -L/ucrt64/lib -lcob -o lib/cajario_native.dll
   \`\`\`

3. **Ejecutar el sistema:**
   \`\`\`bash
   java -Djava.library.path=lib -cp bin com.cajario.api.CajeroAutomatico
   \`\`\`

## 🧠 Desafíos Técnicos Superados
- **Alineación de memoria:** Implementación de \`#pragma pack(1)\` en C para coincidir con la estructura de datos de COBOL.
- **Interoperabilidad:** Gestión del runtime de COBOL mediante \`cob_init\` en una librería compartida.
EOF

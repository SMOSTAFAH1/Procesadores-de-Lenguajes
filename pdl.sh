#!/bin/bash

# Define the names of the output files to be used/deleted
OUTPUT_FILE_1="ASin_parse.txt"
OUTPUT_FILE_2="ALex_token.txt"
OUTPUT_FILE_3="ASem_ts.txt"

# Check if an argument (input file) was provided
if [ -n "$1" ]; then
    # If an argument is provided, use it as the input file for the Java command
    INPUT_FILE="$1"
    echo "Ejecutando el procesador Java con el fichero de entrada: $INPUT_FILE"
    java -jar procesadorPDL.jar "$INPUT_FILE" "$OUTPUT_FILE_1" "$OUTPUT_FILE_2" "$OUTPUT_FILE_3"
    
    if [ $? -eq 0 ]; then
        echo "Comando Java ejecutado exitosamente."
    else
        echo "Error al ejecutar el comando Java."
    fi
else
    # If no argument is provided, delete the output files
    echo "No se proporcionó ningún fichero. Eliminando archivos generados..."
    rm -f "$OUTPUT_FILE_1" "$OUTPUT_FILE_2" "$OUTPUT_FILE_3"
    
    if [ $? -eq 0 ]; then
        echo "Archivos eliminados exitosamente."
    else
        echo "Error al eliminar los archivos. Puede que no existan."
    fi
fi

exit 0
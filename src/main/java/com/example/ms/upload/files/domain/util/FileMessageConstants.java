package com.example.ms.upload.files.domain.util;

public class FileMessageConstants {

    private FileMessageConstants() {}

    public static final String ERROR_MESSAGE_KEY = "Error_Message: ";
    public static final String ERROR_CODE_MESSAGE_KEY = "Error_Code: ";
    public static final String ERROR_EXCEPTION_NAME_KEY = "Exception_Name: ";

    public static final String MAX_RESERVATION_SEATS_ERR_MSG = "Se ha excedido la cantidad de sillas reservadas";
    public static final String EMPTY_FILE_ERR_MSG = "El archivo se encuentra vacío";
    public static final String MAX_FILE_SIZE_ERR_MSG = "El archivo ha excedido el tamaño permitido";
    public static final String EXT_FILE_ERR_MSG = "El archivo no cuenta con unas extension permitida";
    public static final String INVALID_FILENAME_ERR_MSG = "El nombre del archivo es invalido";
    public static final String PROCESS_FILE_FAILED_ERR_MSG = "Fallo procesando archivo: ";
    public static final String EMPTY_OR_CORRUPTED_FILE_ERR_MSG = "Parece que el archivo se encuentra vacío o corrupto";
    public static final String IO_SAVE_FILE_ERR_MSG = "HA OCURRIDO UN ERROR DURANTE LA ELIMINACIÓN DEL ARCHIVO TEMPORAL";
    public static final String INTERNAL_ERROR_MSG = "Internal server error";

    public static final String ACCEPTED_FILE_MSG = "Archivo aceptado para procesar";
    public static final String VALIDATING_FILE_MSG = "Validando el archivo";
    public static final String FILE_STORED_SUCCESS_MSG = "Archivo almacenado para procesar";
    public static final String FILE_VALIDATED_SUCCESS_MSG = "Archivo almacenado para procesar";

    public static final String RUL_ERR_1 = "RUL_ERR_1";
    public static final String UNEX_ERR_1 = "UNEX_ERR_1";
}

package com.fincons.enums;

public enum ErrorCode{

    //100+(N. ERRORE) RISERVATO PER I DIPENDENTI E USER(ACCOUNT)
    //150+(N. ERRORE) RISERVATO PER GLI ERRORI DI VALIDAZIONE
    //200+(N. ERRORE) RISERVATO PER I FILE
    //500+(N. ERRORE) ERRORI GENERICI DI SISTEMA

    //----------------------------PER GLI USER/DIPENDENTI-------------------------
    EMPLOYEE_ALREADY_EXISTS(1001, Gravity.BLOCKING, "Dipendente esistente", "Il dipendente specificato è già presente."),

    USER_ALREADY_EXISTS(1002, Gravity.BLOCKING, "Utente esistente", "L'account specificato è già esistente."),

    //----------------------------PER LA VALIDAZIONE-------------------------
    MISSING_REQUIRED_FIELD(1501, Gravity.BLOCKING, "Manca un parametro obbligatorio", "Manca un parametro obbligatorio"),
    INVALID_EMAIL(1502, Gravity.WARNING, "Email non valida", "L'indirizzo email non è valido."),

    INVALID_NAME_SURNAME(1503, Gravity.WARNING, "Campo non valido", "Il campo inserito non è valido, controlla che non ci siano numeri e/o caratteri speciali"),

    INVALID_GENRE(1504, Gravity.WARNING, "Campo 'Genere' non valido", "Il campo 'Genere' inserito non è valido, deve corrispondere ai seguenti valori (M= Maschio, F=Femmina, O=Other)"),


    INVALID_DATE(1505, Gravity.BLOCKING, "Campo 'Data' non valido", "Il campo 'Data' inserito non è valido, il dipendente associato non sarà inserito, correggere il campo e riprovare."),
    INVALID_ROW(1506, Gravity.BLOCKING, "Campi non validi", "La riga non risulta valida e verrà ignorata perché i campi fondamentali non rispettano i giusti criteri."),

    EMPTY_LIST_AFTER_VALIDATION(1507, Gravity.BLOCKING, "Nessun dipendente da aggiungere", "La lista dei dipendenti dopo la validazione, risulta vuota."),


    //----------------------------PER I FILE-------------------------
    UNEXPECTED_FORMAT(2001, Gravity.BLOCKING, "Formato sbagliato", "Formato file non supportato. "),
    EMPTY_FILE(2002, Gravity.BLOCKING, "File vuoto", "Il file risulta essere vuoto, si prega di controllare e riprovare."),
    MAXIMUM_ROWS(2003,Gravity.BLOCKING, "Massimo di righe raggiunto", "Il numero di righe nel file supera il limite massimo consentito."),
    FILE_NOT_FOUND(2004, Gravity.BLOCKING, "File non trovato", "file non trovati"),
    ERROR_OPEN_FILE(20010, Gravity.BLOCKING, "Errore sul file","Errore durante l'apertura o la lettura del file. "),

    ERROR_READING_FILE_NUMBER(20011, Gravity.BLOCKING, "Errore formato numerico sul file in lettura","Errore durante la lettura del file. Controllare che i campi nel file siano corretti. (Numeri e Date)."),

    //----------------------------PER ERRORI GENERICI DI SISTEMA-------------------------
    ERROR_IO(5001, Gravity.BLOCKING, "Eccezione I/O","Si è verificata una Eccezione di I/O.");

    public final int errorCode;
    public final Gravity gravity;
    public final String shortDescription;
    public final String longDescription;



    ErrorCode(int errorCode, Gravity gravity, String shortDescription, String longDescription) {
        this.errorCode = errorCode;
        this.gravity = gravity;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }



    public int getErrorCode() {
        return errorCode;
    }

    public Gravity getGravity() {
        return gravity;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }


}

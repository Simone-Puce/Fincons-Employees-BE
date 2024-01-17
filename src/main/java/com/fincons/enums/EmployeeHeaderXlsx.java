package com.fincons.enums;

public enum EmployeeHeaderXlsx {

    Nome(0),
    Cognome(1),
    Genere(2),
    Email(3),
    DataDiNascita(4),
    DataDiInizio(5),
    DataDiFine(6),
    Dipartimento(7),
    Posizione(8);


    private final int index;

    EmployeeHeaderXlsx(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}

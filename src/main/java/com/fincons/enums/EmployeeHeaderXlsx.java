package com.fincons.enums;

public enum EmployeeHeaderXlsx {

    Nome(0),
    Cognome(1),
    Genere(2),

    DataDiNascita(3),

    Email(4),
    DataDiInizio(5),
    DataDiFine(6),
    Dipartimento(7),
    Posizione(8),
    Ssn(9);


    private final int index;

    EmployeeHeaderXlsx(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}

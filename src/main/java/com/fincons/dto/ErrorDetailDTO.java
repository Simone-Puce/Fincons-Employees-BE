package com.fincons.dto;

import com.fincons.enums.ErrorCode;
import com.fincons.enums.Gravity;

public class ErrorDetailDTO {
    private long lineNumber;
    private String column;
    private int errorCode;

    //private ErrorCode errorCodee;
    private Gravity gravity;
    private String shortDescription;
    private String longDescription;

    public ErrorDetailDTO() {

    }

    public ErrorDetailDTO(long lineNumber, String column, int errorCode, Gravity gravity, String shortDescription, String longDescription) {
        this.lineNumber = lineNumber;
        this.column = column;
        this.errorCode = errorCode;
        this.gravity = gravity;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }


    public ErrorDetailDTO(long lineNumber, String column, ErrorCode errorCode) {
        this(lineNumber,column,errorCode.errorCode,errorCode.gravity,errorCode.shortDescription,errorCode.longDescription);
    }

    public ErrorDetailDTO(ErrorCode errorCode){
        this.errorCode= errorCode.getErrorCode();
        this.gravity= errorCode.getGravity();
        this.shortDescription= errorCode.getShortDescription();
        this.longDescription= errorCode.getLongDescription();
    }

    public ErrorDetailDTO(long lineNumber, ErrorCode errorCode, String longDescription) {
        this.lineNumber=lineNumber;
        this.shortDescription=shortDescription;
        this.longDescription= longDescription;
    }

    public ErrorDetailDTO(String longDescription){
        this.longDescription=longDescription;
    }

    public ErrorDetailDTO(int errorCode){
        this.errorCode = errorCode;
    }

    public ErrorDetailDTO(int errorCode, String longDescription){
        this.errorCode=errorCode;
        this.longDescription= longDescription;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Gravity getGravity() {
        return gravity;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }


    @Override
    public String toString() {
        return "ErrorDetail{" +
                "lineNumber=" + lineNumber +
                ", column='" + column + '\'' +
                ", errorCode=" + errorCode +
                ", gravity=" + gravity +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                '}';
    }
}

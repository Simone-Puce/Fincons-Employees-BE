package com.fincons.dto;

import com.fincons.enums.ProcessingStatus;
import java.sql.Timestamp;
import java.util.List;

public class ImportResultDTO {
    private Timestamp executionId;
    private ProcessingStatus status;
    private String filename;

    private Long fileSize;
    private String startProcessingDate;
    private String endProcessingDate;
    private List<ErrorDetailDTO> errors;

    public ImportResultDTO() {

    }

    public ImportResultDTO(Timestamp executionId, ProcessingStatus status, String filename, Long fileSize, String startProcessingDate, String endProcessingDate, List<ErrorDetailDTO> errors) {
        this.executionId = executionId;
        this.status = status;
        this.filename = filename;
        this.fileSize = fileSize;
        this.startProcessingDate = startProcessingDate;
        this.endProcessingDate = endProcessingDate;
        this.errors = errors;
    }

    //costruttore per inizializzazione import
    public ImportResultDTO(Timestamp executionId, String filename, Long fileSize, String startProcessingDate) {
        this.executionId = executionId;
        this.filename = filename;
        this.fileSize = fileSize;
        this.startProcessingDate = startProcessingDate;
    }

    public Timestamp getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Timestamp executionId) {
        this.executionId = executionId;
    }

    public ProcessingStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessingStatus status) {
        this.status = status;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStartProcessingDate() {
        return startProcessingDate;
    }

    public void setStartProcessingDate(String startProcessingDate) {
        this.startProcessingDate = startProcessingDate;
    }

    public String getEndProcessingDate() {
        return endProcessingDate;
    }

    public void setEndProcessingDate(String endProcessingDate) {
        this.endProcessingDate = endProcessingDate;
    }

    public List<ErrorDetailDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetailDTO> errors) {
        this.errors = errors;
    }
}

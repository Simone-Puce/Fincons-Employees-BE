package com.fincons.utility;

import com.fincons.dto.ErrorDetailDTO;
import com.fincons.dto.ImportResultDTO;
import com.fincons.enums.ErrorCode;
import com.fincons.enums.ProcessingStatus;
import java.util.List;

public class ImportErrorUtility {
    private ImportErrorUtility() {
        throw new IllegalStateException("Utility class");
    }
    public static void wrongExtension(List<ErrorDetailDTO> errorList, ImportResultDTO importResult){
        errorList.add(new ErrorDetailDTO(ErrorCode.UNEXPECTED_FORMAT));
        importResult.setErrors(errorList);
        importResult.setStatus(ProcessingStatus.NOT_LOADED);
        importResult.setEndProcessingDate(ImportServiceDateUtility.generateDate());

    }
    public static void emptyFile(List<ErrorDetailDTO> errorList, ImportResultDTO importResult){
        errorList.add(new ErrorDetailDTO(ErrorCode.EMPTY_FILE));
        importResult.setStatus(ProcessingStatus.NOT_LOADED);
        importResult.setEndProcessingDate(ImportServiceDateUtility.generateDate());
        importResult.setErrors(errorList);
    }
    public static void emptyListAfterValidation(List<ErrorDetailDTO> errorList, ImportResultDTO importResult){
        errorList.add(new ErrorDetailDTO(0, null, ErrorCode.EMPTY_LIST_AFTER_VALIDATION));
        importResult.setStatus(ProcessingStatus.NOT_LOADED);
        importResult.setEndProcessingDate(ImportServiceDateUtility.generateDate());
        importResult.setErrors(errorList);
    }
    public static void errorDuringOpenFile(List<ErrorDetailDTO> errorList, ImportResultDTO importResult){
        errorList.add(new ErrorDetailDTO(ErrorCode.ERROR_OPEN_FILE));
        importResult.setErrors(errorList);
        importResult.setStatus(ProcessingStatus.NOT_LOADED);
        importResult.setEndProcessingDate(ImportServiceDateUtility.generateDate());

    }
    public static void errorOfStreamDuringOpenFile(List<ErrorDetailDTO> errorList, ImportResultDTO importResult, Exception ex){

        errorList.add(new ErrorDetailDTO(0, null, ErrorCode.ERROR_IO));
        importResult.setErrors(errorList);
        importResult.setStatus(ProcessingStatus.NOT_LOADED);
        importResult.setEndProcessingDate(ImportServiceDateUtility.generateDate());
    }
}

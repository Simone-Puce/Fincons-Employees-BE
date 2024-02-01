package com.fincons.utility;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.dto.ImportFileDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Position;
import com.fincons.enums.EmployeeHeaderXlsx;
import com.fincons.enums.ErrorCode;
import com.fincons.service.importFile.ImportFileReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XlsxReader implements ImportFileReader {

    private InputStream xlsxInputStream;
    private Reader xlsxReader;
    private static final Logger LOGGER = LoggerFactory.getLogger(XlsxReader.class);

    private int chunkSize;
    private int maxRows;

    public XlsxReader(int chunkSize, int maxRows) {
        this.chunkSize = chunkSize;
        this.maxRows = maxRows;
    }

    @Override
    public List<ErrorDetailDTO> openFile(InputStream file) throws IOException {
        List<ErrorDetailDTO> errorOpenList = new ArrayList<>();
        try {
            xlsxInputStream = file;
            xlsxReader = new InputStreamReader(file);
        } catch (Exception e) {
            errorOpenList.add(new ErrorDetailDTO(ErrorCode.ERROR_OPEN_FILE));
        }
        return errorOpenList;
    }

    @Override
    public ImportFileDTO readFile() {

        List<EmployeeDTO> employeeToAdd = new ArrayList<>();
        List<ErrorDetailDTO> errorReadingList = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(xlsxInputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            int readCount = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                readCount++;
                LOGGER.trace("lastrownum: " + sheet.getLastRowNum());

                if (readCount > maxRows) {
                    LOGGER.warn("Il numero di righe nel file supera il limite massimo consentito. maxrows: " + maxRows);
                    errorReadingList.add(new ErrorDetailDTO(ErrorCode.MAXIMUM_ROWS));
                    return new ImportFileDTO(employeeToAdd, errorReadingList);
                }

                Row row = sheet.getRow(i);

                int lineNumber = row.getRowNum()+1;


                // Verifica se la riga è non è nulla
                if (row != null) {
                    // Ottieni i valori delle celle usando l'enumeratore EmployeeHeaderXlsx
                    String nome = getCellValue(row.getCell(EmployeeHeaderXlsx.Nome.getIndex()));
                    String cognome = getCellValue(row.getCell(EmployeeHeaderXlsx.Cognome.getIndex()));

                    String genere = getCellValue(row.getCell(EmployeeHeaderXlsx.Genere.getIndex()));

                    LocalDate dataDiNascita = null, dataDiInizio = null, dataDiFine = null;


                    String dataNascita = getCellValue(row.getCell(EmployeeHeaderXlsx.DataDiNascita.getIndex()));
                    if (EmployeeDataValidator.isValidDateExcelFile(dataNascita)) {
                        dataDiNascita= processData(dataNascita);
                    }else{
                        errorReadingList.add(new ErrorDetailDTO(lineNumber, "Data Di Nascita", ErrorCode.INVALID_DATE));
                        continue;
                    }

                    String email = getCellValue(row.getCell(EmployeeHeaderXlsx.Email.getIndex()));


                    String dataInizio = getCellValue(row.getCell(EmployeeHeaderXlsx.DataDiInizio.getIndex()));
                    if (EmployeeDataValidator.isValidDateExcelFile(dataInizio)) {
                        dataDiInizio =processData(dataInizio);
                    }else{
                        errorReadingList.add(new ErrorDetailDTO(lineNumber, "Data Di Inizio", ErrorCode.INVALID_DATE));
                        continue;
                    }


                    String dataFine = getCellValue(row.getCell(EmployeeHeaderXlsx.DataDiFine.getIndex()));
                    if (EmployeeDataValidator.isValidDateExcelFile(dataFine)) {
                        dataDiFine =processData(dataFine);
                    }else{
                        errorReadingList.add(new ErrorDetailDTO(lineNumber, "Data Di Fine", ErrorCode.INVALID_DATE));
                        continue;
                    }

                    String dep = getCellValue(row.getCell(EmployeeHeaderXlsx.Dipartimento.getIndex()));
                    Department dipartimento = new Department();
                    dipartimento.setId(Math.round(Double.parseDouble(dep)));

                    String pos = getCellValue(row.getCell(EmployeeHeaderXlsx.Posizione.getIndex()));
                    Position posizione = new Position();
                    posizione.setId(Math.round(Double.parseDouble(pos)));


                    // Crea un oggetto EmployeeDto con i valori ottenuti
                    EmployeeDTO employeeDto = new EmployeeDTO(nome, cognome, genere, dataDiNascita, email, dataDiInizio, dataDiFine, dipartimento, posizione);
                    employeeDto.setRowNum(lineNumber);

                    // Aggiungi l'oggetto EmployeeDto alla lista
                    employeeToAdd.add(employeeDto);
                }
            }
        } catch (NumberFormatException e) {
            errorReadingList.add(new ErrorDetailDTO(ErrorCode.ERROR_READING_FILE_NUMBER));

        } catch (IOException e) {
            LOGGER.error("Si è verificata una eccezione di I/O", e);
            errorReadingList.add(new ErrorDetailDTO(ErrorCode.ERROR_IO));
        }
        return new ImportFileDTO(employeeToAdd, errorReadingList);
    }


    private LocalDate processData(String data){
        double doubleDta = Double.parseDouble(data);
        LocalDate processData = DateUtil.getLocalDateTime(doubleDta).toLocalDate();
        return processData;
    }


    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return null;
        }
    }

    @Override

    public void close() throws IOException {
        try {
            if (xlsxReader != null) {  //da chiedere differenze tra tutte e due i close tra csv e xlsx
                xlsxReader.close();
            }
            if (xlsxInputStream != null) {
                xlsxInputStream.close();
            }
        } catch (IOException e) {
            LOGGER.error("Errore nella chiusura del Reader Xlsx", e);
        }
    }


}


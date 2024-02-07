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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Integer, List<String>> data = new HashMap<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(xlsxInputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            int readCount = 0;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                readCount++;

                if (readCount > maxRows) {
                    LOGGER.warn("Il numero di righe nel file supera il limite massimo consentito. maxrows: " + maxRows);
                    errorReadingList.add(new ErrorDetailDTO(ErrorCode.MAXIMUM_ROWS));
                    return new ImportFileDTO(employeeToAdd, errorReadingList);
                }

                Row row = sheet.getRow(i);
                int rowNum = row.getRowNum();
                data.put(rowNum, new ArrayList<>());

                // Verifica se la riga è non è nulla
                if (row != null) {
                    // Ottieni i valori delle celle usando l'enumeratore EmployeeHeaderXlsx
                    String nome = getCellValue(row.getCell(EmployeeHeaderXlsx.Nome.getIndex()));
                    String cognome = getCellValue(row.getCell(EmployeeHeaderXlsx.Cognome.getIndex()));
                    String genere= getCellValue(row.getCell(EmployeeHeaderXlsx.Genere.getIndex()));

                    String dataNascita = getCellValue(row.getCell(EmployeeHeaderXlsx.DataDiNascita.getIndex()));
                    double data1 = Double.parseDouble(dataNascita);
                    LocalDate dataDiNascita= DateUtil.getLocalDateTime(data1).toLocalDate();

                    String email = getCellValue(row.getCell(EmployeeHeaderXlsx.Email.getIndex()));

                    String dataInizio=getCellValue(row.getCell(EmployeeHeaderXlsx.DataDiInizio.getIndex()));
                    double data2 = Double.parseDouble(dataInizio);
                    LocalDate dataDiInizio= DateUtil.getLocalDateTime(data2).toLocalDate();

                    String dataFine= getCellValue(row.getCell(EmployeeHeaderXlsx.DataDiFine.getIndex()));
                    double data3 = Double.parseDouble(dataFine);
                    LocalDate dataDiFine= DateUtil.getLocalDateTime(data3).toLocalDate();

                    String dep= getCellValue(row.getCell(EmployeeHeaderXlsx.Dipartimento.getIndex()));
                    Department dipartimento = new Department();
                    dipartimento.setId(Math.round(Double.parseDouble(dep)));

                    String pos =getCellValue(row.getCell(EmployeeHeaderXlsx.Posizione.getIndex()));
                    Position posizione = new Position();
                    posizione.setId(Math.round(Double.parseDouble(dep)));


                    // Crea un oggetto EmployeeDto con i valori ottenuti
                    EmployeeDTO employeeDto = new EmployeeDTO(nome, cognome, genere,email, dataDiNascita, dataDiInizio,dataDiFine,dipartimento.getDepartmentId(),posizione.getPositionId());
                    employeeDto.setRowNum(row.getRowNum() + 1);
                    // Aggiungi l'oggetto EmployeeDto alla lista
                    employeeToAdd.add(employeeDto);
                }
            }
        } catch (NumberFormatException e) {
            errorReadingList.add(new ErrorDetailDTO(ErrorCode.ERROR_READING_FILE_NUMBER));
        }catch (IOException e) {
            LOGGER.error("Si è verificata una eccezione di I/O", e);
            errorReadingList.add(new ErrorDetailDTO(ErrorCode.ERROR_IO));
        }
        return new ImportFileDTO(employeeToAdd, errorReadingList);
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


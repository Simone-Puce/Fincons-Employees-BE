package com.fincons.utility;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.dto.ImportFileDTO;
import com.fincons.entity.Department;
import com.fincons.entity.Position;
import com.fincons.enums.EmployeeHeaderCsv;
import com.fincons.enums.ErrorCode;
import com.fincons.service.importFile.ImportFileReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvReader implements ImportFileReader {
    private Reader csvReader;
    private CSVParser csvParser;
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReader.class);
    private int chunkSize;
    private int maxRows;

    public CsvReader(int chunkSize, int maxRows) {
        this.chunkSize = chunkSize;
        this.maxRows = maxRows;
    }

    @Override
    public List<ErrorDetailDTO> openFile(InputStream file) throws IOException {
        List<ErrorDetailDTO> errorOpenList = new ArrayList<>();

        try {
            csvReader = new InputStreamReader(file);
            csvParser = CSVParser.parse(csvReader, CSVFormat.DEFAULT.builder().setHeader(EmployeeHeaderCsv.class).setSkipHeaderRecord(true).build());

        } catch (Exception e) {
            LOGGER.error("Errore durante l'apertura o la lettura del file.", e);
            errorOpenList.add(new ErrorDetailDTO(ErrorCode.ERROR_OPEN_FILE));
        }  //TODO - IOEXCEPTION
        // Chiamare readFile solo se non ci sono errori durante l'apertura del file
        return errorOpenList;
    }

    @Override
    public ImportFileDTO readFile() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (maxRows == 0) {
            maxRows = 500;
        }
        List<EmployeeDTO> employeeToAdd = new ArrayList<>();
        List<ErrorDetailDTO> errorReadingList = new ArrayList<>();

        try {
            if (csvParser == null) {
                LOGGER.error("Errore durante la lettura del file: csvParser è nullo.");
                errorReadingList.add(new ErrorDetailDTO(ErrorCode.ERROR_OPEN_FILE));
                return new ImportFileDTO(employeeToAdd, errorReadingList);
            }

            int readCount = 0;
            Iterator<CSVRecord> fileIterator = csvParser.stream().iterator();


            while (fileIterator.hasNext()) {
                readCount++;

                if (readCount > maxRows) {
                    LOGGER.warn("Il numero di righe nel file supera il limite massimo consentito.");
                    errorReadingList.add(new ErrorDetailDTO(ErrorCode.MAXIMUM_ROWS));
                    return new ImportFileDTO(employeeToAdd, errorReadingList);
                }
                //LEGGO IL RECORD SUCCESSIVO
                CSVRecord record = fileIterator.next();


                long lineNumber=record.getRecordNumber()+1;

                //PER OGNI RECORD PRENDI GLI ATTRIBUTI
                String nome = record.get(EmployeeHeaderCsv.Nome);
                String cognome = record.get(EmployeeHeaderCsv.Cognome);
                String genere = record.get(EmployeeHeaderCsv.Genere);


                LocalDate dataDiNascita=null,dataDiInizio=null,dataDiFine=null;

                String dataNascita = record.get(EmployeeHeaderCsv.DataDiNascita);
                if (EmployeeDataValidator.isValidDate(dataNascita)) {
                     dataDiNascita = LocalDate.parse(dataNascita, formatter);
                }else{
                    errorReadingList.add(new ErrorDetailDTO(lineNumber, "Data Di Nascita", ErrorCode.INVALID_DATE));
                    continue;
                }



                String email = record.get(EmployeeHeaderCsv.Email);

                String dataInizio = record.get(EmployeeHeaderCsv.DataInizio);

                if (EmployeeDataValidator.isValidDate(dataInizio)) {
                     dataDiInizio = LocalDate.parse(dataInizio, formatter);
                }else{
                    errorReadingList.add(new ErrorDetailDTO(lineNumber, "Data Di Inizio", ErrorCode.INVALID_DATE));
                    continue;
                }
                String dataFine = record.get(EmployeeHeaderCsv.DataFine);
                if (EmployeeDataValidator.isValidDate(dataFine)) {
                     dataDiFine = LocalDate.parse(dataFine, formatter);
                }else{
                    errorReadingList.add(new ErrorDetailDTO(lineNumber, "Data Di Nascita", ErrorCode.INVALID_DATE));
                    continue;
                }


                String dep = record.get(EmployeeHeaderCsv.Dipartimento);
                Department dipartimento= new Department();
                dipartimento.setId(Long.parseLong(dep));

                String pos= record.get(EmployeeHeaderCsv.Posizione);

                Position posizione = new Position();
                posizione.setId(Long.parseLong(pos));


                //CREATE EMPLOYEE DTO

                EmployeeDTO personToAdd = new EmployeeDTO(nome, cognome, genere,email, dataDiNascita, dataDiInizio,dataDiFine,dipartimento.getDepartmentCode() , posizione.getPositionCode());
                personToAdd.setRowNum(record.getRecordNumber() + 1);

                //ADD EMPLOYEE TO LIST
                employeeToAdd.add(personToAdd);
            }
        } catch (NumberFormatException e) {
            errorReadingList.add(new ErrorDetailDTO(ErrorCode.ERROR_READING_FILE_NUMBER));
        } catch (Exception e) {
            errorReadingList.add(new ErrorDetailDTO(ErrorCode.ERROR_OPEN_FILE));
        }
        return new ImportFileDTO(employeeToAdd, errorReadingList);
    }

    @Override
    public void close() throws IOException {
        try {
            if (csvReader != null) {
                csvReader.close();
            }
            if (csvParser != null) {
                csvParser.close();
            }
        } catch (IOException e) {
            LOGGER.error("Errore nella chiusura del Reader Csv", e);
        }
    }
}

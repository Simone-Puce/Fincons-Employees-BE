package com.fincons.service.importFile.impl;

import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.dto.ImportFileDTO;
import com.fincons.dto.ImportResultDTO;
import com.fincons.enums.Gravity;
import com.fincons.enums.ProcessingStatus;
import com.fincons.service.employeeService.EmployeeService;
import com.fincons.service.importFile.ImportFileReader;
import com.fincons.service.importFile.ImportService;
import com.fincons.service.importFile.PersistenceEmployeeService;
import com.fincons.utility.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@PropertySource("importfile.properties")
@Service
public class ImportServiceImpl implements ImportService {
    private static final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PersistenceEmployeeService persistenceEmployeeService;

    @Value("${file.max.rows}")
    private int MAX_ROWS;


    @Override
    public ImportResultDTO processImport(MultipartFile file) {

        ImportResultDTO importResult = new ImportResultDTO(ImportServiceDateUtility.generateId(), file.getOriginalFilename(), file.getSize(), ImportServiceDateUtility.generateDate());

        List<ErrorDetailDTO> errorList = new ArrayList<>();

        String ext = FileUtility.getExtension(file.getOriginalFilename());
        ImportFileReader fileReader = null;
        if ("csv".equalsIgnoreCase(ext)) {         // inizializza un csvReader se il file è un csv
            fileReader = new CsvReader(100000, MAX_ROWS);
        } else if ("xlsx".equalsIgnoreCase(ext)) { // inizializza un xlsxReader se il file è un xlsx
            fileReader = new XlsxReader(100000, MAX_ROWS);
        } else {  //comportamento nel caso in cui il formato sia diverso da .csv o .xlsx
            ImportErrorUtility.wrongExtension(errorList, importResult);
            return importResult;
        }

        try {
            List<ErrorDetailDTO> openFileResult = fileReader.openFile(file.getInputStream());

            //caso in cui sono presenti degli errori durante l'apertura del file
            if (!CollectionUtils.isEmpty(openFileResult)) {
                ImportErrorUtility.errorDuringOpenFile(openFileResult, importResult);
                return importResult;
            }
            //altrimenti si può proseguire con la lettura
            ImportFileDTO readResult = fileReader.readFile();
            processReadResult(readResult, importResult);

        } catch (IOException ex) {

            logger.error("Si è verificata una eccezione di I/O. ", ex);

            // Controllo prima che errorList non sia null
            if (errorList == null) {
                errorList = new ArrayList<>();
                importResult.setErrors(errorList);
            }

            ImportErrorUtility.errorOfStreamDuringOpenFile(errorList, importResult, ex);

            // Ritorna l'ImportResult con lo stato di errore
            return importResult;

        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (Exception ex) {
                    logger.error("Errore durante la chiusura del fileReader", ex);
                }
            }
        }
        return importResult;
    }


    private void processReadResult(ImportFileDTO readResult, ImportResultDTO importResult) {
        //LISTA DEI DIPENDENTI
        List<EmployeeDTO> employeeList = readResult.getEmployeeList();

        //LISTA GENERICA DEGLI ERRORI AVVENUT IN TUTTO IL PROCESSO DI IMPORT
        List<ErrorDetailDTO> errorList = readResult.getErrorList();

        //1.--------CONTROLLO SE LA LISTA E' VUOTA PRIMA DI ESEGUIRE LE RESTANTI OPERAZIONI--------
        if (employeeList.isEmpty()) {
            ImportErrorUtility.emptyFile(errorList, importResult);
        } else { //SE NON è VUOTA  SI CONTINUA CON TUTTO IL PROCESSO
            //--------ITERAZIONE SULLA LISTA DEI DIPENDENTI PER VALIDARE I RISPETTIVI CAMPI RIPORTANDO EVENTUALI ERRORI NELL'APPOSITA LISTA--------
            for (EmployeeDTO employee : employeeList)
                errorList.addAll(EmployeeDataValidator.isValidEmployee(employee));

            // CONTROLLO DELLA PRESENZA DI ERRORI BLOCCANTI, SE PRESENTI, VENGONO RIMOSSI I RECORD INTERESSATI
            Iterator<EmployeeDTO> iterator = employeeList.iterator();
            while (iterator.hasNext()) {
                EmployeeDTO employee = iterator.next();
                long nrow = employee.getRowNum();
                if (errorList.stream().anyMatch(error -> error.getLineNumber() == nrow && error.getGravity() == Gravity.BLOCKING)) {
                    iterator.remove();
                }
            }


            // 2.--------CONTROLLO SE LA LISTA E' VUOTA DOPO LE VALIDAZIONI--------
            if (employeeList.isEmpty()) {
                ImportErrorUtility.emptyListAfterValidation(errorList, importResult);
            } else {

                //Si passa alla persistenza degli impiegati, ritornando eventualmente una lista di errori se qualche dipendente è già presente.
                //SETTANDO IN UNA APPOSITA LISTA, I DUPLICATI TROVATI DURANTE L'INSERIMENTO NEL DB.
                List<ErrorDetailDTO> duplicatedEmployee = persistenceEmployeeService.addIfNotPresent(employeeList);
                //setto la lista generale per ritornarla comunque nel corpo della risposta di tutti gli errori
                errorList.addAll(duplicatedEmployee);

                // Aggiorna l'oggetto ImportResult con gli errori riscontrati nel processo di importazione
                importResult.setErrors(errorList);

                if (importResult.getErrors().size() > 0 && employeeList.size() > 0) {
                    //se vengono trovati N dipendenti duplicati tanto quanti N dipendenti da aggiungere
                    //l'import result sarà not loaded dato che nessun dipendente verrà aggiunto al db.
                    if (duplicatedEmployee.size() == employeeList.size()) {
                        importResult.setStatus(ProcessingStatus.NOT_LOADED);
                    } else {
                        importResult.setStatus(ProcessingStatus.LOADED_WITH_ERRORS);
                    }

                } else if (importResult.getErrors().size() == 0 && employeeList.size() > 0) {
                    importResult.setStatus(ProcessingStatus.LOADED);
                } else if (importResult.getErrors().size() == 0 && employeeList.size() == 0) {
                    importResult.setStatus(ProcessingStatus.NOT_LOADED);
                } else if (importResult.getErrors().size() > 0 && employeeList.size() == 0) {
                    importResult.setStatus(ProcessingStatus.NOT_LOADED);
                }
                importResult.setEndProcessingDate(ImportServiceDateUtility.generateDate());
        }


        }
    }

}




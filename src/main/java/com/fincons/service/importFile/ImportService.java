package com.fincons.service.importFile;

import com.fincons.dto.ImportResultDTO;
import com.fincons.exception.EmailException;
import org.springframework.web.multipart.MultipartFile;

public interface ImportService {
    ImportResultDTO processImport(MultipartFile file);
}

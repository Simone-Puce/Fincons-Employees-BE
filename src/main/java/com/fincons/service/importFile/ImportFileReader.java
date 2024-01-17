package com.fincons.service.importFile;

import com.fincons.dto.ErrorDetailDTO;
import com.fincons.dto.ImportFileDTO;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ImportFileReader extends Closeable {
     List<ErrorDetailDTO> openFile(InputStream file) throws IOException;
     ImportFileDTO readFile();
}

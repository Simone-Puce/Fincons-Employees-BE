package com.fincons.utilities;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Component

public class EncodingFile {

    public String encodeFile(String filePath) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        return (Base64.getMimeEncoder().encodeToString(bytes));
    }

}

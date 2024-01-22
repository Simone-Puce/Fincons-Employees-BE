package com.fincons.utility;

import org.apache.commons.io.FilenameUtils;

public class FileUtility {
    private FileUtility() {
        throw new IllegalStateException("Utility class");
    }
    public static String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }
}

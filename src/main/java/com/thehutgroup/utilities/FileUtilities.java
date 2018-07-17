package com.thehutgroup.utilities;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

public class FileUtilities {

    public static void fileCopy(String src, String target) throws IOException {
        FileCopyUtils.copy(new File(src), new File(target));
    }

    public static void writeStringToFile(String filename, String value) throws IOException {

        FileUtils.writeStringToFile(new File(filename), value, "UTF-8");
    }

    public static void appendStringToFile(String filename, String value) throws IOException {

        FileUtils.writeStringToFile(new File(filename), value, "UTF-8", true);
    }

    public static String writeFileToString(String filename) throws IOException {

        String value = FileUtils.readFileToString(new File(filename), "UTF-8");

        return value;
    }

    public static void copyAllFilesFromSrcDirToTargetDir(String src, String target) throws IOException {

        File source = new File(src);
        File dest = new File(target);

        if (source.isDirectory() && dest.isDirectory()) {
            FileUtils.copyDirectory(source, dest);
        } else {

        }
    }

    public static void deleteFile(String file) {
        FileUtils.deleteQuietly(new File(file));
    }

    public static void deleteDirectory(File dir) throws IOException { FileUtils.deleteDirectory(dir); }
}

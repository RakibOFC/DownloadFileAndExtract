package com.rakibofc.downloadfiledemo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageUtils {

    public static List<File> getAllImagesFromDirectory(String directoryPath) {
        List<File> imageFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isImageFile(file.getName())) {
                        imageFiles.add(file);
                    }
                }
            }
        }

        return imageFiles;
    }

    private static boolean isImageFile(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".png");
        // Add more image file extensions if needed
    }
}
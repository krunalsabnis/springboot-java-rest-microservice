package com.kru.qualibrate.commons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;


/**
 * To read a File from Jar. using Spring reseourceLoader works well when
 * file system hence in local/eclipse. but fails when run as jar. to read file from jar use
 * inutStream as below.
 *
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
public class ResourceToFile {

    public static File getResourceAsFile(String resourcePath) {
        try {
            InputStream in = new ClassPathResource(resourcePath).getInputStream();
            if (in == null) {
                return null;
            }
            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();
            Files.copy(in, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

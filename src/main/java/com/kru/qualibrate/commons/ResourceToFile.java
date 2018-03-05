/**
 * 
 */
package com.kru.qualibrate.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

/**
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

       try (FileOutputStream out = new FileOutputStream(tempFile)) {
           //copy stream
           byte[] buffer = new byte[1024];
           int bytesRead;
           while ((bytesRead = in.read(buffer)) != -1) {
               out.write(buffer, 0, bytesRead);
           }
       }
       return tempFile;
   } catch (IOException e) {
       e.printStackTrace();
       return null;
   }
}
}

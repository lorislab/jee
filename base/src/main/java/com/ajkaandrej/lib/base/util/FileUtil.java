/*
 * Copyright 2011 Andrej Petras <andrej@ajka-andrej.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ajkaandrej.lib.base.util;

import java.io.*;

/**
 * The file utility class.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class FileUtil {

    /**
     * The default constructor.
     */
    private FileUtil() {
    }

    /**
     * Reads the file as byte array.
     *
     * @param filePath the file path.
     * @return the byte array corresponding to the file.
     * @throws IOException if the method fails.
     */
    public static byte[] readFileAsByteArray(String filePath) throws IOException {
        return readFileAsByteArray(filePath, FileUtil.class.getClassLoader());
    }

    /**
     * Reads the file as byte array.
     *
     * @param filePath the file path.
     * @param loader the class loader.
     * @return the byte array corresponding to the file.
     * @throws IOException if the method fails.
     */
    public static byte[] readFileAsByteArray(String filePath, ClassLoader loader) throws IOException {

        byte result[] = null;
        ByteArrayOutputStream out = null;
        try {

            InputStream in = loader.getResourceAsStream(filePath);
            out = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
            }
            out.flush();

            result = out.toByteArray();

        } finally {
            if (out != null) {
                out.close();
            }
        }

        return result;
    }

    /**
     * Reads the file as string.
     *
     * @param filePath the file path.
     * @return the string corresponding to the file.
     * @throws IOException if the method fails.
     */
    public static String readFileAsString(String filePath) throws IOException {
        return readFileAsString(filePath, FileUtil.class.getClassLoader());
    }

    /**
     * Reads the file as string.
     *
     * @param filePath the file path.
     * @param loader the class loader.
     * @return the string corresponding to the file.
     * @throws IOException if the method fails.
     */    
    public static String readFileAsString(String filePath, ClassLoader loader) throws IOException {

        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = null;
        try {
            InputStream in = loader.getResourceAsStream(filePath);
            reader = new BufferedReader(new InputStreamReader(in));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return fileData.toString();
    }
}

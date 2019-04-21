package com.hoangpm.botsearchgooglediscord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtils {

    public void saveFile(String data, String fileName) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);
            fw.write(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fw != null)
                    fw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public String readFile(String fileName) {
        String s = "";
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            file = new File(fileName);
            if (file.exists()) {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String line = "";
                while ((line = br.readLine()) != null) {
                    return line;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return s;
    }
}

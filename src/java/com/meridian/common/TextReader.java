package com.meridian.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Reticence (liuyang_blue@qq.com)
 * @date 2017年8月30日 下午12:00:44
 * @version 1.0
 * @parameter
 */
public class TextReader {
    
    private static TextReader reader = null;
    
    private StringBuffer text = new StringBuffer();
    
    private TextReader() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(this.getClass().getResource("/text.txt").getPath())));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static synchronized TextReader getReader() {
        if (reader == null) {
            reader = new TextReader();
        }
        return reader;
    }

    public String getText() {
        return text.toString();
    }
}

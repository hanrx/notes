package com.iafoot.notes.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 网页抓取
 * @author iAfoot
 * @Create 20210208 13:40
 * @Version 1.0.0
 */
public class WebpageCrawling extends  Thread{
    public static void main(String[] args)
            throws Exception {
        URL url = new URL("http://www.runoob.com");
        BufferedReader reader = new BufferedReader
                (new InputStreamReader(url.openStream()));
        BufferedWriter writer = new BufferedWriter
                (new FileWriter("data.html"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            writer.write(line);
            writer.newLine();
        }
        reader.close();
        writer.close();
    }


}

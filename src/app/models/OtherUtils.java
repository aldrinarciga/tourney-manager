package app.models;

import java.io.*;

/**
 * Created by samsung on 1/25/2016.
 */
public class OtherUtils {

    public static void writeFile(String fileName, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(fileName));
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

    public static String readFile(String filename) throws Exception {
        String result = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        result = sb.toString();
        return result;
    }

}

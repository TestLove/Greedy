import java.io.*;
import java.nio.CharBuffer;
import java.util.Properties;
import java.util.Scanner;


public class FileOperation {
String name="Untiled";
    public void SaveFile(String contains) throws IOException {

        Properties properties = System.getProperties();
        String Filepath = properties.getProperty("user.dir");
        File file = new File(Filepath+"\\"+name);
        FileWriter fw = new FileWriter(file);
        fw.write(contains);
        fw.close();

    }

    public void OpenFile(String contains) throws IOException {
        File file=new File("");
        FileReader fr=new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String Line=br.readLine();
        while(Line!=null){
            contains+=Line;
            Line=br.readLine();
        }

    }


}

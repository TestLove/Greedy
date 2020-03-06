
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

    public class fileDemo {
    public void mkdir(File file)  {
        file.mkdirs();
        System.out.println("文件夹已经创建完毕,\n"+" 路径为"+file.getAbsolutePath());
    }
    public File[] ls(File file){
       File f[] = file.listFiles();
       if(f!=null){
           for(File a:f)
           System.out.println(a);
       }
       return f;


    }
    public String path(File file,String currentFilepath,Scanner scan){
        boolean flag = false;
        String Filepath = scan.next();
        File[]f = file.listFiles();
        if(currentFilepath.charAt(currentFilepath.length()-1)!='\\'&Filepath.length()>3){
            Filepath = '\\'+Filepath;}
        System.out.println(Filepath);
        for(File F:f){
            if(F.getAbsolutePath().equals(currentFilepath+Filepath)){
                flag = true;
                break;
            }
        }
        if(flag){
            currentFilepath = currentFilepath+Filepath;
        }
        else{
            currentFilepath = Filepath;
        }
        return currentFilepath;
    }
    public void pwd(File file){
        String path = file.getAbsolutePath();
        System.out.println("路径为"+path);

    }
    public void cd(File file){
        System.out.println("已跳转："+file.getAbsolutePath());
    }
    public void cat(File file) throws IOException {
        FileReader fr = new FileReader(file);
        char [] b = new char[1024];
        int count = fr.read(b);
        while(count!=-1){
            System.out.println(b);
            count = fr.read(b);
        }
        fr.close();

    }
    public void cp(File file1,Scanner scan,String newpath) throws IOException {

        File file2 = new File(newpath);
        if(file2.isDirectory()|file1.isDirectory()){
            System.out.println("路径终点应该为文件而非文件夹");
        }
        FileInputStream fis = new FileInputStream(file1);
        FileOutputStream fos = new FileOutputStream(file2);
        byte[]info = new byte[1024];
        int count = fis.read(info);
        while(count!=-1){
            fos.write(info,0,count);
            count = fis.read(info);
        }
        fis.close();
        fos.close();
        System.out.println("文件复制完成");
    }
    public void echo(String str,Scanner scan){
        System.out.println("进行管道操作请输入|，否则输入任意数");
        String a = scan.next();
        if(a.equals("|")){
            String regex = scan.next();//用于输入grep满足格式，并无其他作用
            grep(scan,str);
        }
        else{System.out.println(str);}
    }
    public void grep(String str, String newpath) throws IOException {
        File file = new File(newpath);
        Pattern pattern = Pattern.compile(str);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        System.out.println("匹配结果为：");
        while(line!=null){
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()){
                System.out.println(line);
            }
            line = br.readLine();
        }

    }
    public void grep(Scanner scan,String str){
        String regex = scan.next();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
            System.out.println(str);
        }else{
            System.out.println("未成功匹配");
        }

    }

    public static void main(String[] args) throws IOException {
        String newpath = null;
        Scanner scan = new Scanner(System.in);
        fileDemo f1 = new fileDemo();
        Properties properties = System.getProperties();
        String currentFilepath = properties.getProperty("user.dir");
        while(true){
        System.out.println("输入命令：");
        String str = scan.next();
        if(str.equals("echo")){
            String s = scan.next();
            f1.echo(s,scan);
        }else {
            if(str.equals("exit")){
                exit(0);
            }

            File file = new File(currentFilepath);

            switch (str) {
                case "mkdir":
                    newpath = currentFilepath+scan.next();
                    File file1 = new File(newpath);
                    f1.mkdir(file1);
                    break;
                case "ls":

                    f1.ls(file);
                    break;
                case "cd":
                    currentFilepath = f1.path(file,currentFilepath,scan);

                    file = new File(currentFilepath);
                    f1.cd(file);
                    break;
                case "cp":
                    String newpath1 = f1.path(file,currentFilepath,scan);
                    File file2 = new File(newpath1);
                    String newpath2 = f1.path(file,currentFilepath,scan);
                    f1.cp(file2, scan,newpath2);
                    break;
                case "pwd":
                    f1.pwd(file);
                    break;
                case "cat":
                    f1.cat(file);
                    break;
                case "grep":
                    String s = scan.next();
                    newpath = f1.path(file,currentFilepath,scan);
                    f1.grep(s, newpath);
                    break;
                default:
                    System.out.println("没有这个命令");
            }
        }

        }




    }
}

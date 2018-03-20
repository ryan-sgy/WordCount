import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by jinqi on 2018/3/19.
 */

//Scanner传递参数

public class Run {
    public static String removeFilePath(String fileName){
        if(fileName.matches("^[A-z]:\\\\\\S+$"))                                          // 正则匹配绝对路径
            fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length()); // 获取文件名
        return fileName;
    }

    public static String getPath(String fileName){
        String name = removeFilePath(fileName);
        return fileName.replace("\\"+name,"");                             // 获取路径
    }

//    public static ArrayList<File> getLegalFile(String Directory,String resourceFileName)
//    {
//        String regex = resourceFileName.replace("*", "[0-9A-z]*");
//        ArrayList<File> fileList=new ArrayList<>();
//        File file = new File(Directory);
//        if(file.isFile() && file.getName().matches(regex)) {
//            fileList.add(file);
//            return fileList;
//        }
//        else if(file.isDirectory()){
//            File[] files=file.listFiles();
//            for(File f : files) {
//                if(f.getName().matches(regex)){
//                    fileList.add(f);
//                }
//            }
//        }
//        return  fileList;
//    }

    public static ArrayList<File> getLegalFile(String directory,String resourceFileName)  // directory是文件路径，resourceFileName是文件名，支持*通配符
    {
        String regex = resourceFileName.replace("*", "[0-9A-z]*"); // 通配符替换
        ArrayList<File> fileList = new ArrayList<>();
        File file = new File(directory);
        try{
            if(file.isDirectory()){
                File[] files = file.listFiles();                                          // 获取directory路径下的所有文件组成File[]
                for(File val : files){
                    if(val.getName().matches(regex) && val.isFile()){                     // File[]中的文件名等于或满足通配符要求，添加到ArrayList中
                        fileList.add(val);
                    }
                }
            }
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        return  fileList;
    }

    public static HashSet<String> stopList(File stopFile){
        HashSet<String> stopList = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(stopFile));
            String temp;
            String Str = "";
            while((temp = reader.readLine())!=null) { // 读每一行
                Str += temp;
            }
            String[] words = Str.split(" ");  // 按空格分割单词
            for(String val:words){
                stopList.add(val);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return stopList;
    }

//    public static void main(String args[]){
//        WordCount wc = new WordCount("D:/idea-java/WordCount/src/res/ttt.txt");
////        System.out.print(args[0]);
////        if(args[0].equals("-c"))
////            System.out.print(wc.getWordNum());
////        else
////            System.out.print("null");
//        Scanner sc = new Scanner(System.in);
//        ArrayList<String> list = new ArrayList<>();
//        String str = sc.nextLine();
//        for(String val : str.split(" "))
//            list.add(val);
//        if(list.get(1).equals("-c"))
//            System.out.print(wc.getWordNum());
//        else
//            System.out.print("null");
//    }

    public static void main(String args[]){
        boolean isC = false, isW = false, isL = false, isO = false, isS = false, isA = false, isE = false;  // 是否进行相应操作，默认否
        String resourceFileName = "", outputFileName, stopFileName, directory = "";
        File stopFile = null, outputFile = null;
        String curPath = System.getProperty("user.dir");  // 获取当前路径
        WordCount wc;
        HashSet<String> stopList = null;

//        System.out.println("testing");

        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        String str = sc.nextLine();
        for(String val : str.split(" "))
            list.add(val);
        String [] strList = list.toArray(new String[list.size()]);
        for(int i = 1; i < strList.length; ++i){
            if(strList[i].equals("-c"))
                isC = true;
            else if(strList[i].equals("-w"))
                isW = true;
            else if(strList[i].equals("-l"))
                isL = true;
            else if(strList[i].equals("-o")){
                if(i < strList.length - 1){
                    isO = true;
                    outputFileName = strList[++i];
                    if(outputFileName.contains(":\\")){                                    // 若是绝对路径，则作为文件路径
                        outputFile = new File(outputFileName);
                    }
                    else{
                        outputFile = new File(curPath + "\\" + outputFileName); // 若是相对路径，将当前路径和相对路径拼接获得文件路径
                    }
                }
            }
            else if(strList[i].equals("-s"))
                isS = true;
            else if(strList[i].equals("-a"))
                isA = true;
            else if(strList[i].equals("-e")){
                if(i < strList.length - 1){
                    isE = true;
                    stopFileName = strList[++i];
                    if(stopFileName.contains(":\\")){                                      // 若是绝对路径，则作为文件路径
                        stopFile = new File(stopFileName);
                    }
                    else{
                        stopFile = new File(curPath + "\\" + stopFileName);     // 若是相对路径，将当前路径和相对路径拼接获得文件路径
                    }
                    stopList = stopList(stopFile);
                }
            }
            else{
                resourceFileName = strList[i];
                if(resourceFileName.contains(":\\")){
                    resourceFileName = removeFilePath(resourceFileName);                   // 获取文件名
                    directory = getPath(strList[i]);                                       // 获取文件路径
                }
                else{
                    directory = curPath;
                }
            }
        }
        try{
            ArrayList<File> fileList;
            fileList = getLegalFile(directory, resourceFileName);
//            fileList = getLegalFile("D:/idea-java/WordCount/src", "*.c");
//            String output = "" + fileList.size();
            String output = "";
            if(fileList.size() == 0 || (!isS && fileList.size() > 1)){
                System.out.println("something wrong!" + directory + "\\" +resourceFileName);
                return;
            }
            for(File val : fileList){
                String name = val.getName();
                if(val.equals(stopFile) || val.equals(outputFile)){
                    continue;
                }
                if(isE){
                    wc = new WordCount(val, stopList);
                }
                else{
                    wc = new WordCount(val);
                }
                if(isC){
                    output = output + name + ",字符数: " + wc.getCharNum() + "\r\n";
                }
                if(isW){
                    output = output + name + ",单词数: " + wc.getWordNum() + "\r\n";
                }
                if(isL){
                    output = output + name + ",行数: " + wc.getLine() + "\r\n";
                }
                if(isA){
                    output = output + name + ",代码行/空行/注释行: " + wc.getCodeLine() + "/" + wc.getEmptyLine() + "/" + wc.getNoteLine() + "\r\n";
                }
            }
//            System.out.println(output);
            BufferedWriter out = new BufferedWriter(new FileWriter(new File(curPath + "\\result.txt")));
            out.write(output);
            out.flush();
            out.close();
            if(isO){
                outputFile.createNewFile(); // 创建新文件
                out = new BufferedWriter(new FileWriter(outputFile));
                out.write(output);          // \r\n即为换行
                out.flush();                // 把缓存区内容压入文件
                out.close();                // 关闭文件
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}

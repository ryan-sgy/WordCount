import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by jinqi on 2018/3/18.
 */

//args传递参数
//-c 字符数 -w 单词数 -l 行数 -o 输出文件 -s 所有文件 -a 复杂信息 -e 停用词表
//wc.exe -s -a –c -w *.c –e stop.txt –o output.txt

public class Main {
    public static String removeFilePath(String fileName){
        if(fileName.matches("^[A-z]:\\\\\\S+$"))
            fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
        return fileName;
    }

    public static String getPath(String fileName){
        String name = removeFilePath(fileName);
        return fileName.replace("\\"+name,"");
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

    public static ArrayList<File> getLegalFile(String directory,String resourceFileName)
    {
        String regex = resourceFileName.replace("*", "[0-9A-z]*");
        ArrayList<File> fileList = new ArrayList<>();
        File file = new File(directory);
        try{
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File val : files){
                    if(val.getName().matches(regex) && val.isFile()){
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
            while((temp = reader.readLine())!=null) {
                Str += temp;
            }
            String[] words = Str.split(" ");
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

//    public static void main(String args[]){
//        boolean isC = false, isW = false, isL = false, isO = false, isS = false, isA = false, isE = false;
//        String resourceFileName = "", outputFileName, stopFileName, directory = "";
//        File stopFile = null, outputFile = null;
//        String curPath = System.getProperty("user.dir");
//        WordCount wc;
//        HashSet<String> stopList = null;
//        for(int i = 0; i < args.length; ++i){
//            if(args[i].equals("-c"))
//                isC = true;
//            else if(args[i].equals("-w"))
//                isW = true;
//            else if(args[i].equals("-l"))
//                isL = true;
//            else if(args[i].equals("-o")){
//                if(i < args.length - 1){
//                    isO = true;
//                    outputFileName = args[++i];
//                    if(outputFileName.contains(":\\")){
//                        outputFile = new File(outputFileName);
//                    }
//                    else{
//                        outputFile = new File(curPath + "\\" + outputFileName);
//                    }
//                }
//            }
//            else if(args[i].equals("-s"))
//                isS = true;
//            else if(args[i].equals("-a"))
//                isA = true;
//            else if(args[i].equals("-e")){
//                if(i < args.length - 1){
//                    isE = true;
//                    stopFileName = args[++i];
//                    if(stopFileName.contains(":\\")){
//                        stopFile = new File(stopFileName);
//                    }
//                    else{
//                        stopFile = new File(curPath + "\\" + stopFileName);
//                    }
//                    stopList = stopList(stopFile);
//                }
//            }
//            else{
//                resourceFileName = args[i];  //问题在于 args[i]中含有*时自动匹配//////////////////////////////////////////////////
//                System.out.println(args[0]);
//                if(resourceFileName.contains(":\\")){
//                    resourceFileName = removeFilePath(resourceFileName);
//                    directory = getPath(args[i]);
//                }
//                else{
//                    directory = curPath;
//                }
//            }
//        }
//        try{
//            ArrayList<File> fileList = new ArrayList<>();
////            fileList = getLegalFile(directory, resourceFileName);
//            fileList = getLegalFile("D:/idea-java/WordCount/src", "*.c");///////////////////////
//            String output = "" + fileList.size();
//            if(fileList.size() == 0 || (!isS && fileList.size() > 1)){
//                System.out.println("something wrong!" + directory + resourceFileName);
//                return;
//            }
//            for(File val : fileList){
//                String name = val.getName();
//                if(val.equals(stopFile) || val.equals(outputFile)){
//                    continue;
//                }
//                if(isE){
//                    wc = new WordCount(val, stopList);
//                }
//                else{
//                    wc = new WordCount(val);
//                }
//                if(isC){
//                    output = output + name + ",字符数: " + wc.getCharNum() + "\r\n";
//                }
//                if(isW){
//                    output = output + name + ",单词数: " + wc.getWordNum() + "\r\n";
//                }
//                if(isL){
//                    output = output + name + ",行数: " + wc.getLine() + "\r\n";
//                }
//                if(isA){
//                    output = output + name + ",代码行/空行/注释行: " + wc.getCodeLine() + "/" + wc.getEmptyLine() + "/" + wc.getNoteLine() + "\r\n";
//                }
//            }
//            System.out.println(output);
//            if(isO){
//                outputFile.createNewFile(); // 创建新文件
//                BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
//                out.write(output); // \r\n即为换行
//                out.flush(); // 把缓存区内容压入文件
//                out.close(); // 最后记得关闭文件
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
}

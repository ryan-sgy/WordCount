import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

//统计单词个数 奇葩规则：空格和，分开的均属于一个单词

/**
 * Created by jinqi on 2018/3/18.
 */
public class WordCount {
//    private String resourceFile;
    private int line;
    private char[] buffer;
    private int wordNum;
    private int charNum;
    private int noteLine;
    private int emptyLine;
    private int codeLine;

    public char[] getBuffer(){
        return buffer;
    }

    public int getLine(){
        return line;
    }

//    public String getResourceFile(){
//        return resourceFile;
//    }

    public int getWordNum(){
        return wordNum;
    }

    public int getCharNum(){
        return charNum;
    }

    public int getNoteLine(){return noteLine;}

    public int getEmptyLine(){return emptyLine;}

    public int getCodeLine(){return codeLine;}

    public static int kindOfLine(String line){ //-1 empty 1 code 0 note
        String temp ="";
        if(line.trim().length() == 0)
            return -1;
        if(!line.contains("//") && !line.contains("/*") && !line.contains("*/"))  //不含这些符号必是code
            return 1;
        if(line.contains("//"))                                                   // //之前的code
            temp = line.split("//")[0];
        if(line.contains("/*"))                                                   // /*之前的code
            temp = line.split("/\\*")[0];
        if(line.contains("*/"))                                                   // */之后的code
            temp = line.split("\\*/")[line.split("\\*/").length - 1];
        if(temp.matches(".*\\w+.*"))                                      // 含有字母或数字则该行是code
            return 1;
        return 0;
    }

    public WordCount(File file) {
        BufferedReader bf;
//        this.resourceFile = resourceFile;
        try {
            bf = new BufferedReader(new FileReader(file));
            String temp1, temp2 = "";
            while((temp1 = bf.readLine()) != null) {
                temp2 += temp1 + String.valueOf('\n');
                line++;                                         // 获取行数
                int i = kindOfLine(temp1);                      // 获取行数详细信息
                if(i == -1)
                    emptyLine++;
                else if(i == 1)
                    codeLine++;
                else
                    noteLine++;
                for (String val: temp1.split(" |,")){
                    wordNum += val.equals("") ? 0 : 1;          // 非空单词数
//                    System.out.println(val.equals("") ? "[space]" : val);
                }
//                for(String val : temp1.split("[\\s,]+"))
//                    wordNum ++;
            }
            buffer = temp2.toCharArray();
            bf.close();
            charNum = buffer.length == 0 ? 0 : buffer.length - 1;                        // 字符数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public WordCount(File file) {
//        BufferedReader bf;
//        this.resourceFile = resourceFile;
//        try {
//            bf = new BufferedReader(new FileReader(file));
//            String temp1, temp2 = "";
//            while((temp1 = bf.readLine()) != null) {
//                temp2 += temp1 + String.valueOf('\n');
//                line++;
//                if(temp1.contains("//") || temp1.contains("/*") || temp1.contains("*/"))
//                    noteLine++;
//                else if(temp1.trim().length() == 0)
//                    emptyLine++;
//                else
//                    codeLine++;
//                for (String val: temp1.split(" |,")){
//                    wordNum += val.equals("") ? 0 : 1;
////                    System.out.println(val.equals("") ? "[space]" : val);
//                }
//            }
//            buffer = temp2.toCharArray();
//            bf.close();
//            charNum = buffer.length;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public WordCount(File file, HashSet<String> stopList) {
        BufferedReader bf;
//        this.resourceFile = resourceFile;
        try {
            bf = new BufferedReader(new FileReader(file));
            String temp1, temp2 = "";
            while((temp1 = bf.readLine()) != null) {
                temp2 += temp1 + String.valueOf('\n');
                line++;
                int i = kindOfLine(temp1);
                if(i == -1)
                    emptyLine++;
                else if(i == 1)
                    codeLine++;
                else
                    noteLine++;
                for (String val: temp1.split(" |,")){
                    wordNum += val.equals("") || stopList.contains(val) ? 0 : 1;
//                    System.out.println(val.equals("") ? "[space]" : val);
                }
            }
            buffer = temp2.toCharArray();
            bf.close();
            charNum = buffer.length;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String args[]){
        WordCount wc = new WordCount(new File("D:\\idea-java\\WordCount\\src\\res\\ttt.txt"));
        System.out.println( wc.getCodeLine() + " " + wc.getEmptyLine() + " " + wc.getNoteLine() + " " + System.getProperty("user.dir"));
        System.out.println(wc.getCharNum() +" " + wc.getWordNum());

    }

}

package oop.ex6.main;

import oop.ex6.main.method.mainMethod;
//import src.method.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * get file and filter the irrelevant lines, and parse the file to section of variables , and methods
 */
public class FirstFilter {
    /** global object */
    private GlobalVar globalVar = new GlobalVar();

    /** main src.method object */
    private mainMethod method = new mainMethod(globalVar);
    /** contains section of lines, every arrayList represent one src.method */
    private ArrayList<ArrayList<String>> methodsArray = new ArrayList<>();
    /** all line of variables */
    private ArrayList<String> variables = new ArrayList<>();



    /**
     * filter the empty lines and the doc lines
     * @param line line to check
     * @return true if empty or doc, false if other line
     */
    boolean analysisFile1(String line){
        return (!checkEmpty(line) && !checkDocStr(line));
    }

    /**
     * parse the file to two sections - one methods, one variables
     * @param all all lines
     * @return 1 if ok, -1 if not
     */
    int analysisFile2(ArrayList<String> all) {
        if (checkIfMethod(all) == -1)
            return -1;
        try {
            globalVar.checkVariable(variables);
            method.checkMethod(methodsArray);
            return 1;
        }catch (Exception d){
            return -1;
        }
    }

    /**
     * use to check if found or not
     * @param line line to check
     * @param pattern pattern
     * @return true if find false if not
     */
    private boolean doMatch(String line, String pattern){
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);
        return m.find();
    }
    /**
     * check if the line is docs
     * @param line line
     * @return any thing if ok, no match if not
     */
    private boolean checkDocStr(String line) {
        String result = line.replaceAll("[\\s]", "");
        return  (result.startsWith("//"));
    }

    /**
     * check if the line is empty
     * @param line line
     * @return any thing if ok, no match if not
     */
    private boolean checkEmpty(String line) {
        return (line.replaceAll("[ \\t]*", "").equals(""));
    }
    /**
     * check if the line start with the word "void", and parse the src.method section
     * @param all line
     */
    private int checkIfMethod(ArrayList<String> all) {
        for (int i = 0; i < all.size(); i++) {
            String regex = "^[   \\t ]*void[    \\t ]+";
            if (doMatch(all.get(i), regex)) {
                int num = methodSection(all, i);
                if (num == -1) {
                    return -1;
                }
                i = num;
                continue;
            }
            variables.add(all.get(i));
        }
        return 1;
    }

    /**
     * create section of the line of src.method, and add it to array
     * @param all all file
     * @param num line counter
     * @return num of the line if ok, -1 if not
     */
    private int methodSection(ArrayList<String> all, int num){
        ArrayList<String> method = new ArrayList<>();
        method.add(all.get(num));
        int counter1 = 1;
        int counter2 = 0;
        for (int t = num+1; t < all.size(); t++) {
            String regex1 = "\\{";
            String regex2 = "\\}";
            if (doMatch(all.get(t), regex2)) {
                method.add(all.get(t));
                counter2 += 1;
                if (counter1 == counter2){
                    methodsArray.add(method);
                    return t;
                }
                continue;
            }
            if (doMatch(all.get(t), regex1)) {
                method.add(all.get(t));
                counter1 += 1;
                continue;
            }
            method.add(all.get(t));
        }
        return -1;
    }
}

package oop.ex6.main.method;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that deal with call to uother src.method
 */
public class callMethodLine {
    /** map of methods*/
    private Map<String, ArrayList<String>> methods;
    /** array of type names*/
    private String[] names;
    /** array of type val*/
    private String[] values;
    /** array of type types*/
    private String[] Types;

    callMethodLine(Map<String, ArrayList<String>> m, String[] name, String[] val, String[] t){
        methods = m;
        names = name.clone();
        values = val.clone();
        Types = t.clone();
    }

    /**
     * check the line to now if its call src.method line
     * @param line line to check
     * @throws Exception if not ok
     */
    public void checkLine(String line) throws Exception{
        try{
            String[] result1 = checkMethodName(line);
            String[] result2 = checkBracket1(result1[1]);
            ArrayList<String> methodTypes = methods.get(result1[0]);
            String newLine = result2[1];
            for (String type : methodTypes){
                newLine = typeIteration(type, newLine);
            }
            String[] result3 = checkBracket2(newLine);
            String[] result4 = checkComma2(result3[1]);
            endLine(result4[1]);
        }catch (Exception a){
            throw new Exception();
        }
    }

    /**
     * make iteration on line
     * @param type type
     * @param line line to check
     * @return string of end line
     * @throws Exception if not ok
     */
    private String typeIteration(String type, String line) throws Exception{
        String result = "n";
        switch (type) {
            case "int":
                result = intValue(line)[1];
                break;
            case "String":
                result = stringValue(line)[1];
                break;
            case "char":
                result = charValue(line)[1];
                break;
            case "boolean":
                result = booleanValue(line)[1];
                break;
            case "double":
                result = doubleValue(line)[1];
                break;
        }
        try {
            String[] result1 = checkComma1(result);
            return result1[1];
        }catch (Exception a){
            return result;
        }
    }
    /**
     * check if the src.method name its ok
     * @param line line
     * @return return type if ok, NoMatch if not
     */
    private String[] checkMethodName(String line) throws Exception{
        String regex = "^[\\s]*[a-zA-Z]{1}[\\w]+[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if the ( is ok
     * @param line line
     * @return return type if ok, NoMatch if not
     */
    private String[] checkBracket1(String line) throws Exception{
        String regex = "^[\\s]*\\([\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if the ) is ok
     * @param line line
     * @return return type if ok, NoMatch if not
     */
    private String[] checkBracket2(String line) throws Exception{
        String regex = "^[\\s]*\\)[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if the ; is ok
     * @param line line
     * @return return type if ok, NoMatch if not
     */
    private String[] checkComma2(String line) throws Exception{
        String regex = "^[\\s]*^\\;{1}[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if the type is ok, return type if ok, NoMatch if not
     * @param line line
     * @return return type if ok, NoMatch if not
     */
    private String[] checkComma1(String line) throws Exception{
        String regex = "^[\\s]*\\,[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if end line ok
     * @param line line
     * @return return type if ok, NoMatch if not
     */
    private void endLine(String line) throws Exception{
        String result = line.replaceAll("[\\s]", "");
        if (result.equals("")){
            return;
        }
        throw new Exception();
    }
    private int contain(String[] array, String str){
        try{
            for (int i = 0; i < array.length; i++){
                if (array[i].equals(str)){
                    return i;
                }
            }
        }catch (Exception f){
            return -1;
        }
        return -1;
    }
    /**
     * check if double value ok
     * @param line line to check
     * @return string[] of find, end line
     * @throws Exception if not ok
     */
    private String[] doubleValue(String line) throws Exception{
        try {
            String regex = "^[\\s]*-{0,1}[\\d]+(.[\\d]+)*[\\s]*";
            return doMatch(line, regex);
        }catch (Exception r){
            try {
                String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                String[] result = doMatch(line, regex);
                int index = contain(names, result[0]);
                if (index != -1){
                    if (Types[index].equals("double") && values[index] != null){
                        result[0] = values[index];
                        return result;
                    }
                    throw new Exception();
                }
                throw new Exception();
            }catch (Exception p){
                throw new Exception();
            }
        }
    }
    /**
     * check if string value ok
     * @param line line to check
     * @return string[] of find, end line
     * @throws Exception if not ok
     */
    private String[] stringValue(String line) throws Exception{
        try {
            String regex1 = "^[\\s]*\"([^\"]*)\"[\\s]*";
            return doMatch(line, regex1);
        }catch (Exception r) {
            try {
                String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                String[] result = doMatch(line, regex);
                int index = contain(this.names, result[0]);
                if (index != -1){
                    if (values[index] != null){
                        result[0] = values[index];
                    }
                    throw new Exception();
                }
                throw new Exception();
            } catch (Exception z) {
                throw new Exception();
            }
        }
    }
    /**
     * check if int value ok
     * @param line line to check
     * @return string[] of find, end line
     * @throws Exception if not ok
     */
    private String[] intValue(String line) throws Exception{
        try {
            String regex = "^[\\s]*-{0,1}[\\d]+[\\s]*";
            return doMatch(line, regex);
        }catch (Exception r) {
            try {
                String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                String[] result = doMatch(line, regex);
                int index = contain(this.names, result[0]);
                if (index != -1){
                    if (Types[index].equals("int") && values[index] != null){
                        result[0] = values[index];
                        return result;
                    }
                    throw new Exception();
                }
                throw new Exception();
            } catch (Exception w) {
                throw new Exception();
            }
        }
    }
    /**
     * check if boolean value ok
     * @param line line to check
     * @return string[] of find, end line
     * @throws Exception if not ok
     */
    private String[] booleanValue(String line) throws Exception {
        try {
            String regex = "^[  \\t ]*(true{1}|false{1})+[   \\t ]*";
            return doMatch(line, regex);
        } catch (Exception r) {
            try {
                String regex = "^[\\s]*[\\d]+(.[\\d]+)*";
                return doMatch(line, regex);
            } catch (Exception t) {
                try {
                    String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                    String[] result = doMatch(line, regex);
                    int index = contain(names, result[0]);
                    if (index != -1) {
                        if (Types[index].equals("boolean") && values[index] != null) {
                            result[0] = values[index];
                            return result;
                        }
                        throw new Exception();
                    }
                    throw new Exception();
                } catch (Exception y) {
                    throw new Exception();
                }
            }
        }
    }
    /**
     * check if char value ok
     * @param line line to check
     * @return string[] of find, end line
     * @throws Exception if not ok
     */
    private String[] charValue(String line) throws Exception{
        try {
            String regex = "^[  \\t ]*'\\w{1}'[   \\t ]*";
            return doMatch(line, regex);
        }catch (Exception r) {
            try {
                String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                String[] result = doMatch(line, regex);
                int index = contain(names, result[0]);
                if (index != -1){
                    if (Types[index].equals("char") && values[index] != null){
                        result[0] = values[index];
                        return result;
                    }
                    throw new Exception();
                }
                throw new Exception();
            } catch (Exception q) {
                throw new Exception();
            }
        }
    }
    /**
     * do match between line and pattern return array with the group and the end
     *
     * @param line    line to check
     * @param pattern what to look for
     * @return array with group and the end line
     */
    private String[] doMatch(String line, String pattern) throws Exception{
        String[] result = new String[2];
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);
        if (m.find()) {
            result[0] = m.group().replaceAll("[ \\t]*", "");
            result[1] = line.substring(m.end());
            return result;
        }
        throw new Exception();
    }
}

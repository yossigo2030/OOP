package oop.ex6.main.method;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that deal with the creation of methods
 */
public class voidLine {
    /** array of type names*/
    private String[] typeName = {"int", "boolean", "double", "char", "String",};
    /** map of methods name as key, and array ot types as value*/
    private Map<String, ArrayList<String>> methods = new HashMap<String, ArrayList<String>>();
    /** array of types*/
    private ArrayList<String> param = new ArrayList<>();

    /**
     * add item to param array
     * @param type type to add
     */
    private void addArray(String type){
        param.add(type);
    }

    /**
     * getter to the map of methods
     * @return map
     */
    public Map<String, ArrayList<String >> getMap(){
        return methods;
    }

    /**
     * the main src.method that checking the creation of src.method
     * @param line line to check
     * @throws Exception if not ok
     */
    public void createMethod(String line) throws Exception{
        param = new ArrayList<>();
        try{
            String[] result1 = checkVoid(line);
            String[] result2 = checkMethodName(result1[1]);
            String[] result3 = checkBracket1(result2[1]);
            String[] result4 = checkBracket2(result3[1]);
            String[] result5 = checkParenthesis1(result4[1]);
            endLine(result5[1]);
            methods.put(result2[0], param);
        }catch (Exception a){
            createMethodWithParam(line);
        }
    }

    private void createMethodWithParam(String line) throws Exception{
        try {
            String[] result1 = checkVoid(line);
            String[] result2 = checkMethodName(result1[1]);
            String[] result3 = checkBracket1(result2[1]);
            nameIteration(result3);
            methods.put(result2[0], param);
        }catch (Exception b){
            throw new Exception();
        }
    }

    /**
     * make iteration on the line to now if its ok
     * @param line line to check
     * @throws Exception in not ok
     */
    private void nameIteration(String[] line) throws Exception {
        try {
            String[] result1 = checkFinal(line[1]);
            String[] result2 = checkType(result1[1]);
            addArray(result2[0]);
            String[] result3 = checkName(result2[1]);
            nameIterationCheckEnd(result3[1]);
        } catch (Exception z) {
            nameIterationNotFinal(line[1]);
        }
    }

    private void nameIterationCheckEnd(String str) throws Exception{
        try {
            String[] result1 = checkComma1(str);
            nameIteration(result1);
        } catch (Exception a) {
            String[] result2 = checkBracket2(str);
            String[] result3 = checkParenthesis1(result2[1]);
            endLine(result3[1]);
        }
    }

    private void nameIterationNotFinal(String str) throws Exception{
        String[] result1 = checkType(str);
        addArray(result1[0]);
        String[] result2 = checkName(result1[1]);
        try {
            String[] result3 = checkComma1(result2[1]);
            nameIteration(result3);
        } catch (Exception a) {
            String[] result3 = checkBracket2(result2[1]);
            String[] result4 = checkParenthesis1(result3[1]);
            endLine(result4[1]);
        }
    }

    /**
     * check if there is "final" in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkFinal(String line) throws Exception {
        String regex = "^[  \\t ]*final[    \\t ]+";
        return doMatch(line, regex);
    }
    /**
     * check if there is "void" in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkVoid(String line) throws Exception{
        String regex = "^[\\s]*void[\\s]+";
        return doMatch(line, regex);
    }
    /**
     * check if src.method name ok
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkMethodName(String line) throws Exception{
        String regex = "^[\\s]*[a-zA-Z]{1}[\\w]*[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if there is ( in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkBracket1(String line) throws Exception{
        String regex = "^[\\s]*\\([\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if there is ) in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkBracket2(String line) throws Exception{
        String regex = "^[\\s]*\\)[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if name ok
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkName(String line) throws Exception {
        String regex = "^([a-zA-Z]{1}[\\w]*|_[\\w]+)[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if there is "," in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkComma1(String line) throws Exception{
        String regex = "^[\\s]*\\,[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if there is "{}" in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkParenthesis1(String line) throws Exception{
        String regex = "^[\\s]*\\{[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if end line ok
     * @param line line to check
     * @return true if final, false if not
     */
    private void endLine(String line) throws Exception{
        String result = line.replaceAll("[\\s]", "");
        if (result.equals("")){
            return;
        }
        throw new Exception();
    }
    /**
     * check if the type is ok, return type if ok, NoMatch if not
     * @param line line
     * @return return type if ok, NoMatch if not
     */
    private String[] checkType(String line) throws Exception {
        String regex = "^[  \\t ]*[\\w\\d]+[    \\t ]+";
        String[] result = doMatch(line, regex);
        for (String Type : typeName){
            if (Type.equals(result[0]))
                return result;
        }
        throw new Exception();
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

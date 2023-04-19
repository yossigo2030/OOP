package oop.ex6.main.method;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that deal with if or while line
 */
public class ifWhileLine {
    /** array of type names*/
    private String[] names;
    /** array of type values*/
    private String[] values;
    /** array of type types*/
    private String[] Types;
    /** array of type final*/
    private String[] Final;



    ifWhileLine(String[] name, String[] value, String[] type, String[] f){
        names = name.clone();
        values = value.clone();
        Types = type.clone();
        Final = f.clone();

    }

    /**
     * name iteration on the line
     * @param line line to check
     * @throws Exception if not ok
     */
    public void ifIteration(String line) throws Exception{
        try {
            String[] result1 = checkIfWhile(line);
            String[] result2 = checkBracket1(result1[1]);
            iteration(result2[1]);
        }catch (Exception a){
            throw new Exception();
        }
    }

    /**
     * make iteration on the line to check if its ok
     * @param line line to check
     * @throws Exception if not ok
     */
    private void iteration(String line) throws Exception{
        try {
            String[] result1 = checkTrueFalse(line);
            try {
                String[] result2 = checkOrAnd(result1[1]);
                iteration(result2[1]);
            }catch (Exception a){
                String[] result2 = checkBracket2(result1[1]);
                String[] result3 = checkParenthesis1(result2[1]);
                endLine(result3[1]);
            }
        }catch (Exception b){
            iterationCheckVariable(line);
        }
    }

    private void iterationCheckVariable(String line) throws Exception{
        try {
            String[] result1 = checkVariable(line);
            try {
                String[] result2 = checkOrAnd(result1[1]);
                iteration(result2[1]);
            }catch (Exception c){
                String[] result2 = checkBracket2(result1[1]);
                String[] result3 = checkParenthesis1(result2[1]);
                endLine(result3[1]);
            }
        }catch (Exception d){
            iterationCheckNum(line);
        }
    }

    private void iterationCheckNum(String line) throws Exception{
        String[] result1 = checkNum(line);
        try{
            String[] result2 = checkOrAnd(result1[1]);
            iteration(result2[1]);
        }catch (Exception c){
            String[] result2 = checkBracket2(result1[1]);
            String[] result3 = checkParenthesis1(result2[1]);
            endLine(result3[1]);
        }
    }

    /**
     * check if the num  is ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private String[] checkNum(String line) throws Exception{
        String regex = "^[\\s]*([\\d]+(.[\\d]+)*)|(-[\\d]+(.[\\d]+)*)[\\s]*";
        return doMatch3(line, regex);
    }
    /**
     * check if the true false is ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private String[] checkTrueFalse(String line) throws Exception{
        String regex = "^[\\s]*(true{1}|false{1})+[\\s]*";
        return doMatch3(line, regex);
    }
    /**
     * check if the || && is ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private String[] checkOrAnd(String line) throws Exception{
        String regex = "^[\\s]*([\\|]{2}|[\\&]{2})+[\\s]*";
        return doMatch3(line, regex);
    }
    /**
     * check if end line ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private void endLine(String line) throws Exception{
        String result = line.replaceAll("[\\s]", "");
        if (result.equals("")){
            return;
        }
        throw new Exception();
    }

    /**
     * check if variable ok
     * @param line line to check
     * @return string[] of the result
     * @throws Exception if not ok
     */
    private String[] checkVariable(String line) throws Exception {
        try {
            String[] result1 = checkName(line);
            for (int i = 0; i < names.length; i++){
                if (result1[0].equals(names[i])){
                    if (((Types[i].equals("int") ||(Types[i].equals("double")
                            || (Types[i].equals("boolean")))) && (values[i] != null))){
                        return result1;
                    }
                }
            }
            throw new Exception();
        }catch (Exception a){
            throw new Exception();
        }
    }
    /**
     * check if the name is ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private String[] checkName(String line) throws Exception {
        String regex = "^([a-zA-Z]{1}[\\w]*|_[\\w]+)[\\s]*";
        return doMatch3(line, regex);
    }
    /**
     * check if the { is ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private String[] checkParenthesis1(String line) throws Exception{
        String regex = "[\\s]*\\{[\\s]*";
        return doMatch3(line, regex);
    }
    /**
     * check if the ( is ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private String[] checkBracket1(String line) throws Exception{
        String regex = "^[\\s]*\\([\\s]*";
        return doMatch3(line, regex);
    }
    /**
     * check if the ) is ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private String[] checkBracket2(String line) throws Exception{
        String regex = "^[\\s]*\\)[\\s]*";
        return doMatch3(line, regex);
    }
    /**
     * check if the "if, while" is ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private String[] checkIfWhile(String line) throws Exception{
        String regex = "^[\\s]*(if|while)[\\s]*";
        return doMatch3(line, regex);
    }
    /**
     * do match between line and pattern return array with the group and the end
     *
     * @param line    line to check
     * @param pattern what to look for
     * @return array with group and the end line
     */
    private String[] doMatch3(String line, String pattern) throws Exception{
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
    public String[] getNames(){
        return names;
    }
    public String[] getValues(){
        return values;
    }
    public String[] getTypes(){
        return Types;
    }
    public String[] getFinal(){
        return Final;
    }
}

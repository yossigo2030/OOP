package oop.ex6.main;//package oop.ex6.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that get all the variables , and dicide if its ok or not
 */

public class GlobalVar {
    /** array of names */
    private String[] names = new String[10];
    /** array of values */
    private String[] values = new String[10];
    /** array of types */
    private String[] Types = new String[10];
    /** array of final */
    private String[] Final = new String[10];
    /** array of type names*/
    private String[] typeName = {"int", "boolean", "double", "char", "String",};
    /** remember which type */
    private String flagType;
    /** remember which name */
    private String flagName;

    /**
     * get all the lines of variables and check if them ok
     * @param variables line if the file
     * @throws Exception if not ok
     */
    public void checkVariable(ArrayList<String> variables) throws Exception{
        Arrays.fill(Final, "OK");
        for (String line : variables){
            try {
                try {
                    checkFinalLine(line);
                } catch (Exception f) {
                    String[] result = checkType(line);
                    nameIteration(result);
                }
            } catch (Exception q) {
                checkAssignment(line);
            }
        }
    }

    /**
     * get part of line , and make many iteration , to now if its ok
     * @param line part line of variable
     * @throws Exception if not ok
     */
    private void nameIteration(String[] line) throws Exception{
        String[] result1 = checkName(line[1]);
        addNameToArray(result1[0]);
        try {
            IterationMoltyNames(result1);
        } catch (Exception b) {
            String[] result2 = checkComma2(result1[1]);
            endLine(result2[1]);
        }
    }

    private void IterationMoltyNames(String[] str) throws Exception{
        try {
            String[] result = checkComma1(str[1]);
            nameIteration(result);
        } catch (Exception g) {
            IterationCheckEqual(str[1]);
        }
    }

    private void IterationCheckEqual(String str) throws Exception{
        try {
            String[] result1 = checkEqual(str);
            String[] result2 = value(result1);
            if (!addValueToArray(result2[0])){
                throw new Exception();
            }
            IterationMoeNames(result2[1]);
        } catch (Exception m) {
            String[] result1 = checkComma2(str);
            endLine(result1[1]);
        }
    }

    private void IterationMoeNames(String str) throws Exception{
        try {
            String[] result = checkComma1(str);
            nameIteration(result);
        } catch (Exception n) {
            String[] result = checkComma2(str);
            endLine(result[1]);
        }
    }

    /**
     * check if the line its assignment of variable
     * @param line variable line
     * @throws Exception if not ok
     */
    private void checkAssignment(String line) throws Exception{
        try {
            String[] result1 = checkName(line);
            int num = contain(this.names, result1[0]);
            if (num == -1){
                throw new Exception();
            }
            flagName = result1[0];
            flagType = Types[num];
            String[] result2 = checkEqual(result1[1]);
            String[] result3 = value(result2);
            if (!addValueToArray(result3[0])){
                throw new Exception();
            }
            String[] result4 = checkComma2(result3[1]);
            endLine(result4[1]);
        }catch (Exception v){
            throw new Exception();
        }
    }

    /**
     * check the line if the line start with final
     * @param line line to check
     * @throws Exception if not ok
     */
    private void checkFinalLine(String line) throws Exception{
        try {
            String[] result1 = checkFinal(line);
            String[] result2 = checkType(result1[1]);
            String[] result3 = checkName(result2[1]);
            addNameToArray(result3[0]);
            String[] result4 = checkEqual(result3[1]);
            String[] result5 = value(result4);
            addFinalValue(result5[0]);
            String[] result6 = checkComma2(result5[1]);
            endLine(result6[1]);
        }catch (Exception g){
            throw new Exception();
        }
    }

    /**
     * check if the arrays arr full , and enlarge the array
     */
    private void checkCapacity(){
        for (String item : names){
            if (item == null)
                return;
        }
        String[] tempName = names.clone();
        String[] tempValue = values.clone();
        String[] tempType = Types.clone();
        String[] tempFinal = Final.clone();
        names = new String[names.length*2];
        values = new String[values.length*2];
        Types = new String[Types.length*2];
        Final = new String[Final.length*2];
        Arrays.fill(Final, "OK");
        for (String name : tempName){
            for (int i = 0; i < names.length; i++){
                if (names[i] == null){
                    names[i] = name;
                    Types[i] = tempType[i];
                    if (tempValue[i] != null){
                        values[i] = tempValue[i];
                    }
                    if (tempFinal[i].equals("NO")){
                        Final[i] = tempFinal[i];
                    }
                    break;
                }
            }
        }
    }



    /**
     * return the index of item in the array
     * @param array array to check
     * @param str item
     * @return index if exist, -1 if not
     */
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
     * add item to array
     * @param array array to add
     * @param str item to add
     * @return index
     * @throws Exception if not ok
     */
    private int add(String[] array, String str) throws Exception{
        for (int i = 0 ; i < array.length; i++){
            if (array[i] == null){
                array[i] = str;
                return i;
            }
        }
        throw new Exception();
    }

    /**
     * add name to the names array
     * @param item name
     * @throws Exception if not ok
     */
    private void addNameToArray(String item) throws Exception {
        checkCapacity();
        for (String str : names){
            if (str != null){
                if (str.equals(item)){
                    throw new Exception();
                }
                continue;
            }
            break;
        }
        flagName = item;
        int num =  add(names, item);
        Types[num] = flagType;
    }

    /**
     * add value to value array
     * @param item value
     * @return true if ok , false if not
     */
    private boolean addValueToArray(String item) {
        int index = contain(names, flagName);
        try {
            if (Final[index].equals("OK")) {
                values[index] = item;
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * add "NO" to final variable
     * @param item final item
     */
    private void addFinalValue(String item) {
        int index = contain(names, flagName);
        values[index] = item;
        Final[index] = "NO";
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
     * check if the type is ok, return type if ok, NoMatch if not
     *
     * @param line line
     * @return return type if ok, NoMatch if not
     */
    private String[] checkType(String line) throws Exception {
        String regex = "^[  \\t ]*[\\w\\d]+[    \\t ]+";
        String[] result = doMatch(line, regex);
        for (String Type : this.typeName){
            if (Type.equals(result[0])){
                this.flagType = result[0];
                return result;
            }
        }
        throw new Exception();
    }
    /**
     * check if the name is ok
     * @param line line
     * @return return the name without spaces if ok, NoMatch if not
     */
    private String[] checkName(String line) throws Exception {
        String regex = "^([a-zA-Z]{1}[\\w]*|_[\\w]+)[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if equal ok
     * @param line lne
     * @return = string[] of find, end line
     * @throws Exception if not found
     */
    private String[] checkEqual(String line) throws Exception{
        String regex = "^[\\s]*^\\=[\\s]*";
        return doMatch(line, regex);
    }

    /**
     * check if comma ok
     * @param line line to check
     * @return string[] of find, end line
     * @throws Exception if not found
     */
    private String[] checkComma1(String line) throws Exception{
        String regex = "^[\\s]*\\,[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if ; ok
     * @param line line to check
     * @return string[] of find, end line
     * @throws Exception if not found
     */
    private String[] checkComma2(String line) throws Exception{
        String regex = "^[\\s]*\\;[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if end line ok ok
     * @param line line to check
     * @throws Exception if not ok
     */
    private void endLine(String line) throws Exception{
        String result = line.replaceAll("[\\s]", "");
        if (result.equals("")){
            return;
        }
        throw new Exception();
    }
    /**
     * decide which function call
     * @param line line
     * @return result
     * @throws Exception tt
     */
    private String[] value(String[] line) throws Exception{
        if (flagType.equals("int"))
            return intValue(line[1]);
        if (flagType.equals("String"))
            return stringValue(line[1]);
        if (flagType.equals("char"))
            return charValue(line[1]);
        if (flagType.equals("boolean"))
            return booleanValue(line[1]);
        if (flagType.equals("double"))
            return doubleValue(line[1]);
        throw new Exception();
    }

    /**
     * check if double value ok
     * @param line line to check
     * @return string[] of find, end line
     * @throws Exception if not ok
     */
    private String[] doubleValue(String line) throws Exception{
        try {
            String regex = "^[\\s]*(-{0,1}|\\+{0,1})[\\d]+(\\.)*([\\d])*[\\s]*";
            return doMatch(line, regex);
        }catch (Exception r){
            try {
                String regex = "^[\\s]*(\\.)*([\\d])+[\\s]*";
                return doMatch(line, regex);
            } catch (Exception c){
                try {
                    String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                    String[] result = doMatch(line, regex);
                    int index = contain(names, result[0]);
                    if (index != -1){
                        if (Types[index].equals("double") || Types[index].equals("int") && values[index] != null){
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
                int index = contain(names, result[0]);
                if (index != -1){
                    if (Types[index].equals("String") && values[index] != null){
                        result[0] = values[index];
                        return result;
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
            String regex = "^[\\s]*(-{0,1}|\\+{0,1})[\\d]+[\\s]*";
            return doMatch(line, regex);
        }catch (Exception r) {
            try {
                String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                String[] result = doMatch(line, regex);
                int index = contain(names, result[0]);
                if (index != -1){
                    if (flagType.equals(Types[index]) && values[index] != null){
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
                String regex = "^[\\s]*-{0,1}[\\d]+(\\.)*([\\d])*[\\s]*";
                return doMatch(line, regex);
            } catch (Exception t) {
                try {
                    String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                    String[] result = doMatch(line, regex);
                    int index = contain(names, result[0]);
                    if (index != -1) {
                        if (Types[index].equals("boolean") || Types[index].equals("int") ||
                                Types[index].equals("double") && values[index] != null) {
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
            String regex = "^[\\s]*'.'[\\s]*";
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
     * do match between line and pattern return array with the group, and the end
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

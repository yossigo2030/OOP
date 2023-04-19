package oop.ex6.main.method;


import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that deak with local variables
 */
public class localLine {
    /** array of type names*/
    private String[] names;
    /** array of type values*/
    private String[] values;
    /** array of type types*/
    private String[] Types;
    /** array of type final*/
    private String[] Final;
    /** array of type type names*/
    private String[] typeName = {"int", "boolean", "double", "char", "String",};
    /** now which type */
    private String flagType;
    /** now which name */
    private String flagName;



    localLine(String[] name, String[] value, String[] type, String[] f){
        this.names = name.clone();
        this.values = value.clone();
        this.Types = type.clone();
        this.Final = f.clone();

    }

    /**
     * check if the line its variable line
     * @param line line to check
     * @throws Exception if not ok
     */
    void checkVariable(String line) throws Exception{
        try {
            try {
                checkFinalLine(line);
            } catch (Exception f) {
                String[] result = checkType(line);
                iteration(result[1]);
            }
        } catch (Exception q) {
            checkAssignment(line);
        }
    }

    /**
     * make iteration on line to now if its ok
     * @param line line to check
     * @throws Exception if not ok
     */
    private void iteration(String line) throws Exception{
        String[] result1 = checkName(line);
        addNameToArray(result1[0]);
        try {
            try {
                String[] result2 = checkComma1(result1[1]);
                iteration(result2[1]);
            } catch (Exception g) {
                iterationCheckEqual(result1[1]);
            }
        } catch (Exception b) {
            String[] result2 = checkComma2(result1[1]);
            endLine(result2[1]);
        }
    }

    private void iterationCheckEqual(String str) throws Exception{
        try {
            String[] result1 = checkEqual(str);
            String[] result2 = value(result1);
            if (!addValueToArray(result2[0])){
                throw new Exception();
            }
            iterationCheckComma(result2[1]);
        } catch (Exception m) {
            String[] result2 = checkComma2(str);
            endLine(result2[1]);
        }
    }

    private void iterationCheckComma(String str) throws Exception{
        try {
            String[] result = checkComma1(str);
            iteration(result[1]);
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
     * check if the type is ok, return type if ok, NoMatch if not
     *
     * @param line line
     * @return return type if ok, NoMatch if not
     */
    private String[] checkType(String line) throws Exception {
        String regex = "^[  \\t ]*[\\w\\d]+[    \\t ]+";
        String[] result = doMatch3(line, regex);
        for (String Type : typeName){
            if (Type.equals(result[0])){
                flagType = result[0];
                return result;
            }
        }
        throw new Exception();
    }

    /**
     * check if equal ok
     * @param line lne
     * @return =, and line
     * @throws Exception if not found
     */
    private String[] checkEqual(String line) throws Exception{
        String regex = "^[   \\t ]*^\\={1}[    \\t ]*";
        return doMatch3(line, regex);
    }
    /**
     * check if there is "," in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkComma1(String line) throws Exception{
        String regex = "^[\\s]*\\,[\\s]*";
        return doMatch3(line, regex);
    }
    /**
     * check if there is ";" in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkComma2(String line) throws Exception{
        String regex = "^[   \\t ]*^\\;{1}[    \\t ]*";
        return doMatch3(line, regex);
    }
    /**
     * check if end of line ok
     * @param line line to check
     */
    private void endLine(String line) throws Exception{
        String result = line.replaceAll("[\\s]", "");
        if (result.equals("")){
            return;
        }
        throw new Exception();
    }
    /**
     * check if there is "final" in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkFinal(String line) throws Exception {
        String regex = "^[  \\t ]*final[    \\t ]+";
        return doMatch3(line, regex);
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
            String regex = "^[\\s]*-{0,1}[\\d]+(\\.)*([\\d])*[\\s]*";
            return doMatch3(line, regex);
        }catch (Exception r){
            try {
                String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                String[] result = doMatch3(line, regex);
                int index = contain(this.names, result[0]);
                if (index != -1){
                    if (this.Types[index].equals("double") && this.values[index] != null){
                        result[0] = this.values[index];
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
            return doMatch3(line, regex1);
        }catch (Exception r) {
            try {
                String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                String[] result = doMatch3(line, regex);
                int index = contain(this.names, result[0]);
                if (index != -1){
                    if (this.values[index] != null){
                        result[0] = this.values[index];
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
            return doMatch3(line, regex);
        }catch (Exception r) {
            try {
                String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                String[] result = doMatch3(line, regex);
                int index = contain(this.names, result[0]);
                if (index != -1){
                    if (flagType.equals(this.Types[index]) && this.values[index] != null){
                        result[0] = this.values[index];
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
            return doMatch3(line, regex);
        } catch (Exception r) {
            try {
                String regex = "^[\\s]*[\\d]+(.[\\d]+)*";
                return doMatch3(line, regex);
            } catch (Exception t) {
                try {
                    String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                    String[] result = doMatch3(line, regex);
                    int index = contain(this.names, result[0]);
                    if (index != -1) {
                        if (this.Types[index].equals("boolean") && this.values[index] != null) {
                            result[0] = this.values[index];
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
            return doMatch3(line, regex);
        }catch (Exception r) {
            try {
                String regex = "^([\\w]+[\\w\\d_]*|^_[\\w\\d_]+)[ \\t ]*";
                String[] result = doMatch3(line, regex);
                int index = contain(this.names, result[0]);
                if (index != -1){
                    if (this.Types[index].equals("char") && this.values[index] != null){
                        result[0] = this.values[index];
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
     * add name to names array
     * @param item value
     * @return true if ok , false if not
     */
    private void addNameToArray(String item) throws Exception {
        checkCapacity();
        for (String str : this.names){
            if (str != null){
                if (str.equals(item)){
                    throw new Exception();
                }
                continue;
            }
            break;
        }
        flagName = item;
        int num =  add(this.names, item);
        this.Types[num] = flagType;
    }
    private int index(String[] array, String str) {
        for (int i = 0; i < array.length; i++){
            if (array[i].equals(str)){
                return i;
            }
        }
        return -1;
    }
    /**
     * add value to value array
     * @param item value
     * @return true if ok , false if not
     */
    private boolean addValueToArray(String item) {
        int index = index(this.names, flagName);
        try {
            if (this.Final[index].equals("OK")) {
                this.values[index] = item;
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    /**
     * add final value to array
     * @param item value
     * @return true if ok , false if not
     */
    private void addFinalValue(String item) {
        int index = index(this.names, flagName);
        this.values[index] = item;
        this.Final[index] = "NO";
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
     * check if the arrays arr full , and enlarge the array
     */
    private void checkCapacity(){
        for (String item : this.names){
            if (item == null)
                return;
        }
        String[] tempName = this.names.clone();
        String[] tempValue = this.values.clone();
        String[] tempType = this.Types.clone();
        String[] tempFinal = this.Final.clone();
        this.names = new String[this.names.length*2];
        this.values = new String[this.values.length*2];
        this.Types = new String[Types.length*2];
        this.Final = new String[this.Final.length*2];
        Arrays.fill(this.Final, "OK");
        for (String name : tempName){
            for (int i = 0; i < this.names.length; i++){
                if (this.names[i] == null){
                    this.names[i] = name;
                    this.Types[i] = tempType[i];
                    if (tempValue[i] != null){
                        this.values[i] = tempValue[i];
                    }
                    if (tempFinal[i].equals("NO")){
                        this.Final[i] = tempFinal[i];
                    }
                    break;
                }
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
    String[] getNames(){
        return this.names;
    }
    String[] getValues(){
        return this.values;
    }
    String[] getTypes(){
        return this.Types;
    }
    public String[] getFinal(){
        return this.Final;
    }
}

package oop.ex6.main.method;
//package Global;


import oop.ex6.main.GlobalVar;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import src.GlobalVar;

/**
 * class that get the methods and check if them ok
 */
public class mainMethod {
    /** voidLine object */
    private voidLine Void = new voidLine();
    /** call object */
    private callMethodLine call;
    /** local object */
    private localLine local;
    /** if object */
    private ifWhileLine If;
    /** global object */

    private GlobalVar global;

    public mainMethod(GlobalVar g){
        this.global = g;
        call = new callMethodLine(Void.getMap(), global.getNames(), global.getValues(), global.getTypes());
        local = new localLine(global.getNames(), global.getValues(), global.getTypes(), global.getFinal());
        If = new ifWhileLine(global.getNames(), global.getValues(), global.getTypes(), global.getFinal());
    }

    /**
     * get methods and check if them ok
     * @param methods array of methods to check
     * @throws Exception if not ok
     */
    public void checkMethod(ArrayList<ArrayList<String>> methods) throws Exception {
        for (ArrayList<String> method : methods) {
            Void.createMethod(method.get(0));
        }
        for (ArrayList<String> method : methods) {
            try {
                method.remove(0);
                iteration(method, global.getNames(), global.getValues(), global.getTypes(), global.getFinal());
            } catch (Exception a) {
                throw new Exception();
            }
        }
    }

    /**
     * make iteration of src.method lines to know if it is ok
     * @param method src.method to check
     * @param names names array
     * @param values values array
     * @param types types array
     * @param F final array
     * @throws Exception if not ok
     */
    private void iteration(ArrayList<String> method, String[] names,
                           String[] values, String[] types, String[] F) throws Exception{
        try {
            call = new callMethodLine(Void.getMap(), names, values, types);
            call.checkLine(method.get(0));
            method.remove(0);
            iteration(method, names, values, types, F);
        }catch (Exception a){
            iterationIfWhileLine(method, names, values, types, F);
        }
    }

    private void iterationIfWhileLine(ArrayList<String> method, String[] names,
                                      String[] values, String[] types, String[] f) throws Exception{
        try {
            If = new ifWhileLine(names, values, types, f);
            If.ifIteration(method.get(0));
            method.remove(0);
            iteration(method, If.getNames(), If.getValues(), If.getTypes(), If.getFinal());
        }catch (Exception b){
            iterationLocalLine(method, names, values, types, f);
        }
    }

    private void iterationLocalLine(ArrayList<String> method, String[] names,
                                    String[] values, String[] types, String[] f) throws Exception{
        try {
            local = new localLine(names, values, types, f);
            local.checkVariable(method.get(0));
            method.remove(0);
            iteration(method, local.getNames(), local.getValues(), local.getTypes(), local.getFinal());
        }catch (Exception e){
            if (method.size() > 2){
                iterationNotEndFunction(method, names, values, types, f);
            }
            iterationEndFunction(method);
        }
    }

    private void iterationEndFunction(ArrayList<String> method) throws Exception{
        try {
            String[] result1 = checkReturn(method.get(0));
            String[] result2 = checkComma2(result1[1]);
            endLine(result2[1]);
            String[] result3 = checkParenthesis2(method.get(1));
            endLine(result3[1]);
        }catch (Exception n){
            throw new Exception();
        }
    }

    private void iterationNotEndFunction(ArrayList<String> method, String[] names,
                                         String[] values, String[] types, String[] f) throws Exception{
        try{
            String[] result = checkParenthesis2(method.get(0));
            endLine(result[1]);
            method.remove(0);
            iteration(method, names, values, types, f);
        }catch (Exception g){
            try {
                String[] result1 = checkReturn(method.get(0));
                String[] result2 = checkComma2(result1[1]);
                endLine(result2[1]);
                method.remove(0);
                String[] result3 = checkParenthesis2(method.get(0));
                endLine(result3[1]);
                method.remove(0);
                iteration(method, names, values, types, f);
            }catch (Exception d){
                throw new Exception();
            }
        }
    }

    /**
     * check if there is } in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkParenthesis2(String line) throws Exception{
        String regex = "[\\s]*\\}[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if there is ; in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkComma2(String line) throws Exception{
        String regex = "^[\\s]*\\;[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if there is "return" in the beginning of the line
     * @param line line to check
     * @return true if final, false if not
     */
    private String[] checkReturn(String line) throws Exception{
        String regex = "^[\\s]*return[\\s]*";
        return doMatch(line, regex);
    }
    /**
     * check if end line ok
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

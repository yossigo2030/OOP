package oop.ex6.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * main class that except args , and operate the program to check if the file its ok
 */
public class Sjavac {
    public static void main(String[] args) {
        try {
            File file = new File(args[0]);
            FirstFilter filter = new FirstFilter();
            Scanner code = new Scanner(file);
            ArrayList<String> all = new ArrayList<>();
            while (code.hasNext()){
                String line = code.nextLine();
                if (filter.analysisFile1(line))
                    all.add(line);
            }
            if (filter.analysisFile2(all) == -1) {
                System.out.println(1);
            }
            else System.out.println(0);
        }catch (Exception t){
            System.out.println(2);
            System.err.println("wrong path , or file");
        }
    }
}

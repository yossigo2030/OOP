package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * user module
 */
public class Shell {
    private final Image img;
    private static final int INITIAL_CHARS_IN_ROW = 64;
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private Set<Character> charSet = new HashSet<>();
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private static final String PRINT = "chars";
    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String RES = "res";
    private static final String CONSOLE = "console";
    private static final String RENDER = "render";
    private boolean renderer = false;

    /**
     * constructor of the user module
     * @param img image
     */
    public Shell(Image img) {
        this.img = img;
        this.minCharsInRow = Math.max(1, img.getWidth()/img.getHeight());
        this.maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        this.charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        add("0-9");
    }


    /**
     * parsing the param to the right form
     * @param str char/s
     * @return char/s
     */
    private char[] parsing(String str) {
        if (str.equals("all")) {
            return new char[]{' ', '~'};
        }
        if (str.length() == 1) {
            return new char[]{str.charAt(0), str.charAt(0)};
        }
        if (str.length() == 3) {
            if(str.charAt(1) == '-'){
                return new char[]{str.charAt(0), str.charAt(2)};
            }
            return null;
        }
        if (str.equals("space")) {
            return new char[]{' ', ' '};
        } else {
            return null;
        }
    }

    /**
     * add char to char set
     * @param str char/s
     */
    private void add(String str) {
        char[] charsToAdd = parsing(str);
        if (charsToAdd != null) {
            if (charsToAdd[0] > charsToAdd[1]) {
                char temp = charsToAdd[0];
                charsToAdd[0] = charsToAdd[1];
                charsToAdd[1] = temp;
            }
            while (charsToAdd[0] <= charsToAdd[1]) {
                charSet.add(charsToAdd[0]);
                charsToAdd[0] = (char) ((charsToAdd[0]) + 1);
            }
        }
        else {
            System.out.println("Did not add due to incorrect format");
        }
    }

    /**
     * remove char from char set
     * @param str char/s
     */
    private void remove(String str) {
        char[] charsToAdd = parsing(str);
        if (charsToAdd != null) {
            if (charsToAdd[0] > charsToAdd[1]) {
                char temp = charsToAdd[0];
                charsToAdd[0] = charsToAdd[1];
                charsToAdd[1] = temp;
            }
            while (charsToAdd[0] <= charsToAdd[1]) {
                charSet.remove(charsToAdd[0]);
                charsToAdd[0] = (char) ((charsToAdd[0]) + 1);
            }
        }
        else {
            System.out.println("Did not remove due to incorrect format");
        }
    }

    /**
     * print the char set
     */
    private void print() {
        for (Character Char : charSet) {
            System.out.print(Char + " ");
        }
        System.out.print("\n");
    }

    /**
     * change the resolution of the picture
     * @param str parameter up of down
     */
    //check
    private void res(String str){
        if(str.equals("up")){
            if(charsInRow * 2 <= maxCharsInRow){
                charsInRow *= 2;
                System.out.println("Width set to " + charsInRow);
                return;
            }
            System.out.println("Did not change due to exceeding boundaries");
            return;
        }
        if(str.equals("down")){
            if(charsInRow / 2 >= minCharsInRow){
                charsInRow =  charsInRow / 2;
                System.out.println("Width set to " + charsInRow);
                return;
            }
            System.out.println("Did not change due to exceeding boundaries");
            return;
        }
        System.out.print("Did not executed due to incorrect command");
    }


    /**
     * render the new image of to the console or to html file
     */
    private void render() {
        Character[] charsArray = new Character[charSet.size()];
        int index = 0;
        for (Character Char: charSet) {
            charsArray[index] = Char;
            index += 1;
        }
        char[][] asciiArt = new BrightnessImgCharMatcher(img, "Courier New").
                chooseChars(charsInRow, charsArray);
        if (renderer) {
            ConsoleAsciiOutput console = new ConsoleAsciiOutput();
            console.output(asciiArt);
        } else {
            HtmlAsciiOutput html = new HtmlAsciiOutput("out.html", "Courier New");
            html.output(asciiArt);
        }
    }

    /**
     * get command from usr
     * @param type type of command
     * @param str parameter
     */
    private void moreOptions(String type, String str) {
        switch (type) {
            case PRINT:
                print();
                break;
            case ADD:
                add(str);
                break;
            case REMOVE:
                remove(str);
                break;
            case RES:
                res(str);
                break;
            case CONSOLE:
                renderer = true;
                break;
            case RENDER:
                render();
                break;
            default:
                System.out.println("Did not executed due to incorrect command");
        }
    }

    /**
     * rum all the user module
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("<<< ");
        String type = scanner.next().trim();
        while (!type.equals("exit")) {
            String str = scanner.nextLine().trim();
            moreOptions(type, str);
            System.out.print("<<< ");
            scanner = new Scanner(System.in);
            type = scanner.next();
        }
    }
}


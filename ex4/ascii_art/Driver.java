package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_art.img_to_char.CharRenderer;
import image.Image;

import java.util.Arrays;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) throws Exception {
//        Image img = Image.fromFile("board.jpeg");
//        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(img, "Ariel");
//        Character[] ccc = {'m', 'o'};
//        var chars = charMatcher.chooseChars(2, ccc);
//        System.out.println(Arrays.deepToString(chars));

        if (args.length != 1) {
            System.err.println("USAGE: java asciiArt ");
            return;
        }
        Image img = Image.fromFile(args[0]);
        if (img == null) {
            Logger.getGlobal().severe("Failed to open image file " + args[0]);
            return;
        }
        new Shell(img).run();
    }
}
package ascii_art.img_to_char;


import image.Image;
import java.awt.*;
import java.util.Map;

/**
 * match chars to images
 */
public class BrightnessImgCharMatcher {
    private Image img;
    private String fontName;

    /**
     * constructor
     * @param img image
     * @param fontName fontName
     */
    public BrightnessImgCharMatcher(Image img, String fontName){
        this.img = img;
        this.fontName = fontName;
    }
    // check

    /**
     * count white pixels
     * @param c some char
     * @return sum of white pixels
     */
    private int countWhitePixels(char c){
        int counter = 0;
        boolean[][] myImage = CharRenderer.getImg(c, 16, this.fontName);
        for (int row = 0; row < myImage.length; row++) {
            for (int col = 0; col < myImage[row].length; col++) {
                if(myImage[row][col]){
                    counter += 1;
                }
            }
        }
        return counter;
    }

    /**
     * get min brightness
     * @param chars chars
     * @return min
     */
    private float getMinBrightness(float[] chars) {
        float min = chars[0];
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] < min) {
                min = chars[i];
            }
        }
        return min;
    }
    /**
     * return max brightness
     * @param chars chars
     * @return max
     */
    private float getMaxBrightness(float[] chars) {
        float max = chars[0];
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] > max) {
                max = chars[i];
            }
        }
        return max;
    }
    /**
     * calculate the brightness of the chars
     * @param chars chars
     * @return brightness
     */
    public float[] calculateCharBrightness(Character[] chars){
        float[] normalBrightness = new float[chars.length];
        for (int i = 0; i < chars.length; i++) {
            normalBrightness[i] = (float)countWhitePixels(chars[i]) / (float) Math.pow(16, 2);
        }
        float max = getMaxBrightness(normalBrightness);
        float min = getMinBrightness(normalBrightness);
        float[] charBrightness = new float[normalBrightness.length];
        for (int i = 0; i < normalBrightness.length; i++) {
            charBrightness[i] = (normalBrightness[i] - min) / (max - min);
        }
        return charBrightness;
    }
    /**
     * average of single sub image
     * @param image sub image
     * @return average
     */
    public float subImageAverage(Color[][] image){
        int greyPixel = 0;
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[row].length; col++) {
                greyPixel += image[row][col].getRed() * 0.2126 + image[row][col].getGreen() *
                        0.7152 + image[row][col].getBlue() * 0.0722;
            }
        }
        return (float)(greyPixel) / (image.length * image[0].length * 255);
    }

    /**
     * return the right index of the char that is the right convertion
     * @param subImageBrightness sub image
     * @param charsBrightness chars
     * @return the right index
     */
    private int matchCharToSubImage(float subImageBrightness, float[] charsBrightness){
        float smaller = 1;
        int index = 0;
        for (int i = 0; i < charsBrightness.length; i++) {
            float brightness = Math.abs(subImageBrightness - charsBrightness[i]);
            if (brightness < smaller) {
                smaller = brightness;
                index = i;
            }
        }
        return index;
    }
    /**
     * convert image to ascii art
     * @param numCharsInRow num chars in row
     * @param charSet chars to convert
     * @param charBrightness brightness of chars
     * @return char[][] of ascii art
     */
    private char[][] imageToAscii(int numCharsInRow, Character[] charSet, float[] charBrightness){
        int row = this.img.getHeight() / numCharsInRow;
        char[][] asciiArt = new char[img.getHeight()/row][numCharsInRow];
        int colCounter = 0;
        int rowCounter = 0;
        for(Map.Entry<Integer, Color[][]> subImage : img.makeSubImages(row).entrySet()){
            float subImageBrightness = subImageAverage(subImage.getValue());
            int index = matchCharToSubImage(subImageBrightness, charBrightness);
            char selectedChar = charSet[index];
            asciiArt[rowCounter][colCounter] = selectedChar;
            colCounter += 1;
            if(colCounter >= numCharsInRow){
                colCounter = 0;
                rowCounter += 1;
            }
        }
        return asciiArt;
    }

    /**
     * main method that run the convertion of image to ascii
     * @param numCharsInRow num chars in row
     * @param charSet char set
     * @return ascii art
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet){
        float[] brightnessOfChars = calculateCharBrightness(charSet);
        return imageToAscii(numCharsInRow, charSet, brightnessOfChars);

    }
}

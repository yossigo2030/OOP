package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
class FileImage implements Image {
    private static final Color DEFAULT_COLOR = Color.WHITE;

    public Color[][] pixelArray;
    private final int newWidth;
    private final int newHeight;

    /**
     * read image from file and update color[][] in the pixels of the image
     * @param filename path
     * @throws IOException exception if not a file
     */
    public FileImage(String filename) throws IOException {
        java.awt.image.BufferedImage im = ImageIO.read(new File(filename));
        int origWidth = im.getWidth(), origHeight = im.getHeight();
        this.newWidth = power(origWidth);
        this.newHeight = power(origHeight);
        this.pixelArray = new Color[origHeight][origWidth];
        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < newWidth; col++) {
                if(row <  ((newHeight - origHeight) / 2) && (row >= origHeight + ((newHeight - origHeight) / 2))
                && (col < (newWidth - origWidth) / 2) && (col >= origWidth + ((newWidth - origWidth) / 2))){
                    this.pixelArray[row][col] = DEFAULT_COLOR;
                }
                else {
                    this.pixelArray[row][col] = new Color(im.getRGB(row, col));
                }
            }
        }
    }

    /**
     * find the closest power of 2 for some num
     * @param n num
     * @return power of 2
     */
    static int power(int n){
        int num = 0;
        for (int i = 0; i < n; i++) {
            if(Math.pow(2, i) >= n){
                num = i;
                break;
            }
        }
        return (int) Math.pow(2, num);
    }

//    @Override
//    public Map<Integer, Color[][]> makeSubImages(int scale) {
//        return Image.super.makeSubImages(scale);
//    }
    //    @Override
//    public Map<Integer, Color[][]> makeSubImages(int scale) {
//        return Image.super.makeSubImages(scale);
//    }
//        /**
//     * make map with sub images
//     * @param scale scale of sub images
//     * @return map
//     */
//    public Map<Integer, Color[][]> makeSubImages(int scale){
//        Map<Integer, Color[][]> subImages = new HashMap<>();
//        int key = 0;
//        boolean flag = false;
//        int colProgress = 0;
//        for (int row = 0; row < this.newHeight; row++) {
//            if(row % scale == 0 && row != 0){
//                key = colProgress;
//                flag = true;
//            }
//            colProgress = key;
//            for (int col = 0; col < this.newWidth; col++) {
//                if(col % scale == 0 && col != 0){
//                    colProgress += 1;
//                }
//                if(flag){
//                    key += 1;
//                    colProgress += 1;
//                    flag = false;
//                }
//                Color[][] subImg = subImages.get(colProgress);
//                if(subImg == null){
//                    Color[][] subColorArr = new Color[scale][scale];
//                    subImages.put(colProgress, subColorArr);
//                    subImg = subImages.get(colProgress);
//                }
//                subImg[row % scale][col % scale] = this.pixelArray[row][col];
//            }
//        }
//        return subImages;
//    }

    @Override
    public int getWidth() {

        //TODO: implement the function
        return this.newWidth;
    }

    @Override
    public int getHeight() {
        //TODO: implement the function
        return this.newHeight;
    }

    @Override
    public Color getPixel(int x, int y) {
        return this.pixelArray[x][y];
        //TODO: implement the function
    }

}

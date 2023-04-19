package image;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Facade for the image module and an interface representing an image.
 * @author Dan Nirel
 */
public interface Image {
    Color getPixel(int x, int y);
    int getWidth();
    int getHeight();

    /**
     * Open an image from file. Each dimensions of the returned image is guaranteed
     * to be a power of 2, but the dimensions may be different.
     * @param filename a path to an image file on disk
     * @return an object implementing Image if the operation was successful,
     * null otherwise
     */
    static Image fromFile(String filename) {
        try {
            return new FileImage(filename);
        } catch(IOException ioe) {
            return null;
        }
    }

    /**
     * Allows iterating the pixels' colors by order (first row, second row and so on).
     * @return an Iterable<Color> that can be traversed with a foreach loop
     */
    default Iterable<Color> pixels() {
        return new ImageIterableProperty<>(
                this, this::getPixel);
    }
    default Map<Integer, Color[][]> makeSubImages(int scale){
        Map<Integer, Color[][]> subImages = new HashMap<>();
        int key = 0;
        boolean flag = false;
        int colProgress = 0;
        for (int row = 0; row < this.getHeight(); row++) {
            if(row % scale == 0 && row != 0){
                key = colProgress;
                flag = true;
            }
            colProgress = key;
            for (int col = 0; col < this.getWidth(); col++) {
                if(col % scale == 0 && col != 0){
                    colProgress += 1;
                }
                if(flag){
                    key += 1;
                    colProgress += 1;
                    flag = false;
                }
                Color[][] subImg = subImages.get(colProgress);
                if(subImg == null){
                    Color[][] subColorArr = new Color[scale][scale];
                    subImages.put(colProgress, subColorArr);
                    subImg = subImages.get(colProgress);
                }
                subImg[row % scale][col % scale] = this.getPixel(row, col);
            }
        }
        return subImages;
    }
//    default Map<Integer, Color[][]> makeSubImages(int scale){
//
//    }
}

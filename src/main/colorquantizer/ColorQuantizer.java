package src.main.colorquantizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

public class ColorQuantizer {

    private Pixel[][] pixelArray;
    private ClusteringMapGenerator gen;

    public ColorQuantizer(Pixel[][] pixelArray, ClusteringMapGenerator gen) {
        this.pixelArray = pixelArray;
        this.gen = gen;
    }

    public ColorQuantizer(String bmpFilename, ClusteringMapGenerator gen) {
        this.gen = gen;
        try {
            BufferedImage image = ImageIO.read(new File(bmpFilename));
            this.pixelArray = Util.convertBitmapToPixelMatrix(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pixel[][] quantizeTo2DArray(int numColors) {
        Pixel[] palette = gen.generateColorPalette(pixelArray, numColors);
        Map<Pixel, Pixel> map = gen.generateColorMap(pixelArray, palette);
        int row = pixelArray.length;
        int col = pixelArray[0].length;
        Pixel[][] quantized = new Pixel[row][col];
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Pixel pixel = pixelArray[i][j];
                Pixel quantizedp = map.get(pixel);
                quantized[i][j] = quantizedp;
            }
        }

        return quantized;
    }

    public void quantizeToBMP(String fileName, int numColors) {
        Pixel[][] pixelMatrix = quantizeTo2DArray(numColors);
        Util.savePixelMatrixToBitmap(fileName, pixelMatrix);
    }
}
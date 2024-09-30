package src.main;

import java.io.File;
import src.main.colorquantizer.ClusteringMapGenerator;
import src.main.colorquantizer.ColorQuantizer;
import src.main.colorquantizer.Pixel;
import src.main.colorquantizer.ColorDistance;
import src.main.colorquantizer.Util;

public class Main {
    public static void main(String[] args) {
        /*************************************
        *   Specify the path to input file   *
        *************************************/
        String inputFile = "resources/cathy.bmp";   
        final String outputFile = "results/quantized_image.bmp";
        ColorDistance metric = new ColorDistance();
        ClusteringMapGenerator generator = new ClusteringMapGenerator(metric);
        final long start = System.nanoTime();

        /****************************************************
        *   Specify the number of colors for quantization   *
        ****************************************************/
        int numColors = 15; 

        try {
            System.out.println(numColors + " colors for quantization");
            ColorQuantizer quantizer = new ColorQuantizer(inputFile, generator);
            System.out.println("Processing quantization...");
            quantizer.quantizeToBMP(outputFile, numColors);
            System.out.println("Quantization complete. Output saved to: " + outputFile);

            Pixel[][] quantizedArray = quantizer.quantizeTo2DArray(numColors);
            System.out.println("Quantized array dimensions: " + quantizedArray.length + "x" + quantizedArray[0].length);

            File output = new File(outputFile);
            if (output.exists()) 
                System.out.println("Output file size: " + output.length() + " bytes");
            else 
                System.out.println("Output file was not created.");
            
            Util.savePixelMatrixToFile("results/quantized_array.txt", quantizedArray);
            System.out.println("Quantized array saved to: quantized_array.txt");
        } 
        
        catch (Exception e) {
            if (numColors <= 0) System.err.println("Invalid number of colors!");
            else System.err.println("An error occurred during quantization:");
        }

        final long end = System.nanoTime();
        long seconds = (end - start) / 1000000000;
        if (seconds >= 60) {
            int minutes = (int) seconds % 60;
            seconds -= minutes * 60;
            String output = String.format("Total execution time: %dm %ds", minutes, seconds);
            System.out.println(output);
        }

        else {
            String output = String.format("Total execution time: %ds", seconds);
            System.out.println(output);
        }
    }
}
package src.main.colorquantizer;

public class ColorDistance {

    public int squaredEuclideanMetric (Pixel p1, Pixel p2) {
        int x = p1.getRed() - p2.getRed();
        int y = p1.getGreen() - p2.getGreen();
        int z = p1.getBlue() - p2.getBlue();

        return (x*x + y*y + z*z);
    }
}
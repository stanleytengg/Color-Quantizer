package src.main.colorquantizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClusteringMapGenerator {

    private ColorDistance metric;

    public ClusteringMapGenerator(ColorDistance metric) {
        this.metric = metric;
    }

    public Pixel[] generateColorPalette(Pixel[][] pixelArray, int numColors) {
        Pixel[] centroids = new Pixel[numColors];

        // pick the first initial color as the first pixel
        centroids[0] = pixelArray[0][0];

        // select remaining colors
        for (int i = 1; i < numColors; i++) {
            Pixel centroid = findCentroid(pixelArray, centroids, i);
            centroids[i] = centroid;
        }

        return centroids;
    }

    private Pixel findCentroid(Pixel[][] pixelArray, Pixel[] centroids, int centroidsCount) {
        Pixel centroid = null;
        int max = Integer.MIN_VALUE;            // largest minimum distance between pixel and centroids

        // iterate through all pixels
        for (Pixel[] row : pixelArray) {
            for (Pixel pixel : row) {
                int min = Integer.MAX_VALUE;    // minimum distance between pixel and centroids

                // find minimum distance between the pixel and centroids
                for (int i = 0; i < centroidsCount; i++) {
                    int distance = metric.squaredEuclideanMetric(centroids[i], pixel);
                    min = (distance < min) ? distance : min;
                }

                // choose the pixel as centroid with the greatest distance from its closest centroid
                if (min > max) {
                    max = min;
                    centroid = pixel;
                }

                // choose the pixel with highest RGB value if distances are equal
                else if (min == max) {
                    int centroidRGB = getRGB(centroid);
                    int pixelRGB = getRGB(pixel);
                    centroid = (pixelRGB > centroidRGB) ? pixel : centroid;
                }
            }
        }

        return centroid;
    }

    private int getRGB(Pixel pixel) {
        return pixel.getRed() << 16 | pixel.getGreen() << 8 | pixel.getBlue();
    }

    public Map<Pixel, Pixel> generateColorMap(Pixel[][] pixelArray, Pixel[] initialColorPalette) {
        Map<Pixel, Pixel> map = new HashMap<>();
        Map<Pixel, ArrayList<Pixel>> clusters = new HashMap<>();
        Pixel[] centroids = initialColorPalette.clone();
        boolean convergence = false;

        // populate the map with centroids and empty list
        createClusters(clusters, initialColorPalette);
        
        while (!convergence) {
            // assign each pixel to its nearest centroid
            for (Pixel[] row : pixelArray) {
                for (Pixel pixel : row) {
                    Pixel nearest = findNearestCentroid(pixel, centroids);
                    ArrayList<Pixel> cluster = clusters.get(nearest);
                    cluster.add(pixel);
                }
            }

            // recalculate each centroid
            Pixel[] newCentroids = recalculate(clusters, centroids);

            // check convergence
            convergence = checkConvergence(centroids, newCentroids);

            // update centroids if there is convergence
            if (!convergence) centroids = newCentroids;

            // update clusters
            createClusters(clusters, centroids);
        }

        for (Pixel[] row : pixelArray) {
            for (Pixel pixel : row) {
                Pixel nearest = findNearestCentroid(pixel, centroids);
                map.put(pixel, nearest);
            }
        }

        return map;
    }

    private void createClusters(Map<Pixel, ArrayList<Pixel>> clusters, Pixel[] centroids) {
        if (!clusters.isEmpty()) clusters.clear();
        for (Pixel centroid : centroids) 
            clusters.put(centroid, new ArrayList<>());
    }
    
    private Pixel findNearestCentroid(Pixel pixel, Pixel[] centroids) {
        Pixel nearest = null;
        int min = Integer.MAX_VALUE;

        // find centroid with least distance with the pixel
        for (Pixel centroid : centroids) {
            int distance = metric.squaredEuclideanMetric(pixel, centroid);
            if (distance < min) {
                min = distance;
                nearest = centroid;
            }
        }

        return nearest;
    }

    private Pixel[] recalculate(Map<Pixel, ArrayList<Pixel>> clusters, Pixel[] centroids) {
        Pixel[] newCentroids = new Pixel[centroids.length];

        for (int i = 0; i < centroids.length; i++) {
            Pixel centroid = centroids[i];
            ArrayList<Pixel> cluster = clusters.get(centroid);
            long redSum = 0, greenSum = 0, blueSum = 0;

            for (Pixel pixel : cluster) {
                redSum += pixel.getRed();
                greenSum += pixel.getGreen();
                blueSum += pixel.getBlue();
            }

            int red = (int) (redSum / cluster.size());
            int green = (int) (greenSum / cluster.size());
            int blue = (int) (blueSum / cluster.size());
            
            Pixel newCentroid = new Pixel(red, green, blue);
            newCentroids[i] = newCentroid;
        }

        return newCentroids;
    }

    private boolean checkConvergence(Pixel[] centroids, Pixel[] newCentroids) {
        for (int i = 0; i < centroids.length; i++) 
            if (!centroids[i].equals(newCentroids[i])) return false;
        
        return true;
    }
}
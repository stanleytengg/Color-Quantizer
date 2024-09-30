# Color Quantizer

This Java application was developed for a Data Structures and Algorithms 2 project on color quantization. It processes a BMP image, reduces its color palette, and generates both a quantized image and an array representation of the results.

## Setup and Usage

### Prerequisites

- Java Development Kit (JDK) installed. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

### Steps to Set Up the Project

1. Clone the Repository 

   ```sh
   git clone https://github.com/stanleytengg/Color-Quantizer.git
   cd Color-Quantizer
   ```
2. Add the Image File
   - Place the BMP image you want to quantize in the `resources` folder.
   - Update the code to reference the correct image file name.
   - Modify the code to set the desired number of colors for quantization.
     
3. Compile the Source Code
   ```sh
   javac -d bin src/main/*.java src/main/colorquantizer/*.java
   ```
4. Run the Application on Terminal
   ```sh
   java -cp bin src.main.Main
   ```
## Demostration
Given an input BMP image:
   ![Sample Input](/resources/cathy.bmp)
The application produces a quantized image and its corresponding array:
   ![Sample Output](/results/quantized_image.bmp)
## Clustering Algorithm Explanation
This project implements a custom clustering algorithm for color quantization, which is used to reduce the number of colors in an image. The algorithm works by iteratively finding color centroids and assigning pixels to their closest centroids, forming clusters of similar colors. Here's a breakdown of the key steps:
1. Initial Color Selection:
   - The algorithm begins by selecting a set of initial color centroids. The first centroid is simply the first pixel from the image. Subsequent centroids are selected based on the maximum distance from any previously chosen centroids to ensure a good spread of colors.
2. Pixel Assignment:
   - Once the centroids are chosen, each pixel in the image is assigned to the nearest centroid using the squared Euclidean distance. This forms clusters of pixels around each centroid.
3. Centroid Recalculation:
   - After all pixels have been assigned to centroids, the algorithm recalculates each centroid's position. The new position is determined by averaging the RGB values of all pixels in its cluster.
4. Convergence Check:
   - The algorithm checks if the centroids have converged, meaning that the new centroids are the same as the previous ones. If not, the process repeats with the updated centroids until convergence is reached.
5. Output:
   - Once convergence is achieved, the final centroids form the color palette for the image, and each pixel is mapped to its corresponding centroid. The quantized image is then generated using this reduced color palette.
## Acknowledgements
This project utilizes the `Util` and `Pixel` classes provided by Dr. Farnan and Dr. Garrison, which served as the backbone of the application. These classes include essential utility functions, such as converting BMP images to pixel matrices and saving pixel matrices as BMP files. Their foundational work was instrumental in the successful development of this project.

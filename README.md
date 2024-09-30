# Color Quantizer Project

This project is a Java application for color quantization. The application takes an input BMP image, reduces the number of colors, and saves the quantized image as well as an array representation of the quantized image.

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
   - Place the BMP image you want to process in the resources folder.
   - Update the code to reflect the name of your image file.
   - Update the code to reflect the number of colors you want to quantize.
     
3. Compile the Source Code
   ```sh
   javac -d bin src/main/*.java src/main/colorquantizer/*.java
   ```
4. Run the Application on Terminal
   ```sh
   java -cp bin src.main.Main
   ```
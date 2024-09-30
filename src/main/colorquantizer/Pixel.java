package src.main.colorquantizer;

public class Pixel {

    private int red;
    private int green;
    private int blue;

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public String toString() {
        return "(" + this.red + "," + this.green + "," + this.blue + ")";
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!(other instanceof Pixel))  return false;
        Pixel pOther = (Pixel) other;
        return this.red == pOther.red &&
                this.green == pOther.green &&
                this.blue == pOther.blue;
    }

    @Override
    public int hashCode() {
        return (100 * this.red) + (10 * this.green) + (this.blue);
    }
}
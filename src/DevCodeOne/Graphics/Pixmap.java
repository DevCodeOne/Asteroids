package DevCodeOne.Graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Pixmap {

    private int width, height;
    private int pixels[], off[];

    public Pixmap(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        init_off();
    }

    public Pixmap(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
        init_off();
    }

    public void blit(int offx, int offy, Pixmap pixmap) {
        int lenx = Math.min((width - offx), pixmap.get_width());
        int leny = Math.min((height - offy), pixmap.get_height());
        for (int i = 0; i < lenx; i++) {
            for (int j = 0; j < leny; j++) {
                pixels[(i + offx) + off[j + offy]] = pixmap.pixels[i + pixmap.off[j]];
            }
        }
    }

    private void init_off() {
        this.off = new int[height];
        for (int i = 0; i < height; i++)
            off[i] = i * width;
    }

    public void set_pixel(int x, int y, int color) {
        if (x > 0 && x < width && y > 0 && y < height)
            pixels[x + off[y]] = color;
    }

    public int get_pixel(int x, int y) {
        if (x > 0 && x < width && y > 0 && y < height)
            return pixels[x + off[y]];
        return -1;
    }

    public int get_width() {
        return width;
    }

    public int get_height() {
        return height;
    }

    public int[] get_pixels() {
        return pixels;
    }
}

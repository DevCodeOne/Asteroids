package DevCodeOne.Graphics;

public class PixGraphics {

    private Pixmap render_target;
    private int color = 255;
    public static final int BLUE = 200 | 50 << 8 | 50 << 16;

    public PixGraphics(Pixmap pixmap) {
        this.render_target = pixmap;
    }

    public Pixmap get_render_target() {
        return render_target;
    }

    public void draw_line(float x, float y, float x2, float y2) {
        float dx = (x2 - x);
        float dy = (y2 - y);
        float mx, my;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        mx = dx / len;
        my = dy / len;
        len *= 4;
        while(len-- > 0) {
            render_target.set_pixel((int) x, (int) y, color);
            float xx3 = x;
            float yy3 = y;
            float xx4 = x;
            float yy4 = y;
            for (int i = 0; i < 3; i++) {
                int upixel3 = render_target.get_pixel((int) (xx3), (int) (yy3));
                int upixel4 = render_target.get_pixel((int) (xx4), (int) (yy4));

                xx3 -= my*0.25f;
                yy3 += mx*0.25f;

                xx4 += my*0.25f;
                yy4 -= mx*0.25f;

                int pixel3 = render_target.get_pixel((int) (xx3), (int) (yy3));
                int pixel4 = render_target.get_pixel((int) (xx4), (int) (yy4));
                render_target.set_pixel((int) (xx3), (int) (yy3), mix(0.8f, pixel3, upixel3));
                render_target.set_pixel((int) (xx4), (int) (yy4), mix(0.8f, pixel4, upixel4));
            }
            x += mx*0.25f;
            y += my*0.25f;
        }
    }

    public void dot(float x, float y, int size) {
        if (x > 1 && x < render_target.get_width()-1 && y > 1 && y < render_target.get_height()-1) {

            x -= size >> 1;
            y -= size >> 1;

            int blur_size = size*2;

            for (int i = 0; i <= size; i++) {
                for (int j = 0; j <= size; j++)
                    render_target.set_pixel((int) x+i, (int) y+j, color);
            }

            for (int i = 0; i >= -blur_size; i--) {
                for (int j = 0; j < size; j++) {
                    int upixel = render_target.get_pixel((int) x + i + 1, (int) y + j);
                    int newpixel = render_target.get_pixel((int) x + i, (int) y + j);
                    render_target.set_pixel((int) x + i, (int) y + j, mix(0.25f, newpixel, upixel));
                }
            }

            for (int i = size; i < blur_size+size; i++) {
                for (int j = 0; j < size; j++) {
                    int upixel = render_target.get_pixel((int) x + i - 1, (int) y + j);
                    int newpixel = render_target.get_pixel((int) x + i, (int) y + j);
                    render_target.set_pixel((int) x + i, (int) y + j, mix(0.25f, newpixel, upixel));
                }
            }

            for (int i = -blur_size; i < size+blur_size; i++) {
                for (int j = 0; j >= -blur_size; j--) {
                    int upixel = render_target.get_pixel((int) x + i, (int) y + j + 1);
                    int newpixel = render_target.get_pixel((int) x + i, (int) y + j);
                    render_target.set_pixel((int) x + i, (int) y + j, mix(0.25f, newpixel, upixel));
                }
            }

            for (int i = -blur_size; i < size+blur_size; i++) {
                for (int j = size; j < size+blur_size; j++) {
                    int upixel = render_target.get_pixel((int) x + i, (int) y + j - 1);
                    int newpixel = render_target.get_pixel((int) x + i, (int) y + j);
                    render_target.set_pixel((int) x + i, (int) y + j, mix(0.25f, newpixel, upixel));
                }
            }
        }
    }

    public void dot_norm(float x, float y, int size) {
        if (x > 1 && x < render_target.get_width()-1 && y > 1 && y < render_target.get_height()-1) {
            x -= size >> 1;
            y -= size >> 1;
            for (int i = 0; i <= size; i++) {
                for (int j = 0; j <= size; j++)
                    render_target.set_pixel((int) x+i, (int) y+j, color);
            }
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void clear(int color) {
        int pixels[] = render_target.get_pixels();
        for (int i = 0; i < render_target.get_pixels().length; i++) {
            pixels[i] = color;
        }
    }

    public int mix(float mix, int color, int color2) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;
        int rd = (int)((r - r2) * mix);
        int gd = (int)((g - g2) * mix);
        int bd = (int)((b - b2) * mix);
        return (r2 + rd) << 16 | (g2 + gd) << 8 | (b2 + bd);
    }
}

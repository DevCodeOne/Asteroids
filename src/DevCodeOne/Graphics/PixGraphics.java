package DevCodeOne.Graphics;

public class PixGraphics {

    private Pixmap render_target;

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
            render_target.set_pixel((int) x, (int) y, 200 << 16 | 50 << 8 | 50);
            float xx3 = x;
            float yy3 = y;
            float xx4 = x;
            float yy4 = y;
            for (int i = 0; i < 2; i++) {
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

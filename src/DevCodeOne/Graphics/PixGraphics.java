package DevCodeOne.Graphics;

public class PixGraphics {

    private Pixmap render_target;
    private int color = 255;
    public static final int BLUE = 200 | 50 << 8 | 50 << 16;
    public static final int RED = 200 << 16;

    public PixGraphics(Pixmap pixmap) {
        this.render_target = pixmap;
    }

    public Pixmap get_render_target() {
        return render_target;
    }

    public void drawLine(float x, float y, float x2, float y2) {
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

            int blur_size = (int)(size*1.5f);
            float factor = 0.35f;

            for (int i = 0; i <= size; i++) {
                for (int j = 0; j <= size; j++)
                    render_target.set_pixel((int) x+i, (int) y+j, color);
            }

            for (int i = 0; i >= -blur_size; i--) {
                for (int j = 0; j <= size; j++) {
                    int upixel = render_target.get_pixel((int) x + i + 1, (int) y + j);
                    int newpixel = render_target.get_pixel((int) x + i, (int) y + j);
                    render_target.set_pixel((int) x + i, (int) y + j, mix(factor, newpixel, upixel));
                }
            }

            for (int i = size; i <= blur_size+size; i++) {
                for (int j = 0; j <= size; j++) {
                    int upixel = render_target.get_pixel((int) x + i - 1, (int) y + j);
                    int newpixel = render_target.get_pixel((int) x + i, (int) y + j);
                    render_target.set_pixel((int) x + i, (int) y + j, mix(factor, newpixel, upixel));
                }
            }

            for (int i = -blur_size; i <= size+blur_size; i++) {
                for (int j = 0; j >= -blur_size; j--) {
                    int upixel = render_target.get_pixel((int) x + i, (int) y + j + 1);
                    int newpixel = render_target.get_pixel((int) x + i, (int) y + j);
                    render_target.set_pixel((int) x + i, (int) y + j, mix(factor, newpixel, upixel));
                }
            }

            for (int i = -blur_size; i <= size+blur_size; i++) {
                for (int j = size; j <= size+blur_size; j++) {
                    int upixel = render_target.get_pixel((int) x + i, (int) y + j - 1);
                    int newpixel = render_target.get_pixel((int) x + i, (int) y + j);
                    render_target.set_pixel((int) x + i, (int) y + j, mix(factor, newpixel, upixel));
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

    public void drawChar(char c, int posx, int posy, int size) {
        c = (Character.toUpperCase(c));
        int offset = 0;
        if (c >= 65 && c <= 90) {
            offset = 65;
        } else if (c >= 48 && c <= 57) {
            offset = 22;
        }
        c -= (char) offset;
        for (int i = 0; i < Font.font[c].length; i++) {
            for (int j = 0; j < Font.font[c][0].length; j++) {
                if (Font.font[c][i][j] == 1)
                    dot_norm(posx + j * size, posy + i * size, size);
            }
        }
    }

    public void drawString(String str, int posx, int posy, int size) {
        char chars[] = str.toCharArray();
        int x = posx;
        int y = posy;
        for (int i = 0; i < chars.length; i++) {
            char uc = Character.toUpperCase(chars[i]);
            if (uc >= 65 && uc <= 90) {
                drawChar(chars[i], x, y, size);
                x += (Font.font[Character.toUpperCase(chars[i]) - 65][0].length + 1) * size;
            } else if (uc == ' ') {
                x+= 4 * size + 1;
            } else if (uc >= 48 && uc <= 57) {
                // 22
                drawChar(chars[i], x, y, size);
                x += (Font.font[Character.toUpperCase(chars[i]) - 22][0].length + 1) * size;
            }
        }
    }

    public int calculateStringWidth(String str, int size) {
        int len = 0;
        char chars[] = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char uc = Character.toUpperCase(chars[i]);
            if (uc >= 65 && uc <= 65+26) {
                len += (Font.font[Character.toUpperCase(chars[i]) - 65][0].length + 1) * size;
            } else if (uc == ' ') {
                len+= 4 * size + 1;
            }
        }
        return len;
    }

    public int calculateStringHeight(String str, int size) {
        int len = 0;
        char chars[] = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char uc = Character.toUpperCase(chars[i]);
            if (uc >= 65 && uc <= 65+26) {
                len = Math.max((Font.font[Character.toUpperCase(chars[i]) - 65][0].length + 1) * size, len);
            }
        }
        return len;
    }
}

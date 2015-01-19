package DevCodeOne.GLGraphics;

import org.lwjgl.opengl.GL11;

public class GLUtil {

    public static void drawChar(char c, int posx, int posy, int size) {
        c = (Character.toUpperCase(c));
        int offset = 0;
        if (c >= 65 && c <= 90) {
            offset = 65;
        } else if (c >= 48 && c <= 57) {
            offset = 22;
        }
        c -= (char) offset;
        GL11.glPointSize(size);
        GL11.glBegin(GL11.GL_POINTS);
        for (int i = 0; i < Font.font[c].length; i++) {
            for (int j = 0; j < Font.font[c][0].length; j++) {
                if (Font.font[c][i][j] == 1)
                    GL11.glVertex2f(posx + j * size, posy + i * size);
            }
        }
        GL11.glEnd();
        GL11.glPointSize(1);
    }

    public static void drawString(String str, int posx, int posy, int size) {
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

    public static int calculateStringWidth(String str, int size) {
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

    public static int calculateStringHeight(String str, int size) {
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

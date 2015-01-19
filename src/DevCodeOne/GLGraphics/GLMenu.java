package DevCodeOne.GLGraphics;

import org.lwjgl.opengl.GL11;

public class GLMenu extends GLComponent{

    private String items[];
    private int fontSize;
    private int startx;
    private int starty;
    private int selectedItem;

    public GLMenu(String items[], int fontSize, int startx, int starty) {
        this.items = items;
        this.fontSize = fontSize;
        this.startx = startx;
        this.starty = starty;
    }

    public void draw() {
        this.draw(0, 0);
    }

    public void draw(int offx, int offy) {
        int height = GLUtil.calculateStringHeight(items[0], fontSize) + fontSize*2;
        int posy = starty;
        for (int i = 0; i < items.length; i++) {
            if (i != selectedItem)
                GL11.glColor3b((byte)200, (byte)50, (byte)50);
            else
                GL11.glColor3b((byte) 122, (byte) 122, (byte) 122);
            GLUtil.drawString(items[i], startx + offx, posy + offy, fontSize);
            posy += height;
        }
    }

    public void select(int i) {
        this.selectedItem = i % items.length;
        if (this.selectedItem < 0) {
            this.selectedItem = items.length - 1;
        }
    }

    public int getIndex() { return selectedItem; }

    public String getSelectedItem() { return items[selectedItem]; }
}

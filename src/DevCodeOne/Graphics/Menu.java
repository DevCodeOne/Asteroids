package DevCodeOne.Graphics;

public class Menu extends Component{

    private String items[];
    private int fontSize;
    private int startx;
    private int starty;
    private int selectedItem;

    public Menu(String items[], int fontSize, int startx, int starty) {
        this.items = items;
        this.fontSize = fontSize;
        this.startx = startx;
        this.starty = starty;
    }

    public void draw(PixGraphics graphics) {
        this.draw(graphics, 0, 0);
    }

    public void draw(PixGraphics graphics, int offx, int offy) {
        int height = graphics.calculateStringHeight(items[0], fontSize) + fontSize*2;
        int posy = starty;
        for (int i = 0; i < items.length; i++) {
            if (i != selectedItem)
                graphics.setColor(PixGraphics.BLUE);
            else
                graphics.setColor(255 << 16 | 255 << 8 | 255);
            graphics.drawString(items[i], startx + offx, posy + offy, fontSize);
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

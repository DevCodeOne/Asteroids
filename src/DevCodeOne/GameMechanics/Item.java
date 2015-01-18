package DevCodeOne.GameMechanics;

import DevCodeOne.Mathematics.Vector2f;

public class Item extends Entity {

    protected Vector2f orientation;
    protected boolean isAttached;

    public Item(Vector2f[] vertices, Vector2f position, int color) {
        super(vertices, position, color);
    }

    public void shoot(Player player, Map map) {

    }

    public void incVelocityBy(float x, float y) { }

    public void setVelocityTo(float x, float y) { }

    public Entity[] collideEvent(Entity entity, Map map) {
        if (entity instanceof Player) {
            ((Player)(entity)).addItem(this);
            isAttached = true;
        }
        return null;
    }

    public boolean isAttached() { return isAttached; }
}

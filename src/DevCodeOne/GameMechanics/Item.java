package DevCodeOne.GameMechanics;

import DevCodeOne.Mathematics.Vector2f;

public class Item extends Entity {

    protected Vector2f orientation;
    protected boolean isAttached;

    public Item(Vector2f[] vertices, Vector2f position, int color) {
        super(vertices, position, color);
    }

    public void shoot(Player player, Map map) {
        int rot = 0;
        for (int i = 0; i < 36; i++) {
            Bullet bullet = new Bullet(4, new Vector2f(getVector(i)), new Vector2f((float) Math.cos(Math.toRadians(rot)), (float) Math.sin(Math.toRadians(rot))), getColor());
            map.add(bullet);
            rot += 10;
        }
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

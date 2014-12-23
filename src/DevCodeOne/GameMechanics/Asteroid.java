package DevCodeOne.GameMechanics;

import DevCodeOne.Mathematics.Vector2f;

public class Asteroid extends Entity {

    public static final int EDGES = 10;

    public Asteroid(float size, Vector2f position, int id) {
        super(null, position, id);
        vertices = new Vector2f[EDGES];
        float factor = (float) (2*Math.PI) / (float) EDGES;
        float change = size*1.25f;
        for (int i = 0; i < EDGES; i++) {
            float rand = (float) (Math.random() * 0.5f) - (0.25f);
            vertices[i] = new Vector2f((float) ((Math.sin(i*factor) * size) - (Math.sin(i*factor) * rand * change)), (float) ((Math.cos(i*factor) * size) - (Math.cos(i*factor) * rand * change)));
        }
        createBoundingBox();
    }

    public Asteroid(Vector2f vertices[], Vector2f position, int id) {
        super(vertices, position, id);
        createBoundingBox();
    }
}

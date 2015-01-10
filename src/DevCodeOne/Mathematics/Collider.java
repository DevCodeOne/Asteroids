package DevCodeOne.Mathematics;

import DevCodeOne.GameMechanics.Entity;

import java.util.Arrays;

public class Collider {

    public static boolean collides(Entity entity, Entity entity2) {
        if (!boundingBoxCollides(entity, entity2))
            return false;
        for (int i = 0; i < entity.getVertices().length; i++) {
            int next = (i + 1) % entity.getVertices().length;
            Vector2f t = new Vector2f(entity.getVector(next));
            t.sub(entity.getVector(i));
            t.norm();
            t.set(-t.getY(), t.getX());
            float rangea[] = project(t, entity);
            float rangeb[] = project(t, entity2);
            if (!overlap(rangea, rangeb)) return false;
        }

        for (int i = 0; i < entity2.getVertices().length; i++) {
            int next = (i + 1) % entity2.getVertices().length;
            Vector2f t = new Vector2f(entity2.getVector(next));
            t.sub(entity2.getVector(i));
            t.norm();
            t.set(-t.getY(), t.getX());
            float rangea[] = project(t, entity);
            float rangeb[] = project(t, entity2);
            if (!overlap(rangea, rangeb)) return false;
        }
        return true;
    }

    public static boolean boundingBoxCollides(Entity entity, Entity entity2) {
        if (entity.getMin().getX() > entity2.getMax().getX() || entity.getMax().getX() < entity2.getMin().getX()
                || entity.getMin().getY() > entity2.getMax().getY() || entity.getMax().getY() < entity2.getMin().getY())
            return false;
        return true;
    }

    private static float[] project(Vector2f axis, Entity entity) {
        float min = axis.dot(entity.getVector(0));
        float max = axis.dot(entity.getVector(0));

        for (int i = 0; i < entity.getVertices().length; i++) {
            float proj = axis.dot(entity.getVector(i));
            if (min > proj)
                min = proj;
            if (max < proj)
                max = proj;
        }

        return new float[]{min, max};
    }

    private static boolean overlap(float a[], float b[]) { // check if the ranges (projections ) overlap
        if (contains(a[0], b)) return true;
        else if (contains(a[1], b)) return true;
        else if (contains(b[0], a)) return true;
        else if (contains(b[1], a)) return true;
        return false;
    }

    private static boolean contains(float n, float range[]) {
        float a = Math.min(range[0], range[1]),b = Math.max(range[0], range[1]);
        return (n >= a && n <= b);
    }


}

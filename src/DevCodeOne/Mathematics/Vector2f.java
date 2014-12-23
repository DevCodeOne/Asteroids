package DevCodeOne.Mathematics;

public class Vector2f {

    private float x, y;

    public Vector2f() { }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f vector2f) {
        this(vector2f.x, vector2f.y);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2f vector2f) {
        this.x = vector2f.x;
        this.y = vector2f.y;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void add(Vector2f vector2f) {
        add(vector2f.x, vector2f.y);
    }

    public void sub(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public void sub(Vector2f vector2f) {
        sub(vector2f.x, vector2f.y);
    }

    public void mult(float x) {
        this.x *= x;
        this.y *= y;
    }

    public void div(float x) {
        this.x /= x;
        this.y /= x;
    }

    public float dot(float x, float y) { return this.x * x + this.y * y; }

    public float dot(Vector2f vector2f) { return dot(vector2f.x, vector2f.y); }

    public float len() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void norm() {
        div(len());
    }

    public void neg() {
        mult(-1);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String toString() { return x + "/ " + y; }
}

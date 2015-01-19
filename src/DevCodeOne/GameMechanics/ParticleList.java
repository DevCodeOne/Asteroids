package DevCodeOne.GameMechanics;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ParticleList {

    private ArrayList<Particle> particles;

    public ParticleList() {
        particles = new ArrayList<Particle>();
    }

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    public void draw(int offx, int offy) {
        GL11.glBegin(GL11.GL_POINTS);
        for (Particle particle : particles) {
            particle.draw(offx, offy);
        }
        GL11.glEnd();
    }

    public void iterate() {
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).changePosByVelocity();
            particles.get(i).decLife();
            if (particles.get(i).isDead()) {
                particles.remove(i);
            }
        }
    }
}

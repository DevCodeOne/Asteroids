package DevCodeOne.GameMechanics;

import DevCodeOne.Graphics.PixGraphics;

import java.util.ArrayList;

public class ParticleList {

    private ArrayList<Particle> particles;

    public ParticleList() {
        particles = new ArrayList<Particle>();
    }

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    public void draw(PixGraphics graphics, int offx, int offy) {
        for (Particle particle : particles) {
            particle.draw(graphics, offx, offy);
        }
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

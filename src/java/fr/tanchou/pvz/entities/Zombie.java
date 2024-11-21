package fr.tanchou.pvz.entities;

public abstract class Zombie extends Entitie {
    private int speed;
    private Effect effect;

    Zombie(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void move() {
        this.setPosition(this.getPosition() - speed/100);
        System.out.println("Zombie avance à la position " + this.getPosition());
    }

    public void takeDamage(int damage) {
        // Réduire les points de vie du zombie
        this.healthPoint -= damage;

        // Vérifier si les points de vie tombent à zéro ou en dessous
        if (this.healthPoint <= 0) {
            onDeath(); // Appeler une méthode pour gérer la mort du zombie
        }
    }

    // Méthode pour gérer la mort du zombie
    private void onDeath() {
        System.out.println("Le zombie est éliminé !");
        // Ajouter ici des actions supplémentaires si nécessaire (comme le retrait de la rangée)
    }
}
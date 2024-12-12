package fr.tanchou.pvz.ia;

public class IAGameResult {
    private final String iaName;
    private final boolean victory;
    private final int ticksSurvived;

    public IAGameResult(String iaName, boolean victory, int ticksSurvived) {
        this.iaName = iaName;
        this.victory = victory;
        this.ticksSurvived = ticksSurvived;
    }

    public String getIaName() {
        return iaName;
    }

    public boolean isVictory() {
        return victory;
    }

    public int getTicksSurvived() {
        return ticksSurvived;
    }

    @Override
    public String toString() {
        return "IA " + iaName + ": " + (victory ? "Victoire" : "Défaite") + ", Ticks: " + ticksSurvived;
    }
}
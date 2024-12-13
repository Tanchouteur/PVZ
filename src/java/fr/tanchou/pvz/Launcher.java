package fr.tanchou.pvz;

import fr.tanchou.pvz.game.PVZ;
import fr.tanchou.pvz.game.Player;
import fr.tanchou.pvz.guiJavaFx.PVZGraphic;
import fr.tanchou.pvz.ia.network.GameAI;
import fr.tanchou.pvz.ia.GenerationManager;
import fr.tanchou.pvz.ia.ModelSaver;

public class Launcher {
    public static void main(String[] args) {

        boolean useGraphicalUI = args.length > 0 && args[0].equalsIgnoreCase("gui");
        boolean iaSingle = args.length > 0 && args[0].equalsIgnoreCase("ia");
        boolean iaMulti = args.length > 0 && args[0].equalsIgnoreCase("ia-multi");

        if (useGraphicalUI) {
            PVZ pvz = new PVZ(new Player("Louis"), new GameAI(ModelSaver.loadModel("best_model.json")));
            PVZGraphic.launchView(pvz);
        } else if (iaSingle) {
            PVZ pvz = new PVZ(new Player("IA Single"));
            pvz.startGame(true);
        } else if (iaMulti) {

            GenerationManager generationManager = new GenerationManager(false);

            for (int i = 0; i < 1; i++) {
                generationManager.evolve();
                System.out.println("Génération " + i + " terminée");
            }

            //ModelSaver.saveModel(generationManager.selectOneBestModel(), "best_model.json");

        } else {
            System.out.println("Usage: gui | ia | ia-multi");
        }
    }
}
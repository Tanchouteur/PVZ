package fr.tanchou.pvz;

import fr.tanchou.pvz.game.PVZ;
import fr.tanchou.pvz.game.Player;
import fr.tanchou.pvz.guiJavaFx.PVZGraphic;
import fr.tanchou.pvz.ia.GenerationManager;

public class Launcher {
    public static void main(String[] args) {

        boolean useGraphicalUI = args.length > 0 && args[0].equalsIgnoreCase("gui");
        boolean iaSingle = args.length > 0 && args[0].equalsIgnoreCase("ia");
        boolean iaMulti = args.length > 0 && args[0].equalsIgnoreCase("ia-multi");

        if (useGraphicalUI) {
            PVZ pvz = new PVZ(new Player("Louis"));
            PVZGraphic.launchView(pvz);
        } else if (iaSingle) {
            PVZ pvz = new PVZ(new Player("IA Single"));
            pvz.startGame(true);
        } else if (iaMulti) {

            GenerationManager generationManager = new GenerationManager(false);

            generationManager.evolve();

        } else {
            System.out.println("Usage: gui | ia | ia-multi");
        }
    }
}
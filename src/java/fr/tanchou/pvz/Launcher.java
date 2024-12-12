package fr.tanchou.pvz;

import fr.tanchou.pvz.game.PVZ;
import fr.tanchou.pvz.game.Player;
import fr.tanchou.pvz.guiJavaFx.PVZGraphic;
import fr.tanchou.pvz.ia.IAEnvironmentManager;
import fr.tanchou.pvz.ia.IAGameResult;

import java.util.List;

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
            pvz.startGame(true, true);
        } else if (iaMulti) {
            IAEnvironmentManager manager = new IAEnvironmentManager(10); // Exemple : 10 IA
            manager.initializeGames();
            manager.startSimulations();

            while (!manager.areAllSimulationsCompleted()) {
                try {
                    Thread.sleep(100); // Vérifie périodiquement si toutes les simulations sont terminées
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }


            manager.stopSimulations();
            List<IAGameResult> results = manager.collectResults();
            //results.forEach(System.out::println);
            System.out.println("fini ");
        } else {
            System.out.println("Usage: gui | ia | ia-multi");
        }
    }
}
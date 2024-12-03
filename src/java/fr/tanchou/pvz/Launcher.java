package fr.tanchou.pvz;

import fr.tanchou.pvz.gui.PVZGraphic;

public class Launcher {
    public static void main(String[] args) {

        boolean useGraphicalUI = args.length > 0 && args[0].equalsIgnoreCase("gui");
        PVZ pvz = new PVZ();

        if (useGraphicalUI) {
            PVZGraphic.launchView(pvz);
        } else {
            pvz.startGame(true);
        }
    }
}

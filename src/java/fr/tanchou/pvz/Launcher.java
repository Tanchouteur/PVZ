package fr.tanchou.pvz;

import fr.tanchou.pvz.guiJavaFx.PVZGraphic;

public class Launcher {
    public static void main(String[] args) {

        boolean useGraphicalUI = args.length > 0 && args[0].equalsIgnoreCase("gui");
        PVZ pvz = new PVZ(new Player("Louis"));

        if (useGraphicalUI) {
            PVZGraphic.launchView(pvz);
        } else {
            pvz.startGame(true);
        }
    }
}

//sunflower 25 sec to spwn sun
//peashooter 14 sec to kill zombie normal

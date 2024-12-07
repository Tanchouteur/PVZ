package fr.tanchou.pvz.guiJavaFx.sound;

public class SoundEffect {
    private final String name;
    private final String filePath;

    public SoundEffect(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }
}
package uk.co.oxhack.jukebox.components;

public class Audio {

    private final String path;
    private final String name;
    private final String album;
    private final String artist;

    public Audio(String path, String name, String album, String artist) {
        this.path = path;
        this.name = name;
        this.album = album;
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

}

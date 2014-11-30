package uk.co.oxhack.jukebox.components;

public class Audio {

    private final String path;
    private final String name;
    private final String album;
    private final String artist;
    private final String duration;
    private final String dateModified;

    public Audio(String path, String name, String album, String artist, String duration, String dateModified) {
        this.path = path;
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.dateModified = dateModified;
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

    public String getDuration() { return duration; }

    public String getDateModified() { return dateModified; }

}

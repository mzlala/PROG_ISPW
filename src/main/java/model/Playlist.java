package model;

public class Playlist {
    private String playlistName, link, username, email;

    public Playlist(){

    }
    public Playlist(String playlistName,String link, String username, String email){
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

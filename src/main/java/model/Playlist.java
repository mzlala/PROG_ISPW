package model;

import java.util.ArrayList;

public class Playlist {
    public int id;
    private String playlistName, link, username, email;
    private ArrayList<String> playlist_genre;

    public Playlist(){

    }
    public Playlist(String email, String username, String playlistName, String link, ArrayList<String> playlist_genre){
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
        setPlaylist_genre(playlist_genre);
    }

    public Playlist(String email, String username, String playlistName, String link, ArrayList<String> playlist_genre,int id){ //SI DOVRA FARE UN
        // UNICO COSTRUTTORE NON DUE; L?HO FATTO PER VEDERE SE FUNZIONA; L'ID MI SERVE NEL DB
        setEmail(email);
        setLink(link);
        setPlaylistName(playlistName);
        setUsername(username);
        setPlaylist_genre(playlist_genre);
        setId(id);
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

    public void setPlaylist_genre(ArrayList<String> playlist_genre) {
        this.playlist_genre = playlist_genre;
    }

    public ArrayList<String> getPlaylist_genre() {
        return playlist_genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

package model;

import engineering.exceptions.LinkIsNotValid;

import java.util.List;

public class Playlist {
    private String id;
    private String playlistName;
    private String link;
    private String username;
    private String email;
    private List<String> playlistGenre;
    private boolean approved = false;
    private List<Double> emotional;


    public Playlist(){
        this.approved = false;
    }

    public Playlist(String email, String username, String playlistName, String link, List<String> playlistGenre, boolean approved){
        setEmail(email);
        setUsername(username);
        setPlaylistName(playlistName);
        setLink(link);
        setPlaylistGenre(playlistGenre);
        setApproved(approved);
        this.id = "";
    }

    public Playlist(String email, String username, String playlistName, String link, List<String> playlistGenre, boolean approved, String id){
        this(email, username, playlistName, link, playlistGenre, approved);
        setId(id);
    }

    public Playlist(String email, String username, String playlistName, String link, List<String> playlistGenre, boolean approved, List<Double> emotional){
        this(email,username,playlistName,link, playlistGenre, approved);
        setEmotional(emotional);
        this.id = "";
    }

    public void setEmotional(List<Double> emotional) {
        this.emotional = emotional;
    }

    public List<Double> getEmotional() {
        return emotional;
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



    public void setPlaylistGenre(List<String> playlistGenre) {
        this.playlistGenre = playlistGenre;
    }

    public List<String> getPlaylistGenre() {
        return playlistGenre;
    }



    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }



    public void setApproved(boolean approved){
        this.approved = approved;
    }

    public boolean getApproved(){
        return approved;
    }


}

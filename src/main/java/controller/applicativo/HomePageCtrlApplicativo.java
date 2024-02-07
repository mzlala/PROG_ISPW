package controller.applicativo;

import engineering.bean.PlaylistBean;
import engineering.dao.*;
import engineering.exceptions.LinkIsNotValid;
import engineering.pattern.observer.Observer;
import engineering.pattern.observer.PlaylistCollection;
import engineering.pattern.observer.Subject;
import model.Playlist;
import view.HomePageCtrlGrafico;

import java.util.*;

import static engineering.dao.TypesOfPersistenceLayer.getPreferredPersistenceType;

public class HomePageCtrlApplicativo {
    public List<PlaylistBean> retrivePlaylistsApproved() {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        List<Playlist> playlists = dao.retrieveApprovedPlaylists();      // Recupero lista Playlist
        List<PlaylistBean> playlistsBean = new ArrayList<>();

        try{
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getId());
                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValid e){
            e.fillInStackTrace();
        }

        return playlistsBean;
    }

    public List<PlaylistBean> searchNamePlaylist(PlaylistBean playlistBean) {

        TypesOfPersistenceLayer persistenceType = getPreferredPersistenceType(); // Prendo il tipo di persistenza impostato nel file di configurazione
        PlaylistDAO dao = persistenceType.createPlaylistDAO();           // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        List<PlaylistBean> playlistsBean = new ArrayList<>();           // Creo una lista di playlistBean
        Playlist playlist = new Playlist();                             // Creo la entity da passare al DAO

        playlist.setPlaylistName(playlistBean.getPlaylistName());
        List<Playlist> playlists = dao.searchPlaylistString(playlist);  // Recupero lista Playlist

        try{
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getId());
                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValid e){
            e.fillInStackTrace();
        }
        return playlistsBean;
    }

    public void observePlaylistTable(Observer observer){
        Subject playlistCollection = new PlaylistCollection();
        playlistCollection.attach(observer);
    }

}

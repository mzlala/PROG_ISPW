package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.*;
import engineering.others.Printer;
import engineering.pattern.abstract_factory.DAOFactory;
import engineering.pattern.observer.PlaylistCollection;
import model.*;

import java.util.*;

public class AccountCtrlApplicativo {

    /**
     * Recupera tutte le playlist globali by username
     */
    public List<PlaylistBean> retrievePlaylists(ClientBean clientBean) {
        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO();         // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        String email = clientBean.getEmail();

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retrievePlaylistsByEmail(email);

        ArrayList<PlaylistBean> playlistsBean = new ArrayList<>();
        try {
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional());
                pB.setId(p.getId());
                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValidException e){
            // Non la valuto perché è un retrieve da persistenza, dove è stata caricata correttamente
            handleDAOException(e);
        }

        return playlistsBean;
    }

    /** Utilizzata per aggiornare i generi musicali preferiti dell'utente in caso in cui prema il bottone Salva */
    public void updateGenreUser(ClientBean clientBean){
        ClientDAO dao = DAOFactory.getDAOFactory().createClientDAO();         // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Client client;
        if(clientBean.isSupervisor()){
            client = new Supervisor(clientBean.getUsername(),clientBean.getEmail(),clientBean.getPreferences());
        } else {
            client = new User(clientBean.getUsername(),clientBean.getEmail(),clientBean.getPreferences());
        }

        // Invio utente model al DAO
        dao.updateGenreClient(client);
    }

    /** Utilizzata per permettere all'autore di eliminare le playlist
     * Non è stato implementata l'eliminazione nel front-end, ma si nel back-end */
    public Boolean deletePlaylist(PlaylistBean pB){

        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO(); // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());
        playlist.setId(pB.getId());

        dao.deletePlaylist(playlist);

        if(playlist.getApproved()) {
            /* OBSERVER -> REMOVE PER FAR AGGIORNARE LA HOME PAGE */
            PlaylistCollection.getInstance().removePlaylist(playlist);
        }
        return true;
    }

    private void handleDAOException(Exception e) {
        Printer.logPrint(e.getMessage());
    }
}

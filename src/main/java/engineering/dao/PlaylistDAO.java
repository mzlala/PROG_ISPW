package engineering.dao;

import model.Playlist;

import java.util.List;

public interface PlaylistDAO {

    /** Inserisce una playlist in persistenza*/
    void insertPlaylist(Playlist playlist);

    /** Elimina la playlist */
    void deletePlaylist(Playlist playlist);

    /** Recupera tutte le playlist dell'utente dall'email */
    void retrievePlaylistByMail(String mail);

    /** Recupera tutte le playlist globali per genere */
    void retrievePlaylistByGenre(String genre);

    /** Recupera tutte le playlist globali by username*/
    List<Playlist> retrivePlaylistByUsername(String username);
}

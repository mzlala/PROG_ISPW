package engineering.dao;

import engineering.exceptions.PlaylistLinkAlreadyInUseException;
import engineering.exceptions.PlaylistNameAlreadyInUseException;
import model.Playlist;

import java.util.List;

public interface PlaylistDAO {

    /** Inserisce una playlist in persistenza*/
    void insertPlaylist(Playlist playlist) throws PlaylistLinkAlreadyInUseException, PlaylistNameAlreadyInUseException;

    /** Non serve che crea una nuova istanza di Playlist*/
    Playlist approvePlaylist(Playlist playlist);

    /** Elimina la playlist */
    void deletePlaylist(Playlist playlist);


    /** Recupera tutte le playlist dell'utente dall'email */
    List<Playlist> retrievePlaylistsByEmail(String email);
    List<Playlist> retrievePendingPlaylists();
    List<Playlist> retrieveApprovedPlaylists();


    /** Recupera tutte le playlist, filtrandole per genere  */
    List<Playlist> searchPlaylistByFilters(Playlist playlist);

    List<Playlist> searchPlaylistByTitle(Playlist playlist);

    List<Playlist> searchPlaylistByGenre(Playlist playlist);

    List<Playlist> searchPlaylistByEmotional(Playlist playlist);
}


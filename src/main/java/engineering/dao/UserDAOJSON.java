package engineering.dao;

import com.google.gson.GsonBuilder;
import engineering.exceptions.*;
import engineering.others.*;
import model.User;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

/*
Ho semplificato la verifica dell'esistenza dell'utente all'interno della funzione insertUser usando il metodo userExists.
Ho unito la creazione della directory e la costruzione del percorso del file userInfo.json all'interno di insertUser.
Ho semplificato la funzione loadUser utilizzando la funzione parseUser per ottenere l'oggetto User dal contenuto del file JSON.
Ho utilizzato il metodo parseUser anche nella funzione getUserFromDirectory.
Ho semplificato la funzione updateGenreUser utilizzando il metodo parseUser per ottenere l'oggetto User dal contenuto del file JSON.
 */
public class UserDAOJSON implements UserDAO {
    private static final String BASE_DIRECTORY = ConfigurationJSON.USER_BASE_DIRECTORY;

    public boolean insertUser(User user) throws EmailAlreadyInUse, UsernameAlreadyInUse {
        if (checkIfUserExistsByEmail(user.getEmail())) {
            throw new EmailAlreadyInUse();
        }
        if (retrieveUserByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyInUse();
        }

        try {
            Path userDirectory = Files.createDirectories(Paths.get(BASE_DIRECTORY, user.getEmail()));
            Path userInfoFile = userDirectory.resolve(ConfigurationJSON.USER_INFO_FILE_NAME);

            String json = new GsonBuilder().setPrettyPrinting().create().toJson(user);
            Files.writeString(userInfoFile, json);

            System.out.println("Utente inserito con successo!");
            return true;
        } catch (IOException e) {
            handleDAOException(e);
            return false;
        }
    }


    public User loadUser(String userEmail) throws UserDoesNotExist{
        try {
            Path userInfoFile = Paths.get(BASE_DIRECTORY, userEmail, ConfigurationJSON.USER_INFO_FILE_NAME);

            if (Files.exists(userInfoFile)) {
                String content = Files.readString(userInfoFile);
                return parseUser(content);
            } else {
                System.out.println("Utente non trovato");
                throw new UserDoesNotExist();
            }
        } catch (IOException e) {
            handleDAOException(e);
        }

        return null;
    }

    public String getPasswordByEmail(String email) throws UserDoesNotExist {
        User user = loadUser(email);
        return user != null ? user.getPassword() : null;
    }
    public User retrieveUserByUsername(String username) {
        try (Stream<Path> userDirectories = Files.list(Paths.get(BASE_DIRECTORY))) {
            return userDirectories
                    .filter(Files::isDirectory)
                    .map(this::getUserFromDirectory)
                    .filter(user -> user != null && user.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            handleDAOException(e);
            return null;
        }
    }

    public void updateGenreUser(String email, List<String> preferences) {
        try {
            Path userInfoFile = Paths.get(BASE_DIRECTORY, email, ConfigurationJSON.USER_INFO_FILE_NAME);

            if (Files.exists(userInfoFile)) {
                String content = Files.readString(userInfoFile);
                User user = parseUser(content);

                user.setPref(preferences);
                System.out.println("Nuove preferenze: " + user.getPref());

                String updatedJson = new GsonBuilder().setPrettyPrinting().create().toJson(user);
                Files.writeString(userInfoFile, updatedJson);

                System.out.println("Preferenze utente aggiornate con successo!");
            } else {
                System.out.println("Utente non trovato o file userInfo.json mancante.");
            }
        } catch (IOException e) {
            handleDAOException(e);
        }
    }

    private User getUserFromDirectory(Path userDirectory) {
        try {
            Path userFilePath = userDirectory.resolve(ConfigurationJSON.USER_INFO_FILE_NAME);

            if (Files.exists(userFilePath)) {
                String content = Files.readString(userFilePath);
                return parseUser(content);
            }
        } catch (IOException e) {
            handleDAOException(e);
        }

        return null;
    }

    private User parseUser(String content) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(content, User.class);
    }
    private boolean checkIfUserExistsByEmail(String userEmail) {
        // Costruisci il percorso della directory dell'utente basandoti sulla mail come nome utente
        Path userDirectory = Paths.get(BASE_DIRECTORY, userEmail);

        // Verifica se la directory dell'utente esiste
        return Files.exists(userDirectory);
    }
    private void handleDAOException(Exception e) {
        e.fillInStackTrace();
    }
}

package dao;

public class SQLUtils {

    public static final String GETACC = "SELECT * FROM account WHERE username = ? AND password = ?";

    public static final String GETDOC = "SELECT * FROM docenti";

    public static final String GETMAT = "SELECT * FROM corsi ORDER BY materia ASC";

    public static final String GETDOCMAT = "SELECT docenti.id, docenti.nome FROM corsi, docenti, insegna WHERE insegna.doc = docenti.id AND insegna.mat = corsi.materia AND insegna.mat = ?";

    public static final String GETINS = "SELECT docenti.id, docenti.nome, corsi.materia FROM corsi, docenti, insegna WHERE insegna.doc = docenti.id AND insegna.mat = corsi.materia";

    public static final String GETRIPE_NEW = "SELECT DISTINCT ripetizioni.doc, ripetizioni.mat, ripetizioni.orarioIn, ripetizioni.giorno, ripetizioni.prenotazione FROM ripetizioni WHERE ripetizioni.doc = ? AND ripetizioni.prenotazione = 0";

    public static final String INSERT_TOKEN = "INSERT INTO sessione VALUES (?, ?, DEFAULT)";

    public static final String REMOVE_TOKEN = "DELETE FROM sessione WHERE token = ?";

    public static final String SIGNRIPE = "UPDATE ripetizioni SET prenotazione = 1, mat = ? WHERE doc = ? AND giorno = ? AND orarioIn = ?";

    public static final String SIGNSTORICO = "INSERT INTO storico VALUES (?, ?, ?, ?, ?, ?, 'attiva')";

    public static final String UPDATESTORICO = "UPDATE storico SET stato = 'attiva' WHERE id_utente = ? AND doc = ? AND nome_prof = ? AND mat = ? AND orarioIn = ? AND giorno = ?";

    public static final String GETUSER_FROM_TOKEN = "SELECT * FROM sessione, account WHERE sessione.id = account.id AND sessione.token = ?";

    public static final String UPDATE_TOKENTIME = "UPDATE sessione SET creazione = DEFAULT WHERE token = ?";

    public static final String NEW_STORICO = "SELECT DISTINCT account.username,  storico.doc, storico.nome_prof, storico.mat, storico.giorno, storico.orarioIn, storico.stato FROM storico, account WHERE storico.id_utente = account.id AND storico.id_utente = ?";

    public static final String GETSTORICO_ADMIN = "SELECT DISTINCT account.username, storico.doc, storico.nome_prof, storico.mat, storico.giorno, storico.orarioIn, storico.stato FROM storico, account WHERE storico.id_utente = account.id";

    public static final String DISDETTAPRENOTAZ = "UPDATE ripetizioni SET ripetizioni.prenotazione = 0, ripetizioni.mat = DEFAULT WHERE ripetizioni.doc = ? AND ripetizioni.mat = ? AND ripetizioni.orarioIn = ? AND ripetizioni.giorno = ?";

    public static final String DISDETTAPRENOTAZ_STORICO = "UPDATE storico SET storico.stato = 'disdetta' WHERE storico.id_utente = ? AND storico.doc = ? AND storico.mat = ? AND storico.orarioIn = ? AND storico.giorno = ?";

    public static final String CONFERMAPRENOTAZ = "UPDATE ripetizioni SET ripetizioni.prenotazione = 0, ripetizioni.mat = DEFAULT WHERE ripetizioni.doc = ? AND ripetizioni.mat = ? AND ripetizioni.orarioIn = ? AND ripetizioni.giorno = ?";

    public static final String CONFERMAPRENOTAZ_STORICO = "UPDATE storico SET storico.stato = 'effettuata' WHERE storico.id_utente = ? AND storico.doc = ? AND storico.mat = ? AND storico.orarioIn = ? AND storico.giorno = ? AND storico.stato = 'attiva'";

    public static final String INSERT_PROF = "INSERT INTO docenti VALUES (DEFAULT, ?)";

    public static final String INSERT_MATERIA = "INSERT INTO corsi VALUES (?)";

    public static final String DELETE_PROF = "DELETE FROM docenti WHERE nome = ?";

    public static final String DELETE_MATERIA = "DELETE FROM corsi WHERE materia = ?";

    public static final String GETIDDOC = "SELECT id FROM docenti WHERE nome = ?";

    public static final String BLANKPRENOTAZ = "INSERT INTO ripetizioni VALUES (?, DEFAULT, 15, 'Lunedi', 0), (?, DEFAULT, 16, 'Lunedi', 0), \n" +
            "(?, DEFAULT, 17, 'Lunedi', 0), (?, DEFAULT, 18, 'Lunedi', 0), (?, DEFAULT, 15, 'Martedi', 0), \n" +
            "(?, DEFAULT, 16, 'Martedi', 0), (?, DEFAULT, 17, 'Martedi', 0), (?, DEFAULT, 18, 'Martedi', 0), \n" +
            "(?, DEFAULT, 15, 'Mercoledi', 0), (?, DEFAULT, 16, 'Mercoledi', 0), (?, DEFAULT, 17, 'Mercoledi', 0), \n" +
            "(?, DEFAULT, 18, 'Mercoledi', 0), (?, DEFAULT, 15, 'Giovedi', 0), (?, DEFAULT, 16, 'Giovedi', 0), \n" +
            "(?, DEFAULT, 17, 'Giovedi', 0), (?, DEFAULT, 18, 'Giovedi', 0), (?, DEFAULT, 15, 'Venerdi', 0), \n" +
            "(?, DEFAULT, 16, 'Venerdi', 0), (?, DEFAULT, 17, 'Venerdi', 0), (?, DEFAULT, 18, 'Venerdi', 0)";

    public static final String CANCBLANKPRENOTAZ = "DELETE FROM ripetizioni WHERE doc = ?";

    public static final String INSERISCI_ASSOC = "INSERT INTO insegna VALUES (?, ?)";

    public static final String ELIMINA_ASSOC = "DELETE FROM insegna WHERE doc = ? AND mat = ?";

    public static final String GETPROF_FROMID = "SELECT nome FROM docenti WHERE id = ?";
}

package dao;

import model.Ripetizioni;
import model.Prenotazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RipetizioniDAO {
    public static ArrayList<Ripetizioni> getRipetizioni(String id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Ripetizioni> ripetiz = new ArrayList<>();
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.GETRIPE_NEW);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                ripetiz.add(new Ripetizioni(rs.getInt("doc"), rs.getString("mat"), rs.getFloat("orarioIn"), rs.getString("giorno"), rs.getInt("prenotazione")));
            }
            return ripetiz;
        } finally {
            DAO.closeStatementResult(ps, rs);
        }
    }

    public static void signRipetizione(String id, String giorno, String orarioIn, String materia) throws SQLException {
        System.out.println("9: " + id);
        System.out.println("9: " + giorno);
        System.out.println("9: " + orarioIn);
        System.out.println("9: " + materia);
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.SIGNRIPE);
            ps.setString(1, materia);
            ps.setString(2, id);
            ps.setString(3, giorno);
            ps.setString(4, orarioIn);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static ArrayList<Prenotazioni> getPrenotazioni(int id_cliente) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Prenotazioni> prenotaz = new ArrayList<>();
        try (Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.NEW_STORICO);
            ps.setInt(1, id_cliente);
            rs = ps.executeQuery();
            while(rs.next()) {
                prenotaz.add(new Prenotazioni(rs.getString("username"), rs.getInt("doc"), rs.getString("nome_prof"), rs.getString("mat"), rs.getString("giorno"), rs.getFloat("orarioIn"), rs.getString("stato"))); //TODO: Qua emerge il nome e cognome
            }
            return prenotaz;
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static ArrayList<Prenotazioni> getPrenotazioniAdmin() throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Prenotazioni> prenotaz = new ArrayList<>();
        System.out.println("Ciao_dando");
        try (Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.GETSTORICO_ADMIN);
            rs = ps.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString("username"));
                System.out.println(rs.getInt("doc"));
                System.out.println(rs.getString("nome_prof"));
                System.out.println(rs.getString("mat"));
                System.out.println(rs.getString("giorno"));
                System.out.println(rs.getString("stato"));
                System.out.println(" ");
                prenotaz.add(new Prenotazioni(rs.getString("username"), rs.getInt("doc"), rs.getString("nome_prof"), rs.getString("mat"), rs.getString("giorno"), rs.getFloat("orarioIn"), rs.getString("stato"))); //TODO: Qua emerge il nome e cognome
            }
            return prenotaz;
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void signStorico(String id, String giorno, String orarioIn, String materia, int id_account, String prof) throws SQLException{
        PreparedStatement ps = null;
        Connection conn = DAO.getConnection();
        try{
            ps = conn.prepareStatement(SQLUtils.SIGNSTORICO);
            ps.setInt(1, id_account);
            ps.setString(2, id);
            ps.setString(3, prof);
            ps.setString(4, materia);
            ps.setString(5, orarioIn);
            ps.setString(6, giorno);
            if (ps.executeUpdate() < 0) {
                ps = conn.prepareStatement(SQLUtils.UPDATESTORICO);
                ps.setInt(1, id_account);
                ps.setString(2, id);
                ps.setString(3, prof);
                ps.setString(4, materia);
                ps.setString(5, orarioIn);
                ps.setString(6, giorno);
                if(ps.executeUpdate() > 0) {
                    return;
                }
            } else {
                return;
            }
        }
        catch (SQLException e) {

        }
        finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void disdettaPrenotazione(String id_prof, String materia, String giorno, String orarioIn) throws SQLException{
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.DISDETTAPRENOTAZ);
            //ps.setInt(1, id_account);
            ps.setString(1, id_prof);
            ps.setString(2, materia);
            ps.setString(3, orarioIn);
            ps.setString(4, giorno);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void disdettaPrenotazioneStorico(int id_account, String id_prof, String materia, String giorno, String orarioIn) throws SQLException {
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.DISDETTAPRENOTAZ_STORICO);
            ps.setInt(1, id_account);
            ps.setString(2, id_prof);
            ps.setString(3, materia);
            ps.setString(4, orarioIn);
            ps.setString(5, giorno);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void confermaPrenotazione(String id_prof, String materia, String giorno, String orarioIn) throws SQLException{
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.CONFERMAPRENOTAZ);
            ps.setString(1, id_prof);
            ps.setString(2, materia);
            ps.setString(3, orarioIn);
            ps.setString(4, giorno);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void confermaPrenotazioneStorico(int id_account, String id_prof, String materia, String giorno, String orarioIn) throws SQLException{
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.CONFERMAPRENOTAZ_STORICO);
            ps.setInt(1, id_account);
            ps.setString(2, id_prof);
            ps.setString(3, materia);
            ps.setString(4, orarioIn);
            ps.setString(5, giorno);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void inserisciBlankPrenotazioni(int id_prof) throws SQLException{
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.BLANKPRENOTAZ);
            for (int i=1; i<=20; i++) {
                ps.setInt(i, id_prof);
            }
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void eliminaBlankRipetizioni(int id_prof) throws SQLException{
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.CANCBLANKPRENOTAZ);
            ps.setInt(1, id_prof);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static String getProfFromID(String id) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String prenotaz = null;
        try (Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.GETPROF_FROMID);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                prenotaz = rs.getString("nome");
            }
            return prenotaz;
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }
}

package dao;

import model.Docenti;
import model.Insegna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsegnaDAO {

    public static ArrayList<Insegna> getInsegna() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Insegna> ins = new ArrayList<>();
        try(Connection conn = DAO.getConnection()){
            ps = conn.prepareStatement(SQLUtils.GETINS);
            rs = ps.executeQuery();
            while(rs.next()) {
                ins.add(new Insegna(rs.getInt("id"),rs.getString("nome"), rs.getString("materia")));
            }
            return ins;
        }
        finally {
            DAO.closeStatementResult(ps, rs);
        }
    }

    public static void inserisciAssociazione(int id_prof, String subj) throws SQLException{
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.INSERISCI_ASSOC);
            ps.setInt(1, id_prof);
            ps.setString(2, subj);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void eliminaAssociazione(int id_prof, String subj) throws SQLException{
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.ELIMINA_ASSOC);
            ps.setInt(1, id_prof);
            ps.setString(2, subj);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }
}

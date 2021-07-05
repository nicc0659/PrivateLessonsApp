package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Docenti;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocentiDAO {
    public static ArrayList<Docenti> getDocenti() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Docenti> teachers = new ArrayList<>();
        try(Connection conn = DAO.getConnection()){
            ps = conn.prepareStatement(SQLUtils.GETDOC);
            rs = ps.executeQuery();
            while(rs.next()) {
                teachers.add(new Docenti(rs.getInt("id"), rs.getString("nome")));
            }
            return teachers;

        } finally {
            DAO.closeStatementResult(ps, rs);
        }
    }

    public static ArrayList<Docenti> getDocentiFromMaterie(String subj) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Docenti> teachers = new ArrayList<>();
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.GETDOCMAT);
            ps.setString(1, subj);
            rs = ps.executeQuery();
            while(rs.next()) {
                teachers.add(new Docenti(rs.getInt("id"), rs.getString("nome")));
            }
            return teachers;
        } finally {
            DAO.closeStatementResult(ps, rs);
        }
    }

    public static void putNewDocente(String nome) throws SQLException {
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.INSERT_PROF);
            ps.setString(1, nome);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void deleteDocente(String nome) throws SQLException {
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.DELETE_PROF);
            ps.setString(1, nome);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static int getDocentiIDFromNome(String nome) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        int teacher_id = 0;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.GETIDDOC);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            while(rs.next()) {
                teacher_id = rs.getInt("id");
            }
            return teacher_id;
        } finally {
            DAO.closeStatementResult(ps, rs);
        }
    }
}

package dao;

import model.Corsi;
import model.Docenti;
import exception.ExceptionServlet;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CorsiDAO {
    public static ArrayList<Corsi> getCorsi() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Corsi> courses = new ArrayList<>();
        try(Connection conn = DAO.getConnection()){
            ps = conn.prepareStatement(SQLUtils.GETMAT);
            rs = ps.executeQuery();
            while(rs.next()) {
                courses.add(new Corsi(rs.getString("materia")));
            }
            return courses;

        } finally {
            DAO.closeStatementResult(ps, rs);
        }
    }

    public static void putNewMateria(String subj, HttpServletResponse response) throws SQLException {
        PreparedStatement ps = null;
        Connection conn = DAO.getConnection();
        try {
            ps = conn.prepareStatement(SQLUtils.INSERT_MATERIA);
            ps.setString(1, subj);
            if(ps.executeUpdate() > 0) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                return;
            }
        } catch (SQLException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void eliminaNewMateria(String subj) throws SQLException {
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.DELETE_MATERIA);
            ps.setString(1, subj);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }
}

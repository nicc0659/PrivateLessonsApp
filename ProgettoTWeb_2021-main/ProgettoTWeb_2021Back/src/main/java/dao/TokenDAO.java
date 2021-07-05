/* TODO: Ricordiamoci TUTTI i vari catch del progetto (gestione errori) */

package dao;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import exception.ExceptionServlet;

public class TokenDAO {
    public static void insertToken(int id, String token) throws SQLException {
        PreparedStatement ps = null;
        try(Connection conn = DAO.getConnection()) {
            ps=conn.prepareStatement(SQLUtils.INSERT_TOKEN);
            ps.setInt(1, id);
            ps.setString(2, token);
            if(ps.executeUpdate() > 0) {
                return;
            }
        } finally {
            DAO.closeStatementResult(ps, null);
        }
    }

    public static void delToken(String tok) throws SQLException {
        try(Connection conn = DAO.getConnection()) {
            delToken(conn, tok);
        }
    }

    public static void delToken(Connection conn, String token) throws SQLException {
        PreparedStatement ps = null;
        if(conn.isClosed())
            conn = DAO.getConnection();
        ps = conn.prepareStatement(SQLUtils.REMOVE_TOKEN);
        ps.setString(1, token);
        if(ps.executeUpdate() > 0) {
            DAO.closeStatementResult(ps, null);
            return;
        } else{
            DAO.closeStatementResult(ps, null);
            throw new ExceptionServlet(HttpServletResponse.SC_BAD_REQUEST, "Token is not deleted");
        }
    }

    public static void updateTokenTime(Connection conn, String token) throws SQLException{
        PreparedStatement ps = null;
        if(conn.isClosed())
            conn = DAO.getConnection();
        ps = conn.prepareStatement(SQLUtils.UPDATE_TOKENTIME);
        ps.setString(1, token);
        if(ps.executeUpdate() > 0)
            return;
        else
            throw new ExceptionServlet(HttpServletResponse.SC_BAD_REQUEST, "Token not updated");
    }
}

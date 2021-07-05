package dao;

import exception.ExceptionServlet;
import model.Account;

import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class AccountDAO {

    public static Account getAccountDB(String username, String password) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try(Connection conn = DAO.getConnection()) {
            ps = conn.prepareStatement(SQLUtils.GETACC);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("id");
                return new Account(id, rs.getString("username"), rs.getInt("admin"));
            }
        } finally {
            //chiudere connessioni
            DAO.closeStatementResult(ps, rs);
        }
        return null;
    }

    public static Account getAccountFromToken(String token) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try(Connection conn = DAO.getConnection()) {
            ps=conn.prepareStatement(SQLUtils.GETUSER_FROM_TOKEN);
            ps.setString(1, token);
            rs = ps.executeQuery();
            if(rs.next()){
                long expire = System.currentTimeMillis();
                long durata = Math.abs(expire - rs.getTimestamp("creazione").getTime());
                if(durata > 100000){ //durata > 10 minuti
                    TokenDAO.delToken(token);
                } else{
                    TokenDAO.updateTokenTime(conn, token);
                    Account user = new Account(rs.getInt("id"), rs.getString("username"), rs.getInt("admin"));
                    user.setToken(token);
                    return user;
                }
            }
        } finally {
            DAO.closeStatementResult(ps, rs);
        }
        return null;
    }
}

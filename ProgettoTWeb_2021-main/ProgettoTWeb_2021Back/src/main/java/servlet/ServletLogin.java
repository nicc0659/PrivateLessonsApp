package servlet;

import com.google.gson.Gson;
import dao.AccountDAO;
import dao.DAO;
import dao.TokenDAO;
import dao.TokenUtil;
import exception.ExceptionServlet;
import model.Account;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletLogin", urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DAO.registerDriver();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //verifyParameters(username, password);
        PrintWriter out = response.getWriter();

        try {
            Account account = AccountDAO.getAccountDB(username, password);
            if (account == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            //gestire token
            int id = account.getId();
            String token = TokenUtil.createJWTToken("" + id, account.getUsername());
            account.setToken(token);
            TokenDAO.insertToken(id, token);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.println(new Gson().toJson(account));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void verifyParameters(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty())
            throw new ExceptionServlet(HttpServletResponse.SC_BAD_REQUEST, "Inserire username e password.");
    }*/
}

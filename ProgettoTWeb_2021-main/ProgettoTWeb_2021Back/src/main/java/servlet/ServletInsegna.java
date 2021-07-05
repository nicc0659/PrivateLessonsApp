package servlet;

import com.google.gson.Gson;
import dao.*;
import model.Account;
import model.Docenti;
import model.Insegna;
import model.Ripetizioni;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ServletInsegna", urlPatterns = "/servletinsegna")
public class ServletInsegna extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DAO.registerDriver();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            ArrayList<Insegna> insegna = InsegnaDAO.getInsegna();
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.println(new Gson().toJson(insegna));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String id = request.getParameter("id");
        String token = request.getParameter("token");
        System.out.println("L'id e: " + id);
        PrintWriter out = response.getWriter();
        try {
            if(token != null) {
                Account account = AccountDAO.getAccountFromToken(token);
                if (account == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
            ArrayList<Ripetizioni> ripetizioni = RipetizioniDAO.getRipetizioni(id);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.println(new Gson().toJson(ripetizioni));
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String nome = req.getParameter("nome");
        String subj = req.getParameter("subj");
        String id_bot = req.getParameter("id_bot");
        try {
            if (id_bot.equals("1")) {
                int id_prof = DocentiDAO.getDocentiIDFromNome(nome);
                InsegnaDAO.inserisciAssociazione(id_prof, subj);
            } else if (id_bot.equals("2")) {
                int id_prof = DocentiDAO.getDocentiIDFromNome(nome);
                InsegnaDAO.eliminaAssociazione(id_prof, subj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

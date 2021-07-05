package servlet;

import com.google.gson.Gson;
import dao.AccountDAO;
import dao.DAO;
import dao.DocentiDAO;
import dao.RipetizioniDAO;
import model.Account;
import model.Docenti;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ServletDoc", urlPatterns = {"/servletdoc"})
public class ServletDoc extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DAO.registerDriver();
    }

    //Funzione utile a mostrare i docenti nella tabella aperta a tutti (no controllo admin)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            ArrayList<Docenti> docenti = DocentiDAO.getDocenti();
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.println(new Gson().toJson(docenti));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String subj = request.getParameter("materia");
        String token = request.getParameter("token");
        //System.out.println("Ciao, il token e: " + token);
        //System.out.print(subj);
        PrintWriter out = response.getWriter();
        try {
            if(token != null) {
                Account account = AccountDAO.getAccountFromToken(token);
                if (account == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
            ArrayList<Docenti> docenti = DocentiDAO.getDocentiFromMaterie(subj);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.println(new Gson().toJson(docenti));
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String nome = req.getParameter("nomeh");
        System.out.println(nome);
        String id_button = req.getParameter("id_bot");
        try {
            if (id_button.equals("1")) {
                DocentiDAO.putNewDocente(nome);
                int id_prof = DocentiDAO.getDocentiIDFromNome(nome);
                RipetizioniDAO.inserisciBlankPrenotazioni(id_prof);
            } else if (id_button.equals("2")) {
                int id_prof = DocentiDAO.getDocentiIDFromNome(nome);
                DocentiDAO.deleteDocente(nome);
                RipetizioniDAO.eliminaBlankRipetizioni(id_prof);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

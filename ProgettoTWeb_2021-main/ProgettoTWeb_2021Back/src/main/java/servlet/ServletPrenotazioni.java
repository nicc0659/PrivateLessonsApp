package servlet;

import com.google.gson.Gson;
import dao.AccountDAO;
import dao.DAO;
import dao.DocentiDAO;
import dao.RipetizioniDAO;
import model.Prenotazioni;
import model.Ripetizioni;
import model.Account;
import exception.ExceptionServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ServletPrenotazioni", urlPatterns = "/servletprenotazioni")
public class ServletPrenotazioni extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DAO.registerDriver();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String token = request.getParameter("token");
        String id = request.getParameter("prof");
        String giorno = request.getParameter("giorno");
        String orarioIn = request.getParameter("orarioIn");
        String materia = request.getParameter("materia");
        String admin = request.getParameter("admin");
        System.out.println("8: " + token);
        System.out.println("8: " + id);
        System.out.println("8: " + giorno);
        System.out.println("8: " + orarioIn);
        System.out.println("8: " + materia);
        System.out.println("8: " + admin);
        PrintWriter out = response.getWriter();
        try {
            Account account = AccountDAO.getAccountFromToken(token);
            if (account == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            int id_account = account.getId();
            System.out.println("8: " + id_account);
            if (id == null && giorno == null && orarioIn == null && materia == null && admin == null) {
                System.out.println("Le sto prendendo");
                ArrayList<Prenotazioni> prenotazioni = RipetizioniDAO.getPrenotazioni(id_account);
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                out.println(new Gson().toJson(prenotazioni));
                out.flush();
            } else if (id == null && giorno == null && orarioIn == null && materia == null) {
                System.out.println("Gliele sto dando");
                ArrayList<Prenotazioni> prenotazioni = RipetizioniDAO.getPrenotazioniAdmin();
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                out.println(new Gson().toJson(prenotazioni));
                out.flush();
            } else {
                RipetizioniDAO.signRipetizione(id, giorno, orarioIn, materia);
                String prof = RipetizioniDAO.getProfFromID(id);
                System.out.println(prof);
                RipetizioniDAO.signStorico(id, giorno, orarioIn, materia, id_account, prof);
                ArrayList<Prenotazioni> prenotazioni = RipetizioniDAO.getPrenotazioni(id_account);
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                out.println(new Gson().toJson(prenotazioni));
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String token = req.getParameter("token");
        String id_prof = req.getParameter("id_prof");
        String materia = req.getParameter("mat");
        String giorno = req.getParameter("giorno");
        String orarioIn = req.getParameter("orarioIn");
        String id_button = req.getParameter("id_button");
        System.out.println("Ora provo a cancellare questo: ");
        System.out.println("Ciao: id = " + id_prof);
        System.out.println("Ciao: token = " + token);
        System.out.println("Ciao: materia = " + materia);
        System.out.println("Ciao: giorno = " + giorno);
        System.out.println("Ciao: orarioIn = " + orarioIn);
        System.out.println("Ciao: id_button = " + id_button);
        PrintWriter out = resp.getWriter();
        try {
            Account account = AccountDAO.getAccountFromToken(token);
            if (account == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            int id_account = account.getId();
            if (id_button.equals("1")) {
                RipetizioniDAO.disdettaPrenotazione(id_prof, materia, giorno, orarioIn);
                RipetizioniDAO.disdettaPrenotazioneStorico(id_account, id_prof, materia, giorno, orarioIn);
            } else if (id_button.equals("2")) {
                RipetizioniDAO.confermaPrenotazione(id_prof, materia, giorno, orarioIn);
                RipetizioniDAO.confermaPrenotazioneStorico(id_account, id_prof, materia, giorno, orarioIn);
            }
            ArrayList<Prenotazioni> prenotazioni = RipetizioniDAO.getPrenotazioni(id_account);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.println(new Gson().toJson(prenotazioni));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

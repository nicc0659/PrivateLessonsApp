package servlet;

import com.google.gson.Gson;
import dao.CorsiDAO;
import dao.DAO;
import dao.DocentiDAO;
import model.Corsi;
import model.Docenti;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ServletCorsi", urlPatterns = "/servletcorsi")
public class ServletCorsi extends HttpServlet {

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
            ArrayList<Corsi> corsi = CorsiDAO.getCorsi();
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            out.println(new Gson().toJson(corsi));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String subj = request.getParameter("subj");
        String id_button = request.getParameter("id_bot");
        try {
            if (id_button.equals("1")) {
                CorsiDAO.putNewMateria(subj, response);
            }
            else if (id_button.equals("2")) {
                CorsiDAO.eliminaNewMateria(subj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

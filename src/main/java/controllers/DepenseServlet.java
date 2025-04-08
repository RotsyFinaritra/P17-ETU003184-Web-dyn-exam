package main.java.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.models.Depense;
import main.java.models.DepenseService;
import main.java.utils.BaseObject;

public class DepenseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        if (session.getAttribute("username") == null) {
            resp.sendRedirect("index.html");
        }
        else {
            Depense depense = new Depense(0);
            try {
                List<BaseObject> depenses = depense.findAll();
                req.setAttribute("depenses", depenses);
                req.getRequestDispatcher("liste-depense.jsp").forward(req, resp);
            } catch (Exception e) {
                out.print("Erreur : "+ e);
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("index.html");
        }
        else {
            int idPrevision = Integer.parseInt(req.getParameter("idPrevision"));
            double montant = Double.parseDouble(req.getParameter("montant"));
            try {
                DepenseService.ajouterDepense(idPrevision, montant);
                resp.sendRedirect("form-depense");
            } catch (Exception e) {
                resp.setContentType("text/html");
                out.print("Erreur : " + e.getMessage());
                out.print("<a href=\"form-depense\">Back</a>");
            }

        }
    }
}

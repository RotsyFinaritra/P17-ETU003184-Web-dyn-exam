package main.java.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.models.Prevision;
import main.java.utils.BaseObject;

public class PrevisionServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        if (session.getAttribute("username") == null) {
            resp.sendRedirect("index.html");
        }
        else {
            Prevision instancePrevision = new Prevision(0);
            try {
                List<BaseObject> previsions =  instancePrevision.findAll();
                req.setAttribute("previsions", previsions);
                req.getRequestDispatcher("liste-prevision.jsp").forward(req, resp);
            } catch (Exception e) {
                out.print("Erreur eee : " + e);
            }
        }
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("username") == null) {
            resp.sendRedirect("index.html");
        }
        else {
            PrintWriter out = resp.getWriter();
    
            String libelle = req.getParameter("libelle");
            double montant =  Double.parseDouble(req.getParameter("montant"));
            if (montant < 0) {
                resp.setContentType("text/html");
                out.print("Le montant doit être positif");
                out.print("<a href=\"form-prevision\">Back</a>");
            } else {
                Prevision prevision = new Prevision(0, libelle, montant);
                try {
                    prevision.save();
                    resp.sendRedirect("form-prevision");
                } catch (Exception e) {
                    out.print("Il y a une erreur : " + e);
                }
            }
    
        }

    }
}

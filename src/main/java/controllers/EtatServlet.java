package main.java.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.models.Prevision;
import main.java.utils.BaseObject;

public class EtatServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Prevision instancePrevision = new Prevision(0);
        try {
            List<BaseObject> previsions = instancePrevision.findAll();
            req.setAttribute("previsions", previsions);
            req.getRequestDispatcher("etat.jsp").forward(req, resp);
        } catch (Exception e) {
            out.print("Erreur eee : " + e);
        }
    }

}

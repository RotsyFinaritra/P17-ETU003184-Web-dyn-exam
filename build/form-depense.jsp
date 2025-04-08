<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.java.controllers.*" %>
<%@ page import="main.java.utils.*" %>
<%@ page import="main.java.models.*" %>
<%@ page import="java.util.*" %>

<%
    List<BaseObject> previsions = (List<BaseObject>) request.getAttribute("previsions"); 
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prevision form</title>
</head>
<body>
    <div class="container">
        <form action="addDepense" method="post">
            <h1>Ajouter une depense</h1>
            <label for="libelle">Libellé</label><br>
            <select name="idPrevision" id="libelle" class="form-control" required>
                <% for (BaseObject prev : previsions) { 
                    Prevision p = (Prevision) prev;
                %>
                    <option value="<%= p.getId() %>"><%= p.getLibelle() %></option>
                <% } %>
            </select><br>
            <label for="montant">Montant</label><br>
            <input type="number" name="montant" id="montant" placeholder="ex: 300000"><br>
            <button type="submit">Valider</button>
        </form>
        <a href="home.html">Back</a>
    </div>
</body>
</html>
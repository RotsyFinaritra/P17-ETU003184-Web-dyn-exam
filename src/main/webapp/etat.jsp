<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.java.controllers.*" %>
<%@ page import="main.java.utils.*" %>
<%@ page import="main.java.models.*" %>
<%@ page import="java.util.*" %>

<%
    List<BaseObject> previsions = (List<BaseObject>) request.getAttribute("previsions");
    Depense depenseInstance = new Depense(0);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Etat</title>
</head>
<body>
    <table class="table table-bordered table-hover" border="1">
        <thead>
            <tr>
                <th>Libelle</th>
                <th>Montant prevision</th>
                <th>Depense total</th>
                <th>Reste</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (BaseObject prev : previsions) {
                    Prevision castedPrev = (Prevision) prev;
            %>
                <tr>
                    <td><%= castedPrev.getLibelle() %></td>
                    <td><%= castedPrev.getMontant() %></td>
                    <td><%= depenseInstance.getDepenseTotalByIdPrevision(castedPrev.getId()) %></td>
                    <td><%= DepenseService.getReste(castedPrev.getId()) %></td>
                </tr>
            <%
            } %>
        </tbody>
    </table>
    <a href="index.html">Back</a>
    
</body>
</html>
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
    <title>Liste previsions</title>
</head>
<body>
    <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 col-md-offset-2">

        <h2>Listes des Previsions</h2>
        
        <table class="table table-bordered table-hover" border="1">
            <thead>
                <tr>
                    <th>Libellé</th>
                    <th>Montant</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (BaseObject prev : previsions) {
                %>
                    <tr>
                        <td><%= ((Prevision) prev).getLibelle() %></td>
                        <td><%= ((Prevision) prev).getMontant() %></td>
                    </tr>
                <%
                } %>
            </tbody>
        </table>
        <a href="home.html">Back</a>
    </div>
    
</body>
</html>
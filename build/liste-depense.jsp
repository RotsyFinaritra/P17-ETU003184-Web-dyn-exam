<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.java.controllers.*" %>
<%@ page import="main.java.utils.*" %>
<%@ page import="main.java.models.*" %>
<%@ page import="java.util.*" %>

<%
    List<BaseObject> depenses = (List<BaseObject>) request.getAttribute("depenses");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listes des departements</title>
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="assets/js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="assets/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 col-md-offset-2">

        <h2>Depenses list</h2>
        
        <table class="table table-bordered table-hover" border="1">
            <thead>
                <tr>
                    <th>libelle</th>
                    <th>montant</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (BaseObject depense : depenses) {
                        int idPrev = ((Depense) depense).getIdPrevision();
                        Prevision prev = (Prevision) (new Prevision(0).findById(idPrev));
                %>
                    <tr>
                        <td><%= prev.getLibelle() %></td>
                        <td><%= ((Depense) depense).getMontant() %></td>
                    </tr>
                <%
                } %>
            </tbody>
        </table>
        <a href="home.html">Back</a>
    </div>
</body>
</html>
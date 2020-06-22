<%@page import="BD.Entidades.Usuario"%>
<%@page import="BD.Entidades.Anuncio"%>
<%@page import="BD.DALs.DALAnuncio"%>
<%@page import="BD.Util.HTML"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    DALAnuncio dalAnu = new DALAnuncio();
%>

<!doctype html>
<html lang="pt-br">
    <head>
        <title>Bom serviço</title>

        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">

        <!-- My CSS -->
        <link rel="stylesheet" href="CSS/style.css"/>
        <link rel="stylesheet" href="CSS/index.css"/>
        <link rel="stylesheet" href="CSS/footer.css"/>
    </head>
    <body>
        <!-- MENU -->
        <%Usuario user = (Usuario) session.getAttribute("usuario");%>
        <nav class="navbar navbar-expand-lg navbar-dark orange lighten-1">
            <a class="navbar-brand" href="index.jsp">
                <img src="Images/logo.png" height="30">
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent-555"
                    aria-controls="navbarSupportedContent-555" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent-555">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="index.jsp"><b>Home</b></a>
                    </li>
                    <%if(user == null){%>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Entrar</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="usuario.jsp">Registrar</a>
                    </li>
                    <%}%>
                    <li class="nav-item">
                        <a class="nav-link" href="HelpAPI.jsp">Bom Serviço API</a>
                    </li>
                </ul>
                
                <%if (user != null){%>
                <ul class="navbar-nav ml-auto nav-flex-icons">
                    <li class="nav-item avatar">
                        <a class="nav-link p-0" href='<%=user.getNivel() == 1 ? "adm.jsp" : "prestador.jsp"%>'>
                            <img src='<%="Images\\DBImages\\Users\\" + user.getFoto()%>' class="rounded-circle z-depth-0" alt="avatar image" height="35">
                        </a>
                    </li>
                </ul>
                <%}%>
            </div>
        </nav>

        <!-- SEARCH BOX -->
        <div class="container-fluid">
            <div class="d-flex center-father">
                <div class="row align-self-center w-100">
                    <div class="col-6 mx-auto">
                        <form class="searchBox" novalidate="" role="search">
                            <input type="search" placeholder="Estou procurando por..." name="q" id="q" value="">
                            <span class="separator"></span>
                            <button class="submitBtn" type="submit">
                                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="11" cy="11" r="8"></circle>
                                <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                                </svg>
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- CONTENT -->
            <div style="padding: 50px 30px 0px 30px;">
                <div class="card-columns">
                    <%String filtro = request.getParameter("q") == null ? "" : request.getParameter("q");%>
                    <%for (Anuncio a : dalAnu.getAnuncios("")) {%>
                    <%if (a.getCategoria().getNome().toLowerCase().contains(filtro.toLowerCase())) {%>
                    <div class="card">
                        <!--style="max-height: 500px;"-->
                        <img class="img-fluid mx-auto d-block card-img-top w-100" src='Images/DBImages/Ads/<%=a.getFotos().get(0).getPath()%>'>
                        <div class="card-body">
                            <h5 class="card-title"><%=a.getTitulo()%></h5>
                            <p class="card-text"><%=a.getDescricao()%></p>
                            <a href='<%="viewAnuncio.jsp?anu_cod=" + a.getCod()%>' class="btn btn-danger stretched-link">Visitar Anuncio</a>
                        </div>
                    </div>
                    <%}%>
                    <%}%>
                </div>
            </div>

            <!-- FOOTER -->
            <%=HTML.footer%>

            <!-- My Imports -->

            <!-- jQuery first, then Popper.js, then Bootstrap JS -->
            <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>

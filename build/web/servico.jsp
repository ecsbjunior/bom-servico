<%@page import="BD.Entidades.Usuario"%>
<%@page import="BD.Util.HTML"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario user = (Usuario) session.getAttribute("usuario");
    if (user != null && user.getNivel() == 1) {
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Controle de Servicos</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">

        <!-- My CSS -->
        <link rel="stylesheet" href="CSS/style.css"/>
        <link rel="stylesheet" href="CSS/footer.css"/>
        <link rel="stylesheet" href="CSS/form.css"/>
        <link rel="stylesheet" href="CSS/table.css"/>
    </head>
    <body onload="procurarServico()">
        <!-- MENU -->
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

        <!-- CONTENT -->
        <div class="container d-flex h-100">
            <div class="row align-self-center w-100">
                <div class="col-6 mx-auto">
                    <div class="jumbotron jumbotron-lg">
                        <form id="dServico" class="text-center border border-light p-5">

                            <p class="h4 mb-4">Controle de Serviços</p>

                            <!-- Login -->
                            <input type="text" class="form-control mb-4" name="sCod" placeholder="Código" readonly>

                            <!-- Password -->
                            <input type="text" class="form-control mb-4" name="sNome" placeholder="Nome do serviço">

                            <!-- Save button -->
                            <button class="btn btn-danger btn-block my-4" type="submit" onclick="salvarServico()">Salvar</button>

                            <hr>

                            <form id="dPesquisa">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Digite o nome do serviço" id="sFiltro" name="sPesquisa">
                                </div>
                                <input type="button" class="btn btn-danger btn-block" value="Pesquisar" onclick="procurarServico()">

                                <div class="style-container-table my-4">
                                    <table class="table bg-danger">
                                        <thead>
                                            <tr>
                                                <th>Código</th>
                                                <th>Nome</th>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody id="previewServicos"></tbody>
                                    </table>
                                </div>
                                <div class="mx-auto" id="snackbar"></div>
                            </form>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- FOOTER -->
        <%=HTML.footer%>

        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        
        <!-- My Imports -->
        <script src="JS/ctrlServico.js"></script>
        <script src="JS/snackbar.js"></script>
    </body>
</html>
<%}%>

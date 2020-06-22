<%@page import="BD.Util.HTML"%>
<%@page import="BD.DALs.DALUsuario"%>
<%@page import="BD.Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Usuario user = (Usuario)session.getAttribute("usuario");
    if(user != null){
        if(user.getNivel() == 0)
            response.sendRedirect("prestador.jsp");
        else
            response.sendRedirect("adm.jsp");
    }
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
        <link rel="stylesheet" href="CSS/style.css">
        <link rel="stylesheet" href="CSS/footer.css"/>
        <link rel="stylesheet" href="CSS/form.css"/>
    </head>
    <body>
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
                        <form class="text-center border border-light p-5" action="login.jsp">

                            <p class="h4 mb-4">Sign in</p>

                            <!-- Login -->
                            <input type="text" id="defaultLoginFormEmail" class="form-control mb-4" name="uLogin" placeholder="Login" required>

                            <!-- Password -->
                            <input type="password" id="defaultLoginFormPassword" class="form-control mb-4" name="uPassword" placeholder="Senha" required>
                            
                            <!-- Remember me -->
                            <div class="d-flex justify-content-around">
                                <div>        
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="defaultLoginFormRemember">
                                        <label class="custom-control-label" for="defaultLoginFormRemember">Remember me</label>
                                    </div>
                                </div>
                            </div>

                            <!-- Sign in button -->
                            <button class="btn btn-danger btn-block my-4" type="submit">Entrar</button>

                            <!-- Register -->
                            <p>Ainda não é um membro?
                                <a href="usuario.jsp">Register</a>
                            </p>
                        </form>
                        <%
                            DALUsuario dal = new DALUsuario();
                            Usuario u;
                            String login = request.getParameter("uLogin");
                            String senha = request.getParameter("uPassword");
                            if(login != null){
                                u = dal.getUsuario(login);
                                if(u != null){
                                    if(u.getSenha().equals(senha)){
                                        session.setAttribute("usuario", u);
                                        if(u.getNivel() == 1)
                                            response.sendRedirect("adm.jsp");
                                        else
                                            response.sendRedirect("prestador.jsp");
                                    }
                                }
                            }
                        %>
                    </div>
                </div>
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

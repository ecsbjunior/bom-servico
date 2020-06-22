<%@page import="BD.Entidades.Usuario"%>
<%@page import="BD.Entidades.Mensagem"%>
<%@page import="BD.Entidades.Foto"%>
<%@page import="BD.Entidades.Anuncio"%>
<%@page import="BD.DALs.DALAnuncio"%>
<%@page import="BD.Util.HTML"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    int codAnuncio = Integer.parseInt(request.getParameter("anu_cod"));
    DALAnuncio dalAnu = new DALAnuncio();
    Anuncio anuncio = dalAnu.getAnuncio(codAnuncio);
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Controle de Categorias</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.5.14/css/mdb.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">



        <!-- My CSS -->
        <link rel="stylesheet" href="CSS/style.css"/>
        <link rel="stylesheet" href="CSS/footer.css"/>
        <link rel="stylesheet" href="CSS/form.css"/>
        <link rel="stylesheet" href="CSS/vAnuncio.css"/>
    </head>
    <body onload="loadMessage(<%=anuncio.getCod()%>)">
        <!-- MENU -->
        <%Usuario user = (Usuario) session.getAttribute("usuario");%>
        <nav class="navbar navbar-expand-lg navbar-dark">
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
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3">
                    <!-- FOTO -->
                    <div class="">
                        <img class='mb-4 w-100' src='Images/DBImages/Users/<%=anuncio.getUsuario().getFoto()%>'>
                    </div>

                    <!-- NOME -->
                    <div class="">
                        <h2 class="h5">Nome:</h2>
                        <p><%=anuncio.getUsuario().getNome()%></p>
                    </div>

                    <!-- TELEFONE -->
                    <div class="">
                        <h2 class="h5">Telefone: </h2>
                        <p><%=anuncio.getUsuario().getTelefone()%></p>
                    </div>

                    <!-- TELEFONE -->
                    <div class="">
                        <h2 class="h5">E-mail </h2>
                        <p><%=anuncio.getUsuario().getEmail()%></p>
                    </div>
                </div>

                <div class="col-sm-9">
                    <!-- TITULO -->
                    <div class="">
                        <h2 class="h5">Titulo:</h2>
                        <p><%=anuncio.getTitulo()%></p>
                    </div>

                    <!-- DESCRIÇÃO -->
                    <div class="">
                        <h2 class="h5">Descrição:</h2>
                        <p><%=anuncio.getDescricao()%></p>
                    </div>

                    <!-- DIAS E HORARIOS -->
                    <div class="">
                        <h2 class="h5">Dia e horario de funcionamento </h2>
                        <p><%=anuncio.getDiasTrabalho() + " das " + anuncio.getHorInicio().toString() + " as " + anuncio.getHorFim().toString()%></p>
                    </div>

                    <!-- FOTOS -->
                    <div class="container my-4">
                        <!--Carousel Wrapper-->
                        <div id="multi-item-example" class="carousel slide carousel-multi-item" data-ride="carousel">
                            <!--Controls-->
                            <div class="controls-top">
                                <a class="btn-floating" href="#multi-item-example" data-slide="prev"><i class="fa fa-chevron-left btn-danger"></i></a>
                                <a class="btn-floating" href="#multi-item-example" data-slide="next"><i class="fa fa-chevron-right btn-danger"></i></a>
                            </div>
                            
                            <ol class="carousel-indicators">
                                <li data-target="#multi-item-example" data-slide-to="0" class="active"></li>
                                <%for (int i = 1; i < Math.ceil(anuncio.getFotos().size() / 3.0); i++) {%>
                                <li data-target="#multi-item-example" data-slide-to="<%=i%>"></li>
                                <%}%>
                            </ol>

                            <!--Slides-->
                            <div class="carousel-inner" role="listbox">
                                <!--First slide-->
                                <div class="carousel-item active">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="card mb-2">
                                                <img class="card-img-top" src='Images/DBImages/Ads/<%=anuncio.getFotos().get(0).getPath()%>'>
                                            </div>
                                        </div>
                                        <%for (int j = 1; j <= 2 && j < anuncio.getFotos().size(); j++) {%>
                                        <div class="col-md-4 clearfix d-none d-md-block">
                                            <div class="card mb-2">
                                                <img class="card-img-top" src='Images/DBImages/Ads/<%=anuncio.getFotos().get(j).getPath()%>'>
                                            </div>
                                        </div>
                                        <%}%>
                                    </div>
                                </div>

                                <!--Others slide-->
                                <%for (int i = 3; i < anuncio.getFotos().size(); i += 3) {%>
                                <div class="carousel-item">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="card mb-2">
                                                <img class="card-img-top" src='Images/DBImages/Ads/<%=anuncio.getFotos().get(i).getPath()%>'>
                                            </div>
                                        </div>
                                        <%for (int j = 1; j < 3 && (i + j) < anuncio.getFotos().size(); j++) {%>
                                        <div class="col-md-4 clearfix d-none d-md-block">
                                            <div class="card mb-2">
                                                <img class="card-img-top" src='Images/DBImages/Ads/<%=anuncio.getFotos().get(i + j).getPath()%>'>
                                            </div>
                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <hr/>

            <h2 class="h4 mb-4">Mensagens: </h2>
            <div id='pMessages'></div>
            
            <form id="dMensagem">
                <textarea class="form-control my-4" id="aMensagem" name='aMensagem' rows="2"></textarea>
                <button type="submit" class="btn btn-danger" onclick="sendMessage(<%=anuncio.getCod()%>)">Enviar mensagem</button>
            </form>
            
            <div class="mx-auto" id="snackbar"></div>
        </div>

        <!-- FOOTER -->
        <%=HTML.footer%>

        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

        <!-- My Imports -->
        <script src="JS/ctrlAnuncio.js"></script>
        <script src="JS/snackbar.js"></script>
    </body>
</html>
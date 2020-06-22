<%@page import="BD.Entidades.Categoria"%>
<%@page import="BD.DALs.DALCategoria"%>
<%@page import="BD.Entidades.Usuario"%>
<%@page import="BD.Util.HTML"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    DALCategoria dalCat = new DALCategoria();
    Usuario user = (Usuario) session.getAttribute("usuario");
    if (user != null) {
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Controle de Categorias</title>
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
    <body onload="procurarAnuncio()">
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
                    <%if (user == null) {%>
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

                <%if (user != null) {%>
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
                        <form class="text-center border border-light p-5" method="POST" enctype="multipart/form-data" id="dAnuncio" onsubmit="return salvarAnuncio()">
                            <p class="h4 mb-4">Controle de Anuncio</p>

                            <!-- COD ANUNCIO -->
                            <input type="text" class="form-control mb-4" id="aCod" name="aCod" placeholder="Código" readonly>
                            <!-- LOGIN USUARIO -->
                            <input type="text" class="form-control mb-4" id="uLogin" name="uLogin" value="<%=user.getLogin()%>" placeholder="Login" readonly>

                            <hr/>

                            <!-- Titulo -->
                            <input type="text" class="form-control mb-4" id="aTitle" name="aTitle" placeholder="Título do anuncio" required>
                            <!-- Descrição -->
                            <textarea class="form-control mb-4" id="aDescricao" name="aDescricao" placeholder="Descrição do anuncio" rows="3" required></textarea>

                            <hr/>

                            <!-- Dias de trabalho -->
                            <input type="text" class="form-control mb-4" id="aDiasDeTrabalho" name="aDiasDeTrabalho" placeholder="Digite os dias de trabalho Exemplo: 'Segunda - Sábado'" required>

                            <div class="form-row mb-4">
                                <div class="col">       
                                    <!-- Horario inicio -->
                                    <div class="form-group">
                                        <label class="text-left" style="width: 100%">Início do expediente:</label>
                                        <input type="time" class="form-control mb-4" id="aHOrInicio" name="aHorInicio" required>
                                    </div>
                                </div>
                                <div class="col">
                                    <!-- Horario fim -->
                                    <div class="form-group">
                                        <label class="text-left" style="width: 100%">Fim do expediente:</label>
                                        <input type="time" class="form-control" id="aHorFim" name="aHorFim" required>
                                    </div>
                                </div>
                            </div>

                            <hr/>

                            <!-- Categorias -->
                            <select class="custom-select mb-4" title="Selecione a categoria" id="aCategoria" name="aCategoria" required>
                                <%for (Categoria c : dalCat.getCategorias("")) {%>
                                <option value="<%=c.getCod()%>"><%=c.getNome()%></option>
                                <%}%>
                            </select>

                            <div id="photo-area">
                                <input type="file" id="aPhoto" name="aPhoto" accept="image/*" multiple required>
                                <div id="gallery"></div>
                            </div>

                            <!-- Save button -->
                            <button class="btn btn-danger btn-block my-4" type="submit">Salvar</button>

                            <hr>

                            <form id="dPesquisa">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Digite o titulo do anuncio" id="cFiltro" name="cPesquisa">
                                </div>
                                <input type="button" class="btn btn-danger btn-block" value="Pesquisar" onclick="procurarAnuncio()">

                                <div class="style-container-table my-4">
                                    <table class="table bg-danger">
                                        <thead>
                                            <tr>
                                                <th>Título</th>
                                                <th>Descrição</th>
                                                <th>Horario de trabalho</th>
                                                <th>Categoria</th>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody id="previewAnuncios"></tbody>
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
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

        <!-- My Imports -->
        <script src="JS/photo.js"></script>
        <script src="JS/ctrlAnuncio.js"></script>
        <script src="JS/snackbar.js"></script>
    </body>
</html>
<%
    }
%>
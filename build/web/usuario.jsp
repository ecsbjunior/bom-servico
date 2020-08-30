<%@page import="BD.Util.HTML"%>
<%@page import="BD.Entidades.Usuario"%>
<%@page import="BD.Entidades.Servico"%>
<%@page import="BD.DALs.DALServico"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    Usuario user = (Usuario) session.getAttribute("usuario");
    DALServico dal = new DALServico();
%>

<!doctype html>
<html lang="pt-br">
    <head>
        <title>Pagina do Usuário</title>

        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">

        <!-- My CSS -->
        <link rel="stylesheet" href="CSS/style.css"/>
        <link rel="stylesheet" href="CSS/footer.css"/>
        <link rel="stylesheet" href="CSS/form.css"/>
        <link rel="stylesheet" href="CSS/table.css"/>
        <link rel="stylesheet" href="CSS/usuario.css"/>
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
                        <form class="text-center border border-light p-5" method="POST" enctype="multipart/form-data" id="dUsuario" onsubmit="return salvarUsuario()">

                            <%if (user != null && user.getNivel() == 0){%>
                            <p class="h4 mb-4">Gerenciar Perfil</p>
                            <%} else {%>
                            <p class="h4 mb-4">Sign Up</p>
                            <%}%>
                            
                            <!-- Login -->
                            <input type="text" class="form-control mb-4" placeholder="Login*" id="uLogin" name="uLogin" maxlength="16" required <%if (user != null && user.getNivel() == 0){%>readonly<%}%>>

                            <!-- Password -->
                            <div class="form-row mb-4">
                                <div class="col">        
                                    <input type="password" class="form-control" placeholder="Senha*" id="uPassword" name="uPassword" maxlength="32" required>
                                </div>
                                <div class="col">
                                    <input type="password" class="form-control" placeholder="Confirmar Senha*" id="uConfirmedPassword" name="uConfirmedPassword" maxlength="32" required>
                                </div>
                            </div>

                            <%if (user != null && user.getNivel() == 1) {%>
                            <select class="custom-select" id="uNivel" name="uNivel">
                                <option value="0" selected>Prestador de serviço</option>
                                <option value="1">Administrador</option>
                            </select>
                            <%}%>

                            <hr>

                            <!-- E-mail -->
                            <input type="email" class="form-control mb-4" placeholder="E-mail*" id="uEmail" name="uEmail" maxlength="50" required <%if (user != null && user.getNivel() == 0){%> readonly<%}%>>

                            <!-- Nome -->
                            <div class="form-row mb-4">
                                <div class="col">
                                    <input type="text" class="form-control" placeholder="Nome*" id="uName" name="uName" maxlength="25" onkeypress="return handleAlphaKeyTyped(event)" required>
                                </div>
                                <div class="col">
                                    <input type="text" class="form-control" placeholder="Sobrenome" id="uSobrenome" name="uSobrenome" maxlength="24" onkeypress="return handleAlphaKeyTyped(event)">
                                </div>
                            </div>

                            <!-- Data de Nascimento -->
                            <div class="form-row mb-4">
                                <div class="col-4 style-dtnascimento">
                                    <label>Data Nascimento:*</label>
                                </div>
                                <div class="col">
                                    <input type="date" class="form-control" placeholder="Data Nascimento*" id="uDtNascimento" name="uDtNascimento" required>
                                </div>
                            </div>

                            <!-- CPF -->
                            <input type="text" class="form-control mb-4" placeholder="CPF*" id="uCPF" name="uCPF" maxlength="11" onchange="maskCPF()" onkeypress="return handleNumberKeyTyped(event)" required>

                            <!-- Phone number -->
                            <input type="text" class="form-control mb-4" placeholder="Número de telefone*" id="uTelefone" name="uTelefone" maxlength="11" onchange="maskPhone()" onkeypress="return handleNumberKeyTyped(event)" required>

                            <!-- Endereço -->
                            <div class="form-row mb-4">
                                <div class="col">
                                    <input type="text" class="form-control" placeholder="Endereço*" id="uEndRua" name="uEndRua" maxlength="25" required>
                                </div>
                                <div class="col-4">
                                    <input type="text" class="form-control" placeholder="Numero*" id="uEndNum" name="uEndNum" maxlength="5" onkeypress="return handleNumberKeyTyped(event)" required>
                                </div>
                            </div>
                            <input type="text" class="form-control mb-4" placeholder="Bairro*" id="uEndBairro" name="uEndBairro" maxlength="20" required>

                            <!-- Serviços -->
                            <select class="custom-select mb-4" title="Selecione o tipo de serviço*" id="uServicos" name="uServicos" required>
                                <%for (Servico s : dal.getServicos("")) {%>
                                <option value="<%=s.getCod()%>"><%=s.getNome()%></option>
                                <%}%>
                            </select>

                            <!-- Photo -->
                            <div id="photo-area">
                                <input type="file" id="uPhoto" name="uPhoto" accept="image/*"> 
                                <div id="gallery"></div>
                            </div>

                            <!-- Sign up button -->
                            <button class="btn btn-danger my-4 btn-block" type="submit">Salvar</button>

                            <hr>

                            <%if (user != null && user.getNivel() == 1) {%>
                            <form id="dPesquisa">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Digite o nome de usuario" id="uFiltro" name="uPesquisa">
                                </div>
                                <input type="button" class="btn btn-danger btn-block" value="Pesquisar" onclick="procurarUsuario()">

                                <div class="style-container-table my-4">
                                    <table class="table bg-danger">
                                        <thead>
                                            <tr>
                                                <th>Nivel</th>
                                                <th>Login</th>
                                                <th>Nome</th>
                                                <th>Email</th>
                                                <th>Telefone</th>
                                                <th>Serviço</th>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody id="previewUsuario"></tbody>
                                    </table>
                                </div>
                                <div class="mx-auto" id="snackbar"></div>
                            </form>
                            <%}%>
                        </form>
                        <div class="mx-auto" id="snackbar"></div>
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

        <!-- My Imports -->
        <script src="JS/photo.js"></script>
        <script src="JS/ctrlUsuario.js"></script>
        <script src="JS/snackbar.js"></script>
        <script>
                                    $(document).ready(() => {
            <%if (user != null && user.getNivel() == 1) {%>
                                        procurarUsuario();
            <%}%>
            <%if (user != null && user.getNivel() == 0) {%>
                                        alterarUsuario('<%=user.getLogin()%>');
            <%}%>

                                    });
        </script>
    </body>
</html>

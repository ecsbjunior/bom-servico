package Servlets;

import BD.DALs.DALServico;
import BD.DALs.DALUsuario;
import BD.Entidades.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
        location = "/",
        fileSizeThreshold = 1024 * 1024, // 1MB *      
        maxFileSize = 1024 * 1024 * 100, // 100MB **
        maxRequestSize = 1024 * 1024 * 10 * 10 // 100MB ***
)

@WebServlet(name = "sUsuario", urlPatterns = {"/sUsuario"})
public class sUsuario extends HttpServlet {

    public String buscarUsuario(String filtro) throws ClassNotFoundException {
        String ans = "";
        ArrayList<Usuario> usuarios = new DALUsuario().getUsuarios(filtro);

        ans = usuarios.stream().map((u) -> String.format(
                "<tr>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td onclick='deletarUsuario(\"%s\")'><img src='Images/Icons/delete.png'></td>"
                + "  <td onclick='alterarUsuario(\"%s\")'><img src='Images/Icons/change.png'></td>"
                + "</tr>",
                u.getNivel() == 1 ? "Administrador" : "Prestador",
                u.getLogin() + "",
                u.getNome(),
                u.getEmail(),
                u.getTelefone(),
                u.getServico().getNome(),
                u.getLogin() + "", u.getLogin() + ""
        )).reduce(ans, String::concat);

        return ans;
    }
    
    public String buscarEmail(String filtro) throws ClassNotFoundException {
        String ans = "";
        ArrayList<Usuario> usuarios = new DALUsuario().getUsuarios(filtro);
        
        if(usuarios.size() > 0)
            ans = usuarios.get(0).getEmail();

        return ans;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            DALUsuario dal = new DALUsuario();
            String acao = request.getParameter("acao");
            String login = request.getParameter("uLogin");
            String filtro, ans;
            
            switch (acao.toLowerCase()) {
                case "consultar":
                    filtro = "UPPER(usu_nome) LIKE '%" + request.getParameter("filtro").toUpperCase() + "%'";
                    ans = buscarUsuario(filtro);
                    response.getWriter().print(ans);
                    break;
                case "consultaremail":
                    filtro = "UPPER(usu_email) LIKE '%" + request.getParameter("filtro").toUpperCase() + "%'";
                    ans = buscarEmail(filtro);
                    response.getWriter().print(ans);
                    break;
                case "consultarlogin":
                    if(!request.getParameter("acaodaacao").equals("alterar")) {
                        filtro = "UPPER(usu_login) LIKE '" + request.getParameter("filtro").toUpperCase() + "'";
                        ans = buscarUsuario(filtro);
                        response.getWriter().print(ans);
                    }
                    break;
                case "deletar":
                    if (dal.delete(login)) {
                        response.getWriter().print("Apagado com sucesso!");
                    }
                    else {
                        response.getWriter().print("Erro ao apagar!");
                    }
                    break;
                case "alterar":
                    Usuario u = dal.getUsuario(login);
                    if (u == null) {
                        response.getWriter().print("Erro ao carregar!");
                    }
                    else {
                        response.getWriter().print(u.toString()+"$"+getServletContext().getRealPath("/"));
                    }
                    break;
                case "salvar":
                    String senha = request.getParameter("uPassword");
                    String email = request.getParameter("uEmail");
                    int nivel = request.getParameter("uNivel") != null ? Integer.parseInt(request.getParameter("uNivel")) : 0;
                    String nome = request.getParameter("uName");
                    String sobrenome = request.getParameter("uSobrenome");
                    LocalDate dtNascimento = LocalDate.parse(request.getParameter("uDtNascimento"));
                    String CPF = request.getParameter("uCPF");
                    String telefone = request.getParameter("uTelefone");
                    String rua = request.getParameter("uEndRua");
                    String num = request.getParameter("uEndNum");
                    String bairro = request.getParameter("uEndBairro");
                    String codServico = request.getParameter("uServicos");
                    
                    Part p = request.getPart("uPhoto");
                    if(!p.getSubmittedFileName().isEmpty()){
                        byte[] imagem = new byte[(int) p.getSize()];
                        p.getInputStream().read(imagem);
                        FileOutputStream arquivo = new FileOutputStream(new File(getServletContext().getRealPath("/") + "/Images/DBImages/Users/" + p.getSubmittedFileName()));
                        arquivo.write(imagem);
                        arquivo.close();
                    }
                    
                    Usuario usuario = new Usuario(
                            login, senha, String.format("%s %s", nome, sobrenome),
                            CPF, email, telefone,
                            String.format("%s,%s - %s", rua, num, bairro), nivel,
                            !p.getSubmittedFileName().isEmpty() ? p.getSubmittedFileName() : null, dtNascimento,
                            new DALServico().getServico(Integer.parseInt(codServico))
                    );
                    
                    if (request.getParameter("acaodaacao").equals("insert")) {
                        if (dal.insert(usuario)) {
                            response.getWriter().print("Inserido com sucesso!");
                            if(!p.getSubmittedFileName().isEmpty()) dal.updateFoto(usuario);
                        }
                        else {
                            response.getWriter().print("Erro ao inserir!");
                        }
                    }
                    else {
                        if (dal.update(usuario)) {
                            response.getWriter().print("Alterado com sucesso!");
                            if(!p.getSubmittedFileName().isEmpty()) dal.updateFoto(usuario);
                        }
                        else {
                            response.getWriter().print("Erro ao alterar!");
                        }
                    }
                    break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(sCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(sUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

package Servlets;

import BD.DALs.DALAnuncio;
import BD.DALs.DALCategoria;
import BD.DALs.DALUsuario;
import BD.Entidades.Anuncio;
import BD.Entidades.Categoria;
import BD.Entidades.Foto;
import BD.Entidades.Usuario;
import BD.Util.Banco;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(
        location = "/",
        fileSizeThreshold = 1024 * 1024, // 1MB *      
        maxFileSize = 1024 * 1024 * 100, // 100MB **
        maxRequestSize = 1024 * 1024 * 10 * 10 // 100MB ***
)

@WebServlet(name = "sAnuncio", urlPatterns = {"/sAnuncio"})
public class sAnuncio extends HttpServlet {

    public String buscarAnuncios(String filtro) throws ClassNotFoundException {
        String ans = "";
        ArrayList<Anuncio> anuncios = new DALAnuncio().getAnuncios(filtro);

        ans = anuncios.stream().map((a) -> String.format(
                "<tr>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td onclick='deletarAnuncio(%d)'><img src='Images/Icons/delete.png'></td>"
                + "  <td onclick='alterarAnuncio(%d)'><img src='Images/Icons/change.png'></td>"
                + "</tr>",
                a.getTitulo(),
                a.getDescricao(),
                a.getDiasTrabalho() + ", das " + a.getHorInicio().toString() + " as " + a.getHorFim().toString(),
                a.getCategoria().getNome(),
                a.getCod(),
                a.getCod()
        )).reduce(ans, String::concat);

        return ans;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod;
            DALAnuncio dalAnu = new DALAnuncio();
            String acao = request.getParameter("acao");

            switch (acao.toLowerCase()) {
                case "consultar":
                    HttpSession session = request.getSession();
                    Usuario usuario = (Usuario) session.getAttribute("usuario");
                    String filtro = "UPPER(anu_titulo) LIKE '%" + request.getParameter("filtro").toUpperCase() + "%'";
                    if (usuario.getNivel() == 0) {
                        filtro += " AND usu_login LIKE '" + usuario.getLogin() + "'";
                    }
                    response.getWriter().print(buscarAnuncios(filtro));
                    break;
                case "deletar":
                    cod = Integer.parseInt(request.getParameter("cod"));
                    if (dalAnu.delete(cod)) {
                        response.getWriter().print("Apagado com sucesso!");
                    }
                    else {
                        response.getWriter().print("Erro ao apagar!");
                    }
                    break;
                case "alterar":
                    cod = Integer.parseInt(request.getParameter("cod"));
                    Anuncio a = dalAnu.getAnuncio(cod);
                    if (a == null) {
                        response.getWriter().print("Erro ao carregar!");
                    }
                    else {
                        response.getWriter().print(a.toString());
                    }
                    break;
                case "salvar":
                    cod = Integer.parseInt(request.getParameter("aCod"));
                    String titulo = request.getParameter("aTitle");
                    String descricao = request.getParameter("aDescricao");
                    String diasTrabalho = request.getParameter("aDiasDeTrabalho");
                    String hInicio = request.getParameter("aHorInicio");
                    LocalTime horInicio = LocalTime.parse(hInicio);
                    String hFim = request.getParameter("aHorFim");
                    LocalTime horFim = LocalTime.parse(hFim);
                    Usuario user = new DALUsuario().getUsuario(request.getParameter("uLogin"));
                    Categoria categoria = new DALCategoria().getCategoria(Integer.parseInt(request.getParameter("aCategoria")));

                    ArrayList<Foto> fotos = new ArrayList();
                    List<Part> parts = (List<Part>) request.getParts();
                    for (Part p : parts) {
                        if (p.getName().equalsIgnoreCase("aphoto") && !p.getSubmittedFileName().isEmpty()) {
                            byte[] imagem = new byte[(int) p.getSize()];
                            p.getInputStream().read(imagem);
                            FileOutputStream arquivo = new FileOutputStream(new File(getServletContext().getRealPath("/") + "/Images/DBImages/Ads/" + p.getSubmittedFileName()));
                            arquivo.write(imagem);
                            arquivo.close();
                            fotos.add(new Foto(p.getSubmittedFileName()));
                        }
                    }

                    Anuncio anuncio = new Anuncio(titulo, descricao, diasTrabalho, horInicio, horFim, user, categoria, fotos);

                    if (cod == 0) {
                        if (dalAnu.insert(anuncio)) {
                            Banco.conectar();
                            anuncio.setCod(Banco.getCon().getMaxPK("Anuncio", "anu_cod"));
                            Banco.desconectar();
                            response.getWriter().print("Inserido com sucesso!");
                            if(fotos.size() > 0) dalAnu.updateFotos(anuncio);
                        }
                        else {
                            response.getWriter().print("Erro ao inserir!");
                        }
                    }
                    else {
                        anuncio.setCod(cod);
                        if (dalAnu.update(anuncio)) {
                            response.getWriter().print("Alterado com sucesso!");
                            if(fotos.size() > 0) dalAnu.updateFotos(anuncio);
                        }
                        else {
                            response.getWriter().print("Erro ao alterar!");
                        }
                    }
                    break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(sAnuncio.class.getName()).log(Level.SEVERE, null, ex);
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

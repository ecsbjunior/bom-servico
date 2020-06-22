package Servlets;

import BD.DALs.DALCategoria;
import BD.Entidades.Categoria;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "sCategoria", urlPatterns = {"/sCategoria"})
public class sCategoria extends HttpServlet {

    public String buscarCategorias(String filtro) throws ClassNotFoundException {
        String ans = "";
        ArrayList<Categoria> categorias = new DALCategoria().getCategorias(filtro);

        ans = categorias.stream().map((c) -> String.format(
                "<tr>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td onclick='deletarCategoria(%s)'><img src='Images/Icons/delete.png'></td>"
                + "  <td onclick='alterarCategoria(%s)'><img src='Images/Icons/change.png'></td>"
                + "</tr>", c.getCod() + "", c.getNome(), c.getCod() + "", c.getCod() + ""
        )).reduce(ans, String::concat);

        return ans;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod;
            DALCategoria dal = new DALCategoria();
            String acao = request.getParameter("acao");
            
            switch(acao.toLowerCase()){
                case "consultar":
                    String filtro = "UPPER(cat_nome) LIKE '%" + request.getParameter("filtro").toUpperCase() + "%'";
                    response.getWriter().print(buscarCategorias(filtro));
                    break;
                case "deletar":
                    cod = Integer.parseInt(request.getParameter("cod"));
                    if(dal.delete(cod))
                        response.getWriter().print("Apagado com sucesso!");
                    else
                        response.getWriter().print("Erro ao apagar!");
                    break;
                case "alterar":
                    cod = Integer.parseInt(request.getParameter("cod"));
                    Categoria c = dal.getCategoria(cod);
                    if(c == null)
                        response.getWriter().print("Erro ao carregar!");
                    else
                        response.getWriter().print(c.toString());
                    break;
                case "salvar":
                    cod = Integer.parseInt(request.getParameter("cCod"));
                    String nome = request.getParameter("cNome");
                    if(cod == 0){
                        if(dal.insert(new Categoria(nome)))
                            response.getWriter().print("Inserido com sucesso!");
                        else
                            response.getWriter().print("Erro ao inserir!");
                    }
                    else{
                        if(dal.update(new Categoria(cod, nome)))
                            response.getWriter().print("Alterado com sucesso!");
                        else
                            response.getWriter().print("Erro ao alterar!");
                    }
                    break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(sCategoria.class.getName()).log(Level.SEVERE, null, ex);
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

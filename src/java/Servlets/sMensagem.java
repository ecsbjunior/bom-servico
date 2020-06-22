package Servlets;

import BD.DALs.DALAnuncio;
import BD.DALs.DALMensagem;
import BD.Entidades.Anuncio;
import BD.Entidades.Mensagem;
import BD.Entidades.Usuario;
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

@WebServlet(name = "sMensagem", urlPatterns = {"/sMensagem"})
public class sMensagem extends HttpServlet {
    public boolean verificarUsuAnu(Mensagem m, int anu_cod) throws ClassNotFoundException{
        return new DALAnuncio().getAnuncios("anu_cod = " + anu_cod + " AND usu_login LIKE '" + m.getUsuario().getLogin() + "'").size() > 0;
    }
    public String buscarMensagem(String filtro, int anu_cod, Usuario user) throws ClassNotFoundException {
        String ans = "";
        ArrayList<Mensagem> mensagens = new DALMensagem().getMensagens("anu_cod = " + anu_cod);
        
        for(Mensagem m : mensagens){
            ans += String.format(
                "<p class='ml-4 my-4' style='color: %s'><b>%s:&nbsp</b>%s</p>%s<hr>",
                m.getUsuario() != null ? verificarUsuAnu(m, anu_cod) ? "#333" : "#AAA" : "#AAA",
                m.getUsuario() != null ? m.getUsuario().getLogin() : "Anônimo", m.getMensagem(),
                user != null && user.getNivel() == 1 ? String.format("<button class='btn btn-danger btn-sm' onclick='deleteMessage(%d, %d)'>Deletar</button>", m.getCod(), anu_cod) : ""
            );
        }

        return ans;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DALMensagem dal = new DALMensagem();
        String acao = request.getParameter("acao");
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            switch(acao.toLowerCase()){
                case "consultar":
                    //String filtro = "UPPER(cat_nome) LIKE '%" + request.getParameter("filtro").toUpperCase() + "%'";
                    response.getWriter().print(buscarMensagem("", Integer.parseInt(request.getParameter("aCod")), (Usuario)request.getSession().getAttribute("usuario")));
                    break;
                case "deletar":
                    int cod = Integer.parseInt(request.getParameter("cod"));
                    if(dal.delete(cod))
                        response.getWriter().print("Apagado com sucesso!");
                    else
                        response.getWriter().print("Erro ao apagar!");
                    break;
                case "alterar":
//                    cod = Integer.parseInt(request.getParameter("cod"));
//                    Categoria c = dal.getCategoria(cod);
//                    if(c == null)
//                        response.getWriter().print("Erro ao carregar!");
//                    else
//                        response.getWriter().print(c.toString());
                    break;
                case "salvar":
//                    cod = Integer.parseInt(request.getParameter("cCod"));
                    String m = request.getParameter("aMensagem");
                    Usuario u = (Usuario)request.getSession().getAttribute("usuario");
                    
                    if(dal.insert(new Mensagem(m, u), Integer.parseInt(request.getParameter("aCod"))))
                        response.getWriter().print("Inserido com sucesso!");
                    else
                        response.getWriter().print("Erro ao inserir!");
//                    if(cod == 0){
//                        
//                    }
//                    else{
//                        if(dal.update(new Categoria(cod, nome)))
//                            response.getWriter().print("Alterado com sucesso!");
//                        else
//                            response.getWriter().print("Erro ao alterar!");
//                    }
                    break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(sMensagem.class.getName()).log(Level.SEVERE, null, ex);
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

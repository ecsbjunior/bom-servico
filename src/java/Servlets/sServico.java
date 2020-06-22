package Servlets;

import BD.DALs.DALServico;
import BD.Entidades.Servico;
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

@WebServlet(name = "sServico", urlPatterns = {"/sServico"})
public class sServico extends HttpServlet {

    public String buscarServicos(String filtro) throws ClassNotFoundException {
        String ans = "";
        ArrayList<Servico> servicos = new DALServico().getServicos(filtro);

        ans = servicos.stream().map((s) -> String.format(
                "<tr>"
                + "  <td>%s</td>"
                + "  <td>%s</td>"
                + "  <td onclick='deletarServico(%s)'><img src='Images/Icons/delete.png'></td>"
                + "  <td onclick='alterarServico(%s)'><img src='Images/Icons/change.png'></td>"
                + "</tr>", s.getCod() + "", s.getNome(), s.getCod() + "", s.getCod() + ""
        )).reduce(ans, String::concat);

        return ans;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            int cod;
            DALServico dal = new DALServico();
            String acao = request.getParameter("acao");
            
            switch(acao.toLowerCase()){
                case "consultar":
                    String filtro = "UPPER(ser_nome) LIKE '%" + request.getParameter("filtro").toUpperCase() + "%'";
                    response.getWriter().print(buscarServicos(filtro));
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
                    Servico s = dal.getServico(cod);
                    if(s == null)
                        response.getWriter().print("Erro ao carregar!");
                    else
                        response.getWriter().print(s.toString());
                    break;
                case "salvar":
                    cod = Integer.parseInt(request.getParameter("sCod"));
                    String nome = request.getParameter("sNome");
                    if(cod == 0){
                        if(dal.insert(new Servico(nome)))
                            response.getWriter().print("Inserido com sucesso!");
                        else
                            response.getWriter().print("Erro ao inserir!");
                    }
                    else{
                        if(dal.update(new Servico(cod, nome)))
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

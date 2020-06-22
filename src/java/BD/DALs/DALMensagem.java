package BD.DALs;

import BD.Entidades.Mensagem;
import BD.Util.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALMensagem {
    public boolean insert(Mensagem m, int anu_cod) throws ClassNotFoundException{
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "INSERT INTO Mensagem(men_cod, usu_login, men_mensagem, anu_cod) VALUES(DEFAULT, ?, ?, ?)";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            
            ps.setString(1, m.getUsuario() != null ? m.getUsuario().getLogin() : null);
            ps.setString(2, m.getMensagem());
            ps.setInt(3, anu_cod);
            
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("DEU ERRO AO INSERIT MENSAGEM");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public boolean update(Mensagem m) throws ClassNotFoundException{
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "UPDATE Mensagem SET men_mensagem = ? WHERE men_cod = ?";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            
            ps.setString(1, m.getMensagem());
            ps.setInt(2, m.getCod());
            
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("DEU ERRO AO ATUALIZAR MENSAGEM");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public boolean delete(int cod) throws ClassNotFoundException{
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "DELETE FROM Mensagem WHERE men_cod = " + cod;
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("DEU ERRO AO DELETAR MENSAGEM");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public ArrayList<Mensagem> getMensagens(String filtro) throws ClassNotFoundException{
        String SQL = "SELECT * FROM Mensagem";
        ArrayList<Mensagem> mensagens = new ArrayList();
        ResultSet rs;
        
        if(filtro.length() > 0) SQL += " WHERE " + filtro;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next()){
                mensagens.add(
                    new Mensagem(
                        rs.getInt("men_cod"),
                        rs.getString("men_mensagem"),
                        new DALUsuario().getUsuario(rs.getString("usu_login"))
                    )
                );
            }
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar Usuarios.");
        }
        
        Banco.desconectar();
        return mensagens;
    }
}

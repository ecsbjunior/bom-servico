package BD.DALs;

import BD.Entidades.Anuncio;
import BD.Entidades.Foto;
import BD.Util.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class DALAnuncio {
    public boolean insert(Anuncio a) throws ClassNotFoundException{
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "INSERT INTO Anuncio(anu_cod, usu_login, cat_cod, anu_titulo, anu_descricao, anu_diastrabalho, anu_horarioinicio, anu_horariofim) VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            
            ps.setString(1, a.getUsuario().getLogin());
            ps.setInt(2, a.getCategoria().getCod());
            ps.setString(3, a.getTitulo());
            ps.setString(4, a.getDescricao());
            ps.setString(5, a.getDiasTrabalho());
            ps.setString(6, a.getHorInicio().toString());
            ps.setString(7, a.getHorFim().toString());
            
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("ERRO AO INSERIR ANUNCIO.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public boolean update(Anuncio a) throws ClassNotFoundException{
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "UPDATE Anuncio SET cat_cod = ?, anu_titulo = ?, anu_descricao = ?, anu_diastrabalho = ?, anu_horarioinicio = ?, anu_horariofim = ? WHERE anu_cod = ?";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            
            ps.setInt(1, a.getCategoria().getCod());
            ps.setString(2, a.getTitulo());
            ps.setString(3, a.getDescricao());
            ps.setString(4, a.getDiasTrabalho());
            ps.setString(5, a.getHorInicio().toString());
            ps.setString(6, a.getHorFim().toString());
            ps.setInt(7, a.getCod());
            
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("ERRO AO ATUALIZAR ANUNCIO.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public boolean updateFotos(Anuncio a) throws ClassNotFoundException{
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "DELETE FROM Foto WHERE anu_cod = ?";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            
            ps.setInt(1, a.getCod());
            
            ans = Banco.getCon().manipular(ps.toString());
            if(ans){
                for(Foto foto : a.getFotos())
                    new DALFoto().insert(foto, a.getCod());
            }
        }
        catch(SQLException e){
            System.out.println("ERRO AO ATUALIZAR FOTOS.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public boolean delete(int cod) throws ClassNotFoundException{
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "DELETE FROM Foto WHERE anu_cod = " + cod;
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ans = Banco.getCon().manipular(ps.toString());
            if(ans){
                SQL = "DELETE FROM Anuncio WHERE anu_cod = " + cod;
                ps = Banco.getCon().getConnect().prepareStatement(SQL);
                ans = Banco.getCon().manipular(ps.toString());
            }
        }
        catch(SQLException e){
            System.out.println("DEU ERRO AO DELETAR ANUNCIO");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    
    public Anuncio getAnuncio(int cod) throws ClassNotFoundException {
        String SQL = "SELECT * FROM Anuncio WHERE anu_cod = " + cod;
        Anuncio anuncio = null;
        ResultSet rs;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next()){
                anuncio = new Anuncio(
                        rs.getInt("anu_cod"),
                        rs.getString("anu_titulo"),
                        rs.getString("anu_descricao"),
                        rs.getString("anu_diastrabalho"),
                        LocalTime.parse(rs.getString("anu_horarioinicio")),
                        LocalTime.parse(rs.getString("anu_horariofim")),
                        new DALUsuario().getUsuario(rs.getString("usu_login")),
                        new DALCategoria().getCategoria(rs.getInt("cat_cod")),
                        new DALFoto().getFotos("anu_cod = " + rs.getString("anu_cod")),
                        new DALMensagem().getMensagens("anu_cod = " + rs.getInt("anu_cod"))
                );
            }
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar ANUNCIOS.");
        }
        
        Banco.desconectar();
        return anuncio;
    }
    
    public ArrayList<Anuncio> getAnuncios(String filtro) throws ClassNotFoundException {
        String SQL = "SELECT * FROM Anuncio";
        ArrayList<Anuncio> anuncios = new ArrayList();
        ResultSet rs;
        
        if(!filtro.isEmpty()) SQL += " WHERE " + filtro;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next()){
                anuncios.add(
                    new Anuncio(
                        rs.getInt("anu_cod"),
                        rs.getString("anu_titulo"),
                        rs.getString("anu_descricao"),
                        rs.getString("anu_diastrabalho"),
                        LocalTime.parse(rs.getString("anu_horarioinicio")),
                        LocalTime.parse(rs.getString("anu_horariofim")),
                        new DALUsuario().getUsuario(rs.getString("usu_login")),
                        new DALCategoria().getCategoria(rs.getInt("cat_cod")),
                        new DALFoto().getFotos("anu_cod = " + rs.getString("anu_cod")),
                        new DALMensagem().getMensagens("anu_cod = " + rs.getInt("anu_cod"))
                    )
                );
            }
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar ANUNCIOS.");
        }
        
        Banco.desconectar();
        return anuncios;
    }
}

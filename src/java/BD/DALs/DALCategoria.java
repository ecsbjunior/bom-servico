package BD.DALs;

import BD.Entidades.Categoria;
import BD.Util.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALCategoria {
    public boolean insert(Categoria c) throws ClassNotFoundException {
        boolean ans;
        String SQL = "INSERT INTO Categoria(cat_cod, cat_nome) VALUES(DEFAULT, ?)";
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setString(1, c.getNome());
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar inserir categoria.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }

    public boolean update(Categoria c) throws ClassNotFoundException {
        boolean ans;
        String SQL = "UPDATE Categoria SET cat_nome = ? WHERE cat_cod = ?";
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setString(1, c.getNome());
            ps.setInt(2, c.getCod());
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar atualizar categoria.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }

    public boolean delete(int cod) throws ClassNotFoundException {
        boolean ans;
        String SQL = "DELETE FROM Categoria WHERE cat_cod = ?";
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setInt(1, cod);
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar deletar categoria.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public Categoria getCategoria(int cod) throws ClassNotFoundException{
        String SQL = "SELECT * FROM Categoria WHERE cat_cod = ?";
        Categoria c = null;
        ResultSet rs;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setInt(1, cod);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next())
                c = new Categoria(rs.getInt("cat_cod"), rs.getString("cat_nome"));
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar categoria.");
        }
        
        Banco.desconectar();
        return c;
    }
    
    public ArrayList<Categoria> getCategorias(String filtro) throws ClassNotFoundException{
        String SQL = "SELECT * FROM Categoria";
        ArrayList<Categoria> categorias = new ArrayList();
        ResultSet rs;
        
        if(filtro.length() > 0) SQL += " WHERE " + filtro;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next())
                categorias.add(new Categoria(rs.getInt("cat_cod"), rs.getString("cat_nome")));
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar categoria.");
        }
        
        Banco.desconectar();
        return categorias;
    }
}

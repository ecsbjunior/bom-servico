package BD.DALs;

import BD.Entidades.Usuario;
import BD.Util.Banco;
import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALUsuario {
    public boolean insert(Usuario u) throws ClassNotFoundException, SQLException {
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "INSERT INTO Usuario(usu_login, usu_senha, usu_nivel, usu_nome, usu_CPF, usu_dtnascimento, usu_email, usu_telefone, usu_endereco, ser_cod) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getSenha());
            ps.setInt(3, u.getNivel());
            ps.setString(4, u.getNome());
            ps.setString(5, u.getCPF());
            ps.setDate(6, Date.valueOf(u.getDtNascimento()));
            ps.setString(7, u.getEmail());
            ps.setString(8, u.getTelefone());
            ps.setString(9, u.getEndereco());
            ps.setInt(10, u.getServico().getCod());
            
            ans = ps.executeUpdate() >= 1;
        }
        catch(SQLException e){
            System.out.println("Deu erro ao inserir o usuario.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }

    public boolean update(Usuario u) throws ClassNotFoundException, SQLException {
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "UPDATE Usuario SET usu_senha = ?, usu_nivel = ?, usu_nome = ?, usu_CPF = ?, usu_dtnascimento = ?, usu_email = ?, usu_telefone = ?, usu_endereco = ?, ser_cod = ? WHERE usu_login = ?";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            
            ps.setString(1, u.getSenha());
            ps.setInt(2, u.getNivel());
            ps.setString(3, u.getNome());
            ps.setString(4, u.getCPF());
            ps.setDate(5, Date.valueOf(u.getDtNascimento().toString()));
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getTelefone());
            ps.setString(8, u.getEndereco());
            ps.setInt(9, u.getServico().getCod());
            ps.setString(10, u.getLogin());
            
            ans = ps.executeUpdate() >= 1;
        }
        catch(SQLException e){
            System.out.println("Deu erro ao atualizar o usuario");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public boolean updateFoto(Usuario u) throws ClassNotFoundException, SQLException {
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "UPDATE Usuario SET usu_foto = ?  WHERE usu_login = ?";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            
            ps.setString(1, u.getFoto());
            ps.setString(2, u.getLogin());
            
            ans = ps.executeUpdate() >= 1;
        }
        catch(SQLException e){
            System.out.println("Deu erro ao atualizar o usuario");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }

    public boolean delete(String login) throws ClassNotFoundException, SQLException {
        boolean ans;
        
        try{
            Banco.conectar();
            String SQL = "DELETE FROM Usuario WHERE usu_login = ?";
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);

            ps.setString(1, login);

            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("Deu erro ao deletar usuario.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public Usuario getUsuario(String login) throws ClassNotFoundException{
        String SQL = "SELECT * FROM Usuario WHERE usu_login = ?";
        Usuario u = null;
        ResultSet rs;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setString(1, login);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next()){
                u = new Usuario (
                    rs.getString("usu_login"), rs.getString("usu_senha"), rs.getString("usu_nome"), rs.getString("usu_CPF"), rs.getString("usu_email"),
                    rs.getString("usu_telefone"), rs.getString("usu_endereco"), rs.getInt("usu_nivel"),
                    rs.getString("usu_foto"), rs.getDate("usu_dtnascimento").toLocalDate(), new DALServico().getServico(rs.getInt("ser_cod"))
                );
            }
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar Usuario.");
        }
        
        Banco.desconectar();
        return u;
    }
    
    public ArrayList<Usuario> getUsuarios(String filtro) throws ClassNotFoundException{
        String SQL = "SELECT * FROM Usuario";
        ArrayList<Usuario> usuarios = new ArrayList();
        ResultSet rs;
        
        if(filtro.length() > 0) SQL += " WHERE " + filtro;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next()){
                usuarios.add(
                    new Usuario (
                        rs.getString("usu_login"), rs.getString("usu_senha"), rs.getString("usu_nome"), rs.getString("usu_CPF"), rs.getString("usu_email"),
                        rs.getString("usu_telefone"), rs.getString("usu_endereco"), rs.getInt("usu_nivel"),
                        rs.getString("usu_foto"), rs.getDate("usu_dtnascimento").toLocalDate(), new DALServico().getServico(rs.getInt("ser_cod"))
                    )
                );
            }
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar Usuarios.");
        }
        
        Banco.desconectar();
        return usuarios;
    }
}

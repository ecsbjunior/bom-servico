package BD.DALs;

import BD.Entidades.Servico;
import BD.Util.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALServico {
    public boolean insert(Servico s) throws ClassNotFoundException {
        boolean ans;
        String SQL = "INSERT INTO Servico(ser_cod, ser_nome) VALUES(DEFAULT, ?)";
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setString(1, s.getNome());
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar inserir serviço.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }

    public boolean update(Servico s) throws ClassNotFoundException {
        boolean ans;
        String SQL = "UPDATE Servico SET ser_nome = ? WHERE ser_cod = ?";
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setString(1, s.getNome());
            ps.setInt(2, s.getCod());
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar atualizar serviço.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }

    public boolean delete(int cod) throws ClassNotFoundException {
        boolean ans;
        String SQL = "DELETE FROM Servico WHERE ser_cod = ?";
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setInt(1, cod);
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar deletar serviço.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public Servico getServico(int cod) throws ClassNotFoundException {
        String SQL = "SELECT * FROM Servico WHERE ser_cod = ?";
        Servico s = null;
        ResultSet rs;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setInt(1, cod);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next())
                s = new Servico(rs.getInt("ser_cod"), rs.getString("ser_nome"));
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar serviço.");
        }
        
        Banco.desconectar();
        return s;
    }
    
    public ArrayList<Servico> getServicos(String filtro) throws ClassNotFoundException{
        String SQL = "SELECT * FROM Servico";
        ArrayList<Servico> servicos = new ArrayList();
        ResultSet rs;
        
        if(filtro.length() > 0) SQL += " WHERE " + filtro;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next())
                servicos.add(new Servico(rs.getInt("ser_cod"), rs.getString("ser_nome")));
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar serviço.");
        }
        
        Banco.desconectar();
        return servicos;
    }
}

package BD.DALs;

import BD.Entidades.Foto;
import BD.Util.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DALFoto {
    public boolean insert(Foto f, int anu_cod) throws ClassNotFoundException {
        boolean ans;
        String SQL = "INSERT INTO Foto(fot_cod, anu_cod, fot_foto) VALUES(DEFAULT, ?, ?)";
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ps.setInt(1, anu_cod);
            ps.setString(2, f.getPath());
            ans = ps.execute();
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar inserir foto.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }

    public boolean delete(int anu_cod) throws ClassNotFoundException {
        boolean ans;
        String SQL = "DELETE FROM Foto WHERE anu_cod = " + anu_cod;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            ans = Banco.getCon().manipular(ps.toString());
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar deletar fotos.");
            ans = false;
        }
        
        Banco.desconectar();
        return ans;
    }
    
    public ArrayList<Foto> getFotos(String filtro) throws ClassNotFoundException {
        String SQL = "SELECT * FROM Foto";
        ArrayList<Foto> fotos = new ArrayList();
        ResultSet rs;
        
        if(filtro.length() > 0) SQL += " WHERE " + filtro;
        
        Banco.conectar();
        try{
            PreparedStatement ps = Banco.getCon().getConnect().prepareStatement(SQL);
            rs = Banco.getCon().consultar(ps.toString());
            while(rs.next())
                fotos.add(
                    new Foto(
                        rs.getInt("fot_cod"),
                        rs.getString("fot_foto")
                    )
                );
        }
        catch(SQLException e){
            System.out.println("Erro ao tentar consultar FOTOS.");
        }
        
        Banco.desconectar();
        return fotos;
    }
}

package BD.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
    private Connection connect;
    private String erro;

    public Conexao() {
        erro = "";
        connect = null;
    }

    public boolean conectar(String local, String banco, String usuario, String senha) {
        boolean conectado = false;
        
        try {
            String url = local + banco;
            connect = DriverManager.getConnection(url, usuario, senha);
            conectado = true;
        }
        catch (SQLException sqlex) {
            this.erro = "Impossivel conectar com a base de dados: " + sqlex.toString();
        }
        catch (Exception ex) {
            this.erro = "Outro erro: " + ex.toString();
        }
        
        return conectado;
    }

    public boolean manipular(String SQL) {
        int result = 0;
        
        try {
            PreparedStatement ps = connect.prepareStatement(SQL);
            result = ps.executeUpdate();
        }
        catch(SQLException sqlex) {
            this.erro = "Erro: " + sqlex.toString();
            result = -1;
        }
        
        return result != -1;
    }

    public ResultSet consultar(String SQL) {
        ResultSet rs = null;
        this.erro = "";
        
        try {
            PreparedStatement ps = connect.prepareStatement(SQL);
            rs = ps.executeQuery();
        }
        catch(SQLException sqlex) {
            this.erro = "Erro: " + sqlex.toString();
            rs = null;
        }
        
        return rs;
    }

    public int getMaxPK(String tabela, String chave) {
        String SQL = "SELECT MAX(" + chave + ") FROM " + tabela;
        int max = 0;
        ResultSet rs = this.consultar(SQL);
        
        try {
            if (rs.next()) max = rs.getInt(1);
        }
        catch (SQLException sqlex) {
            this.erro = "Erro: " + sqlex.toString();
            max = -1;
        }
        
        return max;
    }

    public String getErro() {
        return erro;
    }
    
    public Connection getConnect() {
        return connect;
    }

    public boolean getEstadoConexao() {
        return (connect != null);
    }
}

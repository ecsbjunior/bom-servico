package BD.Util;

import java.sql.SQLException;

public class Banco {

    private static Conexao con = null;

    private Banco() {} // construtor privado, ou seja, nao pode dar um new em banco, da uma segurança maior no projeto

    public static boolean conectar() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        con = new Conexao();
        return con.conectar("jdbc:postgresql://localhost:5432/", "bomservicodb", "postgres", "postgres123");
    }
    
    public static boolean desconectar(){
        try{
            con.getConnect().close();
            return true;
        }
        catch(SQLException ex){
            System.out.println("Erro ao desconectar.");
        }
        return false;
    }

    public static Conexao getCon() {
        return con;
    }
}

package BD.Entidades;

public class Mensagem {
    private int cod;
    private String mensagem;
    private Usuario usuario;

    public Mensagem(int cod, String mensagem, Usuario usuario) {
        this.cod = cod;
        this.mensagem = mensagem;
        this.usuario = usuario;
    }

    public Mensagem(String mensagem, Usuario usuario) {
        this(0, mensagem, usuario);
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

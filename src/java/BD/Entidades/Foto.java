package BD.Entidades;

public class Foto {
    private int cod;
    private String path;

    public Foto(int cod, String path) {
        this.cod = cod;
        this.path = path;
    }

    public Foto(String path) {
        this.path = path;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    } 
}

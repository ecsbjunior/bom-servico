package API;

import BD.DALs.DALAnuncio;
import BD.DALs.DALCategoria;
import BD.Entidades.Anuncio;
import BD.Entidades.Categoria;
import BD.Entidades.Foto;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class APIRestful {

    public static String consultarCategoria(String filtro){
        String XML = "";
        
        try{
            DALCategoria dal = new DALCategoria();
            ArrayList<Categoria> categorias = dal.getCategorias("UPPER(cat_nome) LIKE '%" + filtro.toUpperCase() + "%'");
            
            XML = "<?xml version='1.0' encoding='UTF-8'?>\n";
            XML += "<categorias>\n";
            for(Categoria cat : categorias){
                XML += "\t<categoria id='" + cat.getCod() + "'>\n";
                XML += "\t\t<nome>" + cat.getNome() + "</nome>\n";
                XML += "\t</categoria>\n";
            }
            XML += "</categorias>";
        }
        catch(Exception e){
            System.out.println("Deu erro na API");
            XML = "";
        }
        
        return XML;
    }
    
    public static String consultarAnuncio(String filtro) {
        String XML = "";

        try {
            DALAnuncio dal = new DALAnuncio();
            ArrayList<Anuncio> anuncios = dal.getAnuncios("");

            XML = "<?xml version='1.0' encoding='UTF-8'?>\n";
            XML += "<anuncios>\n";
            for (Anuncio anu : anuncios) {
                //Filtro por nome de categoria
                if(anu.getCategoria().getNome().toLowerCase().contains(filtro.toLowerCase())){
                    XML += "\t<anuncio id='" + anu.getCod() + "'>\n";
                    XML += "\t\t<titulo>" + anu.getTitulo() + "</titulo>\n";
                    XML += "\t\t<descricao>" + anu.getDescricao() + "</descricao>\n";
                    XML += "\t\t<atendimento>" + anu.getDiasTrabalho() + ", das " + anu.getHorInicio().toString() + " às " + anu.getHorFim().toString() + "</atendimento>\n";
                    XML += "\t\t<contado>\n";
                    XML += "\t\t\t<nome>" + anu.getUsuario().getNome() + "</nome>\n";
                    XML += "\t\t\t<telefone>" + anu.getUsuario().getTelefone() + "</telefone>\n";
                    XML += "\t\t\t<email>" + anu.getUsuario().getEmail() + "</email>\n";
                    XML += "\t\t</contado>\n";
                    XML += "\t\t<fotos>\n";
                    for (Foto foto : anu.getFotos()) {
                        XML += "\t\t\t<foto src='" + "Images\\DBImages\\Ads\\" + foto.getPath() + "'/>\n";
                    }
                    XML += "\t\t</fotos>\n";
                    XML += "\t</anuncio>\n";
                }
            }
            XML += "</anuncios>";
        }
        catch (Exception e) {
            System.out.println("Deu erro na API");
            XML = "";
        }

        return XML;
    }

    public static String consumirAPIAnuncio(String filtro) {
        StringBuffer dados = new StringBuffer();
        try {
            URL url = new URL("http://localhost:8084/BomServico/consultaAnuncio?servico=anuncio&categoria=" + filtro);
            URLConnection con = url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = "";
            while (null != (s = br.readLine())) {
                dados.append(s);
            }
            br.close();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return dados.toString();
    }
}

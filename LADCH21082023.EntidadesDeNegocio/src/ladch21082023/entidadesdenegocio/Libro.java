package ladch21082023.entidadesdenegocio;

/**
 * @author Leonel
 */
public class Libro {
    
    public int idLibro;
    public String titulo;
    public String autor;
    public String año;
    public int top_aux;

    public Libro() {
    }

    public Libro(int idLibro, String titulo, String autor, String año, int top_aux) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.año = año;
        this.top_aux = top_aux;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String Year) {
        this.año = año;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public void settitulo(String parameter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}

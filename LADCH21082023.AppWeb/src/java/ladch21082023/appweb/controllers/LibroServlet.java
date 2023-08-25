package ladch21082023.appweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import ladch21082023.accesoadatos.LibroDAL;
import ladch21082023.appweb.utils.Utilidad;
import ladch21082023.entidadesdenegocio.Libro;

/**
 * @author Leonel
 */
@WebServlet(name = "LibroServlet", urlPatterns = {"/Libro"})
public class LibroServlet extends HttpServlet {

    private Libro obtenerLibro(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Libro lib = new Libro();
        if (accion.equals("create") == false) {
            lib.setIdLibro(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        lib.setTitulo(Utilidad.getParameter(request, "Nombre", ""));
        lib.setAutor(Utilidad.getParameter(request, "Autor", ""));
        lib.setAño(Utilidad.getParameter(request, "Year", ""));

        lib.setTitulo(Utilidad.getParameter(request, "Nombre", ""));
        if (accion.equals("index")) {  // Si accion es index.

            lib.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));

            lib.setTop_aux(lib.getTop_aux() == 0 ? Integer.MAX_VALUE : lib.getTop_aux());
        }
        return lib;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro lib = new Libro();
            lib.setTop_aux(10);
            ArrayList<Libro> libros = LibroDAL.Search(lib);

            request.setAttribute("libros", libros); // Enviar los roles al jsp utilizando el request.setAttribute con el nombre del atributo categorias.
            // Enviar el Top_aux de Categoria al jsp utilizando el request.setAttribute con el nombre del atributo top_aux.
            request.setAttribute("top_aux", lib.getTop_aux());
            // El request.getRequestDispatcher nos permite direccionar a un jsp desde un servlet.              
            request.getRequestDispatcher("Views/Libro/index.jsp").forward(request, response); // Direccionar al jsp index de Categoria.
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response); // Enviar al jsp de error si hay un Exception.
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro lib = obtenerLibro(request);
            ArrayList<Libro> libros = LibroDAL.Search(lib);
            request.setAttribute("libros", libros);

            request.setAttribute("top_aux", lib.getTop_aux());
            request.getRequestDispatcher("Views/Librp/index.jsp").forward(request, response);
        } catch (Exception ex) {

            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("Views/Libro/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro lib = obtenerLibro(request);

            int result = LibroDAL.create(lib);
            if (result != 0) {

                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {

                Utilidad.enviarError("No se logro registrar una nueva categoria", request, response);
            }
        } catch (Exception ex) {

            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }

    private void requestGetById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro lib = obtenerLibro(request); 
            
            Libro lib_result = LibroDAL.getById(lib);
            if (lib_result.getIdLibro()> 0) { 
                
                request.setAttribute("lib", lib_result);
            } else {
                
                Utilidad.enviarError("El Id: " + lib.getIdLibro()+ " no existe en la tabla de Librp", request, response);
            }
        } catch (Exception ex) {
            
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
}

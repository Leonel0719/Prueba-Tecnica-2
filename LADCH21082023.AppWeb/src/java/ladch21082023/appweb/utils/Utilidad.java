package ladch21082023.appweb.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Leonel
 */
public class Utilidad {

    public static String getParameter(HttpServletRequest request, String pKey, String pDefault) {
        String result = "";

        String value = request.getParameter(pKey);
        if (value != null && value.trim().length() > 0) {
            result = value;
        } else {
            result = pDefault;
        }
        return result;
    }

    public static void enviarError(String pError, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("error", pError);

        request.getRequestDispatcher("Views/Shared/error.jsp").forward(request, response);
    }

    public static String obtenerRuta(HttpServletRequest request, String pStrRuta) {

        return request.getContextPath() + pStrRuta;
    }
}

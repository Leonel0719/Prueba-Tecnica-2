package ladch21082023.accesoadatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ladch21082023.entidadesdenegocio.Libro;
import java.util.*;
import java.util.ArrayList;

/**
 * @author Leonel
 */
public class LibroDAL {

    static String getFields() {

        return "r.Id, r.Nombre, r.Autor, r.Year";
    }

    private static String getSelect(Libro lib) {
        String sql = "SELECT ";
        if (lib.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            sql += "TOP " + lib.getTop_aux() + " ";
        }
        sql += (getFields() + " FROM Libros r");
        return sql;
    }

    private static String addOrderBy(Libro lib) {
        String sql = " ORDER BY r.Id DESC";
        if (lib.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + lib.getTop_aux() + " ";
        }
        return sql;
    }

    public static int create(Libro lib) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "INSERT INTO Libros(Titulo, Autor, Year) VALUES(?, ?, ?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, lib.getTitulo());
                ps.setString(2, lib.getAutor());
                ps.setString(3, lib.getAño());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }

    static int asignarDatosResultSet(Libro lib, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        lib.setIdLibro(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        lib.setTitulo(pResultSet.getString(pIndex)); // index 2
        pIndex++;
        lib.setAutor(pResultSet.getString(pIndex)); // index 3
        pIndex++;
        lib.setAño(pResultSet.getString(pIndex)); // index 4
        return pIndex;
    }

    private static void getData(PreparedStatement pPS, ArrayList<Libro> lib) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Libro libros = new Libro();
                asignarDatosResultSet(libros, resultSet, 0);
                lib.add(libros);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static Libro getById(Libro lib) throws Exception {
        Libro libros = new Libro();
        ArrayList<Libro> xLibro = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = getSelect(lib);
            sql += " WHERE r.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, lib.getIdLibro());
                getData(ps, xLibro);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (xLibro.size() > 0) {
            libros = xLibro.get(0);
        }
        return lib;
    }

    public static ArrayList<Libro> getAll() throws Exception {
        ArrayList<Libro> libros = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = getSelect(new Libro());
            sql += addOrderBy(new Libro());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                getData(ps, libros);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return libros;
    }

    static void querySelect(Libro lib, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (lib.getIdLibro() > 0) {
            pUtilQuery.AgregarWhereAnd(" r.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), lib.getIdLibro());
            }
        }
        if (lib.getTitulo() != null && lib.getTitulo().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" r.TituloLIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + lib.getTitulo() + "%");
            }
        }
        if (lib.getAutor() != null && lib.getAutor().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" r.AutorLIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + lib.getAutor() + "%");
            }
        }
        if (lib.getAño() != null && lib.getAño().trim().isEmpty() == false) {
            pUtilQuery.AgregarWhereAnd(" r.YearLIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + lib.getAño() + "%");
            }
        }
    }

    public static ArrayList<Libro> Search(Libro lib) throws Exception {
        ArrayList<Libro> libros = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = getSelect(lib);
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(lib, utilQuery);
            sql = utilQuery.getSQL();
            sql += addOrderBy(lib);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(lib, utilQuery);
                getData(ps, libros);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return libros;
    }
}

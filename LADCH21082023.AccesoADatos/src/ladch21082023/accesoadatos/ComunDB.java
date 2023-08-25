package ladch21082023.accesoadatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractCollection.*;

/**
 * @author Leonel
 */
public class ComunDB {

    class TipoDB {

        static final int SQLSERVER = 1;
        static final int MYSQL = 2;
    }

    static int TIPODB = TipoDB.SQLSERVER;
    static String connectionUrl = "jdbc:sqlserver://localhost:8080;database=LADCH21082023;user=root;password=12345;loginTimeout=30;encrypt=false;trustServerCertificate=false";

    public static Connection obtenerConexion() throws SQLException {

        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        Connection connection = DriverManager.getConnection(connectionUrl);
        return connection;
    }

    public static Statement createStatement(Connection pConn) throws SQLException {
        Statement statement = pConn.createStatement();
        return statement;
    }

    public static PreparedStatement createPreparedStatement(Connection pConn, String pSql) throws SQLException {
        PreparedStatement statement = pConn.prepareStatement(pSql);
        return statement;
    }

    public static ResultSet obtenerResultSet(Statement pStatement, String pSql) throws SQLException {
        ResultSet resultSet = pStatement.executeQuery(pSql);
        return resultSet;
    }

    public static ResultSet obtenerResultSet(PreparedStatement pPreparedStatement) throws SQLException {
        ResultSet resultSet = pPreparedStatement.executeQuery();
        return resultSet;
    }

    public static int ejecutarSQL(String pSql) throws SQLException {
        int result;
        try (Connection connection = obtenerConexion();) {
            Statement statement = connection.createStatement();
            result = statement.executeUpdate(pSql);
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }

    class UtilQuery {

        private String SQL;
        private PreparedStatement statement;
        private int numWhere;

        public UtilQuery() {
        }

        public UtilQuery(String SQL, PreparedStatement statement, int numWhere) {
            this.SQL = SQL;
            this.statement = statement;
            this.numWhere = numWhere;
        }

        public String getSQL() {
            return SQL;
        }

        public void setSQL(String SQL) {
            this.SQL = SQL;
        }

        public PreparedStatement getStatement() {
            return statement;
        }

        public void setStatement(PreparedStatement statement) {
            this.statement = statement;
        }

        public int getNumWhere() {
            return numWhere;
        }

        public void setNumWhere(int numWhere) {
            this.numWhere = numWhere;
        }
        
        public void AgregarWhereAnd(String pSql) {
            if (this.SQL != null) {
                if (this.numWhere == 0) { 
                    // Si el numWhere es cero significa que es el primer campo agregado en la consulta SELECT entonces agregamos el WHERE
                    this.SQL += " WHERE ";
                } else {
                    // Si el numWhere es diferente a cero significa que agregaremos el AND a la consulta SELECT 
                    this.SQL += " AND ";
                }
                this.SQL += pSql; // Agregar el valor extra a la consulta
            }
             this.numWhere++;
        }   
    }
}

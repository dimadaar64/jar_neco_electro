
package Clases;

 
import java.sql.Connection;
 
import java.sql.DriverManager;
import java.sql.SQLException;
 
 
public class Conexion {
 
    public final String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
 
    public final String url="jdbc:sqlserver://PC-PC\\SQLEXPRESS:1433;databaseName=ensayo;paswurd";
 
    public final String user="dimadaar";
 
    public final String pass="dimadaar";
 
    Conexion(){      
 
    }  
 
    public Connection conectar(){      
 
        try {
 
            Class.forName(driver);
 
            return(DriverManager.getConnection(url, user, pass));
 
        } catch (ClassNotFoundException | SQLException e) {
      
 
        }
 
        return null;
 
    }  
 
    public void CerrarConexion(Connection con){
 
        try {
 
            con.close();
 
        } catch (SQLException e) {
 
        }
 
    }
 
}

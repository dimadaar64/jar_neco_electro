
package clases_DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class kardex_DAO {
    
    
    
    
    public Connection conexion(){
        
        Connection cn = null;
        
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

//            String url="jdbc:sqlserver://SERVIDOR\\SQLEXPRESS;databaseName=necoabastos;user=webapp;password=770604";
            String url="jdbc:sqlserver://LOCALHOST\\SQLEXPRESS;databaseName=necoelectro;user=webapp;password=770604";
            
            cn=DriverManager.getConnection(url);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(kardex_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
        return cn;
    

    }
}

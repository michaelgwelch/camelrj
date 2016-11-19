package welch.subscriber;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.CallableStatement;

public class CallProc {
    public static void main(String[] args) {
        String product_num = "6789934";
        String image_url = "https://mysever.directs.com/images/moon.jpg";
        
        Connection conn = null;

        try {
            conn =
               DriverManager.getConnection("jdbc:mysql://localhost/master_catalog?" +
                                           "user=test&password=test&noAccessToProcedureBodies=true");

            // Do something with the Connection

            CallableStatement cStmt = conn.prepareCall("{call sp_insert_url(?, ?)}");


			cStmt.setString(1, product_num);

			cStmt.setString(2, image_url);
            
            boolean hadResults = cStmt.execute();
            
            conn.close();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    private Connection conn;
    private CallableStatement cStmt;
    public CallProc() {
        try {
			conn =
			        DriverManager.getConnection("jdbc:mysql://localhost/master_catalog?" +
			                                    "user=test&password=test&noAccessToProcedureBodies=true");
            cStmt = conn.prepareCall("{call sp_insert_url(?, ?)}");

        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} 

    }
    
    public Boolean invoke(ContentImage image) {
    	
    	try {
		cStmt.setString(1, image.productNum);

		cStmt.setString(2, image.imageUrl);
        
        return cStmt.execute();
    	} catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return false;
    	}
    }
    
    
}

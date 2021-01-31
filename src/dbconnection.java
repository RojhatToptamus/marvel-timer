import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class dbconnection {
    private String name="root";
    private String password="";    
    private String db_ismi="dbwork";    
    private  String host="localhost";    
    private int port=3306;     
    private  Connection con=null;    
    private Statement statemant=null;    

    public  dbconnection() throws SQLException{
    
        String url= "jdbc:mysql://" + host + ":" + port+ "/" +db_ismi+"?useUnicode=true&characterEncoding=utf8";
         try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
             System.out.println("Driver not exist  ");
        }
         try {
            con=(Connection) DriverManager.getConnection(url, name, password);
             System.out.println("true connection");
         } catch (SQLException ex) {
             System.out.println("false connection");
        }
        
    }    

    public void saveResults(String competitor,int start, int stop){
        try {
            statemant = con.createStatement();
            String name = competitor;
            int starTime=start;
            int stopTime = stop;
            int skor=starTime-stopTime;
            // Insert ınto calisanlar(ad,soyad,email) VALUES('Ad','Soyad','adsoyad@gmail.com')
            String query="Insert into competitors(name,startTime,stopTime,skor) VALUES(" + "'" + name + "',"+ "'" + starTime + "',"+ "'" + stopTime + "',"+ "'" + skor + "')";
           //String sorgu="Insert into calisanlar(ad,soyad,email) VALUES('Ad','Soyad','adsoyad@gmail.com')";
           statemant.executeUpdate(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

   
        public String bringResults() throws SQLException{
        String query = "Select * from competitors  ORDER BY skor"; //skor DESC";//desk menans reverse 
        String result="";
        statemant=con.createStatement();
        ResultSet rs=statemant.executeQuery(query);
        int maxSkor=0;
            while (rs.next()) {
            
            int id = rs.getInt("id");
            String name=rs.getString("name");
            int startTime = rs.getInt("startTime");
            int stopTime = rs.getInt("stopTime");
            int skor =rs.getInt("skor");
           result=result+"\n"+" Name: "+name+" Start Time: "+startTime+" Stop Time: "+stopTime+" Spent Time: "+skor;
            
            
        }
        return result;
    }

    public static void main(String[] args) throws SQLException {
       dbconnection db=new dbconnection();
        System.out.println("before adding");

    }    
}

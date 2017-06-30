
package MealManagementSystem;

import java.sql.*;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class DBConnect {
    
 public Connection con;
 public Statement st;
 public ResultSet rs;
 
 
 public DBConnect(){
     DBSetup dbsetup = new DBSetup();
     String dbServer = dbsetup.getServer();
     String dbDatabase = dbsetup.getDatabase();
     String dbUser=dbsetup.getUser();
     String dbPass = dbsetup.getPass();
     try {
         //con = DriverManager.getConnection("jdbc:mysql://localhost/dbsql","root","himelbd71");
         con = DriverManager.getConnection("jdbc:mysql://"+dbServer+"/"+dbDatabase+"",dbUser,dbPass);
         st = con.createStatement();
     } catch (SQLException ex) {
         System.out.println(ex);
         
        showMessageDialog(null,"Your Database not Setup Yet!!\n To setup your database goto setting below and setup your database\n Thank you.");
        System.exit(0);
             
     }
     
 }
 
public String checkLogin(String username, String password){
        
    String quary = "select * from admin";
    
     try {
         rs = st.executeQuery(quary);
         
         while(rs.next()){
                 String user = rs.getString("username");
                 String pass = rs.getString("password");
         
                if(user.equals(username) && pass.equals(password)){
                    return "Success";
                }
         }
         
     
     } catch (SQLException ex) {
        
     }
    
    
    return "Failed";
}
public String getUser(){
        String quary = "select * from admin";
        
        try{
            rs = st.executeQuery(quary);
            
            while(rs.next()){
                String user = rs.getString("username");
                
               return user;
            }
        }catch(SQLException ex){
            
        }
        return "Error!";
}
public void getPass(){
        String quary = "select * from admin";
        
        try{
            rs = st.executeQuery(quary);
            
            while(rs.next()){
          
                String password = rs.getString("password");
                
               System.out.println(password);
            }
        }catch(SQLException ex){
            
        }
        
}
public void getEmail(){
        String quary = "select * from admin";
        
        try{
            rs = st.executeQuery(quary);
            
            while(rs.next()){
              
                String email = rs.getString("email");
                System.out.println(email);
            }
        }catch(SQLException ex){
            
        }
}
   //================================================ 


// INSERT INTO `mamber`(`id`, `name`, `meal`, `payment`, `bazar`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5])

    public String addMamber(String name){
        String quary = "INSERT INTO mamber (id, name, meal, payment, bazar) VALUES (null,'"+name+"',0,0,0)";
        String onlyName = "^[a-zA-Z]+$";
        if(name.matches(onlyName)){
               try{
                     st.execute(quary);
           
                }catch(SQLException ex){
                    System.out.println("Error: "+ex);
                }
        }else{
            JOptionPane.showConfirmDialog(null, "You have Entered \""+name+"\" That is not a Real Name","Naming Error!",JOptionPane.DEFAULT_OPTION, JOptionPane.OK_OPTION);
            return "Naming Error!!";
        }
        return "Mamber \""+name+"\" Added Successfully";
     
    }
    
    ///===================== Enable Add Meal Gui

    public int mamberCount(){
        String quary = "select * from mamber";
        int count = 0;
        try{
           rs = st.executeQuery(quary);
           while(rs.next()){
               count=count+1;
           }
           return count;
        }catch(SQLException ex){
            
        }
        return count;
    }
    
    //==================== Total Paid
    
    public int totalPaid(){
        //SELECT * FROM `mamber` WHERE `payment` <> 0
        String quary = "SELECT * FROM mamber";
        int totalpaid = 0;
        try{
            rs = st.executeQuery(quary);
            
            while(rs.next()){
                String payment = rs.getString("payment");
                totalpaid = totalpaid + Integer.valueOf(payment);
            }
            return totalpaid;
        }catch(NumberFormatException | SQLException ex){
            
        }
        
        return totalpaid;
    }
    
    public String getPayid(String name){
        
        //String q = "SELECT payment FROM mamber WHERE name='"+name+"'";
        String quary = "SELECT payment FROM mamber WHERE name='"+name+"'";
        String balance = "0";
        try{
            rs = st.executeQuery(quary);
            while(rs.next()){
                balance = rs.getString("payment");
                
            }
            return balance;
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return balance;
    }
    
    
        
    public String getMeal(String name){
        
        //String q = "SELECT payment FROM mamber WHERE name='"+name+"'";
        String quary = "SELECT meal FROM mamber WHERE name='"+name+"'";
        String balance = "0";
        try{
            rs = st.executeQuery(quary);
            while(rs.next()){
                balance = rs.getString("meal");
                
            }
            return balance;
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return balance;
    }
    
        
        
    public String getBazar(String name){
        
        //String q = "SELECT payment FROM mamber WHERE name='"+name+"'";
        String quary = "SELECT bazar FROM mamber WHERE name='"+name+"'";
        String balance = "0";
        try{
            rs = st.executeQuery(quary);
            while(rs.next()){
                balance = rs.getString("bazar");
                
            }
            return balance;
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return balance;
    }
}



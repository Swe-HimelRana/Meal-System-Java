
package MealManagementSystem;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;

public class functions{
  DBConnect d = new DBConnect();
  
  
  public void addPayment(String name,String pay){
      int totalpay = 0;
      
      //UPDATE `mamber` SET `payment`='500' WHERE `name`='Himel'
      //SELECT `payment` FROM `mamber` WHERE `name`='Himel'
     String paid = "SELECT payment FROM mamber WHERE name='"+name+"'";
     String result;
     String add = "UPDATE mamber SET payment='"+totalpay+"' WHERE name='"+name+"'";
     
     try{
         d.rs = d.st.executeQuery(paid);
          result = d.rs.getString("payment");
          totalpay = Integer.valueOf(result);
          totalpay = totalpay + Integer.valueOf(pay);
          
         d.st.execute(add);
         
     }catch(NumberFormatException | SQLException ex){
         
     }
  }
  public void getErrorResult(String error,String title,String Details){
       JOptionPane.showConfirmDialog(null,Details +" : "+ error,title,JOptionPane.DEFAULT_OPTION,JOptionPane.OK_OPTION);
  }
  public int getTotalBazar(){
      DBConnect bazarDB = new DBConnect();
      int totalBazar = 0;
      String q = "select * from bazar";
      
      try {
          bazarDB.rs = bazarDB.st.executeQuery(q);
          while(bazarDB.rs.next()){
              try{
                  int total = Integer.parseInt(bazarDB.rs.getString("total").trim());
                  totalBazar = totalBazar + total;
              }catch(NumberFormatException | SQLException ex){
                  JOptionPane.showConfirmDialog(null,"Error: "+ex, "Error > Functions getTotalBazar",JOptionPane.DEFAULT_OPTION,JOptionPane.OK_OPTION);
                          
              }
              
          }
      } catch (SQLException ex) {
          Logger.getLogger(functions.class.getName()).log(Level.SEVERE, null, ex);
         this.getErrorResult(String.valueOf(ex), "Error > functions getTotalBazar","SQL Error:");
      }
      
      
      return totalBazar;
  }
  public int getTotalPaid(){
      DBConnect totalDB = new DBConnect();
      int totalPaid = 0;
      String quary = "select * from mamber";
      
      try{
          totalDB.rs = totalDB.st.executeQuery(quary);
          while(totalDB.rs.next()){
              try{
                  int total = totalDB.rs.getInt("payment");
                  totalPaid = totalPaid + total;
              }catch(SQLException ex){
                  this.getErrorResult(String.valueOf(ex), "function getTotalPaid", "try catch SQL");
              }
          }
          
      }catch(SQLException ex){
         this.getErrorResult(String.valueOf(ex), "functions getTotalPaid", "quary Execution");
      }
      
      return totalPaid;
  }
  public int getTotalMeal(){
      DBConnect mealDB = new DBConnect();
      int totalMeal = 0;
      String query = "select * from mamber";
      
      try{
          mealDB.rs = mealDB.st.executeQuery(query);
          while(mealDB.rs.next()){
              try{
                  int total = mealDB.rs.getInt("meal");
                  totalMeal += total;
              }catch(SQLException ex){
                   this.getErrorResult(String.valueOf(ex), "function getTotalMeal", "try catch SQL");
              }
          }
      }catch(SQLException ex){
          this.getErrorResult(String.valueOf(ex), "function getTotalMeal", "try catch SQL");
      }
      
      return totalMeal;
  }
  /*
  You can think mealRate should be Double type
  But I have created it integer type why?
  Answer:
  if you use double type, may you will get like this (-25.23) remaining payment
  in fact paying 25.23 is not possible.
  that's why I have used only integer all time  
  
  >> You can update it if you have  more eficient way.
  */
  public int getMealRate(){
      int mealRate = 0;
      try{
        mealRate = this.getTotalBazar() / this.getTotalMeal();  
      }catch(Exception ex){
          //this.getErrorResult(String.valueOf(ex), "functions getMealRate", "calculation Error Or ");
      }

      return mealRate;
  }
  public int getRemainMoney(){
      int remainMoney = 0;
      
      try{
          remainMoney = this.getTotalPaid() - this.getTotalBazar();
      }catch(Exception ex){
          this.getErrorResult(String.valueOf(ex), "functions getRemainMoney", "calculation");
      }
      
      return remainMoney;
  }
  public int enableLoginPage(){
      int ret  = 0;
      
      DBConnect dbcon = new DBConnect();
     
      String query = "select * from admin";
     
      
      try {
          dbcon.rs = dbcon.st.executeQuery(query);
          while(dbcon.rs.next()){
              ret += 1;
          }
      } catch (SQLException ex) {
          int conf = showConfirmDialog(null,"Your Database Tables not setup yet!!\n You want to setup Database tables now?");
          if(conf == 0){
              SetupDatabase setDB = new SetupDatabase();
              setDB.setVisible(true);
              
          }
      }

      return ret;
  }
  public String getDateToday(){
      String ret;
     
      
       LocalDate localDate = LocalDate.now();
       String today = DateTimeFormatter.ofPattern("dd-MM-yyy").format(localDate);
       ret = today;
      return ret;
  }
  

  
}

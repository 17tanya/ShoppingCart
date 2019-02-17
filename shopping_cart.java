import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.*;

public class frame_1 extends javax.swing.JFrame{

  //global variables are intialised.
  
Connection con=null;
Statement st=null;
ResultSet rs=null;
int disc_pic=1;
int sec=30;
Double total_amt=0.0;
String cart_name="";
int a=0, b=0, c=0;
String disc_ans="";
Double disc=0.0;
double discounted_amt=0.0;
int total=0;
Double fin_amt=0.0;
int correct=0;
  //Timers for displaying pictures in a loop.The pictures change every 2seconds.
  
Timer dod_t= new Timer(1000,new ActionListener(){
public void actionPerformed(ActionEvent evt){
a++;
  //a is incremented by 1 every second.
if(a%2==0){
  button_dod.setIcon(new ImageIcon("C:\\IP PROJECT\\IP_1.png"));
  }
  //Every time a is divisible by 2 img1 is displayed.As a is incremented every second the images switch every 2 seconds.
else button_dod.setIcon(new ImageIcon("C:\\IP PROJECT\\IP_2.png"));
  }
  // Every time a is not divisible by 2(ie is an odd no) img 2 is displayed.
});

Timer dod_t1= new Timer(1000,new ActionListener(){
public void actionPerformed(ActionEvent evt){
b++;
if(b%2==0){button_dod2.setIcon(new ImageIcon("C:\\IP PROJECT\\IP_3.jpg"));}
else button_dod2.setIcon(new ImageIcon("C:\\IP PROJECT\\IP_4.jpg"));
}});

Timer dod_t3= new Timer(1000,new ActionListener(){
public void actionPerformed(ActionEvent evt){
c++;
if(c%2==0){button_dod1.setIcon(new ImageIcon("C:\\IP PROJECT\\IP_5.jpg"));}
else button_dod1.setIcon(new ImageIcon("C:\\IP PROJECT\\IP_6.jpg"));
}});

//Timer used in the logo quiz like game for discount.It is a countdown timer of 20seconds.

Timer disc_game=new Timer(1000,new ActionListener(){
public void actionPerformed(ActionEvent e){
int sec=Integer.parseInt(disc_timer.getText());
sec--;
//1 is subtracted from sec every second.
disc_timer.setText(""+sec);
if(sec==0){disc_game.stop();
//once the countdown reaches 0,the timer stops and the bill frame is visible.
JOptionPane.showMessageDialog(null,"Discount earned="+disc+"%");
frame_bill.setVisible(true);
frame_discount.setVisible(false);
}}});

private void formWindowActivated(java.awt.event.WindowEvent evt) {
try{
Class.forName("java.sql.Driver");
con=DriverManager.getConnection("jdbc:mysql://localhost/shoppingcart","root","1234");
st=con.createStatement();
String sql=null;
//To establish jdbc connectivity.
}
catch(Exception e){JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void formWindowOpened(java.awt.event.WindowEvent evt) {
try{
Class.forName("java.sql.Driver");
con=DriverManager.getConnection("jdbc:mysql://localhost/mysql","root","1234");
st=con.createStatement();
String sql1="create database shoppingcart;";
String sql2="use shoppingcart;";
String sql3="create table shopping(name varchar(30),email varchar(50),username varchar(30),password varchar(30));";
st.executeUpdate(sql1);
st.executeUpdate(sql2);
st.executeUpdate(sql3);
//Creates database “shoppingcart” and a table for usernames and passwords.
}
catch(Exception e){System.out.println("");
//Catch is empty because the database may already exist.
}}
private void login_btnActionPerformed(java.awt.event.ActionEvent evt){
frame_login.setVisible(true);
}
private void signup_btnActionPerformed(java.awt.event.ActionEvent evt) {
frame_signup.setVisible(true);
}

private void loginActionPerformed(java.awt.event.ActionEvent evt) {
try{
String username=username_login.getText();
String password= new String(password_login.getPassword());
String sql1="use shoppingcart;";
int a= st.executeUpdate(sql1);
String sql=("select password from shopping where username='"+username+"' and password='"+password+"';");
rs=st.executeQuery(sql);
if (rs.first()==false){
JOptionPane.showMessageDialog(null,"wrong username/password");}
else {
st.executeUpdate("drop table "+username+";");
//Incase the table already exists.
st.executeUpdate("create table "+username+" (item varchar(20),price int(10),qty int(5),total float(10));");
frame_categories.setVisible(true);
frame_login.setVisible(false);
cart_name=username;
//a global variable,so can be used in other parts of the project.
}
//The categories frame opens only when the username and password match a row in the result set.A table whose name is the username is created in the database.The items “added to the cart” by the user will be stored in this table.
}
catch(Exception e){JOptionPane.showMessageDialog(null,e.getMessage());}
}

private void sign_up1ActionPerformed(java.awt.event.ActionEvent evt){
try{
String name=name_signup.getText();
String email=eid_signup.getText();
String username=username_signup.getText();
String password=new String(password_signup.getPassword());
String sql=("insert into shopping values('"+name+"','"+email+"','"+username+"','"+password+"');");
st.executeUpdate(sql);
JOptionPane.showMessageDialog(null,"account created.");
st.executeUpdate("create table "+username+" (item varchar(20),price int(10),qty int(5),total float(10));");
cart_name=username;
frame_categories.setVisible(true);
frame_signup.setVisible(false);
//The user data is stored in a table shopping .And a table is created in the database with the same name as the username.
}
catch(Exception e){JOptionPane.showMessageDialog(null,e.getMessage());}
}

private void frame_categoriesWindowActivated(java.awt.event.WindowEvent evt) {
dod_t.start();
dod_t1.start();
dod_t3.start();
//Timers are activated for displaying two pictures in the buttons in a loop.
}
private void button_dod1ActionPerformed(java.awt.event.ActionEvent evt){
if(a%2==0){frame_clothes_women.setVisible(true);}
//Every time a is a even number(ie divisible by 2)the img on the button will correspond to women’s clothes.otherwise to men’s clothes.
else frame_clothes_men.setVisible(true);
frame_categories.setVisible(false);
}
private void button_dod2ActionPerformed(java.awt.event.ActionEvent evt){
if(b%2==0){frame_watch_women.setVisible(true);}
else frame_watch_men.setVisible(true);
frame_categories.setVisible(false);
}
private void button_dod3ActionPerformed(java.awt.event.ActionEvent evt) {
if(c%2==0){frame_fictional.setVisible(true);}
else frame_nonfic.setVisible(true);
frame_categories.setVisible(false);
}
private void cb_clothesActionPerformed(java.awt.event.ActionEvent evt) {
String sel_item=(cb_clothes.getSelectedItem()).toString();
if(sel_item.equals("Men")){frame_clothes_men.setVisible(true);}
if(sel_item.equals("Women")){frame_clothes_women.setVisible(true);}
if(sel_item.equals("Kids")){frame_clothes_kids.setVisible(true);}
frame_categories.setVisible(false);
}
private void cb_watchesActionPerformed(java.awt.event.ActionEvent evt) {
String sel_item=(cb_watches.getSelectedItem()).toString();
if(sel_item.equals("Men")){frame_watch_men.setVisible(true);}
if(sel_item.equals("Women")){frame_watch_women.setVisible(true);}
frame_categories.setVisible(false);
}


private void cb_booksActionPerformed(java.awt.event.ActionEvent evt) {
String sel_item=(cb_books.getSelectedItem()).toString();
if(sel_item.equals("Fictional"))frame_fictional.setVisible(true);
if(sel_item.equals("Non-Fiction"))frame_nonfic.setVisible(true);
frame_categories.setVisible(false);
}

private void clothes_women_b1ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Grey Dress Size:M Price:2000");
//Displays the information about the product via a message dialog box.
}
private void clothes_women_b2ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Black Top Size:L Price:8000");
}
private void clothes_women_b3ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Black Dress Size:M Price:4000");
}
private void add_wcloth1ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"qty","Item Qty",3));
//The quantity of the item the user wants to buy is entered vis a InputDialog box.
int tot=qty*2000;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('GreenDress',2000,"+qty+","+tot+");");
//The item name,price,quantity and total.
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_wcloth2ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity","Item Qty",3));
int tot=qty*900;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('black Top',900,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_wcloth3ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*4000;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('BlackDress',4000,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void back_wclothActionPerformed(java.awt.event.ActionEvent evt) {
frame_categories.setVisible(true);
frame_clothes_women.setVisible(false);
}

private void opencart_cloth_womenActionPerformed(java.awt.event.ActionEvent evt) {
frame_cart.setVisible(true);
frame_clothes_women.setVisible(false);
}

private void cloths_men_b1ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Jean Jacket Size:M Price:1200");
}
private void cloths_men_b2ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:blue shirt Size:L Price:900");
}
private void cloths_men_b3ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Grey Tshirt Size:M Price:1000");}
private void add_clothes_men1ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*1200;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Jean Jacket',1200,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_clothes_men2ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*900;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('blue shirt',900,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_clothes_men3ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*1000;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Grey Tshirt',1000,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void back_mclothActionPerformed(java.awt.event.ActionEvent evt) {
frame_categories.setVisible(true);
frame_clothes_men.setVisible(false);
}
private void opencart_cloth_menActionPerformed(java.awt.event.ActionEvent evt) {
frame_cart.setVisible(true);
frame_clothes_men.setVisible(false);
}

private void clothes_kid_b1ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Green Sweater Size:L Price:750");
}
private void clothes_kid_b2ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Orange sweater Size:L Price:800");
}
private void clothes_kid_b3ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Peach sweatshirt Size:M Price:900");
}
private void add_clothes_kid1ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*750;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Green Sweater',750,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_clothes_kid2ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*800;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Orange Sweater',800,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}

private void add_clothes_kid3ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*900;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Peach Sweatshirt',900,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void back_kclothActionPerformed(java.awt.event.ActionEvent evt) {
frame_categories.setVisible(true);
frame_clothes_kids.setVisible(false);
}
private void opencart_cloth_kidActionPerformed(java.awt.event.ActionEvent evt) {
frame_cart.setVisible(true);
frame_clothes_kids.setVisible(false);}

private void watch_men_b1ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Timex Black Price:1000");
}
private void watch_men_b2ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Titan Green Price:1500");
}
private void watch_men_b3ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Titan Brown Price:2000");
}
private void add_watch_men1ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*1000;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Timex Black',1000,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_watch_men2ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*1500;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Titan Green',1500,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_watch_men3ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*2000;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Titan Brown',2000,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void back_mwatchActionPerformed(java.awt.event.ActionEvent evt) {
frame_categories.setVisible(true);
frame_watch_men.setVisible(false);
}

private void watch_women_b1ActionPerformed(java.awt.event.ActionEvent evt) {JOptionPane.showMessageDialog(null,"Description:Laurell Orange Price:500");}
private void watch_women_b2ActionPerformed(java.awt.event.ActionEvent evt) {JOptionPane.showMessageDialog(null,"Description:Titan Silver Price:1000");}
private void watch_women_b3ActionPerformed(java.awt.event.ActionEvent evt) {JOptionPane.showMessageDialog(null,"Description:Titan Blue&Silver Price:1200");}
private void add_watch_women1ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*500;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Laurell',500,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_watch_women2ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*1000;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Titan Silver',1000,"+qty+","+tot+");");}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}

private void add_watch_women3ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*1200;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Titan Blue',5000,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void back_wwatchActionPerformed(java.awt.event.ActionEvent evt) {
frame_categories.setVisible(true);
frame_watch_women.setVisible(false);
}
private void opencart_watch_womenActionPerformed(java.awt.event.ActionEvent evt) {
frame_cart.setVisible(true);
frame_watch_women.setVisible(false);
}

//CART
private void frame_cartWindowActivated(java.awt.event.WindowEvent evt){
DefaultTableModel s_cart=(DefaultTableModel)cart.getModel();
s_cart.setRowCount(0);
try{
String sql="select * from "+cart_name+";";
rs=st.executeQuery(sql);
rs.first();
do{
Object[]row={rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)};
s_cart.addRow(row);
}while(rs.next());
//A do while loop is used to add the rows from the result set into the jtable.
ResultSet tot=st.executeQuery("select sum(total) from "+cart_name+";");
tot.first();
total=tot.getInt(1);
L1.setText(""+total);
if(total>8000){ discount.setVisible(true);}
else discount.setVisible(false);
//Discount is offered to the user only if the total amount exceeds Rs.8000.
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void change_cartActionPerformed(java.awt.event.ActionEvent evt) {
//Changes Quantity of items in cart.User inputs the Item name and desired quantity into the respective TextFields.
int qty=Integer.parseInt(change_qty.getText());
String it_name=change_name.getText();
String sql1="update "+cart_name+" set qty="+qty+" where item='"+it_name+"';";
//query changes the qty of the corresponding item.
String sql2="update "+cart_name+" set total=price*qty;";
//This query changes the total amount of the item.
String sql3="select * from "+cart_name+";";
try{
DefaultTableModel dtm=(DefaultTableModel)cart.getModel();
dtm.setRowCount(0);
st.executeUpdate(sql1);
st.executeUpdate(sql2);
rs=st.executeQuery(sql3);
rs.first();
do{
Object arr[]={rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)};
dtm.addRow(arr);
}while(rs.next());
ResultSet tot=st.executeQuery("select sum(total) from "+cart_name+";");
tot.first();
total=tot.getInt(1);
//a global variable.total amount w/o discount.
L1.setText(""+total);
if(total>8000){
discount.setVisible(true);}
else discount.setVisible(false);
}
catch(Exception e){JOptionPane.showMessageDialog(rootPane,e.getMessage());}
}
private void remove_cartActionPerformed(java.awt.event.ActionEvent evt) {
//Changes Quantity of items in cart.User inputs the Item name and desired quantity into the respective TextFields.
String name=JOptionPane.showInputDialog(null,"ENTER ITEM NAME","Item Qty",3);
//user inputs the name of the item to be deleted in the InputDialog box.
String sql="delete from "+cart_name+" where item='"+name+"';";
//deletes the corresponding row from the table in mysql.
try{
DefaultTableModel dtm=(DefaultTableModel)cart.getModel();
dtm.setRowCount(0);
st.executeUpdate(sql);
rs=st.executeQuery("select * from "+cart_name+";");
rs.first();
do{
Object arr[]={rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)};
dtm.addRow(arr);
}while(rs.next());
}
catch(Exception e){JOptionPane.showMessageDialog(null,e.getMessage());}
}

private void discountActionPerformed(java.awt.event.ActionEvent evt) {
frame_discount.setVisible(true);
frame_cart.setVisible(false);
}
private void cart_payActionPerformed(java.awt.event.ActionEvent evt) {
frame_bill.setVisible(true);
frame_cart.setVisible(false);
//Takes the user the the frames that displays the details for payment.
}
private void back_cat_cartActionPerformed(java.awt.event.ActionEvent evt) {
frame_categories.setVisible(true);
frame_cart.setVisible(false);
//Takes user back to the categories frame to continue shopping.
}

//DISCOUNT FRAME
private void frame_discountWindowOpened(java.awt.event.WindowEvent evt) {
pic_disc.setIcon(new ImageIcon("C:\\IP PROJECT\\game_1.jpg"));
//Displays the 1st pic of the game.
disc_game.start();
//game starts the moment to frame opens.
}
private void nextActionPerformed(java.awt.event.ActionEvent evt) {
String ans=ans_disc.getText();
switch(disc_pic){
case 1:if(ans.equalsIgnoreCase("ucb")){correct++;}break;
case 2:if(ans.equalsIgnoreCase("rado")){correct++;}break;
case 3:if(ans.equalsIgnoreCase("lee")){correct++;}break;
case 4:if(ans.equalsIgnoreCase("monte carlo")){correct++;}break;
case 5:if(ans.equalsIgnoreCase("casio")){correct++;}break;
case 6:if(ans.equalsIgnoreCase("titan")){correct++;}break;
case 7:if(ans.equalsIgnoreCase("rolex")){correct++;}break;
default:}
ans_disc.setText("");
ans_disc.requestFocus();
disc_pic++;
pic_disc.setIcon(new ImageIcon("C:\\IP PROJECT\\game_"+disc_pic+".jpg"));
disc=correct*0.1;
disc_disc.setText(""+disc+"%");
//Every time the user clicks the next button,a new image is displayed on the label.The images are named in a serial order so that number of the picture corresponds to the number in the switch case.This is achieved using a global variable that is incremented with every click.
}

//BILL
private void frame_billWindowOpened(java.awt.event.WindowEvent evt) {
item_sum.setText(""+total);
tf_disc.setText(""+disc);
fin_amt=total-(total*disc);
tf_tot_amt.setText(""+fin_amt);
}
private void pay_billActionPerformed(java.awt.event.ActionEvent evt) {
String add=ta_add.getText();
String boo="";
if(add.equals(boo)){JOptionPane.showMessageDialog(rootPane,"PLEASE ENTER ADDRESS");}
if (rb_cod.isSelected()){frame_cod.setVisible(true);
frame_bill.setVisible(false);}
else if (rb_credit.isSelected()){frame_credit.setVisible(true);
frame_bill.setVisible(false);}
else JOptionPane.showMessageDialog(null,"choose a method of payment");
//The user is given two payment options-cash on delivery,credit/debit card.Addition of a buttongroup has made the two options exclusive.
}

//frame credit
private void frame_creditWindowOpened(java.awt.event.WindowEvent evt) {
lbl_c_amt.setText(""+fin_amt);
try{
String sql1="use shoppingcart;";
String sql2="create table credit(name varchar(40),c_no int(20),cvv int(20),e_date date,amt decimal(20,10));";
st.executeUpdate(sql1);
st.executeUpdate(sql2);
//A table credit is created in case it doesn’t already exist in the database.
}
catch(Exception e){System.out.println("");}
}
private void pay_creditActionPerformed(java.awt.event.ActionEvent evt) {
String name=name_c.getText();
int cardno=Integer.parseInt(cardno_c.getText());
int cvv=Integer.parseInt(cvv_c.getText());
String date=expd_c.getText();
Float amt=Float.parseFloat(amt_c.getText());
String sql="insert into credit values('"+name+"',"+cardno+","+cvv+",'"+date+"',"+amt+");";
try{
st.executeUpdate(sql);
//The credit/debit card information input by the user is stored in the table credit.
JOptionPane.showMessageDialog(null,"PAYMENT COMPLETED.THANKS FOR SHOPPING.");
System.exit(0);
}
catch(Exception e){JOptionPane.showMessageDialog(null,e.getMessage());}
}

//farame cash on delivery
private void frame_codWindowActivated(java.awt.event.WindowEvent evt){
lbl_amt.setText(""+total);
lbl_disc_amt.setText(""+fin_amt);
//Total & fin_amt are global variables.
try{
String sql1="select date_add(curdate(),interval 5 day);";
ResultSet r=st.executeQuery(sql1);
r.first();
String d=(r.getString(1));
System.out.println(d);
date_label.setText(""+d);
//The user is given a tentative date to expect the delivery of the items.
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void exit_codActionPerformed(java.awt.event.ActionEvent evt) {
System.exit(0);
}

//frame non-fictional
private void book_nonfic_b1ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:HCV Price:Rs.200");
}
private void book_nonfic_b2ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Cengage Math Price:Rs.300");
}
private void book_nonfic_b3ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Accounting 7th edition. Price:500");
}
private void add_nonficb1ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*200;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('hcv',200,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_ nonficb2 ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*300;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('cengage',300,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_non ficb3ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*500;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Accounts',500,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void back_non ficActionPerformed(java.awt.event.ActionEvent evt) {
frame_categories.setVisible(true);
frame_nonfic.setVisible(false);
}
private void opencart_nonfic ActionPerformed(java.awt.event.ActionEvent evt) {
frame_cart.setVisible(true);
frame_nonfic.setVisible(false);
}

//frame fictional
private void book_fic_b1ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Kite Runner Price:Rs.350");
}
private void book_fic_b2ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Divergent Price:Rs.350");
}
private void book_fic_b3ActionPerformed(java.awt.event.ActionEvent evt) {
JOptionPane.showMessageDialog(null,"Description:Da Vinci Code Price:Rs.350");
}
private void add_ficb1ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*350;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Kite Runner',350,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_ ficb2 ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*350;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Divergent',350,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void add_ ficb3ActionPerformed(java.awt.event.ActionEvent evt) {
int qty=Integer.parseInt(JOptionPane.showInputDialog(null,"Enter Quantity ","Item Qty",3));
int tot=qty*350;
total_amt=total_amt+tot;
try{
st.executeUpdate("insert into "+cart_name+" values ('Da Vinci Code',350,"+qty+","+tot+");");
}
catch(Exception e) {JOptionPane.showMessageDialog(null,e.getMessage());}
}
private void back_ ficActionPerformed(java.awt.event.ActionEvent evt) {
frame_categories.setVisible(true);
frame_fictional.setVisible(false);
}
private void opencart_fic ActionPerformed(java.awt.event.ActionEvent evt) {
frame_cart.setVisible(true);
frame_fictional.setVisible(false);
}


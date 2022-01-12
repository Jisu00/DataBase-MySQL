import java.awt.event.*;
//import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import java.awt.*;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;


public class midterm extends JFrame implements ActionListener {
   JButton btnReset, btnInput, btn1, btn2, btn3, btn4, btn5, btn6, btnScreenReset;
   JTextArea txtResult, txtStatus;
   JTextField	txtOrderid, txtCustid, txtBookid, txtSaleprice, txtOrderdate;
   JLabel	laOrderid, laCustid, laBookid, laSaleprice,	laOrderdate;
   JPanel pn1, pn2;

   static Connection con;
   Statement stmt;
   ResultSet rs;
   String Driver = "";
   String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
   String userid = "madang";
   String pwd = "madang";

   public midterm() {
      super("Swing Database");
      layInit();
      conDB();
      setVisible(true);
      setBounds(0, 0, 1000, 400); //가로위치,세로위치,가로길이,세로길이
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public void layInit() {
  	 	btnReset = new JButton("초기화");
      btn1 = new JButton("검색1");
      btn2 = new JButton("검색2");
      btn3 = new JButton("검색3");
      btn4 = new JButton("검색4");
      btn5 = new JButton("검색5");
      btn6 = new JButton("검색6");
      btnScreenReset = new JButton("화면초기화");
      
      laOrderid = new JLabel("  orderid :");
      laCustid = new JLabel("  custid :");
      laBookid = new JLabel("  bookid :");
      laSaleprice = new JLabel("  saleprice :");
      laOrderdate = new JLabel("  orderdate :");
      
      txtOrderid = new JTextField(10);
      txtCustid = new JTextField(10);
      txtBookid = new JTextField(10);
      txtSaleprice = new JTextField(10);
      txtOrderdate = new JTextField(20);
      
      btnInput = new JButton("입력");
      txtResult = new JTextArea();
      txtStatus = new JTextArea(5,30);
      
      setTitle("19011540/김지수");
      
      pn1 = new JPanel();
      pn1.add(btnReset);
      pn1.add(btn1);
      pn1.add(btn2);
      pn1.add(btn3);
      pn1.add(btn4);
      pn1.add(btn5);
      pn1.add(btn6);
      pn1.add(btnScreenReset);
   
      pn2 = new JPanel(new GridLayout(6, 2, 5, 5));
      pn2.add(laOrderid);pn2.add(txtOrderid);
      pn2.add(laCustid);pn2.add(txtCustid);
      pn2.add(laBookid);pn2.add(txtBookid);
      pn2.add(laSaleprice);pn2.add(txtSaleprice);
      pn2.add(laOrderdate);pn2.add(txtOrderdate);
      pn2.add(btnInput);
      
      txtResult.setEditable(false);
      txtStatus.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(txtResult);
      JScrollPane scrollPane2 = new JScrollPane(txtStatus);
      
      add("North", pn1);
      add("West", pn2);
      add("Center", scrollPane);
      add("South", scrollPane2);
      
      btnReset.addActionListener(this);
      btn1.addActionListener(this);
      btn2.addActionListener(this);
      btn3.addActionListener(this);
      btn4.addActionListener(this);
      btn5.addActionListener(this);
      btn6.addActionListener(this);
      btnInput.addActionListener(this);
      btnScreenReset.addActionListener(this);
   }

   public void conDB() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         //System.out.println("드라이버 로드 성공");
         txtStatus.append("드라이버 로드 성공...\n");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      
      try { /* 데이터베이스를 연결하는 과정 */
          //System.out.println("데이터베이스 연결 준비...");
    	  txtStatus.append("데이터베이스 연결 준비...\n");
          con = DriverManager.getConnection(url, userid, pwd);
          //System.out.println("데이터베이스 연결 성공");
          txtStatus.append("데이터베이스 연결 성공...\n");
       } catch (SQLException e1) {
          e1.printStackTrace();
       }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
  
      try {
         stmt = con.createStatement();
         
        
         if (e.getSource() == btnReset) {
        	 stmt.executeUpdate("DELETE FROM Orders");
        	 stmt.executeUpdate("DELETE FROM Customer");
        	 stmt.executeUpdate("DELETE FROM Book");

        	 // Book
        	 
        	 stmt.executeUpdate("INSERT INTO Book VALUES(1, '축구의 역사', '굿스포츠', 7000)");
        	 stmt.executeUpdate("INSERT INTO Book VALUES(2, '축구아는 여자', '나무수', 13000)");
        	 stmt.executeUpdate("INSERT INTO Book VALUES(3, '축구의 이해', '대한미디어', 22000)");
        	 stmt.executeUpdate("INSERT INTO Book VALUES(4, '골프 바이블', '대한미디어', 35000)");
        	 stmt.executeUpdate("INSERT INTO Book VALUES(5, '피겨 교본', '굿스포츠', 8000)");
        	 stmt.executeUpdate("INSERT INTO Book VALUES(6, '역도 단계별기술', '굿스포츠', 6000)");
        	 stmt.executeUpdate("INSERT INTO Book VALUES(7, '야구의 추억', '이상미디어', 20000)");
        	 stmt.executeUpdate("INSERT INTO Book VALUES(8, '야구를 부탁해', '이상미디어', 13000)");
        	 stmt.executeUpdate("INSERT INTO Book VALUES(9, '올림픽 이야기', '삼성당', 7500)");
        	 stmt.executeUpdate("INSERT INTO Book VALUES(10, 'Olympic Champions', 'Pearson', 13000)");
        	 
        	 
        	 // Customer
        	 
        	 stmt.executeUpdate("INSERT INTO Customer VALUES (1, '박지성', '영국 맨체스타', '000-5000-0001')");
        	 stmt.executeUpdate("INSERT INTO Customer VALUES (2, '김연아', '대한민국 서울', '000-6000-0001')");
        	 stmt.executeUpdate("INSERT INTO Customer VALUES (3, '장미란', '대한민국 강원도', '000-7000-0001')");
        	 stmt.executeUpdate("INSERT INTO Customer VALUES (4, '추신수', '미국 클리블랜드', '000-8000-0001')");
        	 stmt.executeUpdate("INSERT INTO Customer VALUES (5, '박세리', '대한민국 대전',  NULL)");
        	 
        	 // Orders
        	 
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (1, 1, 1, 6000, STR_TO_DATE('2014-07-01','%Y-%m-%d'))");
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (2, 1, 3, 21000, STR_TO_DATE('2014-07-03','%Y-%m-%d'))");
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (3, 2, 5, 8000, STR_TO_DATE('2014-07-03','%Y-%m-%d'))");
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (4, 3, 6, 6000, STR_TO_DATE('2014-07-04','%Y-%m-%d'))");
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (5, 4, 7, 20000, STR_TO_DATE('2014-07-05','%Y-%m-%d'))");
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (6, 1, 2, 12000, STR_TO_DATE('2014-07-07','%Y-%m-%d'))");
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (7, 4, 8, 13000, STR_TO_DATE( '2014-07-07','%Y-%m-%d'))");
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (8, 3, 10, 12000, STR_TO_DATE('2014-07-08','%Y-%m-%d'))");
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (9, 2, 10, 7000, STR_TO_DATE('2014-07-09','%Y-%m-%d'))");
        	 stmt.executeUpdate("INSERT INTO Orders VALUES (10, 3, 8, 13000, STR_TO_DATE('2014-07-10','%Y-%m-%d'))");
        	 
        	 txtResult.setText("초기화 완료");
         }
         else if (e.getSource() == btn1) {
       	 	String query = "SELECT * FROM Book";
           txtResult.setText("");
           txtResult.setText("BOOKNO           BOOK NAME       PUBLISHER      PRICE\n");
           rs = stmt.executeQuery(query);
           while (rs.next()) {
              String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
                    + "\n";
              txtResult.append(str);
           }
        } 
         else if (e.getSource() == btn2) {
       	 String query = "SELECT * FROM Orders";
          txtResult.setText("");
          txtResult.setText("ORDERSNO        CUSTNO       BOOKNO         SALEPRICE      ORDERDATE\n");
          rs = stmt.executeQuery(query);
          while (rs.next()) {

             String str = (rs.getInt(1) == 0 ? null : rs.getInt(1)) + "\t" + (rs.getInt(2) == 0 ? null : rs.getInt(2)) + "\t" 
             + (rs.getInt(3) == 0 ? null : rs.getInt(3)) + "\t" + (rs.getInt(4) == 0 ? null : rs.getInt(4)) + "\t" + rs.getDate(5)
             + "\n";
             txtResult.append(str);
          }
        } 
        else if (e.getSource() == btn3) {
        	 String query = "SELECT * FROM Customer";
           txtResult.setText("");
           txtResult.setText("CUSTNO          NAME                 ADDRESS        PHONE\n");
           rs = stmt.executeQuery(query);
           while (rs.next()) {
              String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
                    + "\n";
              txtResult.append(str);
           }
        } 
        else if (e.getSource() == btn4) {
       	 String query = "SELECT	bookname FROM	Book WHERE	bookid NOT IN "
       	 		+ "(SELECT	bookid "
       	 		+ "FROM	Orders "
       	 		+ "WHERE	custid = '1');";
          txtResult.setText("");
          txtResult.setText("BOOKNAME\n");
          rs = stmt.executeQuery(query);
          while (rs.next()) {
             String str = rs.getString(1) + "\n";
             txtResult.append(str);
          }
        } 
        else if (e.getSource() == btn5) {
       	 String query = "SELECT	COUNT(DISTINCT publisher)"
       	 		+ "FROM	Book "
       	 		+ "WHERE	bookid IN (SELECT	bookid "
       	 		+ "FROM	Orders "
       	 		+ "WHERE	custid = '1');";
          txtResult.setText("");
          txtResult.setText("cntPublisher\n");
          rs = stmt.executeQuery(query);
          while (rs.next()) {
             String str = rs.getString(1) + "\n";
             txtResult.append(str);
          }
        } 
        else if (e.getSource() == btn6) {
       	 String query = "SELECT	COUNT(orderid) "
       	 		+ "FROM	Orders "
       	 		+ "WHERE	custid = '1';";
          txtResult.setText("");
          txtResult.setText("cntOrder\n");
          rs = stmt.executeQuery(query);
          while (rs.next()) {
             String str = rs.getInt(1) + "\n";
             txtResult.append(str);
          }
        } 
        else if (e.getSource() == btnScreenReset) {
        	txtResult.setText("");     
        }
        else if (e.getSource() == btnInput) {
        	
        	String str[] = {txtOrderid.getText(), txtCustid.getText(), txtBookid.getText(), txtSaleprice.getText(), txtOrderdate.getText()};
        	String txt[] = {"orderid", "custid", "bookid", "saleprice", "orderdate"};
        	
        	String string1 = "", string2 = "";
        	
        	for (int i=0; i<5; i++) {
        		if (str[i].length() != 0){
        			if (i == 4) {
              	str[4] = "STR_TO_DATE('" + str[4] + "','%Y-%m-%d')";
        			}
        			
        			string1 += str[i] + " ";
        			string2 += txt[i] + " ";
        		}
        	}    	
	
        	string1 = string1.trim(); string1 = string1.replace(" ", ", ");
        	string2 = string2.trim(); string2 = string2.replace(" ", ", ");

        	String query = "INSERT INTO Orders (" + string2 + ") "
        			+ "VALUES(" + string1 + ")";
        	
        	txtOrderid.setText("");
        	txtCustid.setText("");
        	txtBookid.setText("");
        	txtSaleprice.setText("");
        	txtOrderdate.setText("");
        	
       
        	try {
        		stmt.executeUpdate(query);
        		txtResult.setText("입력 완료");
        		
        	} catch (Exception e2) {
            txtResult.setText("쿼리 읽기 실패 :" + e2);
          }
        	
        	 
        }
      } catch (Exception e2) {
         txtResult.setText("쿼리 읽기 실패 :" + e2);
      }

   }

   public static void main(String[] args) {
      midterm BLS = new midterm();
      
      //BLS.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      //BLS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      BLS.addWindowListener(new WindowAdapter() {
    	  public void windowClosing(WindowEvent we) {
    		try {
    			con.close();
    		} catch (Exception e4) { 	}
    		System.out.println("프로그램 완전 종료!");
    		System.exit(0);
    	  }
    	});
   }
}

import java.awt.event.*;
//import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import java.awt.*;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;


public class Final extends JFrame implements ActionListener {
	 Container c;
	 JPanel SouthPanel, NorthPanel, CenterPanel;
   JButton btnReset, btnInsertPage, btnDeletePage, btnUpdatePage, btnSearch1, btnSearch2, btnSearch3, btnPrev;
   JTextArea txtResult, txtStatus;
   JScrollPane resultScroll, statusScroll;
   String TABLE[] = {"Doctors", "Nurses", "Patients", "Treatments", "Charts"};
   String selectedTable;
   JComboBox tableBox;
   
   // 삽입 창
   JTextField txtInsert;
   JButton btnInsert;
   
   // 삭제 창
   JTextField txtDelete;
   JButton btnDelete;
   
   // 변경 창
   JTextField txtUpdateSet, txtUpdateWhere;
   JButton btnUpdate;
   
   // 검색 창

   static Connection con;
   Statement stmt;
   ResultSet rs;
   String Driver = "";
   String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
   String userid = "madang";
   String pwd = "madang";

   public Final() {
      super("Swing Database");
      layInit();
      conDB();
      setVisible(true);
      setBounds(-9, 0, 1600, 800);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public void layInit() {
  	 
  	 	c = getContentPane();
  	 
      SouthPanel = new JPanel();
      NorthPanel = new JPanel();
      CenterPanel = new JPanel();
      
      txtResult = new JTextArea(30, 100);
      txtStatus = new JTextArea(5, 100);
      
      btnPrev = new JButton("취소");
      
      tableBox = new JComboBox(TABLE);
      
      tableBox.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		selectedTable = tableBox.getSelectedItem().toString();
      	}
      });
      

      txtResult.setEditable(false);
      txtStatus.setEditable(false);
      
      resultScroll = new JScrollPane(txtResult);
      statusScroll = new JScrollPane(txtStatus);
      
      CenterPanel.add(resultScroll);
      SouthPanel.add(statusScroll);
      
      c.add(SouthPanel, BorderLayout.SOUTH);
      c.add(CenterPanel, BorderLayout.CENTER);
      c.add(NorthPanel, BorderLayout.NORTH); 
      
      btnPrev.addActionListener(this);
      
      
      initPage();
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
   
   public void initPage() { // 초기 화면
  	 
  	 NorthPanel.removeAll();
     NorthPanel.revalidate();
     NorthPanel.repaint();
  	 
 	 	 btnReset = new JButton("초기화");
 	 	 btnInsertPage = new JButton("삽입");
     btnDeletePage = new JButton("삭제");
     btnUpdatePage = new JButton("변경");
     btnSearch1 = new JButton("검색1");
     btnSearch2 = new JButton("검색2");
     btnSearch3 = new JButton("검색3");
     
     NorthPanel.add(btnReset);
     NorthPanel.add(btnInsertPage);
     NorthPanel.add(btnDeletePage);
     NorthPanel.add(btnUpdatePage);
     NorthPanel.add(btnSearch1);
     NorthPanel.add(btnSearch2);
     NorthPanel.add(btnSearch3);
     
     
     btnReset.addActionListener(this);
     btnInsertPage.addActionListener(this);
     btnDeletePage.addActionListener(this);
     btnUpdatePage.addActionListener(this);
     btnSearch1.addActionListener(this);
     btnSearch2.addActionListener(this);
     btnSearch3.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
  
      try {
         stmt = con.createStatement();
         
        
         if (e.getSource() == btnReset) {
        	 String query = "drop table if exists `madang`.`Charts`";
           stmt.executeUpdate(query);
           query = "drop table if exists `madang`.`Treatments`";
           stmt.executeUpdate(query);
           query = "drop table if exists `madang`.`Patients`";
           stmt.executeUpdate(query);
           query = "drop table if exists `madang`.`Nurses`";
           stmt.executeUpdate(query);
           query = "drop table if exists `madang`.`Doctors`";
           stmt.executeUpdate(query);

           query = "CREATE TABLE IF NOT EXISTS `madang`.`Doctors` (\r\n"
           		+ "  `doc_id` INT NOT NULL,\r\n"
           		+ "  `major_treat` VARCHAR(25) NOT NULL,\r\n"
           		+ "  `doc_name` VARCHAR(20) NOT NULL,\r\n"
           		+ "  `doc_gen` CHAR(1) NOT NULL,\r\n"
           		+ "  `doc_phone` VARCHAR(15) NULL,\r\n"
           		+ "  `doc_email` VARCHAR(50) NULL,\r\n"
           		+ "  `doc_position` VARCHAR(20) NOT NULL,\r\n"
           		+ "  PRIMARY KEY (`doc_id`))\r\n"
           		+ "ENGINE = InnoDB;";
           stmt.executeUpdate(query);
           
           query = "CREATE TABLE IF NOT EXISTS `madang`.`Nurses` (\r\n"
           		+ "  `nur_id` INT NOT NULL,\r\n"
           		+ "  `major_job` VARCHAR(25) NOT NULL,\r\n"
           		+ "  `nur_name` VARCHAR(20) NOT NULL,\r\n"
           		+ "  `nur_gen` CHAR(1) NOT NULL,\r\n"
           		+ "  `nur_phone` VARCHAR(15) NULL,\r\n"
           		+ "  `nur_email` VARCHAR(50) NULL,\r\n"
           		+ "  `nur_position` VARCHAR(20) NOT NULL,\r\n"
           		+ "  PRIMARY KEY (`nur_id`))\r\n"
           		+ "ENGINE = InnoDB;";
           stmt.executeUpdate(query);
           
           query = "CREATE TABLE IF NOT EXISTS `madang`.`Patients` (\r\n"
           		+ "  `pat_id` INT NOT NULL,\r\n"
           		+ "  `nur_id` INT NOT NULL,\r\n"
           		+ "  `doc_id` INT NOT NULL,\r\n"
           		+ "  `pat_name` VARCHAR(20) NOT NULL,\r\n"
           		+ "  `pat_gen` CHAR(1) NOT NULL,\r\n"
           		+ "  `pat_jumin` VARCHAR(14) NOT NULL,\r\n"
           		+ "  `pat_addr` VARCHAR(100) NOT NULL,\r\n"
           		+ "  `pat_phone` VARCHAR(15) NULL,\r\n"
           		+ "  `pat_email` VARCHAR(50) NULL,\r\n"
           		+ "  `pat_job` VARCHAR(20) NOT NULL,\r\n"
           		+ "  PRIMARY KEY (`pat_id`),\r\n"
           		+ "  INDEX `fk_Patients_Nurses_idx` (`nur_id` ASC) VISIBLE,\r\n"
           		+ "  INDEX `fk_Patients_Doctors1_idx` (`doc_id` ASC) VISIBLE,\r\n"
           		+ "  CONSTRAINT `fk_Patients_Nurses`\r\n"
           		+ "    FOREIGN KEY (`nur_id`)\r\n"
           		+ "    REFERENCES `madang`.`Nurses` (`nur_id`)\r\n"
           		+ "    ON DELETE NO ACTION\r\n"
           		+ "    ON UPDATE NO ACTION,\r\n"
           		+ "  CONSTRAINT `fk_Patients_Doctors1`\r\n"
           		+ "    FOREIGN KEY (`doc_id`)\r\n"
           		+ "    REFERENCES `madang`.`Doctors` (`doc_id`)\r\n"
           		+ "    ON DELETE NO ACTION\r\n"
           		+ "    ON UPDATE NO ACTION)\r\n"
           		+ "ENGINE = InnoDB;";
           stmt.executeUpdate(query);
           
           query = "CREATE TABLE IF NOT EXISTS `madang`.`Treatments` (\r\n"
           		+ "  `treat_id` INT NOT NULL,\r\n"
           		+ "  `pat_id` INT NOT NULL,\r\n"
           		+ "  `doc_id` INT NOT NULL,\r\n"
           		+ "  `treat_contents` VARCHAR(1000) NOT NULL,\r\n"
           		+ "  `treat_date` DATE NOT NULL,\r\n"
           		+ "  PRIMARY KEY (`treat_id`, `pat_id`, `doc_id`),\r\n"
           		+ "  INDEX `fk_Treatments_Patients1_idx` (`pat_id` ASC) VISIBLE,\r\n"
           		+ "  INDEX `fk_Treatments_Doctors1_idx` (`doc_id` ASC) VISIBLE,\r\n"
           		+ "  CONSTRAINT `fk_Treatments_Patients1`\r\n"
           		+ "    FOREIGN KEY (`pat_id`)\r\n"
           		+ "    REFERENCES `madang`.`Patients` (`pat_id`)\r\n"
           		+ "    ON DELETE NO ACTION\r\n"
           		+ "    ON UPDATE NO ACTION,\r\n"
           		+ "  CONSTRAINT `fk_Treatments_Doctors1`\r\n"
           		+ "    FOREIGN KEY (`doc_id`)\r\n"
           		+ "    REFERENCES `madang`.`Doctors` (`doc_id`)\r\n"
           		+ "    ON DELETE NO ACTION\r\n"
           		+ "    ON UPDATE NO ACTION)\r\n"
           		+ "ENGINE = InnoDB;";
           stmt.executeUpdate(query);
           
           query = "CREATE TABLE IF NOT EXISTS `madang`.`Charts` (\r\n"
           		+ "  `chart_id` VARCHAR(20) NOT NULL,\r\n"
           		+ "  `treat_id` INT NOT NULL,\r\n"
           		+ "  `doc_id` INT NOT NULL,\r\n"
           		+ "  `pat_id` INT NOT NULL,\r\n"
           		+ "  `nur_id` INT NOT NULL,\r\n"
           		+ "  `chart_contents` VARCHAR(1000) NOT NULL,\r\n"
           		+ "  PRIMARY KEY (`chart_id`, `treat_id`, `doc_id`, `pat_id`),\r\n"
           		+ "  INDEX `fk_Charts_Treatments1_idx` (`treat_id` ASC, `pat_id` ASC, `doc_id` ASC) VISIBLE,\r\n"
           		+ "  INDEX `fk_Charts_Nurses1_idx` (`nur_id` ASC) VISIBLE,\r\n"
           		+ "  CONSTRAINT `fk_Charts_Treatments1`\r\n"
           		+ "    FOREIGN KEY (`treat_id` , `pat_id` , `doc_id`)\r\n"
           		+ "    REFERENCES `madang`.`Treatments` (`treat_id` , `pat_id` , `doc_id`)\r\n"
           		+ "    ON DELETE NO ACTION\r\n"
           		+ "    ON UPDATE NO ACTION,\r\n"
           		+ "  CONSTRAINT `fk_Charts_Nurses1`\r\n"
           		+ "    FOREIGN KEY (`nur_id`)\r\n"
           		+ "    REFERENCES `madang`.`Nurses` (`nur_id`)\r\n"
           		+ "    ON DELETE NO ACTION\r\n"
           		+ "    ON UPDATE NO ACTION)\r\n"
           		+ "ENGINE = InnoDB;";
           stmt.executeUpdate(query);
           
           
           
           stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (980312,'소아과', '이태정','M','010-333-1340','ltj@hanbh.com','과장');");
           stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (000601, '내과', '안성기','M','011-222-0987', 'ask@hanbh.com','과장');");
           stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (001208,'외과','김민종','M','010-333-8743','kmj@han.com','과장');");
           stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (020403, '피부과','이태서' ,'M' ,'019-777-3764', 'lts@hanbh.com', '과장');");
           stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (050900, '소아과', '김연아', 'F' ,'010-555-3746', 'kya@hanbh.com', '전문의');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (050101, '내과', '차태현', 'M' ,'011-222-7643', 'cth@hanbh.com', '전문의');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)"
           + " VALUES (062019, '소아과', '전지현', 'F', '010-999-1265', 'jjh@hanbh.com', '전문의');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (070576, '피부과', '홍길동', 'M' ,'016-333-7263', 'hgd@hanbh.com', '전문의');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (080543, '방사선과', '유재석', 'M', '010-222-1263', 'yjs@hanbh.com', '과장');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (091001, '외과', '김병만', 'M', '010-555-3542', 'kbm@hanbh.com', '전문의');");


          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)" 
          		 + " VALUES (050302, '소아과', '김은영', 'F', '010-555-8751', 'key@hanbh.com', '수간호사');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (050021, '내과', '윤성애', 'F', '016-333-8745', 'ysa@hanbh.com', '수간호사');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (040089, '피부과', '신지원', 'M', '010-666-7646', 'sjw@hanbh.com', '주임');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (070605, '방사선과', '유정화', 'F', '010-333-4588', 'yjh@hanbh.com', '주임');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (070804, '내과', '라하나', 'F', '010-222-1340', 'nhn@hanbh.com', '주임');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (071018, '소아과', '김화경', 'F', '019-888-4116', 'khk@hanbh.com', '주임');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (100356, '소아과', '이선용', 'M', '010-777-1234', 'lsy@hanbh.com', '간호사');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (104145, '외과', '김현', 'M', '010-999-8520', 'kh@hanbh.com', '간호사');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (120309, '피부과', '박성완', 'M', '010-777-4996', 'psw@hanbh.com', '간호사');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (130211, '외과', '이서연', 'F', '010-222-3214', 'lsy2@hanbh.com', '간호사');");

          		  
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(2345, 050302, 980312, '안상건', 'M', 232345, '서울', '010-555-7845', 'ask@ab.com', '회사원');");
          		 stmt.executeUpdate("INSERT INTO Patients (pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(3545, 040089, 20403, '김성룡', 'M', 543545, '서울', '010-333-7812', 'ksr@bb.com', '자영업');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(3424, 070605, 80543, '이종진', 'M', 433424, '부산', '019-888-4859', 'ljj@ab.com', '회사원');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(7675, 100356, 50900, '최광석', 'M', 677675, '당진', '010-222-4847', 'cks@cc.com', '회사원');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(4533, 70804, 601, '정한경', 'M', 744533, '강릉', '010-777-9630', 'jhk@ab.com', '교수');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(5546, 120309, 70576, '유원현', 'M', 765546, '대구', '016-777-0214', 'ywh@cc.com', '자영업');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(4543, 70804, 50101, '최재정', 'M', 454543, '부산', '010-555-4187', 'cjj@bb.com', '회사원');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(9768, 130211, 91001, '이진희', 'F', 119768, '서울', '010-888-3675', 'ljh@ab.com', '교수');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(4234, 130211, 91001, '오나미', 'F', 234234, '속초', '010-999-6541', 'onm@cc.com', '학생');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(7643, 71018, 62019, '송성묵', 'M', 987643, '서울', '010-222-5874', 'ssm@bb.com', '학생');");

          		  
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (130516023, 2345, 980312, '감기, 몸살', STR_TO_DATE('2013-05-16','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (130628100, 3545, 020403, '피부 트러블 치료', STR_TO_DATE('2013-06-28','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (131205056, 3424, 080543, '목 디스크로 MRI 촬영', STR_TO_DATE('2013-12-05','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (131218024, 7675, 050900, '중이염', STR_TO_DATE('2013-12-18','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (131224012, 4533, 000601, '장염', STR_TO_DATE('2013-12-24','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140103001, 5546, 070576, '여드름 치료', STR_TO_DATE('2014-01-03','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140109026, 4543, 050101, '위염' ,STR_TO_DATE('2014-01-09','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140226102, 9768, 091001, '화상 치료', STR_TO_DATE('2014-02-26','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140303003, 4234, 091001, '교통사고 외상치료', STR_TO_DATE('2014-03-03','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140308087, 7643, 062019, '장염', STR_TO_DATE('2014-03-08','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (210101010, 5546, 070576, '감기', STR_TO_DATE('2020-07-13','%Y-%m-%d'));");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (220202020, 4543, 050101, '중이염', STR_TO_DATE('2020-09-23','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (230303030, 9768, 091001, '화상 치료', STR_TO_DATE('2021-06-01','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (240404040, 2345, 980312, '역류성 식도염', STR_TO_DATE('2021-06-04','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (250505050, 7643, 062019, '피부 염층 치료', STR_TO_DATE('2021-06-10','%Y-%m-%d'))");


          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_130516023', 130516023, 980312, 2345, 050302, '주사 및 약 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_130628100', 130628100, 020403, 3545, 040089, '연고 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_131205056', 131205056, 080543, 3424, 070605, '약 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_131218024', 131218024, 050900, 7675, 100356, '귀 청소 및 약 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_131224012', 131224012, 000601, 4533, 070804, '장염 입원치료 및 약 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140103001', 140103001, 070576, 5546, 120309, '여드름 치료약 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140109026', 140109026, 050101, 4543, 070804, '위내시경 검사');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140226102', 140226102, 091001, 9768, 130211, '화상 크림 연고 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140303003', 140303003, 091001, 4234, 130211, '입원치료');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140308087', 140308087, 062019, 7643, 071018, '장염 입원치료');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_210101010', 210101010, 070576, 5546, 104145, '감기 약 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_220202020', 220202020, 050101, 4543, 100356, '중이염 약 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_230303030', 230303030, 091001, 9768, 130211, '입원 치료');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_240404040', 240404040, 980312, 2345, 071018, '약 처방');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_250505050', 250505050, 062019, 7643, 120309, '피부 염층 치료');");

        	 
        	 txtResult.setText("초기화 완료");
         }
         else if (e.getSource() == btnInsertPage) {
        	 
        	 NorthPanel.removeAll();
        	 NorthPanel.revalidate();
        	 NorthPanel.repaint();
        	 
        	 selectedTable = "Doctors"; // 처음 선택되는 테이블은 doctors
        	 
        	 JLabel txt = new JLabel("INSERT INTO ");
        	 
        	 txtResult.setText("");
        	 
        	 txtInsert = new JTextField(30);
        	 btnInsert = new JButton("입력");
        	 
        	 NorthPanel.add(txt);
        	 NorthPanel.add(tableBox);
        	 NorthPanel.add(txtInsert);
        	 NorthPanel.add(btnInsert);
        	 NorthPanel.add(btnPrev);
        	 
        	 btnInsert.addActionListener(this);
        	 
        } 
         else if (e.getSource() == btnInsert) { 
        	 System.out.println(selectedTable);
        	 try {
        		 String query = "INSERT INTO " + selectedTable
          			 + " " + txtInsert.getText();
          	 
          	 stmt.executeUpdate(query);
          	 
          	 txtResult.setText("삽입 성공");
        	 }
        	 catch (Exception e2) {
             txtStatus.setText("쿼리 읽기 실패 :" + e2);
        	 }
        	 
         }
         else if (e.getSource() == btnDeletePage) {
        	 
        	 NorthPanel.removeAll();
        	 NorthPanel.revalidate();
        	 NorthPanel.repaint();
        	 
        	 selectedTable = "Doctors"; // 처음 선택되는 테이블은 doctors
        	 
        	 JLabel txt = new JLabel("DELETE FROM ");
        	 
        	 txtDelete = new JTextField(30);
        	 btnDelete = new JButton("삭제");
       	  
        	 NorthPanel.add(txt);
        	 NorthPanel.add(tableBox);
        	 NorthPanel.add(txtDelete);
        	 NorthPanel.add(btnDelete);
        	 NorthPanel.add(btnPrev);
        	 
        	 btnDelete.addActionListener(this);
        	    
        } 
         else if (e.getSource() == btnDelete) {
        	
        	 try {
        		 String query = "DELETE FROM " + selectedTable
        				 + " " + txtDelete.getText();
          	 
          	 stmt.executeUpdate(query);
          	 
          	 txtResult.setText("삭제 성공");
        	 }
        	 catch (Exception e2) {
             txtStatus.setText("쿼리 읽기 실패 :" + e2);
        	 }
         }
        else if (e.getSource() == btnUpdatePage) {
        	
        	NorthPanel.removeAll();
       	  NorthPanel.revalidate();
       	  NorthPanel.repaint();
       	  
       	  txtResult.setText("");
       	  
       	  selectedTable = "Doctors"; // 처음 선택되는 테이블은 doctors

       	  JLabel txt1 = new JLabel("UPDATE ");
       	  JLabel txt2 = new JLabel("SET ");
       	  JLabel txt3 = new JLabel("WHERE ");
       	  

       	  txtUpdateSet = new JTextField(30);
       	  txtUpdateWhere = new JTextField(30);
      	  btnUpdate = new JButton("변경");
      	  
      	  NorthPanel.add(txt1);
       	  NorthPanel.add(tableBox);
       	  NorthPanel.add(txt2);
      	  NorthPanel.add(txtUpdateSet);
      	  NorthPanel.add(txt3);
      	  NorthPanel.add(txtUpdateWhere);
      	  NorthPanel.add(btnUpdate);
      	  NorthPanel.add(btnPrev);
       	 
      	  btnUpdate.addActionListener(this);
        } 
        else if (e.getSource() == btnUpdate) {
        	
        	try {
       		 String query = "UPDATE " + selectedTable
         			+ " SET " + txtUpdateSet.getText() + " WHERE " + txtUpdateWhere.getText();
         	 
         	 stmt.executeUpdate(query);
         	 
         	 txtResult.setText("변경 성공");
       	 }
       	 catch (Exception e2) {
            txtStatus.setText("쿼리 읽기 실패 :" + e2);
       	 }
        }
        else if (e.getSource() == btnSearch1) {
        	
         NorthPanel.removeAll();
       	 NorthPanel.revalidate();
       	 NorthPanel.repaint();
       	 
       	 txtResult.setText("");
       	
       	 JButton Doctors = new JButton("Doctors");
       	 
       	 Doctors.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		txtResult.setText("");
         		
        		try {
        			String query = "SELECT * FROM Doctors";
        			
        			rs = stmt.executeQuery(query);
              
              txtResult.append("의사ID\t담당진료과목\t성명\t성별\t전화번호\t이메일\t직급\n");
       
              while (rs.next()) {
                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                        + rs.getString(5) + "\t"+ rs.getString(6) + "\t" + rs.getString(7) + "\n";
               txtResult.append(str);
              }
        			
        		} catch (Exception e2) {
              txtStatus.setText("쿼리 읽기 실패 :" + e2);
           }
         	}
       	 });
       	 
       	 JButton Nurses = new JButton("Nurses");
       	 
       	Nurses.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		txtResult.setText("");
         		
        		try {
        			String query = "SELECT * FROM Nurses";
        			
        			rs = stmt.executeQuery(query);
              
              txtResult.append("간호사ID\t담당업무\t성명\t성별\t전화번호\t이메일\t직급\n");
       
              while (rs.next()) {
                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                        + rs.getString(5) + "\t"+ rs.getString(6) + "\t" + rs.getString(7) + "\n";
               txtResult.append(str);
              }
        			
        		} catch (Exception e2) {
              txtStatus.setText("쿼리 읽기 실패 :" + e2);
           }
         	}
       	 });
         JButton Patients = new JButton("Patients");
         
         Patients.addActionListener(new ActionListener() {
          	public void actionPerformed(ActionEvent e) {
          		txtResult.setText("");
          		
         		try {
         			String query = "SELECT * FROM Patients";
         			
         			rs = stmt.executeQuery(query); // 오류 고치기
               
               txtResult.append("환자ID\t간호사ID\t의사ID\t환자성명\t환자성별\t주민번호\t주소\t전화번호\t이메일\t직업\n");
        
               while (rs.next()) {
                 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t"
                         + rs.getString(5) + "\t"+ rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8) 
                         + "\t" + rs.getString(9) + "\t" + rs.getString(10) +"\n";
                txtResult.append(str);
               }
         			
         		} catch (Exception e2) {
               txtStatus.setText("쿼리 읽기 실패 :" + e2);
            }
          	}
        	 });
         JButton Treatments = new JButton("Treatments");
         
         Treatments.addActionListener(new ActionListener() {
          	public void actionPerformed(ActionEvent e) {
          		txtResult.setText("");
          		
         		try {
         			String query = "SELECT * FROM Treatments";
         			
         			rs = stmt.executeQuery(query);
               
               txtResult.append("진료ID\t환자ID\t의사ID\t진료내용\t\t진료날짜\n");
        
               while (rs.next()) {
                 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t\t"
                         + rs.getDate(5) + "\n";
                txtResult.append(str);
               }
         			
         		  } catch (Exception e2) {
                txtStatus.setText("쿼리 읽기 실패 :" + e2);
              }
            }
        	 });
         JButton Charts = new JButton("Charts");
         

         Charts.addActionListener(new ActionListener() {
          	public void actionPerformed(ActionEvent e) {
          		txtResult.setText("");
          		
         		try {
         			String query = "SELECT * FROM Charts";
         			
         			rs = stmt.executeQuery(query);
               
               txtResult.append("차트ID\t진료ID\t의사ID\t환자ID\t간호사ID\t차트내용\n");
        
               while (rs.next()) {
                 String str = rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
                         + rs.getInt(5) + "\t" + rs.getString(6) + "\n";
                txtResult.append(str);
               }
         			
         		  } catch (Exception e2) {
                txtStatus.setText("쿼리 읽기 실패 :" + e2);
              }
            }
        	 });
       	 
       	 NorthPanel.add(Doctors);
       	 NorthPanel.add(Nurses);
       	 NorthPanel.add(Patients);
       	 NorthPanel.add(Treatments);
       	 NorthPanel.add(Charts);
       	 NorthPanel.add(btnPrev);

        } 
        
        else if (e.getSource() == btnSearch2) {
        	
         	NorthPanel.removeAll();
       	  NorthPanel.revalidate();
       	  NorthPanel.repaint();
       	  
       	 txtResult.setText("");
       	 
       	 txtResult.append("성별이 여자인 의사가 진료한 횟수\n");
       	 
       	 String query = "SELECT doc_id, count(*) FROM treatments WHERE doc_id IN "
        	 		+ "(SELECT doc_id FROM doctors WHERE doc_gen = 'F') GROUP BY doc_id;";
       	 
       	 txtResult.append(query + "\n\n");
       	 
       	 try {
       		 
       		 txtResult.append("의사ID\t진료횟수\n");
       		 
       		 rs = stmt.executeQuery(query);
       		 
	       	 while (rs.next()) {
	       		 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\n";
	           		txtResult.append(str);
	       	 }
       	 } catch (Exception e2) {
       		 	txtStatus.setText("쿼리 읽기 실패 :" + e2);
       	 }
       	 
      	  
       	 NorthPanel.add(btnPrev);
       	 
        } 
        else if (e.getSource() == btnSearch3) {
        	
        	NorthPanel.removeAll();
       	  NorthPanel.revalidate();
       	  NorthPanel.repaint();
       	  
       	 txtResult.setText("");
       	 
       	 
       	 txtResult.append("서울에 거주하는 환자를 진료하는 소아과 전공 의사가 진료한 차트 기록 횟수\n");
       	 
       	 String query = "SELECT pat_id, count(pat_id) FROM charts WHERE pat_id  IN (SELECT patients.pat_id FROM doctors, patients WHERE doctors.doc_id = patients.doc_id"
       	 		+ " AND patients.pat_addr = \"서울\" AND doctors.major_treat = \"소아과\")"
       	 		+ " GROUP BY pat_id;";
       	 
      	 txtResult.append(query + "\n\n");
      	 
       	 txtResult.append("환자ID\t차트기록횟수\n");
       	 
       	 try {
       	
          	 rs = stmt.executeQuery(query);
          	 
          	 while (rs.next()) {
  	       		 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\n";
  	           		txtResult.append(str);
  	       	 }
          	
       	 }
       	 catch (Exception e2) {
     		 		txtStatus.setText("쿼리 읽기 실패 :" + e2);
     	   }
       	 
      
       	 NorthPanel.add(btnPrev);

        } 
         
         
        else if (e.getSource() == btnPrev) {
        	txtResult.setText("");
        	initPage();   
        }
        
      } catch (Exception e2) {
         txtResult.setText("쿼리 읽기 실패 :" + e2);
      }

   }

   public static void main(String[] args) {
      Final BLS = new Final();
      
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

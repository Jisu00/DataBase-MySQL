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
   
   // ���� â
   JTextField txtInsert;
   JButton btnInsert;
   
   // ���� â
   JTextField txtDelete;
   JButton btnDelete;
   
   // ���� â
   JTextField txtUpdateSet, txtUpdateWhere;
   JButton btnUpdate;
   
   // �˻� â

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
      
      btnPrev = new JButton("���");
      
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
         //System.out.println("����̹� �ε� ����");
         txtStatus.append("����̹� �ε� ����...\n");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      
      try { /* �����ͺ��̽��� �����ϴ� ���� */
          //System.out.println("�����ͺ��̽� ���� �غ�...");
    	  txtStatus.append("�����ͺ��̽� ���� �غ�...\n");
          con = DriverManager.getConnection(url, userid, pwd);
          //System.out.println("�����ͺ��̽� ���� ����");
          txtStatus.append("�����ͺ��̽� ���� ����...\n");
       } catch (SQLException e1) {
          e1.printStackTrace();
       }
   }
   
   public void initPage() { // �ʱ� ȭ��
  	 
  	 NorthPanel.removeAll();
     NorthPanel.revalidate();
     NorthPanel.repaint();
  	 
 	 	 btnReset = new JButton("�ʱ�ȭ");
 	 	 btnInsertPage = new JButton("����");
     btnDeletePage = new JButton("����");
     btnUpdatePage = new JButton("����");
     btnSearch1 = new JButton("�˻�1");
     btnSearch2 = new JButton("�˻�2");
     btnSearch3 = new JButton("�˻�3");
     
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
           + " VALUES (980312,'�Ҿư�', '������','M','010-333-1340','ltj@hanbh.com','����');");
           stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (000601, '����', '�ȼ���','M','011-222-0987', 'ask@hanbh.com','����');");
           stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (001208,'�ܰ�','�����','M','010-333-8743','kmj@han.com','����');");
           stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (020403, '�Ǻΰ�','���¼�' ,'M' ,'019-777-3764', 'lts@hanbh.com', '����');");
           stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (050900, '�Ҿư�', '�迬��', 'F' ,'010-555-3746', 'kya@hanbh.com', '������');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (050101, '����', '������', 'M' ,'011-222-7643', 'cth@hanbh.com', '������');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)"
           + " VALUES (062019, '�Ҿư�', '������', 'F', '010-999-1265', 'jjh@hanbh.com', '������');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (070576, '�Ǻΰ�', 'ȫ�浿', 'M' ,'016-333-7263', 'hgd@hanbh.com', '������');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (080543, '��缱��', '���缮', 'M', '010-222-1263', 'yjs@hanbh.com', '����');");
          stmt.executeUpdate("INSERT INTO Doctors(doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position)" 
           + " VALUES (091001, '�ܰ�', '�躴��', 'M', '010-555-3542', 'kbm@hanbh.com', '������');");


          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)" 
          		 + " VALUES (050302, '�Ҿư�', '������', 'F', '010-555-8751', 'key@hanbh.com', '����ȣ��');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (050021, '����', '������', 'F', '016-333-8745', 'ysa@hanbh.com', '����ȣ��');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (040089, '�Ǻΰ�', '������', 'M', '010-666-7646', 'sjw@hanbh.com', '����');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (070605, '��缱��', '����ȭ', 'F', '010-333-4588', 'yjh@hanbh.com', '����');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (070804, '����', '���ϳ�', 'F', '010-222-1340', 'nhn@hanbh.com', '����');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (071018, '�Ҿư�', '��ȭ��', 'F', '019-888-4116', 'khk@hanbh.com', '����');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (100356, '�Ҿư�', '�̼���', 'M', '010-777-1234', 'lsy@hanbh.com', '��ȣ��');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (104145, '�ܰ�', '����', 'M', '010-999-8520', 'kh@hanbh.com', '��ȣ��');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (120309, '�Ǻΰ�', '�ڼ���', 'M', '010-777-4996', 'psw@hanbh.com', '��ȣ��');");
          		 stmt.executeUpdate("INSERT INTO Nurses(nur_id, major_job, nur_name, nur_gen, nur_phone, nur_email, nur_position)"
          		 + " VALUES (130211, '�ܰ�', '�̼���', 'F', '010-222-3214', 'lsy2@hanbh.com', '��ȣ��');");

          		  
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(2345, 050302, 980312, '�Ȼ��', 'M', 232345, '����', '010-555-7845', 'ask@ab.com', 'ȸ���');");
          		 stmt.executeUpdate("INSERT INTO Patients (pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(3545, 040089, 20403, '�輺��', 'M', 543545, '����', '010-333-7812', 'ksr@bb.com', '�ڿ���');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(3424, 070605, 80543, '������', 'M', 433424, '�λ�', '019-888-4859', 'ljj@ab.com', 'ȸ���');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(7675, 100356, 50900, '�ֱ���', 'M', 677675, '����', '010-222-4847', 'cks@cc.com', 'ȸ���');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(4533, 70804, 601, '���Ѱ�', 'M', 744533, '����', '010-777-9630', 'jhk@ab.com', '����');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(5546, 120309, 70576, '������', 'M', 765546, '�뱸', '016-777-0214', 'ywh@cc.com', '�ڿ���');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(4543, 70804, 50101, '������', 'M', 454543, '�λ�', '010-555-4187', 'cjj@bb.com', 'ȸ���');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(9768, 130211, 91001, '������', 'F', 119768, '����', '010-888-3675', 'ljh@ab.com', '����');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(4234, 130211, 91001, '������', 'F', 234234, '����', '010-999-6541', 'onm@cc.com', '�л�');");
          		 stmt.executeUpdate("INSERT INTO Patients(pat_id, nur_id, doc_id, pat_name, pat_gen, pat_jumin, pat_addr, pat_phone, pat_email, pat_job)"
          		 + " VALUES(7643, 71018, 62019, '�ۼ���', 'M', 987643, '����', '010-222-5874', 'ssm@bb.com', '�л�');");

          		  
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (130516023, 2345, 980312, '����, ����', STR_TO_DATE('2013-05-16','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (130628100, 3545, 020403, '�Ǻ� Ʈ���� ġ��', STR_TO_DATE('2013-06-28','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (131205056, 3424, 080543, '�� ��ũ�� MRI �Կ�', STR_TO_DATE('2013-12-05','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (131218024, 7675, 050900, '���̿�', STR_TO_DATE('2013-12-18','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (131224012, 4533, 000601, '�忰', STR_TO_DATE('2013-12-24','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140103001, 5546, 070576, '���帧 ġ��', STR_TO_DATE('2014-01-03','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140109026, 4543, 050101, '����' ,STR_TO_DATE('2014-01-09','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140226102, 9768, 091001, 'ȭ�� ġ��', STR_TO_DATE('2014-02-26','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140303003, 4234, 091001, '������ �ܻ�ġ��', STR_TO_DATE('2014-03-03','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (140308087, 7643, 062019, '�忰', STR_TO_DATE('2014-03-08','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (210101010, 5546, 070576, '����', STR_TO_DATE('2020-07-13','%Y-%m-%d'));");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (220202020, 4543, 050101, '���̿�', STR_TO_DATE('2020-09-23','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (230303030, 9768, 091001, 'ȭ�� ġ��', STR_TO_DATE('2021-06-01','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (240404040, 2345, 980312, '������ �ĵ���', STR_TO_DATE('2021-06-04','%Y-%m-%d'))");
          		 stmt.executeUpdate("INSERT INTO Treatments(treat_id, pat_id, doc_id, treat_contents, treat_date)"
          		 + " VALUES (250505050, 7643, 062019, '�Ǻ� ���� ġ��', STR_TO_DATE('2021-06-10','%Y-%m-%d'))");


          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_130516023', 130516023, 980312, 2345, 050302, '�ֻ� �� �� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_130628100', 130628100, 020403, 3545, 040089, '���� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_131205056', 131205056, 080543, 3424, 070605, '�� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_131218024', 131218024, 050900, 7675, 100356, '�� û�� �� �� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_131224012', 131224012, 000601, 4533, 070804, '�忰 �Կ�ġ�� �� �� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140103001', 140103001, 070576, 5546, 120309, '���帧 ġ��� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140109026', 140109026, 050101, 4543, 070804, '�����ð� �˻�');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140226102', 140226102, 091001, 9768, 130211, 'ȭ�� ũ�� ���� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140303003', 140303003, 091001, 4234, 130211, '�Կ�ġ��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_140308087', 140308087, 062019, 7643, 071018, '�忰 �Կ�ġ��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_210101010', 210101010, 070576, 5546, 104145, '���� �� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_220202020', 220202020, 050101, 4543, 100356, '���̿� �� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_230303030', 230303030, 091001, 9768, 130211, '�Կ� ġ��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_240404040', 240404040, 980312, 2345, 071018, '�� ó��');");
          		 stmt.executeUpdate("INSERT INTO Charts(chart_id, treat_id, doc_id, pat_id, nur_id, chart_contents)"
          		 + " VALUES ('c_250505050', 250505050, 062019, 7643, 120309, '�Ǻ� ���� ġ��');");

        	 
        	 txtResult.setText("�ʱ�ȭ �Ϸ�");
         }
         else if (e.getSource() == btnInsertPage) {
        	 
        	 NorthPanel.removeAll();
        	 NorthPanel.revalidate();
        	 NorthPanel.repaint();
        	 
        	 selectedTable = "Doctors"; // ó�� ���õǴ� ���̺��� doctors
        	 
        	 JLabel txt = new JLabel("INSERT INTO ");
        	 
        	 txtResult.setText("");
        	 
        	 txtInsert = new JTextField(30);
        	 btnInsert = new JButton("�Է�");
        	 
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
          	 
          	 txtResult.setText("���� ����");
        	 }
        	 catch (Exception e2) {
             txtStatus.setText("���� �б� ���� :" + e2);
        	 }
        	 
         }
         else if (e.getSource() == btnDeletePage) {
        	 
        	 NorthPanel.removeAll();
        	 NorthPanel.revalidate();
        	 NorthPanel.repaint();
        	 
        	 selectedTable = "Doctors"; // ó�� ���õǴ� ���̺��� doctors
        	 
        	 JLabel txt = new JLabel("DELETE FROM ");
        	 
        	 txtDelete = new JTextField(30);
        	 btnDelete = new JButton("����");
       	  
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
          	 
          	 txtResult.setText("���� ����");
        	 }
        	 catch (Exception e2) {
             txtStatus.setText("���� �б� ���� :" + e2);
        	 }
         }
        else if (e.getSource() == btnUpdatePage) {
        	
        	NorthPanel.removeAll();
       	  NorthPanel.revalidate();
       	  NorthPanel.repaint();
       	  
       	  txtResult.setText("");
       	  
       	  selectedTable = "Doctors"; // ó�� ���õǴ� ���̺��� doctors

       	  JLabel txt1 = new JLabel("UPDATE ");
       	  JLabel txt2 = new JLabel("SET ");
       	  JLabel txt3 = new JLabel("WHERE ");
       	  

       	  txtUpdateSet = new JTextField(30);
       	  txtUpdateWhere = new JTextField(30);
      	  btnUpdate = new JButton("����");
      	  
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
         	 
         	 txtResult.setText("���� ����");
       	 }
       	 catch (Exception e2) {
            txtStatus.setText("���� �б� ���� :" + e2);
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
              
              txtResult.append("�ǻ�ID\t����������\t����\t����\t��ȭ��ȣ\t�̸���\t����\n");
       
              while (rs.next()) {
                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                        + rs.getString(5) + "\t"+ rs.getString(6) + "\t" + rs.getString(7) + "\n";
               txtResult.append(str);
              }
        			
        		} catch (Exception e2) {
              txtStatus.setText("���� �б� ���� :" + e2);
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
              
              txtResult.append("��ȣ��ID\t������\t����\t����\t��ȭ��ȣ\t�̸���\t����\n");
       
              while (rs.next()) {
                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                        + rs.getString(5) + "\t"+ rs.getString(6) + "\t" + rs.getString(7) + "\n";
               txtResult.append(str);
              }
        			
        		} catch (Exception e2) {
              txtStatus.setText("���� �б� ���� :" + e2);
           }
         	}
       	 });
         JButton Patients = new JButton("Patients");
         
         Patients.addActionListener(new ActionListener() {
          	public void actionPerformed(ActionEvent e) {
          		txtResult.setText("");
          		
         		try {
         			String query = "SELECT * FROM Patients";
         			
         			rs = stmt.executeQuery(query); // ���� ��ġ��
               
               txtResult.append("ȯ��ID\t��ȣ��ID\t�ǻ�ID\tȯ�ڼ���\tȯ�ڼ���\t�ֹι�ȣ\t�ּ�\t��ȭ��ȣ\t�̸���\t����\n");
        
               while (rs.next()) {
                 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t"
                         + rs.getString(5) + "\t"+ rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8) 
                         + "\t" + rs.getString(9) + "\t" + rs.getString(10) +"\n";
                txtResult.append(str);
               }
         			
         		} catch (Exception e2) {
               txtStatus.setText("���� �б� ���� :" + e2);
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
               
               txtResult.append("����ID\tȯ��ID\t�ǻ�ID\t���᳻��\t\t���ᳯ¥\n");
        
               while (rs.next()) {
                 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t\t"
                         + rs.getDate(5) + "\n";
                txtResult.append(str);
               }
         			
         		  } catch (Exception e2) {
                txtStatus.setText("���� �б� ���� :" + e2);
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
               
               txtResult.append("��ƮID\t����ID\t�ǻ�ID\tȯ��ID\t��ȣ��ID\t��Ʈ����\n");
        
               while (rs.next()) {
                 String str = rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
                         + rs.getInt(5) + "\t" + rs.getString(6) + "\n";
                txtResult.append(str);
               }
         			
         		  } catch (Exception e2) {
                txtStatus.setText("���� �б� ���� :" + e2);
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
       	 
       	 txtResult.append("������ ������ �ǻ簡 ������ Ƚ��\n");
       	 
       	 String query = "SELECT doc_id, count(*) FROM treatments WHERE doc_id IN "
        	 		+ "(SELECT doc_id FROM doctors WHERE doc_gen = 'F') GROUP BY doc_id;";
       	 
       	 txtResult.append(query + "\n\n");
       	 
       	 try {
       		 
       		 txtResult.append("�ǻ�ID\t����Ƚ��\n");
       		 
       		 rs = stmt.executeQuery(query);
       		 
	       	 while (rs.next()) {
	       		 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\n";
	           		txtResult.append(str);
	       	 }
       	 } catch (Exception e2) {
       		 	txtStatus.setText("���� �б� ���� :" + e2);
       	 }
       	 
      	  
       	 NorthPanel.add(btnPrev);
       	 
        } 
        else if (e.getSource() == btnSearch3) {
        	
        	NorthPanel.removeAll();
       	  NorthPanel.revalidate();
       	  NorthPanel.repaint();
       	  
       	 txtResult.setText("");
       	 
       	 
       	 txtResult.append("���￡ �����ϴ� ȯ�ڸ� �����ϴ� �Ҿư� ���� �ǻ簡 ������ ��Ʈ ��� Ƚ��\n");
       	 
       	 String query = "SELECT pat_id, count(pat_id) FROM charts WHERE pat_id  IN (SELECT patients.pat_id FROM doctors, patients WHERE doctors.doc_id = patients.doc_id"
       	 		+ " AND patients.pat_addr = \"����\" AND doctors.major_treat = \"�Ҿư�\")"
       	 		+ " GROUP BY pat_id;";
       	 
      	 txtResult.append(query + "\n\n");
      	 
       	 txtResult.append("ȯ��ID\t��Ʈ���Ƚ��\n");
       	 
       	 try {
       	
          	 rs = stmt.executeQuery(query);
          	 
          	 while (rs.next()) {
  	       		 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\n";
  	           		txtResult.append(str);
  	       	 }
          	
       	 }
       	 catch (Exception e2) {
     		 		txtStatus.setText("���� �б� ���� :" + e2);
     	   }
       	 
      
       	 NorthPanel.add(btnPrev);

        } 
         
         
        else if (e.getSource() == btnPrev) {
        	txtResult.setText("");
        	initPage();   
        }
        
      } catch (Exception e2) {
         txtResult.setText("���� �б� ���� :" + e2);
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
    		System.out.println("���α׷� ���� ����!");
    		System.exit(0);
    	  }
    	});
   }
}

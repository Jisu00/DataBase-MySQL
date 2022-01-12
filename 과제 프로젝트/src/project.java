
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
//import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;


public class project extends JFrame implements ActionListener {
   

   //수정
   JButton mainAdmin, mainProf, mainStud;
   JTextArea txtResult, txtStatus;
   JPanel pn2;
   JTextField t1,t2,t3,t4,t5;
   
   // 공통
   Container c;
   JPanel SouthPanel, NorthPanel, CenterPanel;
   JScrollPane mainScroll, stateScroll;
   
   //admin
   JButton resetBtn, insertBtn, deleteBtn, updateBtn, selectBtn, adminCancel;
   JTextField txtAdmin, adminClID, adminStudID, adminLecID, adminProfID, adminYear, adminSemester;
   JButton adminClBtn;
   
   //prof
   JTextField profYear, profSemester, profAttend, profMidterm, profFinal, profEtc, profLecID, profStudID;
   JButton profSearch, profStudent, profDepart, profTable, profInsertGrade, profCancelGrade, profCancel;
   
   //stud
   JTextField studYear, studSemester;
   JButton studSearch, studTable, studClub, studGradeCard, studCancel;
   
   
   //수정필요없음
   static Connection con;
   Statement stmt;
   ResultSet rs;
   String Driver = "";
   String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
   String userid = "madang";
   String pwd = "madang";

   public project() {
      super("Swing Database");
      layInit();
      conDB();
      setVisible(true);
      setBounds(-9, 0, 1600, 800); //가로위치,세로위치,가로길이,세로길이
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public void layInit() {
      
      setTitle("");
      
      c = getContentPane();
      
      txtResult = new JTextArea(17, 90);
      txtAdmin = new JTextField(30);
      txtStatus = new JTextArea(5,100);
      txtStatus.setEditable(false); //editing 안되게끔
      txtResult.setEditable(false);
      
      mainAdmin = new JButton("관리자");
      mainProf = new JButton("교수");
      mainStud = new JButton("학생");
      
      mainAdmin.setBackground(Color.yellow);
      mainProf.setBackground(Color.cyan);
      mainStud.setBackground(Color.pink);
      
      SouthPanel = new JPanel();
      NorthPanel = new JPanel();
      CenterPanel = new JPanel();
        
      CenterPanel.setLayout(new GridLayout(2, 1, 5, 5));
    
      NorthPanel.add(mainAdmin);
      NorthPanel.add(mainProf);
      NorthPanel.add(mainStud);

      
      stateScroll = new JScrollPane(txtStatus);
      mainScroll = new JScrollPane(txtResult);
      
      CenterPanel.add(mainScroll);
      SouthPanel.add(stateScroll);
      
      c.add(SouthPanel, BorderLayout.SOUTH);
      c.add(CenterPanel, BorderLayout.CENTER);
      c.add(NorthPanel, BorderLayout.NORTH); 
      
      mainAdmin.addActionListener(this);
      mainProf.addActionListener(this);
      mainStud.addActionListener(this);
    
   }
   

   public void clickAdmin() {
            
      resetBtn = new JButton("초기화");
      insertBtn = new JButton("입력");
      deleteBtn = new JButton("삭제");
      updateBtn = new JButton("변경");
      selectBtn = new JButton("전체 보기");
      adminCancel = new JButton("입력 취소");
      
      adminClBtn = new JButton("수강 내역 신규 삽입");
     
      
      NorthPanel.removeAll();

      NorthPanel.add(resetBtn);
      NorthPanel.add(txtAdmin);
      NorthPanel.add(insertBtn);
      NorthPanel.add(adminCancel);
      NorthPanel.add(deleteBtn);
      NorthPanel.add(updateBtn);
      NorthPanel.add(selectBtn);
      

      NorthPanel.add(adminClBtn);
      
      NorthPanel.revalidate();
      NorthPanel.repaint();
      

      c.add(NorthPanel);
      
      resetBtn.addActionListener(this);
      insertBtn.addActionListener(this);
      deleteBtn.addActionListener(this);
      updateBtn.addActionListener(this);
      selectBtn.addActionListener(this);
      adminCancel.addActionListener(this);
      adminClBtn.addActionListener(this);
   }
   
   public void clickProf() {
      
      NorthPanel.removeAll();
      NorthPanel.revalidate();
      NorthPanel.repaint();
      
      JLabel txtYear = new JLabel("년도");
      JLabel txtSemester = new JLabel("학기");
     
      profYear = new JTextField(4);
      profSemester = new JTextField(3);
      
      profSearch = new JButton("입력");
      profStudent = new JButton("지도 학생");
      profDepart = new JButton("학과 정보");
      profTable = new JButton("강의 시간표");
      profInsertGrade = new JButton("성적 입력");
      profCancel = new JButton("입력 취소");
      profCancelGrade = new JButton("성적 입력 취소");
      
      
      JLabel txtAttend = new JLabel("출석점수");
      JLabel txtMidterm = new JLabel("중간고사점수");
      JLabel txtFinal = new JLabel("기말고사점수");
      JLabel txtEtc = new JLabel("기타점수");
      JLabel txtLecID = new JLabel("\s\s\s강좌번호");
      JLabel txtStudID = new JLabel("학생번호");
      
      
      profAttend = new JTextField(3);
      profMidterm = new JTextField(3);
      profFinal = new JTextField(3);
      profEtc = new JTextField(3);
      profLecID = new JTextField(3);
      profStudID = new JTextField(3);
      
      
      NorthPanel.add(txtYear);
      NorthPanel.add(profYear);
      NorthPanel.add(txtSemester);
      NorthPanel.add(profSemester);
      NorthPanel.add(profSearch);
      NorthPanel.add(profCancel);
      NorthPanel.add(profStudent);
      NorthPanel.add(profDepart);
      NorthPanel.add(profTable);
      
      
      
      NorthPanel.add(txtLecID);
      NorthPanel.add(profLecID);
      NorthPanel.add(txtStudID);
      NorthPanel.add(profStudID);
     
      NorthPanel.add(txtAttend);
      NorthPanel.add(profAttend);
      NorthPanel.add(txtMidterm);
      NorthPanel.add(profMidterm);
      NorthPanel.add(txtFinal);
      NorthPanel.add(profFinal);
      NorthPanel.add(txtEtc);
      NorthPanel.add(profEtc);
      NorthPanel.add(profInsertGrade);
      NorthPanel.add(profCancelGrade);
      
     
      profSearch.addActionListener(this);
      profTable.addActionListener(this);
      profStudent.addActionListener(this);
      profDepart.addActionListener(this);
      profInsertGrade.addActionListener(this);
      profCancelGrade.addActionListener(this);
      profCancel.addActionListener(this);

   }
   
   public void clickStud() {
      
     NorthPanel.removeAll();
     NorthPanel.revalidate();
     NorthPanel.repaint();
     
            
     JLabel txtYear = new JLabel("년도");
     JLabel txtSemester = new JLabel("학기");
      
     studYear = new JTextField(5);
     studSemester = new JTextField(5);
      
     studSearch = new JButton("입력");
     studCancel = new JButton("입력 취소");
     studTable = new JButton("시간표");
     studClub = new JButton("동아리");
     studGradeCard = new JButton("성적표");
     
     
     NorthPanel.add(txtYear);
     NorthPanel.add(studYear);
     NorthPanel.add(txtSemester);
     NorthPanel.add(studSemester);
     NorthPanel.add(studSearch);
     NorthPanel.add(studCancel);
     NorthPanel.add(studTable);
     NorthPanel.add(studClub);
     NorthPanel.add(studGradeCard);
     

     studSearch.addActionListener(this);
     studTable.addActionListener(this);
     studClub.addActionListener(this);
     studGradeCard.addActionListener(this);
     studCancel.addActionListener(this);
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
         
         if (e.getSource() == mainAdmin) {
            clickAdmin();
         }
         if (e.getSource() == mainProf) {
            clickProf();
         }
         if (e.getSource() == mainStud) {
            clickStud();
         }
         
         /// Admin
         if (e.getSource() == resetBtn) {
            try {
            	
            	String query = "drop table if exists `madang`.`Student_has_Department`";
              stmt.executeUpdate(query);
              query = "drop table if exists `madang`.`Professor_has_Department`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`ClubStudentList`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`AdviseRelation`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`department`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`club`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`courselist`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`lecture`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`lecturelist`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`student`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`professor`";
               stmt.executeUpdate(query);
               query = "drop table if exists `madang`.`tuitionlist`";
               stmt.executeUpdate(query);
              

                query = "CREATE TABLE IF NOT EXISTS `madang`.`Professor` (\r\n"
                		+ "  `prof_id` INT NOT NULL,\r\n"
                		+ "  `prof_name` VARCHAR(45) NULL,\r\n"
                		+ "  `prof_addr` VARCHAR(45) NULL,\r\n"
                		+ "  `prof_tel` VARCHAR(45) NULL,\r\n"
                		+ "  `prof_email` VARCHAR(45) NULL,\r\n"
                		+ "  `prof_deptid` INT NULL,\r\n"
                		+ "  PRIMARY KEY (`prof_id`))\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`Student` (\r\n"
                		+ "  `stud_id` INT NOT NULL,\r\n"
                		+ "  `stud_name` VARCHAR(45) NULL,\r\n"
                		+ "  `stud_addr` VARCHAR(45) NULL,\r\n"
                		+ "  `stud_tel` VARCHAR(45) NULL,\r\n"
                		+ "  `stud_email` VARCHAR(45) NULL,\r\n"
                		+ "  `stud_deptid` INT NULL,\r\n"
                		+ "  `stud_advisorid` INT NOT NULL,\r\n"
                		+ "  `stud_account` VARCHAR(45) NULL,\r\n"
                		+ "  PRIMARY KEY (`stud_id`),\r\n"
                		+ "  INDEX `fk_Student_Professor1_idx` (`stud_advisorid` ASC) VISIBLE,\r\n"
                		+ "  CONSTRAINT `fk_Student_Professor1`\r\n"
                		+ "    FOREIGN KEY (`stud_advisorid`)\r\n"
                		+ "    REFERENCES `madang`.`Professor` (`prof_id`)\r\n"
                		+ "    ON DELETE NO ACTION\r\n"
                		+ "    ON UPDATE NO ACTION)\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`Department` (\r\n"
                		+ "  `dept_id` INT NOT NULL,\r\n"
                		+ "  `dept_name` VARCHAR(45) NULL,\r\n"
                		+ "  `dept_tel` VARCHAR(45) NULL,\r\n"
                		+ "  `dept_office` VARCHAR(45) NULL,\r\n"
                		+ "  `Professor_prof_id` INT NOT NULL,\r\n"
                		+ "  PRIMARY KEY (`dept_id`),\r\n"
                		+ "  INDEX `fk_Department_Professor1_idx` (`Professor_prof_id` ASC) VISIBLE,\r\n"
                		+ "  CONSTRAINT `fk_Department_Professor1`\r\n"
                		+ "    FOREIGN KEY (`Professor_prof_id`)\r\n"
                		+ "    REFERENCES `madang`.`Professor` (`prof_id`)\r\n"
                		+ "    ON DELETE NO ACTION\r\n"
                		+ "    ON UPDATE NO ACTION)\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`Lecture` (\r\n"
                		+ "  `lec_id` INT NOT NULL,\r\n"
                		+ "  `lec_roomnum` INT NULL,\r\n"
                		+ "  `lec_profid` INT NULL,\r\n"
                		+ "  `lec_name` VARCHAR(45) NULL,\r\n"
                		+ "  `lec_day` VARCHAR(45) NULL,\r\n"
                		+ "  `lec_period` INT NULL,\r\n"
                		+ "  `lec_credit` INT NULL,\r\n"
                		+ "  `lec_time` INT NULL,\r\n"
                		+ "  `lec_deptid` INT NULL,\r\n"
                		+ "  `lec_office` VARCHAR(45) NULL,\r\n"
                		+ "  PRIMARY KEY (`lec_id`))\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`CourseList` (\r\n"
                		+ "  `cl_id` INT NOT NULL,\r\n"
                		+ "  `cl_studid` INT NULL,\r\n"
                		+ "  `cl_lecid` INT NULL,\r\n"
                		+ "  `cl_profid` INT NULL,\r\n"
                		+ "  `cl_attend` INT NULL,\r\n"
                		+ "  `cl_midterm` INT NULL,\r\n"
                		+ "  `cl_final` INT NULL,\r\n"
                		+ "  `cl_etc` INT NULL,\r\n"
                		+ "  `cl_total` INT NULL,\r\n"
                		+ "  `cl_grade` VARCHAR(45) NULL,\r\n"
                		+ "  `cl_year` INT NULL,\r\n"
                		+ "  `cl_semester` INT NULL,\r\n"
                		+ "  PRIMARY KEY (`cl_id`))\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`LectureList` (\r\n"
                		+ "  `li_id` INT NOT NULL,\r\n"
                		+ "  `ll_year` INT NULL,\r\n"
                		+ "  `ll_semester` INT NULL,\r\n"
                		+ "  `ll_profid` INT NULL,\r\n"
                		+ "  `ll_lecid` INT NULL,\r\n"
                		+ "  PRIMARY KEY (`li_id`))\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`Club` (\r\n"
                		+ "  `club_id` INT NOT NULL,\r\n"
                		+ "  `club_name` VARCHAR(45) NULL,\r\n"
                		+ "  `club_totalnum` INT NULL,\r\n"
                		+ "  `club_masterid` INT NULL,\r\n"
                		+ "  `club_advisorid` INT NULL,\r\n"
                		+ "  `club_office` VARCHAR(45) NULL,\r\n"
                		+ "  PRIMARY KEY (`club_id`))\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`TuitionList` (\r\n"
                		+ "  `tl_studid` INT NOT NULL,\r\n"
                		+ "  `tl_year` INT NULL,\r\n"
                		+ "  `tl_semester` INT NULL,\r\n"
                		+ "  `tl_total` INT NULL,\r\n"
                		+ "  `tl_paytotal` INT NULL,\r\n"
                		+ "  `tl_lastdate` DATE NULL,\r\n"
                		+ "  PRIMARY KEY (`tl_studid`))\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`AdviseRelation` (\r\n"
                		+ "  `ar_year` INT NOT NULL,\r\n"
                		+ "  `ar_semester` INT NOT NULL,\r\n"
                		+ "  `ar_profid` INT NOT NULL,\r\n"
                		+ "  `ar_studid` INT NOT NULL,\r\n"
                		+ "  PRIMARY KEY (`ar_studid`))\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`Professor_has_Department` (\r\n"
                		+ "  `Professor_prof_id` INT NOT NULL,\r\n"
                		+ "  `Department_dept_id` INT NOT NULL,\r\n"
                		+ "  INDEX `fk_Professor_has_Department_Department1_idx` (`Department_dept_id` ASC) VISIBLE,\r\n"
                		+ "  INDEX `fk_Professor_has_Department_Professor1_idx` (`Professor_prof_id` ASC) VISIBLE,\r\n"
                		+ "  CONSTRAINT `fk_Professor_has_Department_Professor1`\r\n"
                		+ "    FOREIGN KEY (`Professor_prof_id`)\r\n"
                		+ "    REFERENCES `madang`.`Professor` (`prof_id`)\r\n"
                		+ "    ON DELETE NO ACTION\r\n"
                		+ "    ON UPDATE NO ACTION,\r\n"
                		+ "  CONSTRAINT `fk_Professor_has_Department_Department1`\r\n"
                		+ "    FOREIGN KEY (`Department_dept_id`)\r\n"
                		+ "    REFERENCES `madang`.`Department` (`dept_id`)\r\n"
                		+ "    ON DELETE NO ACTION\r\n"
                		+ "    ON UPDATE NO ACTION)\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`ClubStudentList` (\r\n"
                		+ "  `Club_club_id` INT NOT NULL,\r\n"
                		+ "  `Student_stud_id` INT NOT NULL,\r\n"
                		+ "  INDEX `fk_Club_has_Student_Student1_idx` (`Student_stud_id` ASC) VISIBLE,\r\n"
                		+ "  INDEX `fk_Club_has_Student_Club1_idx` (`Club_club_id` ASC) VISIBLE,\r\n"
                		+ "  CONSTRAINT `fk_Club_has_Student_Club1`\r\n"
                		+ "    FOREIGN KEY (`Club_club_id`)\r\n"
                		+ "    REFERENCES `madang`.`Club` (`club_id`)\r\n"
                		+ "    ON DELETE NO ACTION\r\n"
                		+ "    ON UPDATE NO ACTION,\r\n"
                		+ "  CONSTRAINT `fk_Club_has_Student_Student1`\r\n"
                		+ "    FOREIGN KEY (`Student_stud_id`)\r\n"
                		+ "    REFERENCES `madang`.`Student` (`stud_id`)\r\n"
                		+ "    ON DELETE NO ACTION\r\n"
                		+ "    ON UPDATE NO ACTION)\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                query = "CREATE TABLE IF NOT EXISTS `madang`.`Student_has_Department` (\r\n"
                		+ "  `Student_stud_id` INT NOT NULL,\r\n"
                		+ "  `Department_dept_id` INT NOT NULL,\r\n"
                		+ "  INDEX `fk_Student_has_Department_Department1_idx` (`Department_dept_id` ASC) VISIBLE,\r\n"
                		+ "  INDEX `fk_Student_has_Department_Student1_idx` (`Student_stud_id` ASC) VISIBLE,\r\n"
                		+ "  CONSTRAINT `fk_Student_has_Department_Student1`\r\n"
                		+ "    FOREIGN KEY (`Student_stud_id`)\r\n"
                		+ "    REFERENCES `madang`.`Student` (`stud_id`)\r\n"
                		+ "    ON DELETE NO ACTION\r\n"
                		+ "    ON UPDATE NO ACTION,\r\n"
                		+ "  CONSTRAINT `fk_Student_has_Department_Department1`\r\n"
                		+ "    FOREIGN KEY (`Department_dept_id`)\r\n"
                		+ "    REFERENCES `madang`.`Department` (`dept_id`)\r\n"
                		+ "    ON DELETE NO ACTION\r\n"
                		+ "    ON UPDATE NO ACTION)\r\n"
                		+ "ENGINE = InnoDB;\r\n"
                		+ "";
                stmt.executeUpdate(query);
                
                
                query = "INSERT INTO Professor VALUES(601, '가교수', '가로수길', '010-4572-8349', 'email1@daum.net', 901)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(602, '나교수', '강남대로', '010-1238-2390', 'email2@daum.net', 902)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(603, '다교수', '개포로', '010-3491-2234', 'email3@daum.net', 903)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(604, '라교수', '광평로', '010-8901-3023', 'email4@daum.net', 904)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(605, '마교수', '남부순환로', '010-1239-1230', 'email5@daum.net', 905)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(606, '바교수', '논현로', '010-7248-9705', 'email6@daum.net', 906)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(607, '사교수', '도곡로', '010-3248-9809', 'email7@daum.net', 907)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(608, '아교수', '도산대로', '010-2389-3389', 'email8@daum.net', 908)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(609, '자교수', '밤고개로', '010-1923-2039', 'email9@daum.net', 909)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(610, '차교수', '봉은사로', '010-2389-8293', 'email10@daum.net', 910)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(611, '카교수', '삼성로', '010-9903-2940', 'email11@daum.net', 911)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(612, '타교수', '선릉로', '010-2323-6989', 'email12@daum.net', 912)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(613, '파교수', '압구정로', '010-9897-6743', 'email13@daum.net', 913)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(614, '하교수', '양재대로', '010-5643-8492', 'email14@daum.net', 914)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(615, '강교수', '언주로', '010-2349-3204', 'email15@daum.net', 915)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(616, '낭교수', '역삼로', '010-3494-1904', 'email16@daum.net', 916)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(617, '당교수', '영동대로', '010-6892-8929', 'email17@daum.net', 917)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(618, '랑교수', '일원로', '010-1237-1190', 'email18@daum.net',918)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(619, '망교수', '테헤란로', '010-1239-0530', 'email19@daum.net', 919)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(620, '방교수', '학동로', '010-2307-0959', 'email20@daum.net', 920)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(621, '상교수', '헌릉로', '010-9070-5790', 'email21@daum.net', 921)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(622, '앙교수', '가래여울길', '010-8579-3726', 'email22@daum.net', 922)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(623, '장교수', '강동대로', '010-9393-8338', 'email23@daum.net', 923)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(624, '창교수', '고덕로', '010-2376-6949', 'email24@daum.net', 924)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(625, '캉교수', '동남로', '010-2937-0509', 'email25@daum.net', 925)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Professor VALUES(626, '항교수', '동남로', '010-2937-0509', 'email25@daum.net', 901)";
                stmt.executeUpdate(query);

                query = "INSERT INTO TuitionList VALUES(1, 2021, 1, 4500000, 4500000, '2021-02-10')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(2, 2020, 2, 4400000, 4300000, '2020-08-04')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(3, 2020, 2, 4000000, 4000000, '2020-08-25')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(4, 2020, 2, 4100000, 4100000, '2020-08-18')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(5, 2020, 2, 4200000, 4200000, '2020-08-14')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(6, 2020, 2, 4300000, 4000000, '2020-08-05')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(7, 2021, 1, 4300000, 4300000, '2021-02-02')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(8, 2020, 2, 4200000, 4200000, '2020-02-01')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(9, 2020, 2, 4400000, 4200000, '2020-02-12')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(10, 2020, 2, 4200000, 4200000, '2020-08-07')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(11, 2021, 1, 4400000, 4200000, '2021-02-06')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(12, 2020, 2, 4500000, 4500000, '2020-08-08')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(13, 2020, 2, 4200000, 4200000, '2020-08-29')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(14, 2021, 1, 4500000, 4500000, '2021-02-19')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(15, 2021, 1, 4500000, 4300000, '2021-02-28')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(16, 2020, 2, 4200000, 4200000, '2020-08-29')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(17, 2021, 1, 4000000, 4000000, '2021-02-26')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(18, 2020, 2, 4200000, 4200000, '2020-08-25')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(19, 2021, 1, 4100000, 4100000, '2021-02-11')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(20, 2021, 1, 4300000, 4300000, '2021-02-06')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(21, 2020, 2, 4400000, 4200000, '2020-08-24')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(22, 2020, 2, 4300000, 4200000, '2020-08-27')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(23, 2020, 2, 4300000, 4300000, '2020-08-18')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(24, 2020, 2, 4300000, 4300000, '2020-08-02')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(25, 2021, 1, 4000000, 4000000, '2021-02-03')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(26, 2021, 1, 4400000, 4400000, '2021-02-03')";
                stmt.executeUpdate(query);
                query = "INSERT INTO TuitionList VALUES(27, 2021, 1, 4300000, 4000000, '2021-02-03')";
                stmt.executeUpdate(query);

                
                query = " INSERT INTO student VALUES (1, '김지수', '군자로', '010-5432-7981', 'email1@naver.com', 901, 601, '000-00000-000');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (2, '박정은', '양도로', '010-1656-5165', 'email2@naver.com', 902, 602, '111-11111-111');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (3, '김가연', '유현로', '010-9413-8641', 'email3@naver.com', 903, 603, '222-22222-222');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (4, '김나연', '강변북로', '010-6845-8521', 'email4@naver.com', 904, 604, '333-33333-333');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (5, '김다연', '광나루로', '010-5346-4513', 'email5@naver.com', 905, 605, '444-44444-444');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (6, '김라연', '구의로', '010-3653-5897', 'email6@naver.com', 906, 606, '555-55555-555');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (7, '김마연', '능동로', '010-8763-1239', 'email7@naver.com', 907, 607, '666-66666-666');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (8, '김바연', '답십리로', '010-6897-4532', 'email8@naver.com', 908, 608, '777-77777-777');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (9, '김사연', '동일로', '010-9846-5126', 'email9@naver.com', 909, 609, '888-88888-888');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (10, '김아연', '뚝섬로', '010-6845-1712', 'email10@naver.com', 910, 610, '999-99999-999');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (11, '김자연', '면목로', '010-7841-2364', 'email11@naver.com', 911, 611, '101-01010-101');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (12, '김차연', '아차산로', '010-8754-3648', 'email12@naver.com', 912, 612, '110-11011-010');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (13, '김카연', '영화사로', '010-9720-4625', 'email13@naver.com', 913, 613, '121-21212-121');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (14, '김타연', '용마선로', '010-6462-3253', 'email14@naver.com', 914, 614, '131-31313-131');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (15, '김파연', '자양로', '010-7648-5512', 'email15@naver.com', 915, 615, '141-41414-141');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (16, '김하연', '천호대로', '010-3216-8794', 'email16@naver.com', 916, 616, '151-51515-151');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (17, '이가람', '가마산로', '010-0646-4651', 'email17@naver.com', 917, 617, '161-61616-161');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (18, '이나람', '경인로', '010-1235-4453', 'email18@naver.com', 918, 618, '171-71717-171');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (19, '이다람', '국회대로', '010-9451-3568', 'email19@naver.com', 919, 619, '181-81818-181');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (20, '이라람', '노량진로', '010-3216-8412', 'email20@naver.com', 920, 620, '191-91919-191');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (21, '이마람', '당산로', '010-6587-1231', 'email21@naver.com', 921, 621, '202-02020-202');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (22, '이바람', '대림로', '010-9851-2356', 'email22@naver.com', 922, 622, '212-12121-212');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (23, '이사람', '도림로', '010-8645-3815', 'email23@naver.com', 923, 623, '232-32323-232');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (24, '이아람', '도신로', '010-2743-0132', 'email24@naver.com', 924, 624, '242-42424-242');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (25, '정재현', '버드나무로', '010-3216-8798', 'email25@naver.com', 925, 625, '252-52525-252');";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (26, '이제노', '가람로', '010-1683-9585', 'email27@naver.com', 901, 601, '262-62626-262')";
                stmt.executeUpdate(query);
                query = " INSERT INTO student VALUES (27, '나재민', '교화로', '010-6436-3487', 'email28@naver.com', 901, 601, '272-72727-272')";
                stmt.executeUpdate(query);
                
                
                query = "INSERT INTO lecturelist VALUES(1, 2011, 2, 601, 701)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(2, 2010, 2, 602, 702)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(3, 2016, 1, 603, 703)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(4, 2015, 2, 604, 704)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(5, 2017, 2, 605, 705)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(6, 2017, 1, 606, 706)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(7, 2017, 1, 607, 707)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(8, 2020, 1, 608, 708)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(9, 2010, 1, 609, 709)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(10, 2013, 2, 610, 710)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(11, 2015, 1, 611, 711)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(12, 2018, 1, 612, 712)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(13, 2018, 1, 613, 713)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(14, 2014, 1, 614, 714)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(15, 2020, 1, 615, 715)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(16, 2018, 2, 616, 716)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(17, 2018, 2, 617, 717)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(18, 2015, 2, 618, 718)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(19, 2020, 1, 619, 719)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(20, 2013, 1, 620, 720)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(21, 2011, 1, 621, 721)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(22, 2011, 1, 622, 722)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(23, 2016, 2, 623, 723)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(24, 2011, 2, 624, 724)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(25, 2019, 1, 625, 725)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(26, 2020, 1, 601, 726)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(27, 2018, 2, 601, 727)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(28, 2020, 1, 601, 728)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(29, 2020, 1, 601, 729)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(30, 2020, 1, 601, 730)";
                stmt.executeUpdate(query);
                query = "INSERT INTO lecturelist VALUES(31, 2016, 1, 626, 731)";
                stmt.executeUpdate(query);
                
                
                
                query = "INSERT INTO Lecture VALUES (701, 001, 601, '국어의미론', '월요일', 1, 2, 1, 901, '광101')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (702, 001, 602, '중국어입문', '월요일', 5, 2, 1, 902, '광102')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (703, 001, 603, '빅데이터론', '월요일', 9, 2, 1, 903, '광103')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (704, 001, 604, '스타마케팅', '월요일', 13, 2, 1, 904, '광104')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (705, 001, 605, '지방행정론', '월요일', 17, 2, 1, 905, '광105')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (706, 001, 606, '공업수학', '화요일', 1, 2, 1, 906, '광106')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (707, 001, 607, '스토리텔링', '화요일', 5, 2, 1, 907, '광107')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (708, 001, 608, '패션디자인', '화요일', 9, 2, 1, 908, '광108')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (709, 001, 609, '기초소묘', '화요일', 13, 2, 1, 909, '광109')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (710, 001, 610, '현대물리학및실험', '화요일', 17, 2, 1, 910, '광110')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (711, 001, 611, '내진설계', '수요일', 1, 2, 1, 911, '광111')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (712, 001, 612, '공간과사회', '수요일', 5, 2, 1, 912, '광112')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (713, 001, 613, '고체역학', '수요일', 9, 2, 1, 913, '광113')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (714, 001, 614, '열역학', '수요일', 13, 2, 1, 914, '광114')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (715, 001, 615, '바이오재료공학', '수요일', 17, 2, 1, 915, '광115')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (716, 001, 616, '디지털논리회로', '목요일', 1, 2, 1, 916, '광116')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (717, 001, 617, '통신이론', '목요일', 5, 2, 1, 917, '광117')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (718, 001, 618, '외식산업경영론', '목요일', 9, 2, 1, 918, '광118')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (719, 001, 619, '주류학', '목요일', 13, 2, 1, 919, '광119')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (720, 001, 620, '관광자원론', '목요일', 17, 2, 1, 920, '광120')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (721, 001, 621, '생물정보학', '금요일', 1, 2, 1, 921, '광121')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (722, 001, 622, '유전학', '금요일', 5, 2, 1, 922, '광122')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (723, 001, 623, '운영체제', '금요일', 9, 2, 1, 923, '광123')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (724, 001, 624, '수치해석', '금요일', 13, 2, 1, 924, '광124')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (725, 001, 625, '무선통신', '금요일', 17, 2, 1, 925, '광125')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (726, 001, 601, '인적자원개발론', '금요일', 10, 3, 2, 901, '광111')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (727, 001, 601, '웹프로그래밍', '월요일', 5, 3, 3, 901, '광105')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (728, 001, 601, '운영체제', '목요일', 7, 3, 2, 901, '광103')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (729, 001, 601, '신호및시스템', '수요일', 6, 1, 2, 901, '광107')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (730, 001, 601, 'C#프로그래밍', '금요일', 4, 2, 2, 901, '광120')";
                stmt.executeUpdate(query);
                query = "INSERT INTO Lecture VALUES (731, 001, 626, 'C++프로그래밍', '수요일', 4, 2, 2, 901, '광109')";
                stmt.executeUpdate(query);
                

                
                query = "INSERT INTO courselist VALUES (1, 1, 701, 601, 100, 90, 90, 95, 375, 'A', 2011, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (2, 2, 702, 602, 100, 69, 9, 85, 292, 'C', 2010, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (3, 3, 703, 603, 66, 3, 1, 20, 90, 'D', 2016, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (4, 4, 704, 604, 13, 77, 51, 46, 187, 'C', 2015, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (5, 5, 705, 605, 49, 0, 64, 97, 210, 'C', 2017, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (6, 6, 706, 606, 22, 38, 51, 35, 146, 'D', 2017, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (7, 7, 707, 607, 69, 35, 23, 45, 172, 'C', 2017, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (8, 8, 708, 608, 9, 68, 28, 21, 126, 'D', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (9, 9, 709, 609, 66, 73, 76, 30, 245, 'B', 2010, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (10, 10, 710, 610, 42, 9, 58, 62, 171, 'C', 2013, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (11, 11, 711, 611, 6, 72, 13, 50, 141, 'D', 2015, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (12, 12, 712, 612, 73, 10, 55, 6, 144, 'D', 2018, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (13, 13, 713, 613, 82, 73, 61, 2, 218, 'C', 2018, 1)";
              stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (14, 14, 714, 614, 12, 90, 87, 59, 248, 'B', 2014, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (15, 15, 715, 615, 89, 37, 1, 94, 221, 'C', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (16, 16, 716, 616, 18, 96, 57, 48, 219, 'C', 2018, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (17, 17, 717, 617, 45, 43, 11, 16, 115, 'D', 2018, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (18, 18, 718, 618, 3, 50, 21, 30, 104, 'D', 2015, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (19, 19, 719, 619, 21, 66, 4, 67, 158, 'D', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (20, 20, 720, 620, 56, 1, 31, 56, 144, 'D', 2013, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (21, 21, 721, 621, 44, 23, 71, 84, 222, 'C', 2011, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (22, 22, 722, 622, 11, 100, 94, 66, 271, 'B', 2011, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (23, 23, 723, 623, 22, 92, 21, 82, 217, 'C', 2016, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (24, 24, 724, 624, 27, 82, 88, 96, 293, 'B', 2011, 2)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (25, 25, 725, 625, 33, 93, 18, 70, 214, 'C', 2019, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (26, 1, 707, 601, 80, 70, 65, 90, 305, 'A', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (27, 1, 726, 601, 23, 33, 29, 40, 125, 'D', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (28, 1, 728, 601, 50, 60, 55, 70, 235, 'B', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (29, 11, 726, 601, 17, 60, 15, 75, 167, 'D', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (30, 15, 726, 601, 18, 55, 30, 90, 193, 'C', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (31, 16, 728, 601, 80, 50, 30, 80, 185, 'C', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (32, 17, 728, 601, 65, 69, 35, 96, 265, 'B', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (33, 22, 729, 601, 55, 90, 30, 70, 245, 'C', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (34, 26, 729, 601, 98, 62, 22, 83, 265, 'B', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (35, 26, 729, 601, 45, 46, 22, 84, 197, 'C', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (36, 27, 730, 601, 86, 96, 30, 50, 262, 'C', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (37, 27, 730, 601, 77, 46, 32, 78, 233, 'C', 2020, 1)";
             stmt.executeUpdate(query);
             query = "INSERT INTO courselist VALUES (38, 27, 730, 601, 36, 69, 60, 78, 243, 'C', 2020, 1)";
             stmt.executeUpdate(query);
               
                
                query = "INSERT INTO club VALUES (401, '스물', 2, 1, 601, '충101')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (402, '마스터', 2, 2, 602, '충102')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (403, 'SAI', 2, 3, 603, '충103')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (404, 'UNSA', 2, 4, 604, '충104')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (405, '에잇컬쳐', 2, 5, 605, '충105')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (406, '스키부', 2, 6, 606, '충106')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (407, '축구부', 2, 7, 607, '충107')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (408, '야구부', 2, 8, 608, '충108')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (409, '배드민턴부', 2, 9, 609, '충109')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (410, '풋살부', 2, 10, 610, '충110')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (411, '농구부', 2, 11, 611, '충111')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (412, '수영부', 2, 12, 612, '충112')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (413, '보드부', 2, 13, 613, '충113')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (414, '연심', 2, 14, 614, '충114')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (415, '더블랙', 2, 15, 615, '충 115')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (416, '볼링부', 2, 16, 616, '충116')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (417, '태권도부', 2, 17, 617, '충117')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (418, '만들레', 2, 18, 618, '충118')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (419, '포레스트', 2, 19, 619, '충119')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (420, '아벱', 2, 20, 620, '충120')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (421, '한손', 2, 21, 621, '충121')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (422, '시담', 2, 22, 622, '충122')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (423, '대기만성', 2, 23, 623, '충123')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (424, '무붕', 2, 24, 624, '충124')";
                stmt.executeUpdate(query);
                query = "INSERT INTO club VALUES (425, '밤부', 2, 25, 625, '충125')";
                stmt.executeUpdate(query);
                
                
                
                
                
               
                
                
                query = "INSERT INTO Department VALUES (901, '건축학과', '02-556-4633', '동101', 626)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (902, '조경학과', '02-612-6585', '동102', 602)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (903, '항공학과', '02-126-3415', '동103', 603)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (904, '기계공학과', '02-878-6653', '동104', 604)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (905, '금속공학과', '02-745-3105', '동105', 605)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (906, '산업공학과', '02-487-9105', '동106', 606)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (907, '신소재공학과', '02-256-6341', '동107', 607)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (908, '재료공학과', '02-864-5132', '동108', 608)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (909, '전기공학과', '02-386-4131', '동109', 609)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (910, '에너지공학과', '02-762-1895', '동110', 610)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (911, '응용소프트웨어공학과', '02-945-1257', '동111', 611)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (912, '컴퓨터공학과', '02-652-7147', '동112', 612)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (913, '정보통신공학과', '02-839-8810', '동113', 613)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (914, '유아교육학과', '02-014-9865', '동114', 614)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (915, '시각디자인학과', '02-531-6845', '동115', 615)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (916, '패션디자인학과', '02-659-8453', '동116', 616)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (917, '체육학과', '02-546-8651', '동117', 617)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (918, '간호학과', '02-984-5898', '동118', 618)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (919, '치의학과', '02-754-6189', '동119', 619)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (920, '약학과', '02-321-6546', '동120', 620)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (921, '생명과학공학과', '02-656-8798', '동121', 621)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (922, '식품영약학과', '02-654-9821', '동122', 622)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (923, '화학과', '02-129-8795', '동123', 623)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (924, '영어교육학과', '02-089-4786', '동124', 624)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Department VALUES (925, '재활학과', '02-298-4513', '동125', 625)";
                stmt.executeUpdate(query);
                
                
                
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 601, 1)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 602, 2)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 603, 3)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 604, 4)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 605, 5)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 606, 6)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 607, 7)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 608, 8)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 609, 9)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 610, 10)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 611, 11)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 612, 12)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 613, 13)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 614, 14)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 615, 15)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 616, 16)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 617, 17)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 618, 18)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 619, 19)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 620, 20)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 621, 21)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 622, 22)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 623, 23)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 2, 624, 24)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 625, 25)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 601, 26)";
                stmt.executeUpdate(query);
                query = "INSERT INTO AdviseRelation VALUES(2021, 1, 601, 27)";
                stmt.executeUpdate(query);

                
                query = "INSERT INTO clubstudentlist VALUES (401, 1)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (401, 2)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (402, 2)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (402, 3)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (403, 3)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (403, 4)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (404, 4)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (404, 5)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (405, 5)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (405, 6)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (406, 6)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (406, 7)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (407, 7)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (407, 8)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (408, 8)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (408, 9)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (409, 9)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (409, 10)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (410, 10)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (410, 11)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (411, 11)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (411, 12)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (412, 12)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (412, 13)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (413, 13)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (413, 14)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (414, 14)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (414, 15)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (415, 15)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (415, 16)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (416, 16)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (416, 17)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (417, 17)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (417, 18)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (418, 18)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (418, 19)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (419, 19)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (419, 20)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (420, 20)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (420, 21)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (421, 21)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (421, 22)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (422, 22)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (422, 23)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (423, 23)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (423, 24)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (424, 24)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (424, 25)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (425, 25)";
                stmt.executeUpdate(query);
                query = "INSERT INTO clubstudentlist VALUES (425, 1)";
                stmt.executeUpdate(query);
                
                
                query = "INSERT INTO Student_has_Department VALUES (1, 905);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (5, 906);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (9, 915);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (13, 917);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (21, 901);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (1, 901);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (2, 902);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (3, 903);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (4, 904);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (5, 905)";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (6, 906);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (7, 907);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (8, 908);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (9, 909);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (10, 910);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (11, 911);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (12, 912);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (13, 913);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (14, 914);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (15, 915);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (16, 916);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (17, 917);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (18, 918);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (19, 919);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (20, 920);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (21, 921);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (22, 922);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (23, 923);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (24, 924);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (25, 925);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (26, 925);";
                stmt.executeUpdate(query);
                query = "INSERT INTO Student_has_Department VALUES (27, 925);";
                stmt.executeUpdate(query);
                
                
                
                query = "INSERT INTO professor_has_department VALUES (601,901);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (602,902);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (603,903);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (604,904);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (605,905);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (606,906);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (607,907);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (608,908);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (609,909);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (610,910);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (611,911);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (612,912);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (613,913);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (614,914);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (615,915);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (616,916);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (617,917);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (618,918);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (619,919);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (620,920);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (621,921);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (622,922);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (623,923);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (624,924);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (625,901);";
                stmt.executeUpdate(query);
                query = "INSERT INTO professor_has_department VALUES (626,901);";
                stmt.executeUpdate(query);

                
                
                txtStatus.setText("table 초기화 됐음");
            }
            catch (Exception e2) {
                  txtStatus.setText("쿼리 읽기 실패 :" + e2);
            }
         }
         
         //관리자 입력 버튼
         if(e.getSource() == insertBtn) {
            try {
               String txt = txtAdmin.getText();
               
               stmt.executeUpdate(txt);
               
               txtAdmin.setText("");
               
               txtStatus.setText("입력 완료");
            }
            catch (Exception e2) {
                  txtStatus.setText("쿼리 읽기 실패 :" + e2);
            }
         }
         
         if(e.getSource() == adminCancel) {
        	 txtAdmin.setText("");
        	 txtStatus.setText("취소 완료");
         }
         
         //관리자 삭제 버튼
         if(e.getSource() == deleteBtn) {
            try {
               String txt = txtAdmin.getText();
               
               stmt.executeUpdate(txt);
               
               txtAdmin.setText("");
               
               txtStatus.setText("삭제 완료");
            }
            catch (Exception e2) {
                  txtStatus.setText("쿼리 읽기 실패 :" + e2);
            }
         }
         
         //관리자 변경 버튼
         if(e.getSource() == updateBtn) {
            try {
               String txt = txtAdmin.getText();
               
               stmt.executeUpdate(txt);
               
               txtAdmin.setText("");
               
               txtStatus.setText("변경 완료");
            }
            catch (Exception e2) {
                  txtStatus.setText("쿼리 읽기 실패 :" + e2);
            }
         }
         //관리자 전체 테이블 보여주는 버튼
         else if (e.getSource() == selectBtn) {
             
             //case 1
        	 try {
             
             String query = "SELECT * FROM Student ";

               txtResult.setText("");
               txtResult.setText("학생번호           학생이름            학생주소            학생전화번호      학생이메일                                  전공학과           지도교수정보    등록금납부계좌정보\n");
               rs = stmt.executeQuery(query);
               while (rs.next()) {
                  String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                           + rs.getString(5) + "\t" + rs.getInt(6) + "\t" + rs.getInt(7) + "\t" + rs.getString(8) + "\n";
                   txtResult.append(str);
               }
               
              
               
               
               query = "SELECT * FROM Professor ";

               txtResult.append("\n\n교수번호           교수이름            교수주소            교수전화번호      교수이메일\t\t소속학과번호\n");
               rs = stmt.executeQuery(query);
               while (rs.next()) {
                  String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                           + rs.getString(5) + "\t" + rs.getInt(6) + "\n";
                   txtResult.append(str);
               }
               
               
               query = "SELECT * FROM department";
               
               txtResult.append("\n\n학과번호           학과명                 학과전화번호     학과사무실     학과장\n");
               rs = stmt.executeQuery(query);
               while (rs.next()) {
                  String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                           + rs.getInt(5) + "\n";
                   txtResult.append(str);
               }
               
               query = "SELECT * FROM lecture";
               
               txtResult.append("\n\n강좌번호      분반번호         강의하는 교수            강좌이름           강의요일           강의교시           취득학점          강좌시간             개설학과             강의실정보\n");
               rs = stmt.executeQuery(query);
               while (rs.next()) {
                  String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                           + rs.getString(5) + "\t"+ rs.getInt(6) + "\t"+ rs.getInt(7) + "\t"+ rs.getInt(8) + "\t"+ rs.getString(9) + "\t"+ rs.getString(10) + "\n";
                   txtResult.append(str);
               }
               
               query = "SELECT * FROM lecturelist";
               
               txtResult.append("\n\n강의내역번호      강의년도         강의학기            강의교수번호       강좌번호\n");
               rs = stmt.executeQuery(query);
               while (rs.next()) {
                  String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
                           + rs.getInt(5) + "\n";
                   txtResult.append(str);
               }
               
               query = "SELECT * FROM courselist";
               
               txtResult.append("\n\n수강내역번호\t학생번호\t강좌번호\t교수번호\t출석점수\t중간고사점수\t기말고사점수\t기타점수\t총점\t평점\n");
               rs = stmt.executeQuery(query);
               while (rs.next()) {
                  String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
                          + rs.getInt(5) + "\t"+ rs.getInt(6) + "\t" + rs.getInt(7) + "\t" + rs.getInt(8) + "\t" + rs.getInt(9) +
                          "\t" + rs.getString(10) + "\n";
                  txtResult.append(str);
              }
               
               query = "SELECT * FROM club";
               
               txtResult.append("\n\n동아리번호\t동아리이름\t소속학생숫자\t회장학생번호\t동아리지도교수번호\t동아리방\n");
               rs = stmt.executeQuery(query);
               while (rs.next()) {
                  String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
                          + rs.getInt(5) + "\t\t"+ rs.getString(6) + "\n";
                  txtResult.append(str);
              }
              
              query = "SELECT * FROM tuitionlist";
              
              txtResult.append("\n\n학생번호\t등록금납부연도\t등록금납부학기\t등록금총액\t납부총액\t마지막납부일자\n");
              rs = stmt.executeQuery(query);
              while (rs.next()) {
                 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
                          + rs.getInt(5) + "\t"+ rs.getDate(6) + "\n";
                  txtResult.append(str);
              }
              
              query = "SELECT * FROM adviserelation";
              
              txtResult.append("\n\n연도\t학기\t교수번호\t학생번호\n");
              rs = stmt.executeQuery(query);
              while (rs.next()) {
                 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\n";
                  txtResult.append(str);
              }
              
              txtStatus.setText("작업 성공");

         } catch (Exception e2) {
              txtStatus.setText("쿼리 읽기 실패 :" + e2);
         }
         
        
     }
         
     if (e.getSource() == adminClBtn) {
    	 
    	 		NorthPanel.removeAll();
    	 		NorthPanel.revalidate();
    	 		NorthPanel.repaint();
    	 		txtResult.setText("");
    	 		
    	 		CenterPanel.removeAll();
    	 		CenterPanel.add(mainScroll);
    	 		mainScroll.setViewportView(txtResult);
          mainScroll.setPreferredSize(new Dimension(900, 450));
    	 	
       		JLabel txtClID = new JLabel("강좌번호");
       		JLabel txtStudID = new JLabel("학생번호");
       		JLabel txtLecID = new JLabel("강좌번호");
       		JLabel txtProfID = new JLabel("교수번호");
       		JLabel txtYear= new JLabel("년도");
       		JLabel txtSemester = new JLabel("학기");
           
           adminClID = new JTextField(5);
           adminStudID = new JTextField(5);
           adminLecID = new JTextField(5);
           adminProfID = new JTextField(5);
           adminYear = new JTextField(5);
           adminSemester = new JTextField(5);
           
           JButton insertBtn = new JButton("입력");

           
           NorthPanel.add(txtClID);
           NorthPanel.add(adminClID);
           NorthPanel.add(txtStudID);
           NorthPanel.add(adminStudID);
           NorthPanel.add(txtLecID);
           NorthPanel.add(adminLecID);
           NorthPanel.add(txtProfID);
           NorthPanel.add(adminProfID);
           NorthPanel.add(txtYear);
           NorthPanel.add(adminYear);
           NorthPanel.add(txtSemester);
           NorthPanel.add(adminSemester);
           NorthPanel.add(insertBtn);
           
           insertBtn.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent event) {
            	 
            	 String ClID = adminClID.getText();
               String StudID = adminStudID.getText();
               String LecID = adminLecID.getText();
               String ProfID = adminProfID.getText();
               String Year = adminYear.getText();
               String Semester = adminSemester.getText();
               
               if (ClID.equals("") || StudID.equals("") || LecID.equals("") || ProfID.equals("") || Year.equals("") || Semester.equals("")) {
                 txtResult.setText("정보 모두 입력해야 함 !");
               }
               else { // 다 입력 된 경우
              	           	
                  try {
                  	
                  	adminClID.setText("");
                    adminStudID.setText("");
                    adminLecID.setText("");
                    adminProfID.setText("");
                    adminYear.setText("");
                    adminSemester.setText("");
                     
	                  	 String query = "SELECT * FROM tuitionlist WHERE tl_studid = " + StudID;
	                     rs = stmt.executeQuery(query);
	                     
	                     int Goal = 0, Total = 0;
	                     
	                     while (rs.next()) {
	                    	 Goal = rs.getInt(4);
		                     Total = rs.getInt(5);
	                     }
	                     
	                     if (Goal > Total) {
	                    	 txtResult.setText("오류 : 등록금 미납");
	                     }
	                     else {
	                    	 
	                    	 txtResult.setText("");
	                    	 query = "INSERT INTO courselist (cl_id, cl_studid, cl_lecid, cl_profid, cl_year, cl_semester)"
	                    	 		+ " VALUES (" + ClID + ", " + StudID + ", " + LecID + ", " + ProfID + ", " + Year + ", " + Semester + ")";
	                    	 
	                    	 stmt.executeUpdate(query);
	                    	 
	                     }
	                     txtResult.setText("삽입 성공");
                       txtStatus.setText("작업 성공");
                   }
                   catch (Exception e2) {
                        txtStatus.setText("쿼리 읽기 실패 :" + e2);
                   }
               }

             }
             
           });
    
           
       }
     
     // 교수
     if (e.getSource() == profSearch) {
        
        try {
           String year = profYear.getText();
           String sem = profSemester.getText();
          
           CenterPanel.removeAll();
           CenterPanel.add(mainScroll);
           CenterPanel.revalidate();
           
           txtResult.setText("");
           profYear.setText("");
           profSemester.setText("");
         
           if (year != "" && sem != "") {
              txtResult.append("강좌번호\t분반번호\t강의하는 교수\t강좌이름\t강의요일\t강의교시\t취득학점\t강좌시간\t개설학과\t강의실정보\n");
           }
           
           mainScroll.setViewportView(txtResult);
           mainScroll.setPreferredSize(new Dimension(900, 450));
           
           String query = "SELECT lec.lec_id, lec.lec_roomnum, lec.lec_profid, lec.lec_name, lec.lec_day, lec.lec_period, lec.lec_credit, lec.lec_time, lec.lec_deptid\r\n"
                 + ", lec.lec_office\r\n"
                 + "FROM Lecture lec, LectureList ll WHERE ll.ll_year = " + year + " and ll.ll_semester = " + sem + " and lec.lec_id = ll.ll_lecid and ll.ll_profid = 601;";
           
           rs = stmt.executeQuery(query);
           
           JPanel panel = new JPanel();
           
           while (rs.next()) {
                 String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                          + rs.getString(5) + "\t"+ rs.getInt(6) + "\t"+ rs.getInt(7) + "\t"+ rs.getInt(8) + "\t"+ rs.getString(9) + "\t"+ rs.getString(10) + "\n";
                 txtResult.append(str);
                 
                 JButton btn = new JButton(Integer.toString(rs.getInt(1)));
                 
                 btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                       try {
                          JButton get = (JButton)event.getSource();
                          
                          txtResult.setText("");           
                          txtResult.append("<강좌 번호 = " + get.getText() +">\n\n");
                          txtResult.append("학생번호\t학생이름\t학생주소\t학생전화번호\t학생이메일\t\t전공학과\t지도교수정보\t등록금납부계좌정보\n");
                          
                           String query = "select stud.stud_id, stud.stud_name, stud.stud_addr, stud.stud_tel, stud.stud_email, stud.stud_deptid, stud.stud_advisorid, stud.stud_account \r\n"
                                 + "from student stud, courselist cl where stud.stud_id = cl.cl_studid and cl.cl_lecid = " + get.getText();
                           
                           rs = stmt.executeQuery(query);
                           
                             while (rs.next()) {
                                   String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                                            + rs.getString(5) + "\t"+ rs.getString(6) + "\t"+ rs.getInt(7) + "\t"+ rs.getString(8) + "\n";
                                   txtResult.append(str);
                             }
                           
                       }
                       catch (Exception e2) {
                           txtStatus.setText("쿼리 읽기 실패 :" + e2);
                       }
                       
                       
                    }
                 });
                 
                 panel.add(btn);
            }
        
           CenterPanel.add(panel);
           txtStatus.setText("작업 성공");
             
        }
        catch (Exception e2) {
           txtStatus.setText("쿼리 읽기 실패 :" + e2);
        }
           
     }
     
     if (e.getSource() == profStudent) {
        
        try {
           
           CenterPanel.removeAll();
            CenterPanel.add(mainScroll);
            CenterPanel.revalidate();
            
           txtResult.setText("");
           txtResult.setText("학생번호\t학생이름\t학생주소\t학생전화번호\t학생이메일\t\t전공학과\t지도교수정보\t등록금납부계좌정보\n");
           
           String query = "select student.stud_id, student.stud_name, student.stud_addr, student.stud_tel, student.stud_email, student.stud_deptid, student.stud_advisorid, student.stud_account \r\n"
                 + "from adviserelation, student \r\n"
                 + "where adviserelation.ar_profid = 601 and adviserelation.ar_studid = student.stud_id";
           rs = stmt.executeQuery(query);
           
           JPanel panel = new JPanel();

           while (rs.next()) {
                  String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                          + rs.getString(5) + "\t"+ rs.getString(6) + "\t"+ rs.getInt(7) + "\t"+ rs.getString(8) + "\n";
                 txtResult.append(str);
                 
                 JButton btn = new JButton(Integer.toString(rs.getInt(1)));
                 
                 btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                       try {
                          JButton get = (JButton)event.getSource();
                          
                          txtResult.setText("");
                          txtResult.append("<학생 번호 = " + get.getText() +">\n\n");
                          txtResult.append("출석점수\t중간고사점수\t기말고사점수\t기타점수\t총점\t평점\n");
                          
                           String query = "select cl_attend, cl_midterm, cl_final, cl_etc, cl_total, cl_grade \r\n"
                                 + "from courselist \r\n"
                                 + "where cl_studid = " + get.getText();
                           
                           rs = stmt.executeQuery(query);
                           
                             while (rs.next()) {
                                   String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
                                            + rs.getInt(5) + "\t"+ rs.getString(6) + "\n";
                                   txtResult.append(str);
                             }
                           
                       }
                       catch (Exception e2) {
                           txtStatus.setText("쿼리 읽기 실패 :" + e2);
                       }
                       
                       
                    }
                 });
                 
                 panel.add(btn);
                                   
            }
       
           
           
           mainScroll.setViewportView(txtResult);
           mainScroll.setPreferredSize(new Dimension(900, 450));
           
           
           
           CenterPanel.add(panel);
           txtStatus.setText("작업 성공");
        }
        catch (Exception e2) {
           txtStatus.setText("쿼리 읽기 실패 :" + e2);
        }
           
     }
     
     if (e.getSource() == profCancel) {
    	 profYear.setText("");
    	 profSemester.setText("");
    	 
    	 txtStatus.setText("취소 완료");
     }
     
     if (e.getSource() == profCancelGrade) {
    	 profAttend.setText("");
    	 profMidterm.setText("");
    	 profFinal.setText("");
    	 profEtc.setText("");
    	 profLecID.setText("");
    	 profStudID.setText("");
    	 
    	 txtStatus.setText("취소 완료");
     }
     
     if (e.getSource() == profDepart) {
        
        try {
           
           CenterPanel.removeAll();
           CenterPanel.add(mainScroll);
           CenterPanel.revalidate();
           CenterPanel.repaint();
           
           mainScroll.setViewportView(txtResult);
           
           txtResult.setText("");
             txtResult.setText("학과번호\t학과명\t학과전화번호\t학과사무실\t학과장번호\n");
             
             String query = "select dept.dept_id, dept.dept_name, dept.dept_tel, dept.dept_office, dept.Professor_prof_id \r\n"
                   + "from department dept, professor prof \r\n"
                   + "where dept.dept_id = prof.prof_deptid and prof.prof_id = 601";
             rs = stmt.executeQuery(query);
             
             while (rs.next()) {
                 String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                          + rs.getInt(5) + "\n";
                  txtResult.append(str);
              }
             
             txtStatus.setText("작업 성공");
        }
        catch (Exception e2) {
             txtStatus.setText("쿼리 읽기 실패 :" + e2);
         }
        
     }
     
     if (e.getSource() == profTable) {
        
        try {
           CenterPanel.removeAll();
             CenterPanel.add(mainScroll);
             CenterPanel.revalidate();
             CenterPanel.repaint();
             
           String header[] = {"교시", "월", "화", "수", "목", "금", "토"};
           
           String contents[][] = {
              {"<html>1교시<br>8:00 ~ 8:30</html>","","","","","",""},
              {"<html>2교시<br>8:30 ~ 9:00</html>","","","","","",""},
              {"<html>3교시<br>9:00 ~ 9:30</html>","","","","","",""},
              {"<html>4교시<br>9:30 ~ 10:00</html>","","","","","<html>C#프로그래밍<br>광120</html>",""},
              {"<html>5교시<br>10:00 ~ 10:30</html>","","","","","<html>C#프로그래밍<br>광120</html>",""},
              {"<html>6교시<br>10:30 ~ 11:00</html>","","","<html>신호및시스템<br>광107</html>","","<html>C#프로그래밍<br>광120</html>",""},
              {"<html>7교시<br>11:00 ~ 11:30</html>","","","<html>신호및시스템<br>광107</html>","<html>운영체제<br>광103</html>","<html>C#프로그래밍<br>광120</html>",""},
              {"<html>8교시<br>11:30 ~ 12:00</html>","","","<html>신호및시스템<br>광107</html>","<html>운영체제<br>광103</html>","",""},
              {"<html>9교시<br>12:00 ~ 12:30</html>","","","<html>신호및시스템<br>광107</html>","<html>운영체제<br>광103</html>","",""},
              {"<html>10교시<br>12:30 ~ 13:00</html>","","","","<html>운영체제<br>광103</html>","<html>인적자원개발론<br>광111</html>",""},
              {"<html>11교시<br>13:00 ~ 13:30</html>","","","","","<html>인적자원개발론<br>광111</html>",""},
              {"<html>12교시<br>13:30 ~ 14:00</html>","","","","","<html>인적자원개발론<br>광111</html>",""},
              {"<html>13교시<br>14:00 ~ 14:30</html>","","","","","<html>인적자원개발론<br>광111</html>",""},
              {"<html>14교시<br>14:30 ~ 15:00</html>","","","","","",""},
              {"<html>15교시<br>15:00 ~ 15:30</html>","","","","","",""},
              {"<html>16교시<br>15:30 ~ 16:00</html>","","","","","",""},
              {"<html>17교시<br>16:00 ~ 16:30</html>","","","","","",""},
              {"<html>18교시<br>16:30 ~ 17:00</html>","","","","","",""},
              {"<html>19교시<br>17:00 ~ 17:30</html>","","","","","",""},
              {"<html>20교시<br>17:30 ~ 18:00</html>","","","","","",""}
           };
           
           DefaultTableModel dtm = new DefaultTableModel(contents, header){  //셀 수정 못하게 하는 부분
              public boolean isCellEditable(int contents, int header){
                 return false;
              }
           };

           
           JTable table = new JTable(dtm);
           table.getColumn("교시").setPreferredWidth(200);
           table.getColumn("월").setPreferredWidth(150);
           table.getColumn("화").setPreferredWidth(150);
           table.getColumn("수").setPreferredWidth(150);
           table.getColumn("목").setPreferredWidth(150);
           table.getColumn("금").setPreferredWidth(150);
           table.getColumn("토").setPreferredWidth(150);
           
           table.setRowHeight(50);
           
           mainScroll.setPreferredSize(new Dimension(900, 450));
           mainScroll.setViewportView(table);
           
           txtStatus.setText("작업 성공");
        }
        catch (Exception e2) {
          txtStatus.setText("쿼리 읽기 실패 :" + e2);
       }
          
    }
     
     if (e.getSource() == profInsertGrade) {
        
        String StudID = profStudID.getText();
        String LecID = profLecID.getText();
        String Attend = profAttend.getText();
        String Midterm = profMidterm.getText();
        String Final = profFinal.getText();
        String Etc = profEtc.getText();
        
        if (Attend.equals("") || Midterm.equals("") || Final.equals("") || Etc.equals("")) {
          txtResult.setText("성적 모두 입력해야 함 !");
       }
       else {
          profStudID.setText("");
          profLecID.setText("");
          profAttend.setText("");
           profMidterm.setText("");
           profFinal.setText("");
           profEtc.setText("");
           
           int sum = (Integer.parseInt(Attend) + Integer.parseInt(Midterm) + Integer.parseInt(Final) + Integer.parseInt(Etc));
           
           String Grade;
           
           if (sum/4 >= 90) Grade = "A";
           else if (sum/4 >= 80) Grade = "B";
           else if (sum/4 >= 70) Grade = "C";
           else if (sum/4 >= 60) Grade = "D";
           else Grade = "F";
        
           String sum2 = Integer.toString(sum);
           
           try {
              
              String query = "UPDATE courselist\r\n"
                    + "SET cl_attend =" + Attend +", cl_midterm =" + Midterm +", cl_final =" + Final + ", cl_etc =" + Etc + ", cl_total =" + sum2 + ", cl_grade ='" + Grade + "'\r\n"
                    + "WHERE cl_lecid =" + LecID + " and cl_studid =" + StudID;
              
                       
               stmt.executeUpdate(query);
               
               txtStatus.setText("작업 성공");

            }
            catch (Exception e2) {
                 txtStatus.setText("쿼리 읽기 실패 :" + e2);
             }
           
       }
        
        
     }
     
    //학생
     if (e.getSource() == studSearch) {
         
         try {
            
             CenterPanel.removeAll();
                CenterPanel.add(mainScroll);
                CenterPanel.revalidate();
                CenterPanel.repaint();
                
                
            String year = studYear.getText();
            String sem = studSemester.getText();
            
            txtResult.setText("");
             studYear.setText("");
              studSemester.setText("");
            
            if (year != "" && sem != "") {
                    txtResult.append("강좌번호\t분반번호\t강의하는 교수\t강좌이름\t강의요일\t강의교시\t취득학점\t강좌시간\t개설학과\t강의실정보\n");
              }

              mainScroll.setViewportView(txtResult);
              mainScroll.setPreferredSize(new Dimension(900, 450));

        
            
            String query = "SELECT lec.lec_id, lec.lec_roomnum, lec.lec_profid, lec.lec_name, lec.lec_day, lec.lec_period, lec.lec_credit, lec.lec_time, lec.lec_deptid\r\n"
                  + ", lec.lec_office\r\n"
                  + "FROM Lecture lec, courseList cl WHERE cl.cl_year = " + year + " and cl.cl_semester = " + sem + " and lec.lec_id = cl.cl_lecid and cl.cl_studid = 1;";
            
            rs = stmt.executeQuery(query);
            
            
            while (rs.next()) {
               
                   String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                           + rs.getString(5) + "\t"+ rs.getInt(6) + "\t"+ rs.getInt(7) + "\t"+ rs.getInt(8) + "\t"+ rs.getString(9) + "\t"+ rs.getString(10) + "\n";
                  txtResult.append(str);

             }

            mainScroll.setViewportView(txtResult);
              mainScroll.setPreferredSize(new Dimension(900, 450));
              
              txtStatus.setText("작업 성공");
         }
         catch (Exception e2) {
            txtStatus.setText("쿼리 읽기 실패 :" + e2);
         }
         
      }
     
      if (e.getSource() == studCancel) {
      	studYear.setText("");
      	studSemester.setText("");
      	
      	txtStatus.setText("취소 완료");
      }
     //학생 수업시간표
      if (e.getSource() == studTable) {
         
         try {
            
            CenterPanel.removeAll();
            CenterPanel.add(mainScroll);
            CenterPanel.revalidate();
            CenterPanel.repaint();
 
                
            String header[] = {"교시", "월", "화", "수", "목", "금", "토"};
            
            String contents[][] = {
                  {"<html>1교시<br>8:00 ~ 8:30</html>","","","","","",""},
                  {"<html>2교시<br>8:30 ~ 9:00</html>","","","","","",""},
                  {"<html>3교시<br>9:00 ~ 9:30</html>","","","","","",""},
                  {"<html>4교시<br>9:30 ~ 10:00</html>","","","","","",""},
                  {"<html>5교시<br>10:00 ~ 10:30</html>","","<html>스토리텔링<br>광107</html>","","","",""},
                  {"<html>6교시<br>10:30 ~ 11:00</html>","","<html>스토리텔링<br>광107</html>","","","",""},
                  {"<html>7교시<br>11:00 ~ 11:30</html>","","","","","<html>운영체제<br>광103</html>",""},
                  {"<html>8교시<br>11:30 ~ 12:00</html>","","","","","<html>운영체제<br>광103</html>",""},
                  {"<html>9교시<br>12:00 ~ 12:30</html>","","","","","<html>운영체제<br>광103</html>",""},
                  {"<html>10교시<br>12:30 ~ 13:00</html>","","","","","<html>운영체제<br>광103</html>","<html>인적자원개발론<br>광111</html>"},
                  {"<html>11교시<br>13:00 ~ 13:30</html>","","","","","","<html>인적자원개발론<br>광111</html>"},
                  {"<html>12교시<br>13:30 ~ 14:00</html>","","","","","","<html>인적자원개발론<br>광111</html>"},
                  {"<html>13교시<br>14:00 ~ 14:30</html>","","","","","","<html>인적자원개발론<br>광111</html>"},
                  {"<html>14교시<br>14:30 ~ 15:00</html>","","","","","",""},
                  {"<html>15교시<br>15:00 ~ 15:30</html>","","","","","",""},
                  {"<html>16교시<br>15:30 ~ 16:00</html>","","","","","",""},
                  {"<html>17교시<br>16:00 ~ 16:30</html>","","","","","",""},
                  {"<html>18교시<br>16:30 ~ 17:00</html>","","","","","",""},
                  {"<html>19교시<br>17:00 ~ 17:30</html>","","","","","",""},
                  {"<html>20교시<br>17:30 ~ 18:00</html>","","","","","",""}
            };
            
            DefaultTableModel dtm = new DefaultTableModel(contents, header){  //셀 수정 못하게 하는 부분
               public boolean isCellEditable(int contents, int header){
                  return false;
               }
            };

            
            JTable table = new JTable(dtm);
            table.getColumn("교시").setPreferredWidth(200);
            table.getColumn("월").setPreferredWidth(150);
            table.getColumn("화").setPreferredWidth(150);
            table.getColumn("수").setPreferredWidth(150);
            table.getColumn("목").setPreferredWidth(150);
            table.getColumn("금").setPreferredWidth(150);
            table.getColumn("토").setPreferredWidth(150);
            
            table.setRowHeight(50);
            
            
            mainScroll.setViewportView(table);
              mainScroll.setPreferredSize(new Dimension(900, 450));
              txtStatus.setText("작업 성공");
         }
         catch (Exception e2) {
            txtStatus.setText("쿼리 읽기 실패 :" + e2);
         }
            
      }
      
      if (e.getSource() == studClub) {
         try {
            
             CenterPanel.removeAll();
             CenterPanel.add(mainScroll);
             CenterPanel.revalidate();
             CenterPanel.repaint();
            
            String query = "select * from club club, clubstudentlist csl, student stu\r\n"
                     + "where club.club_id = csl.Club_club_id and stu.stud_id = csl.Student_stud_id and stu.stud_id = 1";
            
            rs = stmt.executeQuery(query);
         
            txtResult.setText("");
            txtResult.append("동아리번호\t동아리이름\t소속학생숫자\t회장학생번호\t동아리지도교수번호\t동아리방\n");
          
            
               while (rs.next()) {
                   String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
                           + rs.getInt(5) + "\t\t"+ rs.getString(6) + "\n";
                  txtResult.append(str);
              }
            
            
            //회장이면
               
            query = "select * from student\r\n"
            		+ "where stud_id in (select Student_stud_id from clubstudentlist\r\n"
            		+ "where clubstudentlist.Club_club_id = (select club.club_id from club club, clubstudentlist csl, student stu\r\n"
            		+ "where club.club_id = csl.Club_club_id and stu.stud_id = csl.Student_stud_id and stu.stud_id = 1 and stu.stud_id = club.club_masterid));";
       
            rs = stmt.executeQuery(query);
           
            txtResult.append("\n\n학생번호\t학생이름\t학생주소\t학생전화번호\t학생이메일\t\t전공학과번호\t지도교수번호\t등록금납부계좌정보\n");
     
            while (rs.next()) {
              String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                      + rs.getString(5) + "\t"+ rs.getString(6) +  "\t" +rs.getInt(7) +  "\t" +rs.getString(8) + "\n";
             txtResult.append(str);
         }
       
          while (rs.next()) {
              String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t"
                      + rs.getInt(5) + "\t\t"+ rs.getString(6) + "\n";
             txtResult.append(str);
         }
       
       mainScroll.setViewportView(txtResult);
            
            
            txtStatus.setText("작업 성공");
         }
         catch (Exception e2) {
            txtStatus.setText("쿼리 읽기 실패 :" + e2);
         }
         

      }
      
      if (e.getSource() == studGradeCard) {
      	
      	try {
      		CenterPanel.removeAll();
          CenterPanel.add(mainScroll);
          CenterPanel.revalidate();
          CenterPanel.repaint();
          txtResult.setText("");
          
          String query = "select cl.cl_lecid, lec.lec_name, lec.lec_credit, cl.cl_grade "
          		+ "from courselist cl, lecture lec "
          		+ "where cl.cl_lecid = lec.lec_id and cl.cl_studid = 1;";
          
          rs = stmt.executeQuery(query);
          
          txtResult.setText("");
          txtResult.append("강좌번호\t강좌이름\t취득학점\t평점\n");
        
          int sum = 0;
          int cnt = 0;
          
          while (rs.next()) {
          	cnt++;
          	
            String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\n";
            txtResult.append(str);
            
            
            if (rs.getString(4).equals("A")) sum += 4;
          	if (rs.getString(4).equals("B")) sum += 3;
          	if (rs.getString(4).equals("C")) sum += 2;
          	if (rs.getString(4).equals("D")) 
          		sum += 1;
          }
          
          double GPA = (double)sum/cnt;
          
          
          String str = "\nGPA = " + Double.toString(GPA);
          txtResult.append(str);
          
          mainScroll.setViewportView(txtResult);
          txtStatus.setText("작업 성공");
      	}
      	catch (Exception e2) {
          txtStatus.setText("쿼리 읽기 실패 :" + e2);
       }
            
      }

    
 }
   
 catch (Exception e2) {
    txtStatus.setText("쿼리 읽기 실패 :" + e2);
    }
 
}

public static void main(String[] args) {
 project BLS = new project();
 
 //BLS.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 //BLS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
 BLS.addWindowListener(new WindowAdapter() {
    public void windowClosing(WindowEvent we) {
     try {
        con.close();
     } catch (Exception e4) {    }
     System.out.println("프로그램 완전 종료!");
     System.exit(0);
    }
  });
}
}
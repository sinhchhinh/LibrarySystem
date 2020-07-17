import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * The Admin Menu display user's detail, books, issued books, add books, return books
 * add user 
 * @author sinhchhinh
 *
 */
public class AdminMenu {
  JFrame adminMenu   = new JFrame ("Admin Functions"); 

  public AdminMenu () {
    setUI ();
  }
  public void setUI () {

    DBConnect conn = DBConnect.getConnect();


    JButton viewBttn = new JButton("View Books"); // Creating instance of JButton to view Books
    viewBttn.setBounds(20, 20, 120, 25);

    JButton usrBttn=new JButton("View Users");//creating instance of JButton to view users
    usrBttn.setBounds(150,20,120,25);//x axis, y axis, width, height 

    JButton issued_but=new JButton("View Issued Books");//creating instance of JButton to view the issued books
    issued_but.setBounds(280,20,160,25);//x axis, y axis, width, height 

    JButton addUsrBttn = new JButton("Add User"); //creating instance of JButton to add users
    addUsrBttn.setBounds(20,60,120,25); //set dimensions for button

    /**
     * Clear the library 
     */
    JButton createBttn = new JButton ("Create/Reset");    
    createBttn.setBounds(450,60,120,25);
    createBttn.addActionListener(new ActionListener () {
      @Override
      public void actionPerformed (ActionEvent e) {
       String deleteUsr = "DELETE FROM USERS";
       conn.executeUpdateSQL(deleteUsr);
       
       String deleteIssue = "DELETE FROM ISSUED";
       conn.executeUpdateSQL(deleteIssue);
       
       String deleteBooks = "DELETE FROM BOOKS";
       conn.executeUpdateSQL(deleteBooks);

        JOptionPane.showMessageDialog(null, "Database Created/Reset");
      }
    });

    //Button to display all the books 
    viewBttn.addActionListener(new ActionListener () {
      @Override 
      public void actionPerformed (ActionEvent e){
        JFrame bookFrame = new JFrame ("Books Avaiable");
        JTable bookList = new JTable ();
        String sql = "SELECT * FROM books";
        List<String []> bookListResult = conn.selectSQL(sql);
        int col = 4;
        String[] colNameList = {"Book ID", "Book Name", "Genre", "Price"};
        TableModelMold.setTableModel(bookList, bookListResult, col, colNameList);       
        bookList.setPreferredSize(new Dimension(500, 500));

        JScrollPane scrollPane = new JScrollPane(bookList);

        bookFrame.add(scrollPane);
        bookFrame.setSize(800,400);
        bookFrame.setVisible(true);
        bookFrame.setLocationRelativeTo(null);
      }
    });

    //Button to display all the users
    usrBttn.addActionListener(new ActionListener() { //Perform action on click button
      public void actionPerformed(ActionEvent e){

        JFrame usrListFrame = new JFrame("Users List"); 
        JTable usrTable = new JTable ();
        String sql = "SELECT * FROM users";
        List<String []> usrListResult = conn.selectSQL(sql);
        int resultCol = 4;
        String[] colNameList = {"User ID", "Username", "Password", "Admin"};
        TableModelMold.setTableModel(usrTable, usrListResult,resultCol ,colNameList );
        JScrollPane scrollPane = new JScrollPane(usrTable);
       
        usrListFrame.add(scrollPane); //add scrollpane
        usrListFrame.setSize(800, 400); //set size for frame
        usrListFrame.setVisible(true);
        usrListFrame.setLocationRelativeTo(null);                

      }
    } );  

    //Button to display issued books
    issued_but.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){

        JFrame usrListFrame = new JFrame("Users List");
        String sql = "SELECT * FROM ISSUED";
        JTable bookListTable= new JTable();
        List<String []> result = conn.selectSQL(sql);
        System.out.println("Size of issued book + "+ result.size());
        int eachCol = 7;
        String[] colNameList = {"IID", "User ID", "Book ID", "Issued Date", "Returned Date"  , "Period", "Fine"};
        bookListTable.setPreferredSize(new Dimension(500, 500));

        TableModelMold.setTableModel(bookListTable, result, eachCol,colNameList );
        JScrollPane scrollPane = new JScrollPane(bookListTable);
        usrListFrame.add(scrollPane);
        usrListFrame.setSize(800, 400);
        usrListFrame.setVisible(true);
        usrListFrame.setLocationRelativeTo(null);
      }
    });

    //Adding a new User
    addUsrBttn.addActionListener(new ActionListener () {

      @Override
      public void actionPerformed(ActionEvent e) {

        JFrame usrDetailFrame = new JFrame("Enter User Details"); //Frame to enter user details
        //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create label 
        JLabel usrNameLbel,pwdLabel;  
        usrNameLbel =new JLabel("Username");  //label 1 for username
        usrNameLbel.setBounds(30,15, 100,30); 


        pwdLabel = new JLabel("Password");  //label 2 for password
        pwdLabel.setBounds(30,50, 100,30); 

        //set text field for username 
        JTextField usrNameTextField = new JTextField();
        usrNameTextField.setBounds(110, 15, 200, 30);

        //set text field for password
        JTextField pwdTextField = new JTextField();
        pwdTextField.setBounds(110, 50, 200, 30);

        //set radio button for admin
        JRadioButton adminOption = new JRadioButton("Admin");
        adminOption.setBounds(55, 80, 200,30);

        //set radio button for user
        JRadioButton usrOption = new JRadioButton("User");
        usrOption.setBounds(130, 80, 200,30);

        //add radio buttons
        //add radio buttons
        ButtonGroup bg = new ButtonGroup();    
        bg.add(adminOption);
        bg.add(usrOption); 


        //Creating instance of JButton for Create 
        JButton createBttn =new JButton("Create");//creating instance of JButton for Create 
        createBttn.setBounds(130,130,80,25);//x axis, y axis, width, height 
        createBttn.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent e){

            String usrname = usrNameTextField.getText();
            String pwd = pwdTextField.getText();
            Boolean admin = false;

            if(adminOption.isSelected()) {
              admin=true;
            }

            String updateStr = "INSERT INTO USERS(USERNAME, PASSWORD, ADMIN ) VALUES ('"+usrname+"','"+pwd+"',"+admin+")";
            conn.executeUpdateSQL(updateStr);
            JOptionPane.showMessageDialog(null,"User added!");
            usrDetailFrame.dispose();
          }
        }); //End of createBttn actionPerformed

        usrDetailFrame.add(createBttn);
        usrDetailFrame.add(usrOption);
        usrDetailFrame.add(adminOption);
        usrDetailFrame.add(usrNameLbel);
        usrDetailFrame.add(pwdLabel);
        usrDetailFrame.add(usrNameTextField);
        usrDetailFrame.add(pwdTextField);
        usrDetailFrame.setSize(350,200);//400 width and 500 height  
        usrDetailFrame.setLayout(null);//using no layout managers  
        usrDetailFrame.setVisible(true);//making the frame visible 
        usrDetailFrame.setLocationRelativeTo(null);

      }
    });


    //Adding a new book
    JButton addBook = new JButton("Add Book"); //creating instance of JButton for adding books
    addBook.setBounds(150,60,120,25); 

    addBook.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        //set frame wot enter book details
        JFrame bookDetailFrame = new JFrame("Enter Book Details");
        //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set labels
        JLabel bookNameLabel,genreLabel,priceLabel;  
        bookNameLabel = new JLabel("Book Name");  //lebel 1 for book name
        bookNameLabel.setBounds(30,15, 100,30); 

        genreLabel = new JLabel("Genre");  //label 2 for genre
        genreLabel.setBounds(30,53, 100,30); 

        priceLabel = new JLabel("Price");  //label 2 for price
        priceLabel.setBounds(30,90, 100,30); 

        //set text field for book name
        JTextField bnameTextField = new JTextField();
        bnameTextField.setBounds(110, 15, 200, 30);

        //set text field for genre 
        JTextField genreTextF = new JTextField();
        genreTextF.setBounds(110, 53, 200, 30);

        //set text field for price
        JTextField priceTF=new JTextField();
        priceTF.setBounds(110, 90, 200, 30);


        JButton create_but=new JButton("Submit");//creating instance of JButton to submit details  
        create_but.setBounds(130,130,80,25);//x axis, y axis, width, height 
        create_but.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent e){
            // assign the book name, genre, price
            String bname = bnameTextField.getText();
            String genre = genreTextF.getText();
            String price = priceTF.getText();

            //convert price of integer to int
            int price_int = Integer.parseInt(price);
            String sql = "INSERT INTO BOOKS(BNAME,GENRE,PRINCE) VALUES ('"+bname+"','"+genre+"',"+price_int+")";
            conn.executeUpdateSQL(sql);
            JOptionPane.showMessageDialog(null,"Book added!");
            bookDetailFrame.dispose();                                     

          }
        });
        bookDetailFrame.add(bookNameLabel);
        bookDetailFrame.add(create_but);
        bookDetailFrame.add(genreLabel);
        bookDetailFrame.add(priceLabel);
        bookDetailFrame.add(bnameTextField);
        bookDetailFrame.add(genreTextF);
        bookDetailFrame.add(priceTF);
        bookDetailFrame.setSize(350,200);//400 width and 500 height  
        bookDetailFrame.setLayout(null);//using no layout managers  
        bookDetailFrame.setVisible(true);//making the frame visible 
        bookDetailFrame.setLocationRelativeTo(null);                
      }});

    //Issued book 
    JButton issueBookBttn = new JButton("Issue Book"); //creating instance of JButton to issue books
    issueBookBttn.setBounds(450,20,120,25); 

    issueBookBttn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        //enter details
        JFrame issueBDetailFrame = new JFrame("Enter Details");
        //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //create labels
        JLabel bookIDLbel,usrIDLbel,periodLbel,issueDateLbel;  
        bookIDLbel = new JLabel("Book ID(BID)");  // Label 1 for Book ID
        bookIDLbel.setBounds(30,15, 100,30); 

        usrIDLbel = new JLabel("User ID(UID)");  //Label 2 for user ID
        usrIDLbel.setBounds(30,53, 100,30); 

        periodLbel = new JLabel("Period(days)");  //Label 3 for period
        periodLbel.setBounds(30,90, 100,30); 

        issueDateLbel = new JLabel("Issued Date(DD-MM-YYYY)");  //Label 4 for issue date
        issueDateLbel.setBounds(30,127, 200,30); 

        JTextField F_bid = new JTextField();
        F_bid.setBounds(110, 15, 200, 30);

        JTextField F_uid=new JTextField();
        F_uid.setBounds(110, 53, 200, 30);

        JTextField F_period=new JTextField();
        F_period.setBounds(110, 90, 200, 30);

        JTextField F_issue=new JTextField();
        F_issue.setBounds(180, 130, 130, 30);   


        JButton submitBttn = new JButton("Submit");//creating instance of JButton  
        submitBttn.setBounds(130,170,80,25);//x axis, y axis, width, height 
        submitBttn.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent e){

            String uid = F_uid.getText();
            String bid = F_bid.getText();
            String period = F_period.getText();
            String issued_date = F_issue.getText();

            int period_int = Integer.parseInt(period);
            String sql = "INSERT INTO ISSUED (UID,BID,ISSUED_DATE,PERIOD) "
                + "VALUES ('"+uid+"','"+bid+"','"+issued_date+"',"+period_int+")";
            conn.executeUpdateSQL(sql) ;
            System.out.print("The insert value: " + sql);
            
            issueBDetailFrame.dispose();
            JOptionPane.showMessageDialog(null,"Book Issued!");

          }                   

        });
        issueBDetailFrame.add(bookIDLbel);
        issueBDetailFrame.add(usrIDLbel);
        issueBDetailFrame.add(submitBttn);
        issueBDetailFrame.add(periodLbel);
        issueBDetailFrame.add(issueDateLbel);
        issueBDetailFrame.add(F_uid);
        issueBDetailFrame.add(F_bid);
        issueBDetailFrame.add(F_period);
        issueBDetailFrame.add(F_issue);
        issueBDetailFrame.setSize(350,250);//400 width and 500 heights  
        issueBDetailFrame.setLayout(null);//usinissueBDetailFrame no layout managers
        issueBDetailFrame.setVisible(true);//making the frame visible 
        issueBDetailFrame.setLocationRelativeTo(null);

      }

    });

    //Return book
    JButton return_book = new JButton("Return Book"); //creating instance of JButton to return books
    return_book.setBounds(280,60,160,25); 
    return_book.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){

        JFrame g = new JFrame("Enter Details");
        //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set labels 
        JLabel l1,l2,l3,l4;  
        l1=new JLabel("Issue ID(IID)");  //Label 1 for Issue ID
        l1.setBounds(30,15, 100,30); 


        l4=new JLabel("Return Date(DD-MM-YYYY)");  
        l4.setBounds(30,50, 150,30); 

        JTextField F_iid = new JTextField();
        F_iid.setBounds(110, 15, 200, 30);

        JTextField F_return=new JTextField();
        F_return.setBounds(180, 50, 130, 30);


        JButton returnBttn = new JButton("Return");//creating instance of JButton to mention return date and calculcate fine
        returnBttn.setBounds(130,170,80,25);//x axis, y axis, width, height 
        returnBttn.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent e){                 

            String iid = F_iid.getText();
            String return_date = F_return.getText();
            String sql = "SELECT ISSUED_DATE FROM ISSUED WHERE IID="+iid;
            List< String[]> result = conn.selectSQL(sql);
            String date1 = result.get(0)[0];
            String date2 = return_date; //Intialize date2 with return date

            Date date_1;
            Date date_2;
            long diff = 0;
            
            try {
              date_1  = new SimpleDateFormat("dd-MM-yyyy").parse(date1);
              date_2  = new SimpleDateFormat("dd-MM-yyyy").parse(date2);
              //subtract the dates and store in diff

              diff = date_2.getTime() - date_1.getTime();

            } catch (ParseException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }


            //Convert diff from milliseconds to days

            int days = (int)(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

            //update return date
            String updateStr = "UPDATE ISSUED SET RETURN_DATE='"+return_date+"' WHERE IID="+iid;
            System.out.println("Update String :" + updateStr);
            conn.executeUpdateSQL(updateStr);
            g.dispose();

            String setPeriod = "SELECT PERIOD FROM ISSUED WHERE IID="+iid;
            List< String[]> resultPeriod = conn.selectSQL(setPeriod);

            String  diffPeriod = resultPeriod.get(0)[0];

            int diff_int = Integer.parseInt(diffPeriod);
            System.out.println ("diff int :" + diff_int);
            System.out.println ("diff days :" + days);
            
            if(days > diff_int)
            { //If number of days are more than the period then calculcate fine

              //System.out.println(ex.days);
              int fine = (days-diff_int)*10; //fine for every day after the period is Rs 10.
              //update fine in the system
              String fineSql = "UPDATE ISSUED SET FINE="+fine+" WHERE IID="+iid;
              conn.executeUpdateSQL(fineSql);
              JOptionPane.showMessageDialog(null,fine);

            }

            JOptionPane.showMessageDialog(null,"Book Returned!");

          }
        });
        g.add(l4);
        g.add(returnBttn);
        g.add(l1);
        g.add(F_iid);
        g.add(F_return);
        g.setSize(350,250);//400 width and 500 height  
        g.setLayout(null);//using no layout managers  
        g.setVisible(true);//making the frame visible 
        g.setLocationRelativeTo(null); 

      }
    });

    JButton logout = new JButton ("Logout");
    logout.setBounds(250,120,80,25);//x axis, y axis, width, height 
    logout.setBackground(Color.gray);
    logout.addActionListener(new ActionListener () {

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        System.exit(0);
        conn.closeConn();
      }
      
    });
    
    
    adminMenu.add(createBttn);
    adminMenu.add(return_book);
    adminMenu.add(issueBookBttn);
    adminMenu.add(addBook);
    adminMenu.add(issued_but);
    adminMenu.add(usrBttn);
    adminMenu.add(viewBttn);
    adminMenu.add(addUsrBttn);
    adminMenu.add(logout);
    adminMenu.setSize(600,200);//400 width and 500 height  
    adminMenu.setLayout(null);//using no layout managers  
    adminMenu.setVisible(true);//making the frame visible 
    adminMenu.setLocationRelativeTo(null);


  }
}

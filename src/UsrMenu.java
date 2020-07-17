import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class UsrMenu {
  JFrame menuFrame = new JFrame("User Functions");
  
  /**
   * A Constructor that will take in the user's id and generate
   * the User's issued book and views books
   * @param UID
   */
  public UsrMenu ( String UID) {
    setUI(UID);
  }

  /**
   * Setting all the UI 
   * @param UID
   */
  public void setUI ( String UID)
  {
    DBConnect conn = DBConnect.getConnect();
    JButton viewBttn = new JButton ("View Books");

    viewBttn.setBounds(20,20,120, 25); //x, y, w, h
    viewBttn.addActionListener(new ActionListener () {

      @Override
      public void actionPerformed(ActionEvent e) {
        JTable bookTable = new JTable(); //To display the books in table format
        JFrame avalBookFrame = new JFrame ("Books Available");
        String sql = "SELECT * FROM BOOKS";
        List<String []> bookList = conn.selectSQL(sql);
        String[] colNameList = {"ID", "User ID", "Book ID", "Issued Date"};
        int col = 3;
        TableModelMold.setTableModel(bookTable, bookList, col, colNameList);
        JScrollPane scrollPane = new JScrollPane (bookTable); //Enable scroll bar

        avalBookFrame.add(scrollPane);
        avalBookFrame.setSize(800,400);
        avalBookFrame.setVisible(true);
        avalBookFrame.setLocationRelativeTo(null);    
      }
    });


    JButton myBookBttn = new JButton("My Books");//creating instance of JButton  
    myBookBttn.setBounds(150,20,120,25);//x axis, y axis, width, height 
    myBookBttn.addActionListener(new ActionListener() { //Perform action
      public void actionPerformed(ActionEvent e) 
      {

        JFrame f = new JFrame("My Books"); //View books issued by user
        JTable bookTable = new JTable(); //To display the books in table format

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int UID_int = Integer.parseInt(UID); //Pass user ID


        String sql = "SELECT DISTINCT ISSUED.*, BOOKS.BNAME, BOOKS.GENRE, BOOKS.PRINCE FROM ISSUED" +
            " LEFT JOIN BOOKS ON  ISSUED.UID = '"+ UID_int + "' AND (BOOKS.BID IN (SELECT BID FROM ISSUED WHERE UID='" +UID_int + "' GROUP BY IID))";  

        List<String []> issuedList = conn.selectSQL(sql);
        String[] colNameList = {"IID", "User ID", "Book ID", "Issued Date",
            "Returned Date"  , "Period", "Fine",
            "Book Name", "Book Genre", "Book Price"};
        int col = 10;
        
        TableModelMold.setTableModel(bookTable, issuedList, col, colNameList);        
        JScrollPane scrollPane = new JScrollPane(bookTable);
        f.add(scrollPane); //add scroll bar
        f.setSize(800, 400); //set dimensions of my books frame
        f.setVisible(true);
        f.setLocationRelativeTo(null);

      }
    });

    menuFrame.add(myBookBttn); //add my books
    menuFrame.add(viewBttn); // add view books
    menuFrame.setSize(300,100);//400 width and 500 height  
    menuFrame.setLayout(null);//using no layout managers  
    menuFrame.setVisible(true);//making the frame visible 
    menuFrame.setLocationRelativeTo(null);
  }
}

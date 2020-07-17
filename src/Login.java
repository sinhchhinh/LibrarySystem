import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Login {
  JFrame frame = new JFrame ("Login");
  JLabel nameLbel,pwdLbel;
  JTextField usrTextField = new JTextField();
  JTextField pwdField = new JTextField(); //Create text field for password

  public Login () {
    initUI();
  }

  public void initUI () {

    nameLbel = new JLabel("Username");
    nameLbel.setBounds(30,15, 100,30); //x axis, y axis, width, height 

    pwdLbel = new JLabel("Password");
    pwdLbel.setBounds(30,50, 100,30); //x axis, y axis, width, height 

    usrTextField.setBounds(110, 15, 200, 30);
    pwdField.setBounds(110, 50, 200, 30);

    JButton loginBttn = new JButton("Login");
    loginBttn.setBounds(130, 90,80, 25);
    loginBttn.addActionListener(new ActionListener (){
      public void actionPerformed (ActionEvent e) {
        String usrname = usrTextField.getText();
        String pwd = pwdField.getText();

        if (usrname.equals("") || pwd.equals("")) {
          JOptionPane.showMessageDialog(null, "Please enter username and password");
        } else 
        {
          DBConnect conn = DBConnect.getConnect();
          String sql  = "SELECT * FROM USERS WHERE USERNAME='"+usrname+
              "' AND PASSWORD='"+ pwd + "'";

          List<String[]> result = conn.selectSQL(sql);
          if (result.isEmpty()){
            JOptionPane.showMessageDialog(null, "Incorrect username or password");
          } else 
          {
            frame.dispose();
            for (int i= 0; i < result.size(); i ++) 
            {
              String UUID = result.get(i)[0];
              String admin = result.get(i)[3];
              if (admin.equals("1")){
                
                //bring to Admin menu
                AdminMenu usr = new AdminMenu();
              } else {
                //bring to user menu
                UsrMenu usr = new UsrMenu(UUID);

              }
            }
          }

        }
      }
    });

    frame.add(usrTextField); //add password
    frame.add(loginBttn);//adding button in JFrame  
    frame.add(pwdField);  //add user
    frame.add(nameLbel);  // add label1 i.e. for username
    frame.add(pwdLbel); // add label2 i.e. for password

    frame.setSize(400,180);//400 width and 500 height  
    frame.setLayout(null);//using no layout managers  
    frame.setVisible(true);//making the frame visible 
    frame.setLocationRelativeTo(null);

  }

}

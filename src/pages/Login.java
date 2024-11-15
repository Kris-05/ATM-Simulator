package pages;
// Swing for desktop application
// Swing frame is in JAVA extended package -> so javax
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JButton login, clear, signup;
    JTextField cardTextField;
    JPasswordField pinTextField;

    public JLabel createLabels(String content, int size, int x, int y, int width, int height) {
        JLabel temp = new JLabel(content);
        temp.setFont(new Font("Courier New", Font.BOLD, size));
        temp.setBounds(x,y,width,height);
        add(temp);
        return temp;
    }

    public JButton createButton(String content, int x, int y, int width) {
        JButton temp = new JButton(content);
        temp.setBounds(x,y,width,30);
        temp.setBackground(Color.BLACK);
        temp.setForeground(Color.WHITE);
        temp.addActionListener(this);
        add(temp);
        return temp;
    }

    public Login(){

        setTitle("LOGIN PAGE");
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg")); // to load the image
        Image img = i1.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT); // to make it small
        ImageIcon logo = new ImageIcon(img); // change the Image to ImageIcon to use
        JLabel label = new JLabel(logo); // to show the image above frame
        label.setBounds(150,10,100,100); // bound inside the frame
        add(label);

        JLabel title = createLabels("Welcome USER!",38,270,50,400,40);
        JLabel cardno = createLabels("Card No: ",28,120,150,200,40);
        JLabel pin = createLabels("PIN: ",28,120,200,400,40);

        cardTextField = new JTextField();
        cardTextField.setBounds(300, 155, 350, 30);
        cardTextField.setFont(new Font("Courier New", Font.BOLD, 24));
        add(cardTextField);

        pinTextField = new JPasswordField();
        pinTextField.setBounds(300, 205, 350, 30);
        pinTextField.setFont(new Font("Courier New", Font.BOLD, 24));
        add(pinTextField);

        login = createButton("SIGN IN",300,300,100);
        clear = createButton("CLEAR",430,300,100);
        signup = createButton("SIGN UP",300,350,230);

        getContentPane().setBackground(Color.WHITE);

        setSize(800, 480); // fixing length and breadth
        setVisible(true);   // to make it visible
        setLocation(350,200);   // to make it center
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == clear) {
            cardTextField.setText("");
            pinTextField.setText("");
        } else if(ae.getSource() == login) {
            Connect c = new Connect();
            String cardNo = cardTextField.getText();
            String pinNo = pinTextField.getText();
            String query = "SELECT * FROM LOGIN WHERE cardNo='"+cardNo+"' AND pin='"+pinNo+"'";
            try {
                ResultSet rs = c.s.executeQuery(query);
                if(rs.next()){
                    setVisible(false);
                    new Transaction(pinNo).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,"Incorrect cardNumber or PIN!");
                }
            } catch (Exception e) {
                System.out.println("Error in executing query in LOGIN class: " + e.getMessage());
            }
        } else if(ae.getSource() == signup) {
            setVisible(false);
            new SignUpOne().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}

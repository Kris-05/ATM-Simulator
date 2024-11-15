package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Withdraw extends JFrame implements ActionListener {

    JLayeredPane layer;
    JTextField amount;
    JButton withdraw, back;
    String pinNo;

    public JLabel createLabels(String content, int size, int x, int y, int width, int height) {
        JLabel temp = new JLabel(content);
        temp.setFont(new Font("Courier New", Font.BOLD, size));
        temp.setBounds(x,y,width,height);
        temp.setForeground(Color.WHITE);
        layer.add(temp, JLayeredPane.PALETTE_LAYER);
        return temp;
    }

    public JTextField createField(int y){
        JTextField temp = new JTextField();
        temp.setFont(new Font("Courier New", Font.BOLD, 14));
        temp.setBounds(170,y,250,30);
        add(temp);
        return temp;
    }

    public JButton createButton(String content, int x, int y) {
        JButton temp = new JButton(content);
        temp.setBounds(x,y,130,28);
        temp.setBackground(Color.WHITE);
        temp.setForeground(Color.BLACK);
        temp.addActionListener(this);
        add(temp);
        return temp;
    }

    public Withdraw(String pinNo){
        this.pinNo = pinNo;
        setTitle("CASH WITHDRAW");
        setLayout(null);

        layer = new JLayeredPane();
        layer.setBounds(0,0,800,830);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image img = i1.getImage().getScaledInstance(800,830,Image.SCALE_DEFAULT);
        ImageIcon bg = new ImageIcon(img);
        JLabel image = new JLabel(bg);
        image.setBounds(0,0,800,830);
        layer.add(image, JLayeredPane.DEFAULT_LAYER);

        JLabel text = createLabels("ENTER YOUR AMOUNT: ",22,155,270,500,30);
        amount = createField(320);

        withdraw = createButton("Withdraw",320,447);
        back = createButton("Back",320,479);

        add(layer);
        getContentPane().setBackground(Color.WHITE);

        setSize(800,800);
        setLocation(350,10);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == withdraw) {
            String val = amount.getText();
            if(val.isEmpty())
                JOptionPane.showMessageDialog(null,"Please enter the amount!");
            else {
                Connect c = new Connect();
                try {
                    String query1 = "SELECT * FROM BANK WHERE PIN='"+pinNo+"'";
                    ResultSet rs = c.s.executeQuery(query1);
                    int balance = 0;
                    while(rs.next()){
                        if(rs.getString("type").equalsIgnoreCase("Deposit")){
                            balance += Integer.parseInt(rs.getString("amount"));
                        } else {
                            balance -= Integer.parseInt(rs.getString("amount"));
                        }
                    }

                    if(ae.getSource() != back && balance < Integer.parseInt(val)){
                        JOptionPane.showMessageDialog(null,"Insufficient balance");
                        return;
                    }

                    Date date = new Date();
                    String query2 = "INSERT INTO BANK VALUES('"+pinNo+"','"+date+"','withdraw','"+val+"')";
                    c.s.executeUpdate(query2);
                    JOptionPane.showMessageDialog(null,"Rs: "+val+" Withdrew Successfully");

                    setVisible(false);
                    new Transaction(pinNo).setVisible(true);
                } catch (SQLException e) {
                    System.out.println("Error in executing query in  WITHDRAW class: " + e.getMessage());
                }
            }
        } else if(ae.getSource() == back) {
            setVisible(false);
            new Transaction(pinNo).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Withdraw("");
    }
}

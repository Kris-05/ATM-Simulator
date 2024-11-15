package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MiniStatement extends JFrame implements ActionListener {
    String pinNo;
    JLayeredPane layer;

    public JLabel createLabels(String content, int size, int x, int y, int width, int height) {
        JLabel temp = new JLabel(content);
        temp.setFont(new Font("Courier New", Font.BOLD, size));
        temp.setBounds(x,y,width,height);
        temp.setForeground(Color.BLACK);
        layer.add(temp, JLayeredPane.PALETTE_LAYER);
        return temp;
    }

    public MiniStatement(String pinNo){
        this.pinNo = pinNo;
        setTitle("MINI STATEMENT");
        setLayout(null);

        layer = new JLayeredPane();
        layer.setBounds(0,0,800,830);

        JLabel bank = createLabels("State Bank of India",16,150,20,300,20) ;
        JLabel card = createLabels("",16,80,80,400,20);
        JLabel statement = createLabels("",12,20,140,500,200);
        JLabel balance = createLabels("",16,20,400,500,20);

        try{
          Connect c = new Connect();
          String query = "SELECT * FROM LOGIN WHERE PIN='"+2008+"'";
          ResultSet rs = c.s.executeQuery(query);
          if(rs.next()){
              String val = rs.getString("cardNo");
              String cardNo = val.substring(0,4)+ " XXXX XXXX "+ val.substring(12);
              card.setText("Card Number: "+ cardNo);
          } else {
              card.setText("No card found for the provided PIN.");
          }
        } catch (SQLException e) {
            System.out.println("Error in executing query in  WITHDRAW class: " + e.getMessage());
        }

        try{
            Connect c = new Connect();
            String query = "SELECT * FROM BANK WHERE PIN='"+2008+"'";
            ResultSet rs = c.s.executeQuery(query);
            int remain = 0;
            while(rs.next()){
                statement.setText(statement.getText()
                        + "<html>"
                        + rs.getString("date")
                        + "&nbsp;&nbsp;&nbsp;&nbsp;"
                        + rs.getString("type")
                        + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + rs.getString("amount")
                        + "<br><br>"
                );
                if(rs.getString("type").equalsIgnoreCase("deposit")){
                    remain += Integer.parseInt(rs.getString("amount"));
                } else {
                    remain -= Integer.parseInt(rs.getString("amount"));
                }
                balance.setText("Your Current Account Balance is Rs. "+ remain);
            }
        } catch (SQLException e) {
            System.out.println("Error in executing query in  WITHDRAW class: " + e.getMessage());
        }

        add(layer);
        getContentPane().setBackground(Color.WHITE);

        setSize(500,700);
        setLocation(20,20);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MiniStatement("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

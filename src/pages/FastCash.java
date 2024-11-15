package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    String pinNo;
    JLayeredPane layer;
    JButton Rs1hundred, Rs5hundered, Rs1Thousand, Rs2Thousand, Rs5Thousand, Rs10Thousand, back;

    public JLabel createLabels(String content, int size, int x, int y, int width, int height) {
        JLabel temp = new JLabel(content);
        temp.setFont(new Font("Courier New", Font.BOLD, size));
        temp.setBounds(x,y,width,height);
        temp.setForeground(Color.WHITE);
        layer.add(temp, JLayeredPane.PALETTE_LAYER);
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

    public FastCash(String pinNo){
        this.pinNo = pinNo;
        setTitle("FASTCASH PAGE");
        setLayout(null);
        layer = new JLayeredPane();
        layer.setBounds(0,0,800,830);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image img = i1.getImage().getScaledInstance(800,830,Image.SCALE_DEFAULT);
        ImageIcon bg = new ImageIcon(img);
        JLabel image = new JLabel(bg);
        image.setBounds(0,0,800,830);
        layer.add(image, JLayeredPane.DEFAULT_LAYER);

        JLabel text = createLabels("SELECT WITHDRAW AMOUNT",16,195,300,500,30);

        Rs1hundred = createButton("Rs 100",150,383);
        Rs5hundered = createButton("Rs 500",320,383);
        Rs1Thousand = createButton("Rs 1000",150,415);
        Rs2Thousand = createButton("Rs 2000",320,415);
        Rs5Thousand = createButton("Rs 5000",150,447);
        Rs10Thousand = createButton("Rs 10000",320,447);
        back = createButton("Back",320,479);

        add(layer);
        getContentPane().setBackground(Color.WHITE);

        setSize(800,800);
        setLocation(350,10);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FastCash("");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == back) {
            setVisible(false);
            new Transaction(pinNo).setVisible(true);
        } else {
            String amount = ((JButton)ae.getSource()).getText().substring(3);
            Connect c = new Connect();
            try{
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

                if(ae.getSource() != back && balance < Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }

                Date date = new Date();
                String query2 = "INSERT INTO BANK VALUES('"+pinNo+"','"+date+"','withdraw','"+amount+"')";
                c.s.executeUpdate(query2);
                JOptionPane.showMessageDialog(null, "Rs "+amount+" debited successfully");

                setVisible(false);
                new Transaction(pinNo).setVisible(true);
            } catch (Exception e) {
                System.out.println("Error in executing query in FASTCASH class: " + e.getMessage());
            }
        }
    }
}

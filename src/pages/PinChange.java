package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PinChange extends JFrame implements ActionListener {

    String pinNo;
    JLayeredPane layer;
    JTextField pin, repin;
    JButton change, back;

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
        temp.setBounds(300,y,150,30);
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

    public PinChange(String pinNo){
        this.pinNo = pinNo;
        setTitle("PIN CHANGE");
        setLayout(null);

        layer = new JLayeredPane();
        layer.setBounds(0,0,800,830);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image img = i1.getImage().getScaledInstance(800,830,Image.SCALE_DEFAULT);
        ImageIcon bg = new ImageIcon(img);
        JLabel image = new JLabel(bg);
        image.setBounds(0,0,800,830);
        layer.add(image, JLayeredPane.DEFAULT_LAYER);

        JLabel text = createLabels("WANT TO CHANGE YOUR PIN?",16,175,290,500,30);
        JLabel pinText = createLabels("ENTER NEW PIN:",14,145,350,200,30);
        JLabel repinText = createLabels("RE-ENTER NEW PIN:",14,145,390,200,30);

        pin = createField(350);
        repin = createField(390);
        change = createButton("Change",320,479);
        back = createButton("Back",150,479);

        add(layer);
        getContentPane().setBackground(Color.WHITE);

        setSize(800,800);
        setLocation(350,10);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PinChange("");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == back){
            setVisible(false);
            new Transaction(pinNo).setVisible(true);
        } else if(ae.getSource() == change){
            try{
                String epin = pin.getText();
                String rpin = repin.getText();

                if(!epin.equals(rpin)){
                    JOptionPane.showMessageDialog(null, "Both Fields doesn't match!");
                    return;
                }

                if(epin.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please Enter PIN!");
                    return;
                }

                Connect c = new Connect();
                String query1 = "UPDATE BANK SET PIN='"+rpin+"' WHERE PIN='"+pinNo+"'";
                String query2 = "UPDATE LOGIN SET PIN='"+rpin+"' WHERE PIN='"+pinNo+"'";
                String query3 = "UPDATE account_info SET pinNo='"+rpin+"' WHERE pinNo='"+pinNo+"'";

                c.s.executeUpdate(query1);
                c.s.executeUpdate(query2);
                c.s.executeUpdate(query3);

                JOptionPane.showMessageDialog(null,"PIN changed successfully");

                setVisible(false);
                new Transaction(rpin).setVisible(true);
            } catch (Exception e) {
                System.out.println("Error in executing query: " + e.getMessage());
            }
        }
    }
}

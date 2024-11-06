package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Transaction extends JFrame implements ActionListener {

    JLayeredPane layer;
    String pinNo;
    JButton deposit, withdraw, miniStatement, pinChange, fastCash, balance, exit;

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

    public Transaction(String pinNo){
        this.pinNo = pinNo;
        setTitle("TRANSACTION PAGE");
        setLayout(null);
        layer = new JLayeredPane();
        layer.setBounds(0,0,800,830);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image img = i1.getImage().getScaledInstance(800,830,Image.SCALE_DEFAULT);
        ImageIcon bg = new ImageIcon(img);
        JLabel image = new JLabel(bg);
        image.setBounds(0,0,800,830);
        layer.add(image, JLayeredPane.DEFAULT_LAYER);

        JLabel text = createLabels("PLEASE SELECT YOUR TRANSACTION",16,155,270,500,30);

        deposit = createButton("Deposit",150,383);
        withdraw = createButton("Withdraw",320,383);
        fastCash = createButton("Fast Cash",150,415);
        miniStatement = createButton("Mini Statement",320,415);
        pinChange = createButton("Pin Change",150,447);
        balance = createButton("Balance Inquiry",320,447);
        exit = createButton("Exit",320,479);

        add(layer);
        getContentPane().setBackground(Color.WHITE);

        setSize(800,800);
        setLocation(350,10);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == exit) {
            System.exit(0);
        } else if(ae.getSource() == deposit) {
            setVisible(false);
            new Deposit(pinNo).setVisible(true);
        } else if(ae.getSource() == withdraw) {
            setVisible(false);
            new Withdraw(pinNo).setVisible(true);
        } else if(ae.getSource() == fastCash) {
            setVisible(false);
            new FastCash(pinNo).setVisible(true);
        } else if(ae.getSource() == pinChange){
            setVisible(false);
            new PinChange(pinNo).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Transaction("");
    }
}

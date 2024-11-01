package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SignUpThree extends JFrame implements ActionListener {

    JRadioButton savings, current, fd, recurring;
    JCheckBox atmCard, netBanking, mobileBanking, alerts, cheque, passbook, declaration;
    JButton submit, cancel;
    String formno;

    public JLabel createLabels(String content, int size, int x, int y, int width, int height) {
        JLabel temp = new JLabel(content);
        temp.setFont(new Font("Courier New", Font.BOLD, size));
        temp.setBounds(x,y,width,height);
        add(temp);
        return temp;
    }

    public JRadioButton createRadio(String content,int x, int y, int width, int height){
        JRadioButton temp = new JRadioButton(content);
        temp.setBounds(x,y,width,height);
        temp.setBackground(Color.WHITE);
        add(temp);
        return temp;
    }

    public JCheckBox createCheck(String content, int x, int y, int width, int height){
        JCheckBox temp = new JCheckBox(content);
        temp.setBounds(x,y,width,height);
        temp.setBackground(Color.WHITE);
        add(temp);
        return temp;
    }

    private JButton createButton(String content, int x, int y, int width) {
        JButton temp = new JButton(content);
        temp.setBounds(x,y,width,30);
        temp.setBackground(Color.BLACK);
        temp.setForeground(Color.WHITE);
        temp.addActionListener(this);
        add(temp);
        return temp;
    }

    public SignUpThree(String formno) {
        this.formno = formno;
        setLayout(null);
        setTitle("APPLICATION FORM - PAGE THREE");

        JLabel title = createLabels("PAGE 3 : ACCOUNT DETAILS", 22, 240,40,400,30);
        JLabel type = createLabels("ACCOUNT TYPE: ", 22, 100,100,200,30);
        JLabel services = createLabels("Services Required: ", 22, 100,190,300,30);

        /*JLabel card = createLabels("CARD NUMBER: ", 22, 100,190,200,30);
        JLabel cNumber = createLabels("XXXX-XXXX-XXXX-1234", 22, 300,190,400,30);
        JLabel cDetails = createLabels("This is your 16-digit Card number", 12, 100,215,400,30);
        JLabel pin = createLabels("PIN NUMBER: ", 22, 100,270,200,30);
        JLabel pNumber = createLabels("XXXX", 22, 300,270,200,30);
        JLabel pDetails = createLabels("This is your 4-digit PIN", 12, 100,300,400,30);*/

        savings = createRadio("SAVINGS ACCOUNT",110,140,150,30);
        current = createRadio("CURRENT ACCOUNT",280,140,150,30);
        fd = createRadio("FD ACCOUNT",450,140,120,30);
        recurring = createRadio("RECURRING ACCOUNT",580,140,170,30);
        ButtonGroup groupAccount = new ButtonGroup();
        groupAccount.add(savings);
        groupAccount.add(current);
        groupAccount.add(fd);
        groupAccount.add(recurring);

        atmCard = createCheck("ATM CARD",180,230,150,30);
        netBanking = createCheck("INTERNET BANKING",350, 230,150,30);
        alerts = createCheck("EMAIL&SMS ALERTS",520,230,150,30);
        cheque = createCheck("CHEQUE BOOK",180,280,150,30);
        mobileBanking = createCheck("MOBILE BANKING",350,280,150,30);
        passbook = createCheck("E-STATEMENT",520,280,150,30);
        declaration = createCheck("I hereby declare that the above entered details are correct to the best of my knowledge..",100,320,600,30);

        submit = createButton("SUBMIT",420,380,150);
        cancel = createButton("CANCEL",620,380,150);

        getContentPane().setBackground(Color.WHITE);

        setSize(850, 500);
        setLocation(350,200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == submit){
            Random r = new Random();
            String cardNo = "" + Math.abs((r.nextLong() % 90000000L) + 4096172800000000L);
            String pinNo =  "" + Math.abs((r.nextLong() % 9000L) + 1000L);

            String accountType = "";
            if(savings.isSelected()) accountType = "Savings Account";
            else if(current.isSelected()) accountType = "Current Account";
            else if(fd.isSelected()) accountType = "FD Account";
            else if(recurring.isSelected()) accountType = "Recurring Account";

            String facility = "";
            if(atmCard.isSelected()) facility += " ATM-Card";
            else if(netBanking.isSelected()) facility += " Net-Banking";
            else if(mobileBanking.isSelected()) facility += " Mobile-Banking";
            else if(cheque.isSelected()) facility += " Cheque-Book";
            else if(passbook.isSelected()) facility += " E-Statement";
            else if(alerts.isSelected()) facility += " Notifications";

            try {
                // JOptionPane.showMessageDialog(null,"CARD NUMBER: " +cardNo+ "\n PIN NUMBER: "+pinNo);
                Connect c = new Connect();
                String query = "INSERT INTO ACCOUNT_INFO VALUES('"+formno+"','"+accountType+"','"+facility+"','"+cardNo+"','"+pinNo+"')";
                c.s.executeUpdate(query);
                System.out.println("Insertion for account_info successful.");

                JOptionPane.showMessageDialog(null,"CARD NUMBER: " +cardNo+ "\n PIN NUMBER: "+pinNo);
            } catch (Exception e) {
                System.out.println("Error in executing query: " + e.getMessage());
            }

        } else if (ae.getSource() == cancel) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new SignUpThree("");
    }
}

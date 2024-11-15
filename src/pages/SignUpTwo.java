package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpTwo extends JFrame implements ActionListener {

    JComboBox<String> religionTypes, categoryTypes, incomeTypes, educationTypes, occupationTypes;
    JTextField panNumber, aadharNumber;
    JRadioButton seniorYes, seniorNo, accountYes, accountNo;
    JButton next;
    String formno;

    public JLabel createLabels(String content, int size, int x, int y, int width, int height) {
        JLabel temp = new JLabel(content);
        temp.setFont(new Font("Courier New", Font.BOLD, size));
        temp.setBounds(x,y,width,height);
        add(temp);
        return temp;
    }

    public JTextField createField(int y){
        JTextField temp = new JTextField();
        temp.setFont(new Font("Courier New", Font.BOLD, 14));
        temp.setBounds(375,y,350,30);
        add(temp);
        return temp;
    }

    public JRadioButton createRadio(String content,int x, int y){
        JRadioButton temp = new JRadioButton(content);
        temp.setBounds(x,y,90,30);
        temp.setBackground(Color.WHITE);
        add(temp);
        return temp;
    }

    public JComboBox<String> createDropDown(String[] items, int y){
        JComboBox<String> temp = new JComboBox<>(items);
        temp.setBounds(375,y,350,30);
        temp.setBackground(Color.WHITE);
        add(temp);
        return temp;
    }

    public SignUpTwo(String formno){
        this.formno = formno;
        setLayout(null);
        setTitle("APPLICATION FORM - PAGE TWO");

        JLabel title = createLabels("PAGE 2 : ADDITIONAL DETAILS", 22, 240,80,400,30);
        JLabel religion = createLabels("RELIGION: ",20,100,140,200,30);
        JLabel category = createLabels("CATEGORY: ",20,100,190,200,30);
        JLabel income = createLabels("INCOME: ",20,100,240,200,30);
        JLabel education = createLabels("EDUCATION: ",20,100,290,300,30);
        JLabel occupation = createLabels("OCCUPATION: ",22,100,340,300,30);
        JLabel pan = createLabels("PAN NUMBER: ",22,100,390,300,30);
        JLabel aadhar = createLabels("AADHAR NUMBER: ",22,100,440,300,30);
        JLabel senior = createLabels("SENIOR CITIZEN: ",22,100,490, 300,30);
        JLabel exist = createLabels("EXISTING ACCOUNT: ",22,100,540,300,30);

        religionTypes = createDropDown(new String[]{"Hindu", "Christian", "Muslim", "Others"},140);
        categoryTypes = createDropDown(new String[]{"General", "OBC", "SC", "Others"},190);
        incomeTypes = createDropDown(new String[]{"< 150k", "< 250k", "> 500k", "upto 1M"},240);
        educationTypes = createDropDown(new String[]{"Non-Graduate", "Graduate", "Post-Graduate", "Doctorate", "Others"},290);
        occupationTypes = createDropDown(new String[]{"Salaried", "Self-Employed", "Business", "Student", "Retired", "Others"},340);

        panNumber = createField(390);
        aadharNumber = createField(440);

        seniorYes = createRadio("YES",375,490);
        seniorNo = createRadio("NO",485,490);
        ButtonGroup groupSenior = new ButtonGroup();
        groupSenior.add(seniorYes);
        groupSenior.add(seniorNo);

        accountYes = createRadio("YES",375,540);
        accountNo = createRadio("NO",485,540);
        ButtonGroup groupAccount = new ButtonGroup();
        groupAccount.add(accountYes);
        groupAccount.add(accountNo);

        next = new JButton("NEXT PAGE");
        next.setForeground(Color.WHITE);
        next.setBackground(Color.BLACK);
        next.setFont(new Font("Courier New", Font.BOLD, 14));
        next.setBounds(600,610,140,30);
        next.addActionListener(this);
        add(next);

        getContentPane().setBackground(Color.WHITE);

        setSize(850, 700);
        setLocation(350,70);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String religion = (String) religionTypes.getSelectedItem();
        String category = (String) categoryTypes.getSelectedItem();
        String income = (String) incomeTypes.getSelectedItem();
        String education = (String) educationTypes.getSelectedItem();
        String occupation = (String) occupationTypes.getSelectedItem();
        String pan = panNumber.getText();
        String aadhar = aadharNumber.getText();

        String isSenior = null;
        if(seniorYes.isSelected()) isSenior = "Yes";
        else if(seniorNo.isSelected()) isSenior = "No";

        String hasAccount = null;
        if(accountYes.isSelected()) hasAccount = "Yes";
        else if(accountNo.isSelected()) hasAccount = "No";

        try {
            Connect c = new Connect();
            String query = "INSERT INTO ADDITIONAL_SIGNUP VALUES('"+formno+"','"+religion+"','"+category+"','"+income+"','"+education+"','"+ occupation +"','"+pan+"','"+aadhar+"','"+isSenior+"','"+hasAccount+"')";
            c.s.executeUpdate(query);
            System.out.println("Insertion for additional_signup successful.");

            setVisible(false);
            new SignUpThree(formno).setVisible(true);
        } catch (Exception e) {
            System.out.println("Error in executing query in SIGNUP-TWO class: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new SignUpTwo("");
    }
}

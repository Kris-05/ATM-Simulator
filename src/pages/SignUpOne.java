package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import com.toedter.calendar.JDateChooser;

public class SignUpOne extends JFrame implements ActionListener {

    long random;
    JTextField nameTextField, fnameTextField, emailTextField, addressTextField, cityTextField, stateTextField, pinTextField;
    JRadioButton male, female, others, married, unmarried;
    JDateChooser dateChooser;
    JButton next;

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
        temp.setBounds(350,y,400,30);
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

    public SignUpOne(){
        setLayout(null);
        setTitle("APPLICATION FORM - PAGE ONE");
        Random r = new Random();
        random = Math.abs((r.nextLong() % 9000L) + 1000L);

        JLabel formno = createLabels("APPLICATION FORM NO. " + random,28,200,20,600,40);
        JLabel title = createLabels("PAGE 1 : PERSONAL DETAILS", 22, 240,80,400,30);
        JLabel name = createLabels("NAME : ",20,100,140,200,30);
        JLabel fname = createLabels("FATHER's NAME : ",20,100,190,200,30);
        JLabel dob = createLabels("DATE OF BIRTH : ",20,100,240,200,30);
        JLabel gender = createLabels("GENDER : ",20,100,290,200,30);
        JLabel email = createLabels("EMAIL: ",22,100,340,100,30);
        JLabel marital = createLabels("MARITAL STATUS: ",22,100,390,300,30);
        JLabel address = createLabels("ADDRESS: ",22,100,440,200,30);
        JLabel city = createLabels("CITY: ",22,100,490,200,30);
        JLabel state = createLabels("STATE: ",22,100,540, 200,30);
        JLabel pincode = createLabels("PINCODE: ",22,100,590,200,30);

        nameTextField = createField(140);
        fnameTextField = createField(190);
        emailTextField = createField(340);
        addressTextField = createField(440);
        cityTextField = createField(490);
        stateTextField = createField(540);
        pinTextField = createField(590);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(350,240,200,30);
        add(dateChooser);

        male = createRadio("MALE",350,290,90,30);
        female = createRadio("FEMALE",460,290,90,30);
        others = createRadio("OTHERS",570,290,90,30);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(others);

        married = createRadio("MARRIED",350,390,90,30);
        unmarried = createRadio("UNMARRIED",460,390,140,30);
        ButtonGroup groupMarital = new ButtonGroup();
        groupMarital.add(married);
        groupMarital.add(unmarried);

        next = new JButton("NEXT PAGE");
        next.setForeground(Color.WHITE);
        next.setBackground(Color.BLACK);
        next.setFont(new Font("Courier New", Font.BOLD, 14));
        next.setBounds(600,660,140,30);
        next.addActionListener(this);
        add(next);

        getContentPane().setBackground(Color.WHITE);

        setSize(850, 800);
        setLocation(350,10);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String formno = "" + random;
        String name = nameTextField.getText();
        String fname = fnameTextField.getText();
        String email = emailTextField.getText();
        String address = addressTextField.getText();
        String city = cityTextField.getText();
        String state = stateTextField.getText();
        String pin = pinTextField.getText();
        String dob = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();

        String gender = null;
        if(male.isSelected()) gender = "Male";
        else if(female.isSelected()) gender = "Female";
        else if(others.isSelected()) gender = "Others";

        String marital = null;
        if(married.isSelected()) marital = "Married";
        else if(unmarried.isSelected()) marital = "Unmarried";

        try {
            if(name.isEmpty()){
                JOptionPane.showMessageDialog(null, "Name is Required!");
            } else {
                Connect c = new Connect();
                String query = "INSERT INTO SIGNUP VALUES('"+formno+"','"+name+"','"+fname+"','"+dob+"','"+gender+"','"+email+"','"+marital+"','"+address+"','"+city+"','"+state+"','"+pin+"')";
                c.s.executeUpdate(query);
                System.out.println("Insertion for signup successful.");

                setVisible(false);
                new SignUpTwo(formno).setVisible(true);
            }
        } catch (Exception e) {
            System.out.println("Error in executing query: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new SignUpOne();
    }
}

package com.ehb.dnd.view;

import com.ehb.dnd.database.UserAPI;
import com.ehb.dnd.database.UserDb;
import com.ehb.dnd.model.User;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserFrame extends JFrame {

    private JTextField userText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JLabel warrenLabel;
    private JPanel panel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserFrame frame = new UserFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserFrame() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        panel = new JPanel();
        add(panel);
        placeComponents();
    }

    private void placeComponents() {

        panel.setLayout(null);

        userLabel = new JLabel("Email");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        warrenLabel = new JLabel("Email or password isn't right");
        warrenLabel.setBounds(10, 80, 300, 25);
        warrenLabel.setForeground(Color.red);
        warrenLabel.setVisible(false);
        panel.add(warrenLabel);

        loginButton = new JButton("login");
        loginButton.setBounds(10, 120, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClickLogin();
            }
        });
        panel.add(loginButton);

        registerButton = new JButton("register");
        registerButton.setBounds(180, 120, 80, 25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClickRegister();
            }
        });
        panel.add(registerButton);
    }

    private void onClickLogin(){
        System.out.println(passwordText.getText());
        User user = User.checkUserPassword(userText.getText(),passwordText.getText());
        if(user != null){
            this.setVisible(false);
            JFrame frame = new PlayerFrame(user);
            frame.setVisible(true);
        }else{
            warrenLabel.setVisible(true);
        }
}

    private void onClickRegister(){
        User user = User.checkUserPassword(userText.getText(),passwordText.getText());
        if(user == null){
            user = new User(userText.getText(),passwordText.getText());
            System.out.println("new User" + user.getPassword());
            if(User.insertUserWithHash(user)){
                user = UserAPI.findUserByEmail(user.getEmail());
                System.out.println("onClickRegister" + user.getPassword());
                if(user != null){
                    this.setVisible(false);
                    JFrame frame = new PlayerFrame(user);
                    frame.setVisible(true);
                }
            }
        }
    }
}

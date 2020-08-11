package com.mongodb.quickstart.view;

import com.mongodb.quickstart.models.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerFrame extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldName;
    private JTextField textFieldHP;
    private JTextField textFieldAttack;
    private JCheckBox chckbxDM;
    private Player player;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlayerFrame frame = new PlayerFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PlayerFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 542, 401);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{258, 258, 0};
        gbl_contentPane.rowHeights = new int[]{88, 88, 88, 88, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JLabel lblNewLabel = new JLabel("Name: ");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);

        textFieldName = new JTextField();
        GridBagConstraints gbc_textFieldName = new GridBagConstraints();
        gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldName.gridx = 1;
        gbc_textFieldName.gridy = 0;
        contentPane.add(textFieldName, gbc_textFieldName);
        textFieldName.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("HP:");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 1;
        contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

        textFieldHP = new JTextField();
        GridBagConstraints gbc_textFieldHP = new GridBagConstraints();
        gbc_textFieldHP.anchor = GridBagConstraints.WEST;
        gbc_textFieldHP.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldHP.gridx = 1;
        gbc_textFieldHP.gridy = 1;
        textFieldHP.setColumns(10);
        JPanel panelHP = new JPanel();
        panelHP.add(textFieldHP);
        contentPane.add(panelHP, gbc_textFieldHP);
        PlainDocument docHP = (PlainDocument) textFieldHP.getDocument();
        docHP.setDocumentFilter(new MyIntFilter());
        //JOptionPane.showMessageDialog(null, panelHP);

        JLabel lblNewLabel_1 = new JLabel("Attack:");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 2;
        contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

        textFieldAttack = new JTextField();
        GridBagConstraints gbc_textFieldAttack = new GridBagConstraints();
        gbc_textFieldAttack.anchor = GridBagConstraints.WEST;
        gbc_textFieldAttack.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldAttack.gridx = 1;
        gbc_textFieldAttack.gridy = 2;
        textFieldAttack.setColumns(10);
        JPanel panelAttack = new JPanel();
        panelAttack.add(textFieldAttack);
        contentPane.add(panelAttack, gbc_textFieldAttack);
        PlainDocument docAttack = (PlainDocument) textFieldAttack.getDocument();
        docAttack.setDocumentFilter(new MyIntFilter());
        //JOptionPane.showMessageDialog(null, panelAttack);

        chckbxDM = new JCheckBox("Dongeon Master");
        GridBagConstraints gbc_chckbxDM = new GridBagConstraints();
        gbc_chckbxDM.insets = new Insets(0, 0, 0, 5);
        gbc_chckbxDM.gridx = 0;
        gbc_chckbxDM.gridy = 3;
        contentPane.add(chckbxDM, gbc_chckbxDM);

        JButton btnPlay = new JButton("Go Play!");
        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doSomething();
            }
        });
        GridBagConstraints gbc_btnPlay = new GridBagConstraints();
        gbc_btnPlay.fill = GridBagConstraints.BOTH;
        gbc_btnPlay.gridx = 1;
        gbc_btnPlay.gridy = 3;
        contentPane.add(btnPlay, gbc_btnPlay);
    }
    public void doSomething() {
        if(!textFieldName.getText().isEmpty() && !textFieldHP.getText().isEmpty() && !textFieldAttack.getText().isEmpty()) {
            player = new Player(
                    textFieldName.getText(),
                    Integer.parseInt(textFieldHP.getText()),
                    Integer.parseInt(textFieldAttack.getText()),
                    chckbxDM.isSelected());

            this.setVisible(false);
            JFrame frame = new GameFrame(player);
        }
    }
}

package com.ehb.dnd.view;



import com.ehb.dnd.database.UserAPI;
import com.ehb.dnd.database.UserDb;
import com.ehb.dnd.model.Player;
import com.ehb.dnd.model.User;
import org.bson.Document;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PlayerFrame extends JFrame implements ItemListener {
    private JPanel contentPane;
    private JTextField textFieldName;
    private JTextField textFieldHP;
    private JTextField textFieldAttack;
    private JCheckBox chckbxDM;
    private Player player;
    private User user;
    private JComboBox characterCombo;
    private int currentCharater = -1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

    }

    /**
     * Create the frame.
     */
    public PlayerFrame(User user) {
        this.user = user;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 542, 451);

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
        gbc_lblNewLabel.gridy = 0 + 1;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);

        textFieldName = new JTextField();
        GridBagConstraints gbc_textFieldName = new GridBagConstraints();
        gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldName.gridx = 1;
        gbc_textFieldName.gridy = 0 + 1;
        contentPane.add(textFieldName, gbc_textFieldName);
        textFieldName.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("HP:");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 1 + 1;
        contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

        textFieldHP = new JTextField();
        GridBagConstraints gbc_textFieldHP = new GridBagConstraints();
        gbc_textFieldHP.anchor = GridBagConstraints.WEST;
        gbc_textFieldHP.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldHP.gridx = 1;
        gbc_textFieldHP.gridy = 1 + 1;
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
        gbc_lblNewLabel_1.gridy = 2 + 1;
        contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

        textFieldAttack = new JTextField();
        GridBagConstraints gbc_textFieldAttack = new GridBagConstraints();
        gbc_textFieldAttack.anchor = GridBagConstraints.WEST;
        gbc_textFieldAttack.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldAttack.gridx = 1;
        gbc_textFieldAttack.gridy = 2 + 1;
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
        gbc_chckbxDM.gridy = 3 + 1;
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
        gbc_btnPlay.gridy = 3 + 1;
        contentPane.add(btnPlay, gbc_btnPlay);

        String s1[] = {"New Player"};
        if(this.user.getPlayers() != null){
            s1 = new String[this.user.getPlayers().size()+1];
            s1[0] = "New Player";
            for (int i = 0; i < this.user.getPlayers().size(); i++){
                s1[i+1] = this.user.getPlayers().get(i).getName();
            }
        }

        characterCombo = new JComboBox(s1);

        characterCombo.addItemListener(this);
        GridBagConstraints gbc_charCombo = new GridBagConstraints();
        gbc_charCombo.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
        gbc_charCombo.gridx = 1;
        gbc_charCombo.gridy = 0;
        contentPane.add(characterCombo, gbc_charCombo);
    }
    public void doSomething() {
        if(!textFieldName.getText().isEmpty() && !textFieldHP.getText().isEmpty() && !textFieldAttack.getText().isEmpty()) {
            player = new Player(
                    textFieldName.getText(),
                    Integer.parseInt(textFieldHP.getText()),
                    Integer.parseInt(textFieldAttack.getText()),
                    chckbxDM.isSelected());

            System.out.println("Player : " + player.toString());
            System.out.println("User : " + user.toString());
            if(currentCharater >= 0){
                user.getPlayers().set(currentCharater, player);
                user = UserAPI.updateUser(user);
                if(user != null){
                    nextFrame();
                }
            } else {
                user.addPlayer(player);
                user = UserAPI.updateUser(user);
                if(user != null){
                    nextFrame();
                }
            }
        }
    }

    public void nextFrame(){
        this.setVisible(false);
        if(user != null && player != null){
            JFrame frame = new LobbyFrame(user, player);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == characterCombo) {
            //TODO set character when clicked on
            int i = characterCombo.getSelectedIndex();
            if (i > 0){
                textFieldName.setText(user.getPlayers().get(i-1).getName());
                textFieldHP.setText("" + user.getPlayers().get(i-1).getHp());
                textFieldAttack.setText("" + user.getPlayers().get(i-1).getAttack());
                chckbxDM.setSelected(user.getPlayers().get(i-1).isDM());
                currentCharater = i - 1;
            } else {
                currentCharater =  -1;
            }
        }
    }
}

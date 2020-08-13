package com.ehb.dnd.view;

import com.ehb.dnd.model.Player;
import com.ehb.dnd.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyFrame extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldLobby;

    private User user;
    private Player player;
    private ClientSideConnectionTest csc;


    public LobbyFrame(User user, Player player) throws HeadlessException {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 250, 250);
        this.user = user;
        this.player = player;
        this.csc = new ClientSideConnectionTest();
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{258, 258, 0};
        gbl_contentPane.rowHeights = new int[]{88, 88, 88, 88, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);
        if(this.player.isDM()){
            System.out.println("this.player.isDM()");
            JLabel lblNewLabel = new JLabel("Lobby: ");
            GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
            gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
            gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel.gridx = 0;
            gbc_lblNewLabel.gridy = 0;
            contentPane.add(lblNewLabel, gbc_lblNewLabel);

            System.out.println("this.player.isDM()2");
            int lobbyId = this.csc.makeLobby();

            System.out.println("this.player.isDM() = " + lobbyId);
            JLabel lblLobby = new JLabel("Give this to " + lobbyId );
            GridBagConstraints gbc_lblLobby = new GridBagConstraints();
            gbc_lblLobby.fill = GridBagConstraints.HORIZONTAL;
            gbc_lblLobby.insets = new Insets(0, 0, 5, 5);
            gbc_lblLobby.gridx = 0;
            gbc_lblLobby.gridy = 1;
            contentPane.add(lblLobby, gbc_lblLobby);

            JButton btnPlay = new JButton("Go Play!");
            btnPlay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    goNext();
                }
            });
            GridBagConstraints gbc_btnPlay = new GridBagConstraints();
            gbc_btnPlay.fill = GridBagConstraints.BOTH;
            gbc_btnPlay.gridx = 0;
            gbc_btnPlay.gridy = 2;
            contentPane.add(btnPlay, gbc_btnPlay);
        } else {
            JLabel lblNewLabel = new JLabel("Lobby: ");
            GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
            gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
            gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel.gridx = 0;
            gbc_lblNewLabel.gridy = 0;
            contentPane.add(lblNewLabel, gbc_lblNewLabel);

            textFieldLobby = new JTextField();
            GridBagConstraints gbc_textFieldLobby = new GridBagConstraints();
            gbc_textFieldLobby.fill = GridBagConstraints.HORIZONTAL;
            gbc_textFieldLobby.insets = new Insets(0, 0, 5, 0);
            gbc_textFieldLobby.gridx = 0;
            gbc_textFieldLobby.gridy = 1;
            textFieldLobby.setColumns(10);
            JPanel panelHP = new JPanel();
            panelHP.add(textFieldLobby);
            contentPane.add(panelHP, gbc_textFieldLobby);
            PlainDocument docHP = (PlainDocument) textFieldLobby.getDocument();
            docHP.setDocumentFilter(new MyIntFilter());

            JButton btnPlay = new JButton("Go Play!");
            btnPlay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(getInLobby())
                        goNext();
                }
            });
            GridBagConstraints gbc_btnPlay = new GridBagConstraints();
            gbc_btnPlay.fill = GridBagConstraints.BOTH;
            gbc_btnPlay.gridx = 0;
            gbc_btnPlay.gridy = 2;
            contentPane.add(btnPlay, gbc_btnPlay);

        }
    }

    private boolean getInLobby() {
        int lobbyId = Integer.parseInt(textFieldLobby.getText());
        if(lobbyId>-1){
            return this.csc.getInLobby(lobbyId);
        }
        return false;
    }

    private void goNext() {
        this.setVisible(false);
        if(user != null && player != null && csc != null){
            JFrame frame = new GameFrame(user, player, csc);
        }
    }
}

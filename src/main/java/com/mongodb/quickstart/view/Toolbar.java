package com.mongodb.quickstart.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Toolbar extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton makeMapButton;
    private JButton northButton;
    private JButton southMapButton;
    private JButton eastMapButton;
    private JButton westMapButton;
    private JButton fightButton;
    private JButton runButton;
    private StringListener textListener;

    //Give another interface to the DM
    public Toolbar(boolean isDM) {
        if(isDM){
            makeMapButton = new JButton("Make map");
            makeMapButton.addActionListener(this);
            northButton = new JButton("Up");
            northButton.addActionListener(this);
            southMapButton = new JButton("Down");
            southMapButton.addActionListener(this);
            eastMapButton = new JButton("Right");
            eastMapButton.addActionListener(this);
            westMapButton = new JButton("Left");
            westMapButton.addActionListener(this);
            setLayout(new FlowLayout(FlowLayout.LEFT));

            add(makeMapButton);
            add(northButton);
            add(southMapButton);
            add(eastMapButton);
            add(westMapButton);
        } else {
            fightButton = new JButton("Attack!");
            fightButton.addActionListener(this);
            runButton = new JButton("Run away!");
            runButton.addActionListener(this);
            setLayout(new FlowLayout(FlowLayout.LEFT));
            add(fightButton);
            add(runButton);
        }

    }

    public void setStringListener(StringListener listener) {
        this.textListener = listener;
    }
    public void toggleButtons(boolean isDM) {
        //Disable all the buttons
        if(isDM) {
            makeMapButton.setEnabled(false);
            northButton.setEnabled(false);
            southMapButton.setEnabled(false);
            eastMapButton.setEnabled(false);
            westMapButton.setEnabled(false);
        } else {
            fightButton.setEnabled(false);
            runButton.setEnabled(false);
        }
    }
    public void untoggleButtons(boolean isDM) {
        //Enable all the buttons
        if(isDM) {
            makeMapButton.setEnabled(true);
            northButton.setEnabled(true);
            southMapButton.setEnabled(true);
            eastMapButton.setEnabled(true);
            westMapButton.setEnabled(true);
        } else {
            fightButton.setEnabled(true);
            runButton.setEnabled(true);
        }
    }
    @Override
    //When clicked on a button do something
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();
        if(clicked == makeMapButton) {
            if(textListener != null) {
                try {
                    textListener.makeMap();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(clicked == northButton) {
            if(textListener != null) {
                try {
                    textListener.clickNorth();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(clicked == southMapButton) {
            if(textListener != null) {
                try {
                    textListener.clickSouth();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(clicked == eastMapButton) {
            if(textListener != null) {
                try {
                    textListener.clickEast();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(clicked == westMapButton) {
            if(textListener != null) {
                try {
                    textListener.clickWest();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(clicked == fightButton) {
            if(textListener != null) {
                try {
                    textListener.clickFight();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if(clicked == runButton) {
            if(textListener != null) {
                try {
                    textListener.clickRun();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


}

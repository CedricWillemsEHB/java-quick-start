package com.mongodb.quickstart.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    /**
     * JPanel to show the image
     */
    private static final long serialVersionUID = 1L;
    private BufferedImage image;

    public ImagePanel() {
    }
    //Change image and paint it
    void setImage(BufferedImage image)
    {
        this.image = image;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (image != null)
        {
            g.drawImage(image, 0, 0, null);
        }
    }
}

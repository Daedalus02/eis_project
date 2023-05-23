package org.project;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame3 extends  JFrame implements ActionListener {
    private final static int SCREEN_WIDTH = 800;
    private final static int SCREEN_HEIGHT = 600;
    private JButton[] buttons2;
    private JPanel[] panels2;
    private JLabel[] labels2;
    private JTextArea field2;
    private String[] elements;
    private int elementPosition = 0;

    /**
     * this class allow a minimal user interface for setting the parameters of the research in the "the Guardian" sites content
     */
    public Frame3(String[] elements1){
        //setting the variable that holds tokens
        elements = elements1;

        //setting the frame to then host panels adn components like buttons, labels, textfields...
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setFocusable(false);
        this.getContentPane().setBackground(Color.white);

        //DEFINING VARIABLE FOR FRAME STATE
        labels2 = new JLabel[1];
        labels2[0] = new JLabel();
        setLabels(labels2[0]);
        labels2[0].setText("PRINTING THE RESULTS:");
        labels2[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        labels2[0].setPreferredSize(new Dimension(800,100));
        labels2[0].setAlignmentX(Component.CENTER_ALIGNMENT);

        buttons2 = new JButton[3];

        buttons2[0] = new JButton();
        setButton(buttons2[0]);
        buttons2[0].setIcon(new ImageIcon("res/images/left.png"));

        buttons2[1] = new JButton();
        setButton(buttons2[1]);
        buttons2[1].setIcon(new ImageIcon("res/images/right.png"));

        buttons2[2] = new JButton();
        setButton(buttons2[2]);
        buttons2[2].setText("STOP");

        field2 = new JTextArea();
        field2.setBackground(Color.white);
        field2.setPreferredSize(new Dimension(500,400));
        field2.setLayout(new GridLayout());
        field2.setFont(new Font("Monospaced",Font.BOLD,40));
        field2.setVisible(true);

        panels2 = new JPanel[3];

        panels2[0] = new JPanel();
        setPanel(panels2[0]);
        panels2[0].setLayout(new GridLayout(1,1));
        panels2[0].add(labels2[0]);

        panels2[1] = new JPanel();
        setPanel(panels2[1]);
        panels2[1].setLayout(new BorderLayout());
        panels2[1].add(buttons2[0],BorderLayout.WEST);
        panels2[1].add(field2,BorderLayout.CENTER);
        panels2[1].add(buttons2[1],BorderLayout.EAST);

        panels2[2] = new JPanel();
        setPanel(panels2[2]);
        panels2[2].setLayout(new GridLayout(1,1));
        panels2[2].add(buttons2[2]);

        this.add(panels2[0],BorderLayout.NORTH);
        this.add(panels2[1],BorderLayout.CENTER);
        this.add(panels2[2],BorderLayout.SOUTH);
        this.revalidate();
    }

    //this method is used by this class to set a given button with particular aspects (color, font,...)
    private void setButton(JButton button){
        button.setFont(new Font("Monospaced",Font.BOLD,40));
        button.setVerticalTextPosition(button.CENTER);
        button.setHorizontalTextPosition(button.CENTER);
        button.setBounds(225, 25, 150, 75);
        button.setPreferredSize(new Dimension(150,75));
        button.setVisible(true);
        button.setFocusable(false);
        button.addActionListener(this);
        button.setForeground(Color.gray);
        button.setBackground(Color.lightGray);
        button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3, false));
    }

    //this method is used by this class to set a given panel with specific layout, dimension, background
    private void setPanel(JPanel panel){
        panel.setVisible(true);
        panel.setBackground(Color.white);
    }

    private void setLabels(JLabel label){
        label.setFont(new Font("Monospaced",Font.BOLD,20));
        label.setForeground(Color.DARK_GRAY);
    }


    //this is a method inherited by the interface ActionListener, it is always invoked when an action occurs
    @Override
    public void actionPerformed(ActionEvent event) {
        //determining the source of the action event "event"
       if (event.getSource().equals(buttons2[0])) {
            System.out.println("Left");
            if(elementPosition > 0){
                elementPosition--;
                field2.setText(elements[elementPosition]);
            }
        }else if (event.getSource().equals(buttons2[1])){
            System.out.println("Right");
            if(elementPosition < elements.length){
                field2.setText(elements[elementPosition]);
                elementPosition++;
            }
        }else if (event.getSource().equals(buttons2[2])){
            System.out.println("Frame 3 finished");
            System.exit(0);
        }
    }
}

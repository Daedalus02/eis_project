package org.project;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.*;

public class Frame2c extends  JFrame implements ActionListener, Runnable {
    private final static int SCREEN_WIDTH = 800;
    private final static int SCREEN_HEIGHT = 600;
    private JButton startButton;
    private JPanel panel;
    private JPanel[] textPanel;
    private JPanel startPanel;
    private JTextField[] text;
    private JLabel[] labels;
    private String[] answer;
    private String[] elements;
    private BlockingQueue<String> writingQueue;
    private BlockingQueue<String> readingQueue;

    /**
     * this class allow a minimal user interface for setting the parameters of the research in the "the Guardian" sites content
     */
    public Frame2c(BlockingQueue<String> reading, BlockingQueue<String> writing){
        //setting the BlockingQueues
        readingQueue = reading;
        writingQueue = writing;

        //setting the variable that holds tokens
        elements = new String[50];

        //setting answer variable
        answer = new String[2];


        //setting the frame to then host panels adn components like buttons, labels, textfields...
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setLayout(new GridLayout(3,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setFocusable(false);
        this.getContentPane().setBackground(Color.white);

        //initializing button to confirm the entered parameters
        startButton = new JButton();
        setButton(startButton);
        startButton.setText("START");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        //setting all text fields for user to enter the parameters
        text = new JTextField[2];
        text[0] = new JTextField();
        setText(text[0],"");
        text[0].addActionListener(this);
        text[1] = new JTextField();
        setText(text[1],"");
        text[1].addActionListener(this);

        //setting the label to display at the top of the frame
        labels = new JLabel[2];
        labels[0] = new JLabel();
        setLabels(labels[0]);
        labels[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        labels[0].setText("Enter the name of the csv here:");
        labels[1] = new JLabel();
        setLabels(labels[1]);
        labels[1].setAlignmentX(Component.CENTER_ALIGNMENT);
        labels[1].setText("Enter the name of the file here:");


        startPanel = new JPanel();
        setPanel(startPanel);
        startPanel.add(startButton);

        textPanel = new JPanel[2];
        textPanel[0] = new JPanel();
        setPanel(textPanel[0]);
        textPanel[0].add(labels[0]);
        textPanel[0].add(text[0]);
        textPanel[1] = new JPanel();
        setPanel(textPanel[1]);
        textPanel[1].add(labels[1]);
        textPanel[1].add(text[1]);

        //adding panel to the frame
        this.add(textPanel[0]);
        this.add(textPanel[1]);
        this.add(startPanel);
        this.validate();
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
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setVisible(true);
        panel.setPreferredSize(new Dimension(300,250));
        panel.setBackground(Color.white);
    }


    private void setLabels(JLabel label){
        label.setFont(new Font("Monospaced",Font.BOLD,20));
        label.setForeground(Color.DARK_GRAY);
    }

    //this method is used by this class to set a given textField with specific properties like font, color, ...
    private void setText(JTextField text,String str){
        text.setText(str);
        text.setFont(new Font("Monospaced",Font.BOLD,15));
        text.setPreferredSize(new Dimension(600,100));
        text.setBackground(new Color(220,220,220));
        text.setForeground(Color.gray);
        text.setBorder(BorderFactory.createLineBorder(Color.darkGray/*new Color(0,0,70)*/, 1, false));
    }

    //this is a method inherited by the interface ActionListener, it is always invoked when an action occurs
    @Override
    public void actionPerformed(ActionEvent event) {

        //determining the source of the action event "event"
        if (event.getSource().equals(startButton)) {
            Boolean valid = true;
            System.out.println("BUTTON PRESSED!");
            for(int i = 0; i < 2; i++ ) {
                answer[i] = text[i].getText();
                if(answer[i].equals("")){
                    System.out.println("not  valid");
                    valid = false;
                }else{
                    System.out.println(answer[i]);
                }
            }
            if(valid) {
                //SETTINGS PHASE

                //setting file name
                String csvName = text[0].getText();
                String fileName = text[1].getText();

                //setting the blocking queue with the read values
                try {
                    System.out.println("Frame is entering the keywords");
                    writingQueue.put(csvName);
                    writingQueue.put(fileName);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Frame is trying to read the 50 tokens");
                boolean smtRead = true;
                try {
                    while(true){
                        sleep(100);
                        if(readingQueue.size() != 0){
                            break;
                        }
                    }
                    for(int i = 0; i < elements.length; i++ ){
                        if((elements[i] = readingQueue.poll()) == null) {
                            smtRead = false;
                            break;
                        }
                    }
                    System.out.println("Frame finished reading tokens");

                    //PRINTING IN PANEL
                    if(smtRead){
                        new Frame3(elements);
                        System.out.println("Frame 2 finished");
                        this.setVisible(false);
                    }else{
                        throw new IllegalStateException();
                    }
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    @Override
    public void run() {

    }
}

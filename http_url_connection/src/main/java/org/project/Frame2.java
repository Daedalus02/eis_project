package org.project;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.*;

public class Frame2 extends  JFrame implements ActionListener, MouseListener, Runnable {
    private final static int SCREEN_WIDTH = 800;
    private final static int SCREEN_HEIGHT = 600;
    private JButton startButton;
    private JPanel[] panels1;
    private JTextField[] texts;
    private JLabel[] labels1;
    private boolean[] selected;
    private String[] answer;
    private String[] elements;
    private BlockingQueue<String> writingQueue;
    private BlockingQueue<String> readingQueue;

    /**
     * this class allow a minimal user interface for setting the parameters of the research in the "the Guardian" sites content
     */
    public Frame2(BlockingQueue<String> reading, BlockingQueue<String> writing){
        //setting the BlockingQueue
        readingQueue = reading;
        writingQueue = writing;

        //setting the variable that holds tokens
        elements = new String[50];

        //setting the frame to then host panels adn components like buttons, labels, textfields...
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setLayout(new GridLayout(6,1));
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
        texts = new JTextField[4];

        texts[0] = new JTextField();
        setText(texts[0],"");
        texts[0].addActionListener(this);
        texts[0].addMouseListener(this);

        texts[1] = new JTextField();
        setText(texts[1],"");
        texts[1].addActionListener(this);
        texts[1].addMouseListener(this);

        texts[2] = new JTextField();
        setText(texts[2],"");
        texts[2].addActionListener(this);
        texts[2].addMouseListener(this);

        texts[3] = new JTextField();
        setText(texts[3],"");
        texts[3].addActionListener(this);
        texts[3].addMouseListener(this);


        //setting the label to display at the top of the frame
        labels1 = new JLabel[5];
        for(int i = 0; i < labels1.length; i++){
            labels1[i] = new JLabel();
            setLabels(labels1[i]);
        }
        labels1[0].setText("DO YOU WANNA PLAY GAME?");
        labels1[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        labels1[1].setText("Enter the text for the query here:");
        labels1[2].setText("Enter the number of articles for the research here: ");
        labels1[3].setText("Enter the api_key for the research here: ");
        labels1[4].setText("Do you want to visualize the 50 more frequent words? (Y/N)");

        //setting all the panels to group components in the frame
        panels1 = new JPanel[6];
        panels1[0] = new JPanel();
        setPanel(panels1[0]);
        panels1[0].add(labels1[0]);
        panels1[0].addMouseListener(this);

        panels1[1]=new JPanel();
        setPanel(panels1[1]);
        panels1[1].add(labels1[1]);
        panels1[1].add(texts[0]);

        panels1[2]=new JPanel();
        setPanel(panels1[2]);
        panels1[2].add(labels1[2]);
        panels1[2].add(texts[1]);

        panels1[3]=new JPanel();
        setPanel(panels1[3]);
        panels1[3].add(labels1[3]);
        panels1[3].add(texts[2]);

        panels1[4]=new JPanel();
        setPanel(panels1[4]);
        panels1[4].add(labels1[4]);
        panels1[4].add(texts[3]);

        panels1[5] = new JPanel();
        setPanel(panels1[5]);
        panels1[5].add(startButton);

        //this is used to keep track of which text field is selected in each moment
        selected = new boolean[4];
        answer = new String[4];

        //adding panels to the frame
        this.add(panels1[0]);
        this.add(panels1[1]);
        this.add(panels1[2]);
        this.add(panels1[3]);
        this.add(panels1[4]);
        this.add(panels1[5]);
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

    private void setPanel2(JPanel panel){
        panel.setVisible(true);
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
            for(int i = 0; i < 4; i++ ) {
                answer[i] = texts[i].getText();
                if(answer[i].equals("")){
                    System.out.println("not  valid");
                    valid = false;
                }else{
                    System.out.println(answer[i]);
                }
            }
            if(valid) {
                //SETTINGS PHASE

                //setting query
                String query = texts[0].getText();

                //setting max articles number
                String maxArticle = texts[1].getText();

                //setting api_key
                String apiKey = texts[2].getText();

                //setting download answer
                String downloadAnswer = texts[3].getText();

                //setting the blocking queue with the read values
                try {
                    System.out.println("Frame is entering the keywords");
                    writingQueue.put(apiKey);
                    writingQueue.put(query);
                    writingQueue.put(maxArticle);
                    writingQueue.put(downloadAnswer);
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
                    }
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    //this method is used by this class to keep track of the selected text field and updating the colors of each of them basing on the value the state variable selected
    private void textSelected( int index){
        texts[index].setText("");
        selected[index] = true;
        texts[index].setBackground(Color.darkGray);
        for(int i = 0; i < 4; i++){
            if(i != index){
                texts[i].setBackground(new Color(220, 220, 220));
                selected[i] = false;
            }
        }
    }

    /**
     * Inherited method from the interface MouseListener
     * These methods are always called when a specific mouse event happens to the components added to the MouseListener class(Frame)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource().equals(texts[0])){
            textSelected(0);
        }else if(e.getSource().equals(texts[1])){
            textSelected(1);
        }else if(e.getSource().equals(texts[2])){
            textSelected(2);
        }else if(e.getSource().equals(texts[3])){
            textSelected(3);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(!(selected[0] || selected[1] || selected[2] || selected[3])) {
            if (e.getSource().equals(texts[0])) {
                texts[0].setBackground(Color.darkGray);
            } else if (e.getSource().equals(texts[1])) {
                texts[1].setBackground(Color.darkGray);
            } else if (e.getSource().equals(texts[2])) {
                texts[2].setBackground(Color.darkGray);
            }else if(e.getSource().equals(texts[3])){
                texts[3].setBackground(Color.darkGray);
            }
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!(selected[0] || selected[1] || selected[2] || selected[3])) {
            if (e.getSource().equals(texts[0])) {
                texts[0].setBackground(new Color(220, 220, 220));
            } else if (e.getSource().equals(texts[1])) {
                texts[1].setBackground(new Color(220, 220, 220));
            } else if (e.getSource().equals(texts[2])) {
                texts[2].setBackground(new Color(220, 220, 220));
            } else if (e.getSource().equals(texts[3])) {
                texts[3].setBackground(new Color(220, 220, 220));
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void run() {

    }
}

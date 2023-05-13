package org.project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Frame extends  JFrame implements ActionListener, MouseListener {
    private final static int SCREEN_WIDTH = 1000;
    private final static int SCREEN_HEIGHT = 400;
    private final static int DELAY = 100;
    private Panel panel;
    private JButton startButton;
    private JPanel[] panels;
    private JTextField[] texts;
    private  JLabel[] labels;
    private boolean[] selected;

    private String[] answare;

    /**
     * this class allow a minimal user interface for setting the parameters of the research in the "the Guardian" sites content
     */
    public Frame(){
        //setting the frame to then host panels adn components like buttons, labels, textfields...
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setLayout(new GridLayout(6,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setFocusable(false);
        this.getContentPane().setBackground(Color.white);

        //initializing buttons to confirm the entered parameters
        startButton =new JButton();
        setButton(startButton);


        //setting all text fields for user to enter the parameters
        texts = new JTextField[4];
        texts[0] = new JTextField();
        setText(texts[0],"Enter the text for the query here: ");
        texts[0].addActionListener(this);
        texts[0].addMouseListener(this);

        texts[1] = new JTextField();
        setText(texts[1],"Enter the number of articles for the search here: ");
        texts[1].addActionListener(this);
        texts[1].addMouseListener(this);

        texts[2] = new JTextField();
        setText(texts[2],"Enter the content for the search here: ");
        texts[2].addActionListener(this);
        texts[2].addMouseListener(this);

        texts[3] = new JTextField();
        setText(texts[3],"Enter the tag for the search here: ");
        texts[3].addActionListener(this);
        texts[3].addMouseListener(this);

        //setting the label to display at the top of the frame
        labels = new JLabel[1];
        labels[0] = new JLabel();
        labels[0].setFont(new Font("Monospaced",Font.BOLD,20));
        labels[0].setForeground(Color.DARK_GRAY);
        labels[0].setText("DO YOU WANNA PLAY GAME?\n");

        //setting all the panels to group components in the frame
        panels = new JPanel[6];
        panels[0] = new JPanel();
        setPanel(panels[0]);
        panels[0].add(labels[0]);
        panels[0].addMouseListener(this);

        panels[1]=new JPanel();
        setPanel(panels[1]);
        panels[1].add(texts[0]);


        panels[2]=new JPanel();
        setPanel(panels[2]);
        panels[2].add(texts[1]);

        panels[3]=new JPanel();
        setPanel(panels[3]);
        panels[3].add(texts[2]);

        panels[4]=new JPanel();
        setPanel(panels[4]);
        panels[4].add(texts[3]);

        panels[5] = new JPanel();
        setPanel(panels[5]);
        panels[5].add(startButton);

        //this is used to keep track of which text field is selected in each moment
        selected = new boolean[4];
        answare = new String[4];

        //adding panels to the frame
        this.add(panels[0]);
        this.add(panels[1]);
        this.add(panels[2]);
        this.add(panels[3]);
        this.add(panels[4]);
        this.add(panels[5]);
        this.validate();
    }

    //this method is used by this class to set a given button with particular aspects (color, font,...)
    private void setButton(JButton button){
        button.setFont(new Font("Monospaced",Font.BOLD,40));
        button.setText("START");
        button.setVerticalTextPosition(startButton.CENTER);
        button.setHorizontalTextPosition(startButton.CENTER);
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
        panel.setLayout(new GridLayout(1,1));
        panel.setVisible(true);
        panel.setPreferredSize(new Dimension(300,250));
        panel.setBackground(Color.white);
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
    public void actionPerformed(ActionEvent e) {

        //determining the source of the action event "e"
        if (e.getSource().equals(startButton)) {
            System.out.println("BUTTON PRESSED!");
            for(int i = 0; i < 4; i++ ){
                System.out.println(answare[i]);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                if (e.getSource().equals(texts[i])) {
                    answare[i] = texts[i].getText();
                    System.out.println(answare[i]);
                    texts[i].setText("");
                    texts[i].setBackground(new Color(220, 220, 220));
                    selected[i] = false;
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
            } else if (e.getSource().equals(texts[3])) {
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
}

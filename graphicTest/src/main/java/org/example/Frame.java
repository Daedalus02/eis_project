package org.example;
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
    private JButton button6;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JLabel label1;
    private JTextField text2;
    private JTextField text3;
    private JTextField text4;
    private JTextField text5;

    public Frame(){
        //panel = new Panel();
        //this.add(panel);
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setLayout(new GridLayout(6,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setFocusable(false);
        this.getContentPane().setBackground(Color.white);
        //this.addMouseListener(this);

        button6 =new JButton();
        setButton(button6);

        text2 = new JTextField();
        setText(text2,"Enter the text for the query here: ");
        text2.addActionListener(this);
        text2.addMouseListener(this);

        text3 = new JTextField();
        setText(text3,"Enter the number of articles for the search here: ");
        text3.addActionListener(this);
        text3.addMouseListener(this);

        text4 = new JTextField();
        setText(text4,"Enter the content for the search here: ");
        text4.addActionListener(this);
        text4.addMouseListener(this);

        text5 = new JTextField();
        setText(text5,"Enter the tag for the search here: ");
        text5.addActionListener(this);
        text5.addMouseListener(this);

        label1 = new JLabel();
        label1.setFont(new Font("Monospaced",Font.BOLD,20));
        label1.setForeground(Color.DARK_GRAY);
        label1.setText("DO YOU WANNA PLAY GAME?\n");

        panel1 = new JPanel();
        setPanel(panel1);
        panel1.add(label1);

        panel2=new JPanel();
        setPanel(panel2);
        panel2.add(text2);
        panel1.addMouseListener(this);

        panel3=new JPanel();
        setPanel(panel3);
        panel3.add(text3);

        panel4=new JPanel();
        setPanel(panel4);
        panel4.add(text4);

        panel5=new JPanel();
        setPanel(panel5);
        panel5.add(text5);

        panel6 = new JPanel();
        setPanel(panel6);
        panel6.add(button6);

        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);
        this.add(panel5);
        this.add(panel6);
        this.validate();
    }
    private void setButton(JButton button){
        button.setFont(new Font("Monospaced",Font.BOLD,40));
        button.setText("START");
        button.setVerticalTextPosition(button6.CENTER);
        button.setHorizontalTextPosition(button6.CENTER);
        button.setBounds(225, 25, 150, 75);
        button.setPreferredSize(new Dimension(150,75));
        button.setVisible(true);
        button.setFocusable(false);
        button.addActionListener(this);
        button.setForeground(Color.gray/*new Color(255,255,102)*/);
        button.setBackground(Color.lightGray);
        button.setBorder(BorderFactory.createLineBorder(Color.darkGray/*new Color(0,0,70)*/, 3, false));
    }
    public void setPanel(JPanel panel){
        panel.setLayout(new GridLayout(1,1));
        panel.setVisible(true);
        panel.setPreferredSize(new Dimension(300,250));
        panel.setBackground(Color.white);
    }
    public void setText(JTextField text,String str){
        text.setText(str);
        text.setFont(new Font("Monospaced",Font.BOLD,15));
        text.setBackground(new Color(220,220,220));
        text.setForeground(Color.gray);
        text.setBorder(BorderFactory.createLineBorder(Color.darkGray/*new Color(0,0,70)*/, 1, false));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(button6)){
           System.out.println("BUTTON PRESSED!");
        }else if(e.getSource().equals(text2)){
            System.out.println(text2.getText());
            text2.setText("");
        }else if(e.getSource().equals(text3)){
            System.out.println(text3.getText());
            text3.setText("");
        }else if(e.getSource().equals(text4)){
            System.out.println(text4.getText());
            text4.setText("");
        }else if(e.getSource().equals(text5)){
            System.out.println(text5.getText());
            text5.setText("");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource().equals(text2)){
            text2.setBackground(Color.darkGray);
        }else if(e.getSource().equals(text3)){
            text3.setBackground(Color.darkGray);
        }else if(e.getSource().equals(text4)){
            text4.setBackground(Color.darkGray);
        }else if(e.getSource().equals(text5)){
            text5.setBackground(Color.darkGray);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource().equals(text2)){
            text2.setBackground(new Color(220,220,220));
        }else if(e.getSource().equals(text3)){
            text3.setBackground(new Color(220,220,220));
        }else if(e.getSource().equals(text4)){
            text4.setBackground(new Color(220,220,220));
        }else if(e.getSource().equals(text5)){
            text5.setBackground(new Color(220,220,220));
        }

    }
}

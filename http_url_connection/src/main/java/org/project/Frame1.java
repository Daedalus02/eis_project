package org.project;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Frame1 extends  JFrame implements ActionListener{
    private final static int SCREEN_WIDTH = 800;
    private final static int SCREEN_HEIGHT = 600;
    private JPanel panel0;
    private JLabel label0;
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem[] menuItems;

    /**
     * this class allow a minimal user interface for setting the parameters of the research in the "the Guardian" sites content
     */
    public Frame1(){
        //setting the frame to then host panels adn components like buttons, labels, textfields...
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setLayout(new GridLayout(1,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setFocusable(false);
        this.getContentPane().setBackground(Color.white);

        //DEFINING VARIABLES FOR FIRST FRAME STATE
        //create a menu bar
        menuBar = new JMenuBar();
        //create a menu
        menu = new JMenu("SOURCE");
        //create items for menu
        menuItems = new JMenuItem[3];
        menuItems[0] = new JMenuItem("CSV");
        menuItems[1] = new JMenuItem("XML");
        menuItems[2] = new JMenuItem("THE GUARDIAN API");
        menu.add(menuItems[0]);
        menu.add(menuItems[1]);
        menu.add(menuItems[2]);
        menuBar.add(menu);
        menuItems[0].addActionListener(this);
        menuItems[1].addActionListener(this);
        menuItems[2].addActionListener(this);
        //setting the first message
        label0 = new JLabel();
        setLabels(label0);
        label0.setAlignmentX(Component.CENTER_ALIGNMENT);
        label0.setText("WELCOME,\nTO START ANALYZING SELECT THE SOURCE IN THE UPPER LEFT MENU.");
        //setting the first panel
        panel0 = new JPanel();
        setPanel(panel0);
        panel0.setLayout(new GridLayout(1,1));
        panel0.add(label0);
        //adding the panel0 with the text and the menuBar to the frame
        this.setJMenuBar(menuBar);
        this.add(panel0);
        this.validate();
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


    //this is a method inherited by the interface ActionListener, it is always invoked when an action occurs
    @Override
    public void actionPerformed(ActionEvent event) {
        //determining the source of the action event "event"
        if(event.getSource().equals(menuItems[0])){
            System.out.println("1st item selected!");
            BlockingQueue<String> words = new ArrayBlockingQueue<String>(50);
            BlockingQueue<String> settings = new ArrayBlockingQueue<String>(2);
            Executer3 executer1 = new Executer3(settings,words);
            Frame2c frame2c = new Frame2c(words,settings);
            Thread thread1 = new Thread(executer1);
            Thread thread2 = new Thread(frame2c);
            thread1.start();
            thread2.start();
            System.out.println("Frame 1 finished!");
            this.setVisible(false);
        }else if(event.getSource().equals(menuItems[1])){
            System.out.println("2nd item selected!");
            BlockingQueue<String> words = new ArrayBlockingQueue<String>(50);
            BlockingQueue<String> settings = new ArrayBlockingQueue<String>(1);
            Executer2 executer2 = new Executer2(settings,words);
            Frame2b frame2b = new Frame2b(words,settings);
            Thread thread1 = new Thread(executer2);
            Thread thread2 = new Thread(frame2b);
            thread1.start();
            thread2.start();
            System.out.println("Frame 1 finished!");
            this.setVisible(false);
        }else if(event.getSource().equals(menuItems[2])){
            System.out.println("3rd item selected!");
            BlockingQueue<String> words = new ArrayBlockingQueue<String>(50);
            BlockingQueue<String> settings = new ArrayBlockingQueue<String>(4);
            Frame2a frame2a = new Frame2a(words, settings);
            Executer1 executer3 = new Executer1(settings, words);
            Thread thread1 = new Thread(frame2a);
            Thread thread2 = new Thread(executer3);
            thread1.start();
            thread2.start();
            System.out.println("Frame 1 finished!");
            this.setVisible(false);
        }
    }
}

package org.project.tests;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class test {

    private JFrame frame, frame2;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        test window = new test();
        window.frame.setVisible(true);
    }

    /**
     * Create the application.
     */
    public test() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        frame2 = new JFrame();
        frame2.setBounds(100, 100, 450, 300);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.getContentPane().setLayout(null);

        JButton btnOpenWindow = new JButton("Open Window");
        btnOpenWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame2.setVisible(true);
            }
        });
        btnOpenWindow.setBounds(167, 118, 120, 23);
        frame.getContentPane().add(btnOpenWindow);

    }
}

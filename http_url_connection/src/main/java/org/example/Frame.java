package org.example;
import org.json.JSONException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Frame extends  JFrame implements ActionListener {
    private final static int SCREEN_WIDTH = 1000;
    private final static int SCREEN_HEIGHT = 400;
    private final static int DELAY = 100;

    private JButton button1;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel label1;
    private JLabel label2;
    private JButton button2;
    private JButton button3;
    private String[] words;
    private int[] wordFrequency;
    private int textCounter = 0;

    public Frame(){
        //panel = new Panel();
        //this.add(panel);
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setLayout(new GridLayout(2,1,10,10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);
        this.setFocusable(false);
        this.getContentPane().setBackground(Color.lightGray);



        button1=new JButton();
        button1.setText("START CALCULATOR");
        setButton(button1);

        label1 = new JLabel();
        label1.setText("DO YOU WANNA PLAY GAME?\n");
        setLabel(label1,20);

        panel1=new JPanel();
        panel1.setLayout(new GridLayout(2,1));
        panel1.setVisible(true);
        panel1.setPreferredSize(new Dimension(300,250));
        panel1.setBackground(Color.lightGray);
        panel1.add(label1);
        panel1.add(button1);

        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,3));
        panel2.setVisible(true);
        panel2.setPreferredSize(new Dimension(300,250));
        panel2.setBackground(Color.lightGray);

        button2 = new JButton();
        button2.setText("<");
        setButton(button2);

        button3 = new JButton();
        button3.setText(">");
        setButton(button3);

        label2 = new JLabel();
        label2.setText("Hello");
        setLabel(label2,20);

        panel2.add(button2);
        panel2.add(label2);
        panel2.add(button3);

        words = new String[500];
        wordFrequency = new int[500];

        this.add(panel1);
        this.add(panel2);
        this.validate();
    }

    private void setButton(JButton button){
        button.setFont(new Font("Monospaced",Font.ITALIC,20));
        button.setVerticalTextPosition(button1.CENTER);
        button.setHorizontalTextPosition(button1.CENTER);
        button.setBounds(225, 25, 150, 75);
        button.setPreferredSize(new Dimension(150,75));
        button.setVisible(true);
        button.setFocusable(false);
        button.addActionListener(this);
        button.setForeground(Color.darkGray/*new Color(255,255,102)*/);
        button.setBackground(Color.lightGray);
        button.setBorder(BorderFactory.createLineBorder(Color.gray/*new Color(0,0,70)*/, 3, false));
    }

    private void setTextFormat(TextField text){
        text.setPreferredSize(new Dimension(250,40));
        text.setFont(new Font("Monospaced",Font.ITALIC,20));
        text.setForeground(Color.DARK_GRAY);
        text.setBackground(Color.white);
    }
    private void setLabel(JLabel label, int size){
        label.setFont(new Font("Monospaced",Font.BOLD,size));
        label.setForeground(Color.DARK_GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        if(action.getSource().equals(button1)){
            label2.setText("Calculating...");
            try {
                String apiString = "";
                httpGetter getter = new httpGetter(new URL("https://content.guardianapis.com/search?api-key=your_api"));
                apiString = getter.getHttpString();
                jsonParser jsonparser = new jsonParser(apiString);
                article[] articles = jsonparser.getArticles();
                htmlDownloader downloader = new htmlDownloader();
                String[] fileNames = new String[articles.length];
                int counter = 0;
                String pageContent = "";
                String pageText = "";
                Tokenizer tokenizer = new Tokenizer(pageContent,true);
                htmlParser htmlparser = new htmlParser();
                for (article  article : articles) {
                    fileNames[counter] = downloader.download(article.getWebUrl().toString());
                    Scanner reader = new Scanner(new File(fileNames[counter]));
                    while(reader.hasNextLine()){
                        pageContent += reader.nextLine();
                    }
                    pageText = htmlparser.parse(pageContent);
                    tokenizer.switchDocument(pageText);

                    System.out.println("The more 50 more frequent words in article named \""+article.getWebTitle()+"\" are: ");
                    Set<Map.Entry<Integer, java.util.List<String>>> set =  tokenizer.getOrderedTokens(50);

                    //printing 50 more frequent tokens
                    int wordCounter = 0;
                    Iterator<Map.Entry<Integer, java.util.List<String>>> iter = set.iterator();
                    Map.Entry<Integer, List<String>> pair = iter.next();
                    while(iter.hasNext()) {
                        int index = pair.getValue().size();
                        while(index > 0){
                            index--;
                            words[textCounter] = pair.getValue().get(index);
                            wordFrequency[textCounter] = pair.getKey();
                            System.out.println("          "+wordCounter+": "+words[textCounter]+" "+wordFrequency[textCounter]);
                            wordCounter++;
                            textCounter++;
                        }
                        pair = iter.next();
                    }

                    counter++;
                    pageContent = "";
                }
                textCounter = 0;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch(JSONException e){
                e.printStackTrace();
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }else if (action.getSource().equals(button3)){
            if (textCounter < 500) {
                label2.setText(words[textCounter]+" "+wordFrequency[textCounter]);
                textCounter++;
            }
        }else if (action.getSource().equals(button2)){
            if(textCounter > 0){
                textCounter--;
                label2.setText(words[textCounter]+" "+wordFrequency[textCounter]);
            }
        }

    }
}

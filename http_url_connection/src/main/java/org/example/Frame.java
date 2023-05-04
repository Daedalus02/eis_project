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
import java.util.*;
import java.util.List;


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
            Scanner console = new Scanner(System.in);
            System.out.println("enter the topic you want to investigate: ");
            String query = console.nextLine();

            int tagNumber = 0;
            System.out.println("Enter the number of tag you want to search: ");
            tagNumber = console.nextInt();
            String[] tags = new String[tagNumber];
            for(int i = 0; i < tagNumber; i++ ){
                tags[i] = console.next();
                System.out.println();
            }

            int contentNunber = 0;
            System.out.println("Enter the number of contents you want to search: ");
            contentNunber = console.nextInt();
            String[] contents = new String[contentNunber];
            for(int i = 0; i < contentNunber; i++){
                contents[i] = console.next();
                System.out.println();
            }

            System.out.println("Enter the max number of page to elaborate: ");
            int maxArticle = console.nextInt();
            int pageCount = 1;
            int articleCount = 0;

            try {
                Tokenizer tokenizer = new Tokenizer("", true);

                String pageText = " ";
                String url = "";
                httpGetter getter;
                String apiString ="";
                jsonParser jsonparser;
                List<Article> articles = new ArrayList<Article>();
                htmlDownloader downloader;
                String fileName = "res\\pages\\test.xml";

                while(articleCount < maxArticle) {
                    url = (new urlSetter("https://content.guardianapis.com", "your_api_key", pageCount, query, new String[]{}, new String[]{})).getUrl();
                    System.out.println("from " + url + " :");
                    getter = new httpGetter(new URL(url));
                    apiString = getter.getHttpString();

                    jsonparser = new jsonParser(apiString);
                    articles .addAll(Arrays.stream(jsonparser.getArticles()).toList());

                    if(jsonparser.getPages() < maxArticle){
                        maxArticle = jsonparser.getPages();
                        System.out.println("Limited to "+maxArticle+" pages...");
                    }

                    downloader = new htmlDownloader();

                    for (Article article : articles.subList(articleCount,articles.size())) {
                        if(articleCount == maxArticle){
                            break;
                        }
                        System.out.println("Anayzing site number: "+(articleCount+1)+" with title: "+article.getWebTitle());
                        downloader.download(article);
                        pageText = article.getHead() + article.getBody();
                        tokenizer.addDocument(pageText);
                        articleCount++;
                    }

                    pageCount++;
                }
                Serializer serializer = new Serializer();
                serializer.serialize(articles,fileName);
                //printing 50 more frequent tokens
                System.out.println("The 50 more frequent words in the analyzed articles are: ");
                Set<Map.Entry<Integer, List<String>>> set = tokenizer.getOrderedTokens(50);

                int wordCounter = 0;
                Iterator<Map.Entry<Integer, List<String>>> iter = set.iterator();
                Map.Entry<Integer, List<String>> pair = iter.next();
                while (iter.hasNext()) {
                    int index = pair.getValue().size();
                    while (index > 0) {
                        index--;
                        System.out.println("          " + (wordCounter + 1) + ": " + pair.getValue().get(index) + " " + pair.getKey());
                        wordCounter++;
                    }
                    pair = iter.next();
                }
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

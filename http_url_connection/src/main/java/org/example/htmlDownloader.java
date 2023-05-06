package org.example;
        import java.io.*;
        import java.net.MalformedURLException;
        import java.net.URL;

        import com.thoughtworks.xstream.XStream;
        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import javax.swing.text.BadLocationException;

/**
 *
 */
public class htmlDownloader {
    public htmlDownloader(){

    }

    /**
     *
     * @param article
     * @throws MalformedURLException
     * @throws IOException
     * @throws BadLocationException
     */
    public void download(Article article) throws MalformedURLException, IOException, BadLocationException {


        URL url = new URL(article.getWebUrl().toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        String line = "";
        String htmlString = "";
        while((line = reader.readLine()) != null) {
            htmlString += (" " + line);
        }

        Document doc =Jsoup.parse(htmlString);
        String head = doc.head().text();
        String body = doc.body().text();
        article.setBody(body);
        article.setHead(head);

        reader.close();
    }
}

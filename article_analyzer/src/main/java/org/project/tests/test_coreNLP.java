package org.project.tests;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class test_coreNLP {
    public static void main(String Args[]) {
        String text = "Lance Franklin has been reunited with the ball he kicked for his historic 1,000th AFL goal on Friday night after the fan who caught it in the stands of the Sydney Cricket Ground was identified and invited to present the prized Sherrin to the Sydney Swans player. At a press conference held with Franklin at the SCG on Monday morning, Swans fan Alex Wheeler said he had always planned to return the valuable piece of memorabilia – reported to have a six-figure value. “I had a few stubbies that night and went to the pub after but got paranoid that someone would get it from my house so went home pretty early,” Wheeler said on Monday. “It was always my intention to get it back to the Swans and Buddy. Related: Part riot, part coronation: Lance Franklin milestone ignites SCG and caps joyful career | Jonathan Horn “It’s going to be no good to me, I don’t have a pool room to put it up in unfortunately. It was good to get in contact with the Swans and get it back to them.” Franklin entered the annals of AFL history when he became just the sixth man to kick 1,000 career AFL/VFL career goals, sparking wild scenes of celebration at the SCG as the crowd invaded the pitch en masse and play was held up for more than half an hour. The Swans launched an appeal to track down Wheeler after video footage emerged on social media of him falling into the row of seats in front of him as he attempted to catch the ball, before emerging from the pandemonium as other fans stormed on to the pitch shouting, “I got the pill!” “When Buddy marked the ball he was about 10 [metres] inside the 50. I did a bit of quick maths and realised I’m probably 10 or 15 from the fence, so I thought [I was a] really, really big chance,” he said. “From that point on there were so many people rushing to the fence, which sort of probably helped me get to the ball – everyone was filming. I almost felt like I was the only guy throwing the paw out to get it. Just unbelievable that I had the chance to have a crack at it, it just landed in the paw and the rest is history.” Wheeler managed to hold on to the ball and enter the field of play himself – where he did a couple of handballs to himself – before exiting the stadium and heading home to stash it in a “secret place” in his room: his cupboard. The Swans made a public appeal for it to be returned to the club, proposing a meeting with whoever had possession of it to make a presentation to Franklin in person. Wheeler said he was aware of its potential value, but that selling it for a fortune was “not the right way to go”. The ball is back with Bud! #Buddy1000 pic.twitter.com/DufmPGQE4C — Sydney Swans (@sydneyswans) March 27, 2022 Franklin thanked Wheeler for delivering the ball to him and said “it means the world to me”. “It’s nice of Alex to give the ball back,” Franklin said. “I haven’t had too much time to reflect on it just yet but obviously it was a significant moment. I absolutely loved it. “It was a really special night – to see so many Swans supporters out here, especially with what happened the last couple of years with Covid, to have a packed stadium was very special and then to kick the goal was awesome. “You could feel it, really, kicking those three goals, you feel the crowd coming down early in the fourth, and then I was lucky enough for Horse [coach John Longmire] to put me back on the ground.” Given its historic nature, the ball has an estimated value of $200,000 according to some reports. A normal Sherrin match ball costs just $200. Wheeler posed with Franklin on Monday holding the cherished ball, and was compensated with another ball, a shirt and a pair of footy boots, all signed by Franklin. He was also gifted two five-year club memberships and a thank you letter signed by Longmire. After the goal was kicked, the match was held up for 33 minutes, as Franklin slowly made his way through the throng of people off the field and the crowd eventually returned to the stands. At one point, even getting the game back under way appeared unlikely, with stories later emerging of two Swans players who found themselves outside the ground amid the chaos. Another crazy image from last night. Ollie and Chad walking down Driver Ave back to the rooms before the restart after the crowd pitch invasion! \uD83D\uDE2E #Buddy1000 pic.twitter.com/0cpr8zwcBS — Sydney Swans (@sydneyswans) March 26, 2022";
        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize");
        //props.setProperty("annotators", "tokenize,pos,lemma,ner,parse,depparse,coref,kbp,quote");
        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        //props.setProperty("coref.algorithm", "neural");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument document = pipeline.processToCoreDocument(text);
        List<String> l = document.tokens().stream().map(coreLabel -> coreLabel.toString().toLowerCase()).distinct().collect(Collectors.toList());
        List<String> words = Arrays.stream(text.split("[\\p{Punct}\\s.!?”“–—’‘'…+1234567890-]")).distinct().collect(Collectors.toList());
        for (String li : l) {
            System.out.println(li);
        }
        System.out.println("STOP");
        for(String lu : words) {
            if (!(Pattern.matches("\\p{Punct}", lu) | Pattern.matches("[.!?”“–—’‘'…-]", lu) | lu.equals("") )) {
                System.out.println(lu);
            }
        }

    }
}
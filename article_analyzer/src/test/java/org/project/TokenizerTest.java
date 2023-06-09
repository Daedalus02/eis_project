package org.project;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/* This class contains test for the Tokenizer class method tokenize, setting the presence of checks(common words)
 * and the dimension of the String to tokenize.  */
@Tag("Backlog")
@Tag("CSV")
@Tag("API")
class TokenizerTest {
    // This variable store the general path for the articles.
    private final String RELATIVE_PATH = "test_resources" + File.separator + "articles" + File.separator;
    // This variable is used to store the value of article 1.
    private String article1 = "Atomic Hope review – a powerful case for pressing the nuclear power button\n" +
            "It’s not the first doc to herald the eco-nuclear movement but, even so, this is still a convincing argument in favour of the long-tabooed energy source\n" +
            "Here is a film that returns us to a thorny revisionist subject which I haven’t seen aired in documentary form since the film Pandora’s Promise in 2013\n" +
            "– which isn’t mentioned here, though a poster for it is visible in one shot. For many environmentalists, the last realistic hope we have to avert climate\n" +
            "disaster is the great unthinkable, the great unmentionable: stop worrying and learn to love nuclear energy, because nuclear is a colossally efficient\n" +
            "and very clean energy source.\n" +
            "Like Pandora’s Promise, Atomic Hope revisits the case studies of Chornobyl and Fukushima and argues that, although clearly catastrophic, a mythology of\n" +
            "horror has grown up around these events that has stymied all debate and shut down thought. The film doesn’t say so, but another way the eco-nuclear movement\n" +
            "became tainted was perhaps a speech by Margaret Thatcher to the UN general assembly which made the case in 1989, partly to undermine the coal industry\n" +
            "as a trade union powerbase.\n" +
            "At any rate, here again is the argument: nuclear energy provides the clean, climate-friendly energy we need. Renewables such as wind and solar are important,\n" +
            "but progress on them is desperately slow and time is running out. The risks of nuclear are real, but they are misunderstood and uncontextualised, safety\n" +
            "measures have evolved and risk must in any case now be considered in the light of clear and present danger of the global harm from fossil fuels.\n" +
            "But none of this is easy. Generations have been brought up on the idea that nuclear equals apocalypse. Convincing them of the opposite is a challenge.\n" +
            "Guardian readers will know that George Monbiot has ventilated ideas on this issue. The inevitable question is: what does Greta think? This film was apparently\n" +
            "made too late to include Thunberg’s startling intervention in October 2022, when she claimed the German government was wrong to close down nuclear\n" +
            "plants in favour of coal. Anything that stimulates discussion of this issue is to be welcomed.";
    // This variable is used to store the value of article 2.
    private String article2 = "Leaks from Minnesota nuclear power plant raise safety fears across US\n" +
            "Leaks were contained and posed no danger, official reports say, but past disasters continue to cause fears of power source\n" +
            "In December, Janica Jammes started a microgreens business in the basement of her home in Big Lake, Minnesota, just across the river from Xcel Energy’s\n" +
            "nuclear plant in Monticello.\n" +
            "At least once each day, she uses water from her well to nourish the plant trays. She delivers her product to customers within a 10-mile radius and says\n" +
            "the business has been a success.\n" +
            "But now she worries that her water could be contaminated by a leak of about 400,000 gallons of radioactive water that occurred in November at the plant,\n" +
            "which is about 40 miles north-west of Minneapolis.\n" +
            "Moreover, Jammes is upset that the company did not alert the public about the leak until March – and then detected a second leak, which the company described\n" +
            "as smaller than the first one.\n" +
            "“We don’t know for sure if” side-effects from the leaks “will happen or when anything will happen but just the lack of transparency is very concerning”,\n" +
            "said Jammes, a 36-year-old mother of four.\n" +
            "While Xcel Energy representatives have said the leaks did not affect local drinking water or pose a safety threat to residents, residents such as Jammes\n" +
            "want more answers from the company.\n" +
            "Independent nuclear energy experts agree that the company should have been more transparent, but they say that based on reports from state and federal\n" +
            "agencies, they also do not think the leaks pose a health risk to residents or that the incidents will serve as a significant setback to efforts to promote\n" +
            "the carbon-free power source in the US.\n" +
            "“This leak, even though it was contained and poses no danger”, according to the official reports, “it should be used as some sort of wake-up call”, said\n" +
            "Najmedin Meshkati, an engineering professor who specializes in nuclear safety at the University of Southern California.\n" +
            "While some scientists see increasing nuclear energy as a crucial, safe way to reducing carbon emissions and increasing the country’s energy independence,\n" +
            "the disasters at the Chernobyl, Three Mile Island and Fukushima nuclear power plants continue to cause fears of the power source.\n" +
            "“Nuclear is the only clean energy sector that has the capacity to” transition away from fossil fuels “on a large scale”, said Charlyne Smith, a senior\n" +
            "nuclear energy analyst at the Breakthrough Institute, an energy thinktank. “It is an industry that is highly scrutinized compared to other industries,\n" +
            "and I think the Nuclear Regulatory Commission does a really good job at ensuring that safety is something that is practiced in the industry.”\n" +
            "Even though Xcel did not announce the leak publicly, they notified the Regulatory Commission, which is a federal agency, and the state and in November,\n" +
            "according to the Minnesota Pollution Control Agency. The company reported that about 400,000 gallons of water containing tritium leaked from a pipe at\n" +
            "the facility.\n" +
            "The regulators concluded that the spill had not reached the Mississippi River or contaminated drinking water sources near the plant.\n" +
            "“While we immediately informed state and federal agencies, with no immediate safety risk, we focused on investigating the situation and containing the\n" +
            "affected water in concert with our regulatory agencies,” Kevin Coss, an Xcel Energy spokesperson, stated in an email to the Guardian. “Making the announcement\n" +
            "when we did allowed us to provide the public a more accurate and complete understanding of the leak and our plan to resolve it.”\n" +
            "Smith said she agreed that the leak did not pose a significant safety risk but “learning about it months after really help the industry”.\n" +
            "After the announcement, Xcel held two open houses about the leaks. The company also shut the plant down after discovering the second leak but said it\n" +
            "would reopen this week.\n" +
            "Jammes was among hundreds of people to attend the meetings. She and others said they were frustrated that there was no presentation about the leaks and\n" +
            "that company representatives just stood at tables and answered only some of attendees’ questions.\n" +
            "“It was just a quick question and answer sort of thing, like if you have questions, then we’ll try to answer them, but it was very much: you’re going\n" +
            "to hear what we want you to hear,” Jammes said.\n" +
            "She wants to know why the pipe broke and what Xcel will do to prevent such accidents.\n" +
            "Michael Voll, a 60-year-old warehouse associate, also criticized the company’s approach at the meetings. He has lived in Monticello for most his life\n" +
            "and said Xcel, which opened the plant in 1971, has benefited the local economy.\n" +
            "“You didn’t have to come from a nuclear submarine. You could go out there, and if your uncle or your dad worked there, you probably were going to get\n" +
            "a job,” Voll said.\n" +
            "He also remains a supporter of nuclear energy and has never feared the plant.\n" +
            "But after the leaks and the public meetings, he said his trust in Xcel “is way down”.\n" +
            "Xcel sent the pipe that leaked to an independent group, where experts are studying it to determine what caused it to fail, Coss, the Xcel spokesperson\n" +
            "stated. “The results will help us understand whether there are other potential steps we need to take at the plant,” he wrote.\n" +
            "The company also will conduct a “thorough inspection” of the plant while it’s offline for refueling this month, Coss added. Xcel “will work to maintain”\n" +
            "the community’s trust, Coss wrote, “by thoroughly cleaning up the leaked tritium and providing prompt updates if anything about the situation changes”.";
    // This variable is used to store the value of article 3.
    private String article3 = "Jeremy hunt wants nuclear power classed as sustainable: is it?\n" +
            "Uk chancellor has launched consultation to classify nuclear as environmentally sustainable\n" +
            "jeremy hunt has kickstarted a fresh push into nuclear power, which he hopes will provide a quarter of britains electricity by 2050. In his budget speech,\n" +
            "the chancellor announced a competition to co-fund small nuclear plants and hopes a new delivery body, great british nuclear, will ease the creation of\n" +
            "nuclear projects. He also launched a consultation to classify nuclear as environmentally sustainable. But is it?\n" +
            "Does britain need nuclear power?\n" +
            "Hunt follows in the footsteps of the former prime minister boris johnson in making bold statements on the importance of nuclear. Britains nuclear power\n" +
            "stations date back to the 1950s and are now ageing, with just one, sizewell b, still scheduled to be running after 2028. Last week, frances edf which\n" +
            "operates the stations said it had extended the life of two other plants.\n" +
            "Proponents argue that nuclear provides a baseload of power that can be relied on, whereas renewable energy such as wind and solar is dependent on weather\n" +
            "conditions, meaning it cannot always help match supply with demand. There are also high hopes for nuclear fusion as an energy source, but this has not\n" +
            "been commercialised.\n" +
            "Why is nuclear being reclassified?\n" +
            "To attract private investment. The consultation is on the taxonomy or financial classification system of energy. This is important as there have been\n" +
            "a proliferation of funds dedicated to environmental, social and governance (esg) investments springing up in global financial markets in recent years.\n" +
            "These funds promise their investors that their cash is going towards social good, such as tackling the climate crisis. Hunt was also under pressure to\n" +
            "respond to the inflation reduction act, joe bidens $369bn climate subsidy package.\n" +
            "Is britain the first country to do this?\n" +
            "No. The european commission decided last year to label nuclear as a green investment. The eus parliament said the taxonomy change aimed to boost green\n" +
            "investments and prevent greenwashing. However, the decision has faced legal challenges by groups including greenpeace.\n" +
            "Will it make a difference?\n" +
            "Nuclear projects cost tens of billions of pounds to develop and build, so smoothing the path to secure investment is seen as crucial. In the uk, the government\n" +
            "has tasked bankers with finding funders for sizewell c, in partnership with edf, after easing china general nuclear out of the project over security fears.\n" +
            "The government has committed 700m to the suffolk plant, which could cost more than 30bn to build. Its sister site, hinkley point c in somerset, has been\n" +
            "beset with delays and cost overruns. Senior industry sources said the taxonomy change would help in the hunt for sizewell cs funders.\n" +
            "The backers of small modular reactors, including a programme developed by rolls-royce, will also hope to receive significant investment. Rolls-royces\n" +
            "aim is to create a fleet of smaller, factory-built nuclear plants across britain.\n" +
            "Is nuclear energy renewable?\n" +
            "Uranium, the element used in nuclear power, is a finite resource, which is mined from the ground and not considered renewable. However, it can be argued\n" +
            "that, as nuclear power stations use a very small amount of fuel to generate the same amount of electricity, they are preferable to gas-fired power stations,\n" +
            "which are expected to be around for many years.\n" +
            "Is it really green?\n" +
            "Advocates of nuclear claim it should be seen as a green energy source as it does not release harmful emissions into the air. National grid says that the\n" +
            "lifecycle emissions emissions resulting from every stage of the production process are also significantly lower than in fossil fuel-based generation.\n" +
            "Hunt said on wednesday that increasing nuclear capacity is vital to meet our net zero obligations. However, there are questions over how useful nuclear\n" +
            "will realistically be in this mission. As projects take years to build sizewell c could be finished by the mid-2030s at a push it can be argued that pursuing\n" +
            "the project could prove a drain on government and financial resources, which could be better spent on rapidly scaling up britains wind and solar capacity\n" +
            "and improving electricity networks and storage. An effective ban on onshore wind developments remains in place.\n" +
            "Nuclear detractors also say nuclear cannot be seen as sustainable given the concerns over the potential environmental hazards in the long term. Shutting\n" +
            "down sites such as sellafield, in north-west england, will take generations and involves a painstaking process of carefully managing ageing buildings\n" +
            "to ensure radioactive waste is not mishandled. In finland, a huge effort is under way to bury waste in concrete deep underground.\n" +
            "It has also been argued that as nuclear plants need water for cooling so are often built near the coast rising sea levels could make some projects obsolete\n" +
            "before building work is completed.\n" +
            "That is not the only concern, is it?\n" +
            "No. The history of the nuclear industry has shown an oscillating interest from governments and investors, influenced by a series of devastating incidents\n" +
            "including three mile island in 1979, chornobyl in 1986 and fukushima in 2011. Russias attack on zaporizhzhia, europes biggest nuclear plant, in ukraine,\n" +
            "has again raised concerns over a nuclear incident.";

    /*
     * The following test considers three string as the tokenizer tokens source. The strings should be elaborated without checks.
     * in that case the only operation out of the standard tokenization is the separation of the words that are composite with punctuation.
     * (example: "it's" --> "it" and "s").
     * After this operation the tokenizer should only count tokens (in lowercase format) what happens to be in all the three strings.
     */
    @Test
    @DisplayName("Tokenize small string without any checks.")
    void testTokenizeWithSmallStringsWithoutChecks(){
        String string1 = "We don’t know for sure if side-effects from the leaks will happen or when anything will" +
                " happen but just the lack of transparency is very concerning said Jammes, a 36-year-old mother of four.";
        // Tokens : A-E {a,anything,but,concerning,do,effects,
        //          F-P  for,four,from,happen, if,is,jammes,just,know,lack,leaks,mother,n,of,old,or,
        //          R-Z  said,side,sure,t,the,transparency,very,we,when,will,year}
        String string2 = "In his budget speech, the chancellor announced a competition to co-fund small nuclear plants and hopes a new delivery body, " +
                "great british nuclear, will ease the creation of nuclear projects.";
        // Tokens : A-E {a,and,announced,body,british,budget,chancellor,co,competition,creation,delivery,ease,
        //          F-P  fund,great,his,hopes, in,new, nuclear,of,plants,projects,
        //          R-Z  small,speech,the,to,will}
        String string3 = "At any rate, here again is the argument: nuclear energy provides the clean, climate-friendly energy we need." +
                "Renewables such as wind and solar are important but progress on them is desperately slow and time is running out.";
        // Tokens : A-E {again,and,any,are,argument,as,at,but,clean,climate,desperately,energy,
        //          F-P  friendly,here, important,is,need,nuclear,on,out, progress,provides,
        //          R-Z  rate,renewables,running, slow,solar,such,the,them,time,we,wind}
        /* Analizing the three sorted list of tokens we notice that the common tokens are:
         *   *-* {A-E ; F-P ; R-Z}
         *   1-2 {a   ;    of   ; the,will}
         *   1-3 {but ;    is   ; the,we}
         *   2-3 {and ; nuclear ; the}
         *   *-*-* {A-E ; F-P ; R-Z}
         *   1-2-3 {  ;         ; the}
         *
         *  So terms with frequency one are all minus those with frequency 2 or 3. Terms with frequency 2 are those that appear in couples
         *  of tokens group minus those with a frequency 3.
         *
         *  1 : {year,wind,when,very,transparency,to,time,them,t,sure,such,speech,solar,small,slow,side,said,running,renewables,rate,
         *       provides,projects,progress,plants,out,or,on,old,new,need,n,mother,leaks,lack,know,just,jammes,in,important,if,hopes,
         *       his,here,happen,great,fund,from,friendly,four,for,energy,effects,ease,do,desperately,delivery,creation,concerning,competition,
         *       co,climate,clean,chancellor,budget,british,body,at,as,argument,are,anything,any,announced,again}
         *  2 : {will, we, nuclear, but, and, a}
         *  3 : {the}
         */
        List<String> list1 = Arrays.stream(new String[]{"year","wind","when","very","transparency","to","time","them","t","sure","such",
                "speech","solar","small","slow","side","said","running","renewables","rate","provides","projects","progress","plants","out",
                "or","on","old","new","need","n","mother","leaks","lack","know","just","jammes","in","important","if","hopes","his",
                "here","happen","great","fund","from","friendly","four","for","energy","effects","ease","do","desperately","delivery","creation",
                "concerning","competition","co","climate","clean","chancellor","budget","british","body","at","as","argument","are","anything","any",
                "announced","again"}).collect(Collectors.toList());
        List<String> list2 = Arrays.stream(new String[]{"will", "we", "of", "nuclear", "is", "but", "and", "a"}).collect(Collectors.toList());
        List<String> list3 = Arrays.stream(new String[]{"the"}).collect(Collectors.toList());
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        expected.put(1,list1);
        expected.put(2,list2);
        expected.put(3,list3);
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(false,storage);
        tokenizer.tokenize(string1, new ArrayList<String>()) ;
        tokenizer.tokenize(string2, new ArrayList<String>());
        tokenizer.tokenize(string3, new ArrayList<String>());
        assertEquals(expected.entrySet(), storage.getOrderedTokens(83));
    }
    /*
     * Note this test also needs to consider the presence of common words (present in the blacklist
     * or with small dimension(under 3))
     * */
    @Test
    @DisplayName("Testing small string with checks.")
    void testTokenizeWithSmallStringsWithChecks()  {
        String string1 = "'We don't know for sure if' side-effects from the leaks “will happen or when anything will" +
                " happen but just the lack of transparency is very concerning said Jammes, a 36-year-old mother of four.";
        String string2 = "In his budget speech, the chancellor announced a competition to co-fund small nuclear plants and hopes a new delivery body, " +
                "great british nuclear, will ease the creation of nuclear projects.";
        String string3 = "At any rate, here again is the argument: nuclear energy provides the clean, climate-friendly energy we need." +
                "Renewables such as wind and solar are important but progress on them is desperately slow and time is running out.";
        /* Note that analyzing the Strings we get first the same result as before:
         *
         *  1 : {year,wind,when,very,transparency,to,time,them,t,sure,such,speech,solar,small,slow,side,said,running,renewables,rate,
         *       provides,projects,progress,plants,out,or,on,old,new,need,n,mother,leaks,lack,know,just,jammes,in,important,if,hopes,
         *       his,here,happen,great,fund,from,friendly,four,for,energy,effects,ease,do,desperately,delivery,creation,concerning,competition,
         *       co,climate,clean,chancellor,budget,british,body,at,as,argument,are,anything,any,announced,again}
         *  2 : {will, we, nuclear, but, and, a}
         *  3 : {the}
         *
         * Then confronting with the blacklist we get the following result
         * 1 : {year, wind, transparency, time, speech, solar, small, slow, side, running, renewables, rate, projects, progress, plants,
         *      mother, leaks, lack, jammes, important, hopes, happen, great, fund, friendly, energy, effects, ease, desperately, delivery,
         *      creation, competition, climate, clean, chancellor, budget, british, body, argument, announced}
         * 2 : {nuclear}
         * 3 : {}
         *
         * Indeed these words are all present in the blacklist:
         *  {when, to, them, sure such, said, provides, out, or, on, old, new, need, n, know, just, in, important, if, his, here, from, four,
         *   for, do, concerning, co, at, as, are, anything any, again }
         *  {will, we, but, and, a}
         *  {the}
         */
        List<String> list1 = Arrays.stream(new String[]{"year","wind","transparency","time","speech","solar","small","slow","side","running",
                "renewables","rate","projects","progress","plants","mother","leaks","lack","jammes","important","hopes","happen","great","fund",
                "friendly","energy","effects","ease","desperately","delivery","creation","competition","climate","clean","chancellor","budget",
                "british","body","argument","announced"}).collect(Collectors.toList());
        List<String> list2 = Arrays.stream(new String[]{"nuclear"}).collect(Collectors.toList());
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        expected.put(1,list1);
        expected.put(2,list2);
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(true,storage);
        tokenizer.tokenize(string1, new ArrayList<String>()) ;
        tokenizer.tokenize(string2, new ArrayList<String>());
        tokenizer.tokenize(string3, new ArrayList<String>());
        assertEquals(expected.entrySet(),storage.getOrderedTokens(41));
    }


    /* Testing tokenizer with big string (with and without checks).
     * To understand the full process of extraction of the frequent tokens from the 3 articles
     * see the files at "test_resources/articles/matches","test_resources/articles/Article1Tokenization.txt",
     * "test_resources/articles/Article2Tokenization.txt", "test_resources/articles/Article3Tokenization.txt"
     * */
    @Test
    @DisplayName("Tokenize big String without doing any checks.")
    void testTokenizeWithBigStringsWithoutChecks(){
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(true,storage);
        tokenizer.tokenize(article1, new ArrayList<String>());
        tokenizer.tokenize(article2, new ArrayList<String>());
        tokenizer.tokenize(article3, new ArrayList<String>());
        List<String> list3 = Arrays.stream(new String[]{"source","power","plants","nuclear","industry","fukushima","fossil","energy"}).collect(Collectors.toList());;
        List<String> list2 = Arrays.stream(new String[]{"safety","running","risk","remains","radioactive","questions","question","provide",
                "promise","prevent","potential","plant","north","mile","making","long","life","island","increasing","incidents","important","hope",
                "guardian","great","government","good","global","generations","general","fuels","fears","emissions","danger","crucial","country",
                "considered","commission","climate","clean","chornobyl","capacity","based"}).collect(Collectors.toList());
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        expected.put(3,list3);
        expected.put(2,list2);
        assertEquals(expected.entrySet(), storage.getOrderedTokens(50));
    }
    /*
     * Note this test needs to consider the presence of common words (present in the blacklist or with small dimension(under 3))
     * */
    @Test
    @DisplayName("Tokenize big strings with checks.")
    void testTokenizeWithBigStringsDoingChecks() {
        TreeStorage storage = new TreeStorage();
        Tokenizer tokenizer = new Tokenizer(false,storage);
        tokenizer.tokenize(article1, new ArrayList<String>());
        tokenizer.tokenize(article2, new ArrayList<String>());
        tokenizer.tokenize(article3, new ArrayList<String>());
        List<String> list3 = Arrays.stream(new String[]{"will","which","way","was","very","up","to","this","they","the","that","such",
                "source","say","s","power","plants","out","one","on","of","nuclear","now","not","need","it","is","industry","in","have",
                "has","fukushima","from","fossil","for","first","energy","down","does","by","but","been","be","at","as","are","and","a",""}).collect(Collectors.toList());;
        List<String> list2 = Arrays.stream(new String[]{"across"}).collect(Collectors.toList());
        Map<Integer, List<String>> expected = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
        expected.put(3,list3);
        expected.put(2,list2);
        assertEquals(expected.entrySet(), storage.getOrderedTokens(50));
    }
}
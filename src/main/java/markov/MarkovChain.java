package markov;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;

public class MarkovChain {

    private static final String START = "--START--";
    private static final String END = "--END--";
    private Map<String, Map<String, Integer>> vocabulary = new HashMap<>();
    private Random RND = new Random();

    public MarkovChain(String source) {

        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.ENGLISH);
        iterator.setText(source);
        int start = iterator.first();
        int i = 0;
        
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String sentence = source.substring(start, end);
            System.out.println("" + i + ": " + sentence);
            processSentence(sentence);
            i++;
        }
        
    }


    private void processSentence(String sentence) {

        String[] words = sentence.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            String keyWord = clearWord(words[i]);
            String nextWord = END;
            if (i < words.length - 1) {
                nextWord = clearWord(words[i + 1]);
            }

            if (i == 0) {
                nextWord = keyWord;
                keyWord = START;

            }
            processWorld(keyWord, nextWord);
        }

    }

    private String clearWord(String word) {
        return word.replaceAll("[^\\p{L}+]", "").replaceAll("/.", "").replaceAll(",", "").toLowerCase();
    }

    private void processWorld(String keyWord, String nextWord) {
        Map<String, Integer> map = vocabulary.get(keyWord);
        if (map != null) {
            if (map.get(nextWord) != null) {
                map.put(nextWord, map.get(nextWord) + 1);
            } else {
                map.put(nextWord, 1);
            }
        } else {
            Map<String, Integer> wordsMap = new HashMap<>();
            wordsMap.put(nextWord, 1);
            vocabulary.put(keyWord, wordsMap);
        }
    }

    public String generate(int maxLength) {
        StringBuilder sb = new StringBuilder();
        int numWords = 0;
        String lastWord = "";
        while (numWords < maxLength) {
            if (lastWord == "" || lastWord == END) {
                lastWord = START;
            }
            lastWord = getRandomWord(lastWord);
            
            if (lastWord == END)
                sb.append(".");
            else if (lastWord != START)
                sb.append(" " + lastWord);

            numWords++;
        }
        return sb.toString();
    }

    private String getRandomWord(String lastWord) {
        Map<String, Integer> map = vocabulary.get(lastWord);
        if (map == null)
            return END;
        Set<Entry<String, Integer>> entrySet = map.entrySet();
        List<String> list = new ArrayList<>();
        int maxNum = 0;
        for (Entry<String, Integer> entry : entrySet) {
            maxNum = maxNum + entry.getValue();
            for (int i = 0; i < entry.getValue(); i++) {
                list.add(entry.getKey());
            }
        }
        String word = "";
        if (maxNum == 1) {
            word = list.get(0);
        } else {
            word = list.get(RND.nextInt(maxNum));
        }
        return word;
    }

    public void printVoc() {
        for (Entry<String, Map<String, Integer>> set : vocabulary.entrySet()) {
            String st = set.getKey() + ":";
            Set<Entry<String, Integer>> entrySet = set.getValue().entrySet();
            for (Entry<String, Integer> entry : entrySet) {
                st = st + entry.getKey() + "(" + entry.getValue() + "), ";
            }
            System.out.println(st);

        }

    }


    public void getInfo(String string) {
        // TODO Auto-generated method stub
        
    }

}

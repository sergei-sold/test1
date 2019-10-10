package markov;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class MainApp {

    public static void main(String[] args) throws IOException {
        MarkovChain chain = new MarkovChain(
                new String(FileUtils.readFileToString(new File("e:\\docs\\my\\java\\text.txt"), "UTF-8")));

        chain.getInfo("");
        String text = chain.generate(30);
        //chain.printVoc();
        

        System.out.println("Text: " + text);
    }

}

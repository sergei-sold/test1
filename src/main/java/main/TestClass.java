package main;

import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siska.blog4prog.translator.DeeplResponse;
import com.siska.blog4prog.translator.DeeplTranslator;

public class TestClass {

    public static void main(String[] args)
            throws InterruptedException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
        DeeplTranslator tr = new DeeplTranslator();
        tr.setPoxy("");
        String result = tr.translate(
                "<p>The first approach we'll see for converting a JSON <em>String</em> to a <em>JsonObject</em> is a two-step process that uses the <em>JsonParser</em> class.</p>");
        System.out.println("JSON: " + result);

        ObjectMapper mapper = new ObjectMapper();
        if (result != null) {
            DeeplResponse response = mapper.readValue(result, DeeplResponse.class);

            System.out
                    .println("Translated text: " + response.result.translations.get(0).beams.get(0).processedSentence);
        }
    }

}

package main;

import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import translator.DeeplResponse;
import translator.DeeplTranslator;

public class TestClass {

    public static void main(String[] args)
            throws InterruptedException, UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
        DeeplTranslator tr = new DeeplTranslator();
        String result = tr.translate(" Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Gson can work with arbitrary Java objects including pre-existing objects that you do not have source-code of.");
        System.out.println("JSON: " + result);

        ObjectMapper mapper = new ObjectMapper();
        DeeplResponse response = mapper.readValue(result, DeeplResponse.class);

        System.out.println("Translated text: " + response.result.translations.get(0).beams.get(0).processedSentence);
    }

}

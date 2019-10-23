package com.siska.blog4prog;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class YandexTranslator {
    private static final Logger LOGGER = LoggerFactory.getLogger(YandexTranslator.class);
    private String apiKey;
    private int responseCode;

    public YandexTranslator(String key) {
        this.apiKey = key;
    }

    public String translateHtml(String text) {
        String result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpURLConnection connection = null;

            connection = (HttpURLConnection) new URL(
                    "https://translate.yandex.net/api/v1.5/tr.json/translate?lang=en-ru&format=html&key=" + apiKey)
                            .openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept", "text/xml");
            connection.setRequestMethod("POST");

            String postParams = "text=" + text;
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(postParams.getBytes());
            outputStream.flush();
            outputStream.close();

            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                YandexTranslatorResponse value = mapper.readValue(
                        IOUtils.toString(connection.getInputStream(), "UTF-8"), YandexTranslatorResponse.class);
                result = String.join(" ", value.translatedText);
            }
        } catch (Exception e) {
            LOGGER.error("Error during translation.", e);
        }
        return result;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getResponseCode() {
        return responseCode;
    }

}

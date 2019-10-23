package com.siska.blog4prog;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YandexTranslatorResponse {
    @JsonProperty("code")
    public Integer code;

    @JsonProperty("lang")
    public String lang;

    @JsonProperty("text")
    public List<String> translatedText;
    /*
     * { "code": 200, "lang": "en-ru", "text": [ "Здравствуй, Мир!" ] }
     */
}

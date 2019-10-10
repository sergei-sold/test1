package translator;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty("date")
    public String date;
    
    @JsonProperty("source_lang")
    public String sourceLanguage;
    
    @JsonProperty("source_lang_is_confident")
    public int sourceLangIsConfident;
    
    @JsonProperty("target_lang")
    public String targetLanguage;
    
    @JsonProperty("timestamp")
    public long timestamp;
    
    @JsonProperty("translations")
    public List<Translation> translations;
    
    /*
    "date": "20191001",
    "source_lang": "EN",
    "source_lang_is_confident": 0,
    "target_lang": "RU",
    "timestamp": 1569931784,
    "translations"
    */
}

package translator;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Beam {
    @JsonProperty("num_symbols")
    public Integer numSymbols;
    
    @JsonProperty("postprocessed_sentence")
    public String processedSentence;

    @JsonProperty("score")
    public Double score;
    
    @JsonProperty("totalLogProb")
    public Double totalLogProb;
    /*
     *                      "score": -5000.02,
                        "totalLogProb": -2.89973
     */
}

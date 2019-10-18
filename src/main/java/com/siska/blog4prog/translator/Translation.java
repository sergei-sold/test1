package com.siska.blog4prog.translator;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Translation {
    @JsonProperty("beams")
    public List<Beam> beams;

    @JsonProperty("quality")
    public String quality;

    @JsonProperty("timeAfterPreprocessing")
    public Integer timeAfterPreprocessing;

    @JsonProperty("timeReceivedFromEndpoint")
    public Integer timeReceivedFromEndpoint;

    @JsonProperty("timeSentToEndpoint")
    public Integer timeSentToEndpoint;

    @JsonProperty("total_time_endpoint")
    public Integer total_time_endpoint;
    /*
     * "quality": "normal", "timeAfterPreprocessing": 0, "timeReceivedFromEndpoint":
     * 351, "timeSentToEndpoint": 60, "total_time_endpoint": 0
     */
}

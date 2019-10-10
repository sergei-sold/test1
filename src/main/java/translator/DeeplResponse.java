package translator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeeplResponse {
    @JsonProperty("id")
    public Integer id;
    
    @JsonProperty("jsonrpc")
    public String jsonrpc;
    
    @JsonProperty("result")
    public Result result;
    
    

}

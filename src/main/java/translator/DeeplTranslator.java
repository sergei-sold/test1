package translator;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class DeeplTranslator {

    private static final String TRANSLATOR_ENDPOINT_URL = "https://www2.deepl.com/jsonrpc";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";
    String JSON = "{\r\n" + "    \"jsonrpc\": \"2.0\",\r\n" + "    \"method\": \"LMT_handle_jobs\",\r\n"
            + "    \"params\": {\r\n" + "        \"jobs\": [\r\n" + "            {\r\n"
            + "                \"kind\": \"default\",\r\n" + "                \"raw_en_sentence\": \"%s\",\r\n"
            + "                \"raw_en_context_before\": [],\r\n" + "                \"raw_en_context_after\": [\r\n"
            + "                    \r\n" + "                ]\r\n" + "            }\r\n" + "        ],\r\n"
            + "        \"lang\": {\r\n" + "            \"user_preferred_langs\": [\r\n" + "                \"RU\",\r\n"
            + "                \"EN\"\r\n" + "            ],\r\n" + "            \"source_lang_computed\": \"EN\",\r\n"
            + "            \"target_lang\": \"RU\"\r\n" + "        },\r\n" + "        \"priority\": 1,\r\n"
            + "        \"timestamp\": %s\r\n" + "    },\r\n" + "    \"id\": %s\r\n" + "}";

    public String translate(String text) throws UnsupportedEncodingException {
        String result = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(TRANSLATOR_ENDPOINT_URL);
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("accept", "text/xml");
        request.addHeader("Content-Type", "ttext/plain");

        String timestamp = "" + (new Date()).getTime();
        String id = "7580090";
        String json = String.format(JSON, text, timestamp, id);

        request.setEntity(new StringEntity(json));
        try {
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {

                result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}

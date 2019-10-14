package translator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;

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

        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("167.71.186.105", 3128));
            HttpURLConnection connection = (HttpURLConnection) new URL(TRANSLATOR_ENDPOINT_URL).openConnection(proxy);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Content-type", "text/plain");
            connection.setRequestProperty("Accept", "text/xml");
            connection.setRequestMethod("POST");

            String timestamp = "" + (new Date()).getTime();
            String id = "7580002";
            String json = String.format(JSON, text, timestamp, id);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                result = IOUtils.toString(connection.getInputStream(), "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

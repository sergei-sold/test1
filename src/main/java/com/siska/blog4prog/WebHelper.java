package com.siska.blog4prog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.omg.DynamicAny.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebHelper.class);
	private CloseableHttpClient httpClient;
	private BasicCookieStore cookieStore;
	private StatusLine statusLine;
	private List<Header> responseHeaders;
	private String responseBody;

	public WebHelper() {
		// Initialize
		httpClient = HttpClients.createDefault();
		cookieStore = new BasicCookieStore();
		httpClient = HttpClients.custom().setDefaultHeaders(getDefaultHeaders()).setDefaultCookieStore(cookieStore)
				.build();
	}

	private Collection<Header> getDefaultHeaders() {
		Collection<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader(HttpHeaders.USER_AGENT,
				"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36"));
		headers.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7"));
		headers.add(new BasicHeader(HttpHeaders.ACCEPT,
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"));

		return headers;
	}

	public void get(String url) {
		HttpGet request = new HttpGet(url);

		try (CloseableHttpResponse response = httpClient.execute(request)) {
			LOGGER.info("----------------------------------------");
			this.statusLine = response.getStatusLine();
			this.responseHeaders = Arrays.asList(response.getAllHeaders());
			this.setResponseBody(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			LOGGER.error("Error", e);
		}

	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public BasicCookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(BasicCookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public StatusLine getStatusLine() {
		return statusLine;
	}

	public void setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}

	public List<Header> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(List<Header> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

}

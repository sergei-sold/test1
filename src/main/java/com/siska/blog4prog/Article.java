package com.siska.blog4prog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Article {


	@Override
	public String toString() {
		return "Article [title=" + title + ", translatedTitle=" + translatedTitle + ", href=" + href
				+ ", detailedTitle=" + detailedTitle + ", translatedDetailedTitle=" + translatedDetailedTitle
				+ ", categories=" + categories + ", tags=" + tags + "]";
	}

	private String title;
	private String translatedTitle;
	private String href;
	private String detailedTitle;
	private String translatedDetailedTitle;
	private Set<String> categories = new HashSet<>();
	private Set<String> tags = new HashSet<>();
	private List<Element> contentNodes = new ArrayList<>();
	private List<String> translatedNodes = new ArrayList<>();

	private Document articleDoc;

	public Document getArticleDoc() {
		return articleDoc;
	}

	public String getTranslatedTitle() {
		return translatedTitle;
	}

	public void setTranslatedTitle(String translatedTitle) {
		this.translatedTitle = translatedTitle;
	}

	public String getTranslatedDetailedTitle() {
		return translatedDetailedTitle;
	}

	public List<String> getTranslatedNodes() {
		return translatedNodes;
	}

	public void setTranslatedNodes(List<String> translatedNodes) {
		this.translatedNodes = translatedNodes;
	}

	public List<Element> getContentNodes() {
		return contentNodes;
	}

	public Article(String title, String href) {
		this.title = title;
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getDetailedTitle() {
		return detailedTitle;
	}

	public void setDetailedTitle(String detailedTitle) {
		this.detailedTitle = detailedTitle;
	}

	public void addCategory(String category) {
		categories.add(category);
	}

	public void addTag(String tag) {
		tags.add(tag);
	}

	public void addNode(Element element) {
		contentNodes.add(element);
	}

	public void downloadAndFillContent() {
		WebHelper infoWebHelper = new WebHelper();
		infoWebHelper.get(getHref());
		articleDoc = Jsoup.parse(infoWebHelper.getResponseBody());

		try {
			processArticleHeader();
			getArticleContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processArticleHeader() {
		Elements header = articleDoc.getElementsByClass("page-header");
		if (header.size() > 0) {
			setDetailedTitle(header.get(0).getElementsByTag("h1").text());
			Elements categorieselems = header.get(0).getElementsByClass("categories");
			Elements tagsElems = header.get(0).getElementsByClass("post-tags");
			if (categorieselems.size() > 0) {
				for (Element element : categorieselems) {
					Elements linkElem = element.getElementsByTag("a");
					addCategory(linkElem.text());
				}
			}
			if (tagsElems.size() > 0) {
				for (Element element : tagsElems) {
					Elements linkElem = element.getElementsByTag("a");
					addTag(linkElem.text());
				}
			}
		}
	}

	private void getArticleContent() throws IOException {
		Elements content = articleDoc.getElementsByClass("post-content");
		if (content.size() > 0) {
			Elements childrenElements = content.get(0).children();
			int count = 0;
			for (Element element : childrenElements) {
				addNode(element);
				count++;
			}
		}
	}

	public void addTranslatedNode(String translatedText) {
		translatedNodes.add(translatedText);

	}

}

package com.siska.blog4prog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BlogApp {

	private static final String BASE_DIR = "e:\\_work\\java\\blog-for-prog\\";

	public static void main(String[] args) throws IOException {

		List<Article> articles = new ArrayList<>();
		// getAllArticles(articles, "https://www.baeldung.com/category/java/");
		articles.add(new Article("Intro to XPath with Java", "https://www.baeldung.com/java-xpath"));
		StringBuilder sb = new StringBuilder();

		for (Article article : articles) {
			sb.append(article.getTitle());
			sb.append("~~~");
			sb.append(article.getHref());
			sb.append("\r\n");
			// extract Article info
			article.downloadAnfFillContent();

			try {
				// processArticleHeader(article, articleDoc);
				// getAndSaveArticleContent(article, article.getArticleDoc());

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				e.printStackTrace();
			}
		}

		// BufferedWriter writer = new BufferedWriter(new FileWriter(BASE_DIR +
		// "links.txt"));
		// writer.write(sb.toString());
		// writer.close();

		// Article art = new Article("Intro to XPath with Java",
		// "https://www.baeldung.com/java-xpath");

	}

	private static void getAllArticles(List<Article> articles, String pageUrl) {
		WebHelper webHelper = new WebHelper();
		webHelper.get(pageUrl);
		boolean hasNextPage = true;
		while (hasNextPage) {
			Document doc = Jsoup.parse(webHelper.getResponseBody());
			Elements elems = doc.getElementsByTag("article");
			parseArticles(articles, elems);
			hasNextPage = getNextPageIfExists(webHelper, doc);
		}
	}

	private static void getAndSaveArticleContent(Article art, Document articleDoc) throws IOException {
		Elements content = articleDoc.getElementsByClass("post-content");
		if (content.size() > 0) {
			new File(BASE_DIR + art.getTitle()).mkdir();
			Elements childrenElements = content.get(0).children();
			int count = 0;

			BufferedWriter artWriter = new BufferedWriter(
					new FileWriter(BASE_DIR + art.getTitle() + "//article.info.txt"));
			// writer.write("tag="+element.outerHtml());
			artWriter.write(art.toString());
			artWriter.close();
			for (Element element : childrenElements) {
				BufferedWriter nodeWriter = new BufferedWriter(
						new FileWriter(BASE_DIR + art.getTitle() + "//node" + count + ".txt"));
				// writer.write("tag="+element.outerHtml());
				art.addNode(element);
				nodeWriter.write(element.outerHtml());
				nodeWriter.close();
				count++;
			}
			System.out.println("Saved '" + art.getTitle() + "' content");
		}
	}

	private static boolean getNextPageIfExists(WebHelper webHelper, Document doc) {
		boolean hasNextPage = false;
		Elements pagination = doc.getElementsByClass("pagination");
		if (pagination.size() > 0) {
			Element paginationElem = pagination.get(0);
			Elements pages = paginationElem.getElementsByTag("a");
			for (Element page : pages) {
				if (page.text().contains("Next")) {
					System.out.println("Next page: " + page.attr("href"));
					webHelper.get(page.attr("href"));
					hasNextPage = true;
				}
			}
		}
		return hasNextPage;
	}

	private static void parseArticles(List<Article> articles, Elements elems) {
		System.out.println("Found " + elems.size() + " articles");
		for (Element elem : elems) {
			Elements link = elem.getElementsByTag("a");
			Article article = new Article(link.get(0).attr("title"), link.get(0).attr("href"));
			System.out.println(article.getTitle() + ": " + article.getHref());
			articles.add(article);
		}
	}

}

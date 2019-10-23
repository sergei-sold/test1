package com.siska.blog4prog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.siska.blog4prog.translator.DeeplTranslator;
import com.siska.blog4prog.translator.ProxyManager;

public class BlogApp {

    private static final String BASE_DIR = "e:\\_work\\java\\blog-for-prog\\";
    private static ProxyManager proxyManager = new ProxyManager();

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
            article.downloadAndFillContent();
            article.getContentNodes().stream().forEach(node -> translateNode(node, article));
            article.setTranslatedTitle(translateText(article.getTitle()));
            article.setDetailedTitle(translateText(article.getDetailedTitle()));
            try {
                // processArticleHeader(article, articleDoc);
                saveArticleContent(article);

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

    private static void translateNode(Element node, Article article) {

        String text = node.outerHtml().replaceAll("\"", "'");
        if (notTranslateableNode(text))
            return;

        String translatedText = translateText(text);
        article.addTranslatedNode(translatedText);
    }

    private static boolean notTranslateableNode(String text) {
        return text.contains("code-block") || text.contains("highlighter") || text.contains("display:none");
    }

    private static String translateText(String text) {
        /*
        YandexTranslator tr = new YandexTranslator(
                "trnsl.1.1.20190924T110141Z.cb4dbe7d67fa1ca3.bd02b74e7e377ed47c33cbf6cb63058f1319e2cb");
         */
        DeeplTranslator tr = new DeeplTranslator();
        String translatedText = null;
        //while (translatedText == null) {
        try {
            translatedText = tr.translate(text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // }
        return translatedText;
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

    private static void saveArticleContent(Article art) throws IOException {
        new File(BASE_DIR + art.getTitle()).mkdir();
        int count = 0;

        OutputStreamWriter artWriter = new OutputStreamWriter(
                new FileOutputStream(BASE_DIR + art.getTitle() + "//article.info.txt"), StandardCharsets.UTF_8);
        // writer.write("tag="+element.outerHtml()); ва
        artWriter.write(art.toString());
        artWriter.close();
        // write translated content
        for (String element : art.getTranslatedNodes()) {
            OutputStreamWriter nodeWriter = new OutputStreamWriter(
                    new FileOutputStream(
                            BASE_DIR + art.getTitle() + "//translatedNode" + String.format("%04d", count) + ".txt"),
                    StandardCharsets.UTF_8);
            // writer.write("tag="+element.outerHtml());
            nodeWriter.write(element);
            nodeWriter.close();
            count++;
        }

        // write all content
        count = 0;
        for (Element el : art.getContentNodes()) {
            OutputStreamWriter nodeWriter = new OutputStreamWriter(
                    new FileOutputStream(BASE_DIR + art.getTitle() + "//node" + String.format("%04d", count) + ".txt"),
                    StandardCharsets.UTF_8);
            // writer.write("tag="+element.outerHtml());
            nodeWriter.write(el.outerHtml());
            nodeWriter.close();
            count++;
        }

        System.out.println("Saved '" + art.getTitle() + "' content");

    }

    private static boolean getNextPageIfExists(WebHelper webHelper, Document doc) {
        boolean hasNextPage = false;
        Elements pagination = doc.getElementsByClass("pagination");
        if (!pagination.isEmpty()) {
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

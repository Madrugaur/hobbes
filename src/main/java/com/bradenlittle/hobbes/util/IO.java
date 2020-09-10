package com.bradenlittle.hobbes.util;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class IO {
    private static String user_dir = System.getProperty("user.dir");
    private static String res_dir = "src/main/resources";
    private static String connection = "jdbc:sqlite:" + res_dir +"/hobbes.db";
    public static JSONObject readJSON(String name) throws IOException {
        String source = Files.readString(Paths.get(user_dir, res_dir, name));
        JSONObject jobj = new JSONObject(source);
        return jobj;
    }
    @SuppressWarnings({})
    public static Comic getComic() throws MalformedURLException {
        URL url = new URL("https://www.gocomics.com/random/calvinandhobbes");
        Comic comic = new Comic();
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(false);
            final HtmlPage main_page = webClient.getPage(url);
            final HtmlElement element = main_page.getElementByName("twitter:image");
            URL image_url = new URL(element.getAttribute("content"));
            BufferedImage response = ImageIO.read(image_url);
            File output = new File("chtest.gif");
            ImageIO.write(response, "gif", output);
            comic.image = output;
            comic.imageURl = image_url;
            comic.siteURl = main_page.getBaseURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comic;
    }
    public static Connection getSQLConnection(){
        try {
            return DriverManager.getConnection(connection);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}

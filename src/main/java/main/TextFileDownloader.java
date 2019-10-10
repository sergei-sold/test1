package main;

import java.io.IOException; 
import java.net.MalformedURLException; 
import java.net.URL; 
import java.util.Objects; 
import java.util.Scanner; 

public final class TextFileDownloader { 

 private final String urlText; 

 public TextFileDownloader(String urlText) { 
  this.urlText = Objects.requireNonNull(urlText, "The URL text is null."); 
 } 

 public String download() { 
  try { 
   URL url = new URL(urlText); 
   StringBuilder sb = new StringBuilder(); 
   Scanner scanner = new Scanner(url.openStream()); 

   while (scanner.hasNextLine()) { 
    sb.append(scanner.nextLine()); 
   } 

   return sb.toString(); 
  } catch (MalformedURLException ex) { 
   throw new IllegalStateException("Bad URL", ex); 
  } catch (IOException ex) { 
   throw new RuntimeException("IO failed", ex); 
  } 
 } 
} 


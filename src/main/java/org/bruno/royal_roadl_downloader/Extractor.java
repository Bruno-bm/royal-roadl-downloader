package org.bruno.royal_roadl_downloader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Extractor 
{
    public static void main(String[] args) throws IOException
    {	
    	Document doc = Jsoup.connect("http://royalroadl.com/fiction/5701/savage-divinity/chapter/58095/chapter-1-new-beginnings").get();
    	Elements nextChapter = doc.getElementsByClass("btn btn-primary col-xs-4");
    	PrintWriter writer = GeraArquivo();
    	while(VerificaExistenciaNextChapter(nextChapter)) {
    		Document doc1 = Jsoup.connect("http://royalroadl.com/"+nextChapter.get(2).attr("href")).get();
    		Elements capitulo = doc1.getElementsByTag("h1");
    		Elements texto = doc1.getElementsByClass("chapter-inner");
        	nextChapter = doc1.getElementsByClass("btn btn-primary col-xs-4");
    		SalvaTexto("<br>"+capitulo.get(0).html()+"<br>"+texto.get(0).html(), writer);
    	}
    	CloseTexto(writer);
    }
    public static boolean VerificaExistenciaNextChapter(Elements nextChapter) {
    	if(nextChapter.size() == 3) {
    		return true;
    	}else if(nextChapter.size() == 2) {
    		String regex = "\\/fiction\\/[0-9]*\\/[a-zA-Z\\-]*\\/chapter\\/[0-9]*";
    		Pattern pattern = Pattern.compile(regex);
    		Matcher matcher = pattern.matcher(nextChapter.get(1).attr("href"));
    		if(matcher.find())
    			return true;
    	}
    	return false;
    }
    public static PrintWriter GeraArquivo() throws FileNotFoundException, UnsupportedEncodingException {
    	PrintWriter writer = new PrintWriter("the-file-name.html", "UTF-8");
		return writer;
    }
    public static void SalvaTexto(String texto, PrintWriter writer) {
    	writer.append(texto);
    }
    public static void CloseTexto(PrintWriter writer) {
    	writer.close();
    }
}

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;


public class Scraper {
	
	public static void main(String[] args) throws Exception {
//       final String dir = System.getProperty("user.dir");
//       System.out.println("current dir = " + dir);

       Document document = Jsoup.parse(new File("testpage.html"), "utf-8"); 
       
       Elements tbodies = document.select("tbody");
       System.out.println("Number of tbodies: " + tbodies.size());

       System.out.println("\n*** TABLES ***\n");
       Elements tables = document.select("body > table");
       System.out.println("Number of tables: " + tables.size());
       
       
       // Loop over the tables in the page and construct the appropriate type
       String tableType = ""; 
       // integer count keeps track of table number 
       int count = 1;
       for(Element t : tables) {
    	   tableType = t.select("caption").text();
    	   if (tableType.equals("Events & Orders of the Court")) {
    	       System.out.println("table " + count + ": Events table created.");
    		   EventsTable eventTable = new EventsTable(t);
    	   } else {
    		   System.out.println("Table does not match identified type; no table constructed in data");
    	   }
    	   count++;
       }

    }
}
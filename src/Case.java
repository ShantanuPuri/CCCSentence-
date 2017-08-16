import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.util.*;

public class Case {
	String crossRefNumber 				= "";
	String originalCaseNumber           = "";
    EventsTable eventTable 				= new EventsTable(); 
    ChargeInformationTable chargeTable 	= new ChargeInformationTable();
    int caseFound; 
    
    public String getCrossRefNumber() {
    	return crossRefNumber; 
    }
    
    public ChargeInformationTable getChargeInformationTable() {
    	return chargeTable; 
    }
    
    public EventsTable getEventsTable() {
    	return eventTable;
    }
    

    // Get information directly from Events Information Table
	public String getDispositionsString() {
		return getEventsTable().getDispositionsString(); 
	}
	
	public String getSentencesString() {
		return getEventsTable().getSentencesString();
	}
	
	public String getEventsString() {
		return getEventsTable().getEventsString();
	}
	
	public TreeMap<String, Sentence> getSentences() {
		return getEventsTable().getSentences();
	}
	
	public int isIncarcerated() {
		int inc = 0; 
		for(String k : getEventsTable().getSentences().keySet()) {
			inc = getEventsTable().getSentences().get(k).isIncarcerated();
		}
		
		return inc;
	}
	
	public int isSuspended() {
		int susp = 0; 
		for(String k : getEventsTable().getSentences().keySet()) {
			susp = getEventsTable().getSentences().get(k).isSuspended();
		}
		
		return susp;
	}
	
	public String getSuspendedText() {
		String susp = ""; 
		for(String k : getEventsTable().getSentences().keySet()) {
			susp = susp + getEventsTable().getSentences().get(k).getSuspendedText();
		}
		
		return susp;
	}
	
	public String getMaxSentenceText() {
		String s = ""; 
		for(String k : getEventsTable().getSentences().keySet()) {
			s += getEventsTable().getSentences().get(k).getMaxSentenceText() + ";"; 
		}
		if (s.endsWith(";;")) {
			s = s.replace(";;", ";"); 
		}
		return s;
	}
	
	public String getMinSentenceText() {
		String s = ""; 
		for(String k : getEventsTable().getSentences().keySet()) {
//			System.out.println(k + " TEST: " + getEventsTable().getSentences().get(k).getMinSentenceText());
			s += getEventsTable().getSentences().get(k).getMinSentenceText() + ";";
		}
//		System.out.println("TEST: " + s);
		if (s.endsWith(";;")) {
			s = s.replace(";;", ";"); 
		}
		return s;
	}
	
	public String getSentencedToText() {
		String s = ""; 
		for(String k : getEventsTable().getSentences().keySet()) {
			s = getEventsTable().getSentences().get(k).getSentencedToText(); 
		}
		
		return s;
		
	}
	
	public String getSentenceMin() {
		int min = -1;
		int tempMin = -2;
		String tempMinUnit = "";
		String minUnit = "";
		for(String k : getEventsTable().getSentences().keySet()) {
			tempMin = getEventsTable().getSentences().get(k).getMin();
			tempMinUnit = getEventsTable().getSentences().get(k).getMinUnit();
//			System.out.println(tempMinUnit);
			if (tempMinUnit == null) {
				System.out.println("CONTINUED MIN");
				continue; 
			}
			if (min == -1) {
				min = tempMin;
				minUnit = tempMinUnit;
			}
			else if (minUnit.compareTo(tempMinUnit) == 0) {
 				System.out.println("MIN ENTERED 1");
// 				System.out.println("TEMP MIN UNIT: " + tempMinUnit);
// 				System.out.println("ACTUAL MIN UNIT: " + getEventsTable().getSentences().get(k).getMinUnit());
				if (tempMin >= min) {
	 				System.out.println("MIN CHANGED 1");
					min = tempMin; 
				}
			}
			else {
 				System.out.println("MIN ENTERED 2");
				if (tempMinUnit.compareTo("Months") == 0) {
					tempMin *= 30; 
					tempMinUnit = "Days";
				}
				else if (tempMinUnit.compareTo("Years") == 0) {
					tempMin *= 365; 
					tempMinUnit = "Days";
				}
				if (minUnit.compareTo("Months") == 0) {
					min *= 30; 
					minUnit = "Days";
				}
				else if (minUnit.compareTo("Years") == 0) {
					min *= 365; 
					minUnit = "Days";
				}
				
				if (tempMin >= min) {
	 				System.out.println("MIN CHANGED 2");
					min = tempMin;
				}
			}
			System.out.println("mintest: " + min);
		}
		
		return min + " " + minUnit;	
	}
	
	public String getSentenceMax() {
		int max = -1;
		int tempMax = -2;
		String tempMaxUnit = "";
		String maxUnit = "";
		for(String k : getEventsTable().getSentences().keySet()) {
			tempMax = getEventsTable().getSentences().get(k).getMax();
			tempMaxUnit = getEventsTable().getSentences().get(k).getMaxUnit();
//			System.out.println(tempMaxUnit);
			if (tempMaxUnit == null) {
				System.out.println("CONTINUED MAX");
				continue; 
			}
			if (max == -1) {
				max = tempMax;
				maxUnit = tempMaxUnit;
			}
			else if (maxUnit.equals(tempMaxUnit)) {
 				System.out.println("MAX ENTERED 1");
// 				System.out.println("TEMP MAX UNIT: " + tempMaxUnit);
// 				System.out.println("ACTUAL MAX UNIT: " + getEventsTable().getSentences().get(k).getMaxUnit());
				if (tempMax >= max) {
	 				System.out.println("MAX CHANGED 1");
					max = tempMax; 
				}
			}
			else {
 				System.out.println("MAX ENTERED 2");
				System.out.println(tempMaxUnit);
 				if (tempMaxUnit.equals("Months")) {
					tempMax *= 30; 
					tempMaxUnit = "Days";
				}
				else if (tempMaxUnit.equals("Years")) {
					tempMax *= 365; 
					tempMaxUnit = "Days";
				}
				if (maxUnit.equals("Months")) {
					max *= 30; 
					maxUnit = "Days";
				}
				else if (maxUnit.equals("Years")) {
					max *= 365; 
					maxUnit = "Days";
				}
				
				if (tempMax >= max) {
	 				System.out.println("MAX CHANGED 2");
					max = tempMax;
				}
			}
			System.out.println("maxtest: " + max);
		}
		
		return max + " " + maxUnit;	
	}
	
	public String getSentenceMinUnit() {
		String sentenceMinUnit = ""; 
//		String minString = min + "";
		for(String k : getEventsTable().getSentences().keySet()) {
			sentenceMinUnit = getEventsTable().getSentences().get(k).getMinUnit(); 
//			System.out.println("testMinUnit: " + sentenceMinUnit + ", " + k);
		}
		System.out.println("minUnittest: " + sentenceMinUnit);
		return sentenceMinUnit;	
	}
	
	public String getSentenceMaxUnit() {
		String sentenceMaxUnit = ""; 
//		String maxString = max + "";
		for(String k : getEventsTable().getSentences().keySet()) {
			sentenceMaxUnit = getEventsTable().getSentences().get(k).getMaxUnit(); 
//			System.out.println("testMaxUnit: " + sentenceMaxUnit + ", " + k);
		}
		System.out.println("maxUnittest: " + sentenceMaxUnit);
		return sentenceMaxUnit;	
	}
	
	// Get information directly from Charge Information Table
	public ArrayList<String> getCharges() {
		return getChargeInformationTable().getCharges();
	}
	
	public String getChargesString() {
		return getChargeInformationTable().getChargesString();
	}
	
	public int getCaseFound() {
		return caseFound;
	}
	
	public String getOriginalCaseNumber() {
		return originalCaseNumber;
	}
	
    
	// constructor to decide type of table and create new instance accordingly
	public Case(Document page, String originalCaseNumber) {
		this.originalCaseNumber = originalCaseNumber;
		if(!page.text().equals("Sorry, case not found!")) {
			caseFound = 1;
			crossRefNumber = page.select(".ssCaseDetailCaseNbr").text();
		    Elements tables = page.select("body > table");
		    String tableType = ""; 
		    
		    for(Element t : tables) {
		    	tableType = t.select("caption").text();
		    	if (tableType.equals("Events & Orders of the Court")) {
	    	       eventTable = new EventsTable(t);
	    	   } else if (tableType.equals("Charge Information")) {
	    	       chargeTable = new ChargeInformationTable(t);

	    	   } else {

	    	   }
	       }
	       
		} else { 
			caseFound = 0;
			System.out.println("No case here!");
		}   
	}
}
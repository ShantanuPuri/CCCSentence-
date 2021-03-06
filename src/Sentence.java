import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import org.jsoup.nodes.Element;
public class Sentence {
	String tempSentencedToText = "";
	String tempMinText         = "";
	String charge			= ""; 
	String sentencedToText 	= "";
	String minSentenceText	= ""; 
	String maxSentenceText	= "";
	String sentenceTermText	= "";
	String suspendedText	= ""; 
	String sentenceAllText	= "";
	int incarcerated		= 0; 
	int suspended			= 0; 
	
	private int min = -1;
	private int max = -1;
	private String minUnit;
	private String maxUnit;
	
	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}
	
	public String getMinUnit() {
		return minUnit;
	}
	
	public String getMaxUnit() {
		return maxUnit;
	}
	
	public String getCharge() {
		return charge;
	}
	
	public String getSentencedToText() {
		return sentencedToText.replaceAll("[,]", "-");
	}

	public String getMinSentenceText() {
		return minSentenceText.replaceAll("[,]", "-");
	}
	
	public String getMaxSentenceText() {
		return maxSentenceText.replaceAll("[,]", "-");
	}
	
	public String getSentenceTermText() {
		return sentenceTermText.replaceAll("[,]", "-");
	}
	
	public String getSuspendedText() {
		return suspendedText.replaceAll("[,]", "-");
	}
	
	public String getSentenceAllText() {
		return sentenceAllText.replaceAll("[,]", "-");
	}
	
	public int isIncarcerated() {
		return incarcerated;
	}
	
	public int isSuspended () {
		return suspended;
	}

	
	public Sentence() {

	}
	
//	int testMax[] = {0, 0, 0, 0};
//	int j = 0;

	public Sentence(Element aTable) {
		String chargeText = aTable.select("div > div > div").first().ownText();
//		System.out.println("CHARGE ALL TEXT: " + chargeText);
		sentenceAllText = aTable.select("div > div > div > div").text();
		charge = chargeText;
		
		// This gets the innermost element that contains the phrase indicated
		// "Sentenced to" is usually followed by the institution. This is an indication of incarceration
		if(null != aTable.select(":contains(Sentenced to):not(:has(:contains(Sentenced to)))").first()) {
			if(suspended == 0) {
				incarcerated = 1;
			}
			sentencedToText = aTable.select(":contains(Sentenced to):not(:has(:contains(Sentenced to)))").first().text();
//			if (!aTable.select(":contains(Sentenced to):not(:has(:contains(Sentenced to)))").isEmpty()) {
//				tempSentencedToText = aTable.select(":contains(Sentenced to):not(:has(:contains(Sentenced to)))").text();
//				System.out.println("TEMP: " + tempSentencedToText);
//			}
//			else {
//				System.out.println("SORRY SENTENCED TO");
//			}
		}
		
		// Contains the minimum length of sentence (may contain other text)
		if(null != aTable.select(":contains(Minimum):not(:has(:contains(Minimum)))").first()) {
			if(suspended == 0) {
				incarcerated = 1;	
			}
			
			minSentenceText = aTable.select(":contains(Minimum):not(:has(:contains(Minimum)))").first().text();
			if (!aTable.select(":contains(Minimum):not(:has(:contains(Minimum)))").isEmpty()) {
				tempMinText = aTable.select(":contains(Minimum):not(:has(:contains(Minimum)))").text();
//				System.out.println("TEMP: " + tempMinText);
				
			}
			else {
				System.out.println("SORRY MIN");
			}
		}
		
		// Contains maximum length of sentence (may contain other text)
		if(null != aTable.select(":contains(Maximum):not(:has(:contains(Maximum)))").first()) {
			if(suspended == 0) {
				incarcerated = 1;	
			}
			maxSentenceText = aTable.select(":contains(Maximum):not(:has(:contains(Maximum)))").first().text();
		}
		
		// "Term" may contain the sentence length
		if(null != aTable.select(":contains(Term):not(:has(:contains(Term)))").first()) {
			if(suspended == 0) {
				incarcerated = 1;	
			}
			sentenceTermText = aTable.select(":contains(Term):not(:has(:contains(Term)))").first().text();			 
		}
		
		// Indication of suspended sentence
		if(null != aTable.select(":contains(Suspended):not(:has(:contains(Suspended)))").first()) {
			suspendedText = aTable.select(":contains(Suspended):not(:has(:contains(Suspended)))").first().text();
			suspended = 1; 
			incarcerated = 0; 
		}

		// isMinUnit and isMaxUnit check whether minimum and maximum have valid units (days/months/year)
		boolean isMinUnit, isMaxUnit;
		String[] splitSentence;
		String sentence = "";
		
		if (minSentenceText == "") {
			sentence = sentenceAllText;
		}
		else {
			sentence = minSentenceText;
		}
		splitSentence = sentence.split("\\W+");
		for (int i = 0; i < splitSentence.length; i++) {
			if (splitSentence[i].compareTo("Minimum") == 0) {
				if (StringUtils.isNumeric(splitSentence[i + 1])) {
					min = Integer.parseInt(splitSentence[i + 1]);
					isMinUnit = splitSentence[i + 2].compareTo("days") == 0 || splitSentence[i + 2].compareTo("Days") == 0 || 
							splitSentence[i + 2].compareTo("months") == 0 || splitSentence[i + 2].compareTo("Months") == 0 || 
							splitSentence[i + 2].compareTo("years") == 0 || splitSentence[i + 2].compareTo("Years") == 0;
					if (isMinUnit) {
						minUnit = splitSentence[i + 2];
						System.out.println("Split Min Print: " + splitSentence[i + 1] + " " + splitSentence[i + 2]);
					}
					
				}
			}
			else if (splitSentence[i].compareTo("Maximum") == 0) {
				if (StringUtils.isNumeric(splitSentence[i + 1])) {
					max = Integer.parseInt(splitSentence[i + 1]);
					isMaxUnit = splitSentence[i + 2].compareTo("days") == 0 || splitSentence[i + 2].compareTo("Days") == 0 || 
							splitSentence[i + 2].compareTo("months") == 0 || splitSentence[i + 2].compareTo("Months") == 0 || 
							splitSentence[i + 2].compareTo("years") == 0 || splitSentence[i + 2].compareTo("Years") == 0;
					if (isMaxUnit) {
						maxUnit = splitSentence[i + 2];
						System.out.println("Split Max Print: " + splitSentence[i + 1] + " " + splitSentence[i + 2]);
					}	
				}
			}
			else {
				if (splitSentence[i].compareTo("Term") == 0) {
					if (StringUtils.isNumeric(splitSentence[i + 1])) {
						min = Integer.parseInt(splitSentence[i + 1]);
						isMinUnit = splitSentence[i + 2].compareTo("days") == 0 || splitSentence[i + 2].compareTo("Days") == 0 || 
								splitSentence[i + 2].compareTo("months") == 0 || splitSentence[i + 2].compareTo("Months") == 0 || 
								splitSentence[i + 2].compareTo("years") == 0 || splitSentence[i + 2].compareTo("Years") == 0;
						if (isMinUnit) {
							minUnit = splitSentence[i + 2];
							System.out.println("Split Term Print: " + splitSentence[i + 1] + " " + splitSentence[i + 2]);
						}	
					}
				}	
			}
		}
	}
	
	public static void main(String[] args) {
	}
}
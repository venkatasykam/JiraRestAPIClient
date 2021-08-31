package com.jira.grafana.integration.JiraAPIClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.IssueFieldId;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.opencsv.CSVWriter;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;


/**
 * Hello world!
 *
 */
public class JiraClient 
{
	/*
    public static String JIRA_URL = System.getenv("JIRA_URL");
    public static  String JIRA_ADMIN_USERNAME = System.getenv("JIRA_ADMIN_USERNAME");
    public static  String JIRA_ADMIN_PASSWORD = System.getenv("JIRA_ADMIN_PASSWORD");
    */
    public static void main( String[] args )
    {
    	try {
    		
    		String JIRA_PROJECT_KEY = args[0].toString();
    	    String JIRA_URL = args[1].toString();
    	    String JIRA_ADMIN_USERNAME = args[2].toString();
    	    String JIRA_ADMIN_ACCESS_TOKEN = args[3].toString();
    		
    		String[] names = {""};
    		String sName = "NONE";
    		String sprintName = "none";
    		int totalStories = 0;
    		int storyPointsCommitted = 0;
    		int storyPointsDelivered = 0;
    		ArrayList<String> sprintDetailsList = new ArrayList<String>();
    		Set<String> sprintNames = new LinkedHashSet<String>();  
    		
    		File file = new File("jira-datasource.csv");
    		// create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);
            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);
            // adding header to csv
            String[] header = { "Sprint", "Total Stories", "Story Points Committed", "Story points Delivered"};
            writer.writeNext(header);
	        
	        //System.out.println("totalStories1: "+totalStories);
    		
    		URI jiraServerUri = URI.create(JIRA_URL);
    		
    		AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
    		
    		AuthenticationHandler auth = new BasicHttpAuthenticationHandler(JIRA_ADMIN_USERNAME, JIRA_ADMIN_ACCESS_TOKEN);
    		
    		JiraRestClient restClient = factory.create(jiraServerUri, auth);
    		
    		SearchRestClient src = restClient.getSearchClient();
    		
    		String JQL = "project = "+JIRA_PROJECT_KEY+" AND issuetype = Story";

    		SearchResult searchResult = src.searchJql(JQL).get();
    		
    		Iterable<Issue> issueList = searchResult.getIssues();
    		
    		System.out.println("[Debug] totalStories2: "+searchResult.getTotal());
    		
    		for(Issue issue:issueList) {
    			
    			IssueField issueFieldStory = issue.getFieldByName("Story Points");
    			IssueField issueFieldSprint = issue.getFieldByName("Sprint");
    			
    			String issueType=issue.getIssueType().getName();
    			String issueKey = issue.getKey();
    			String issueStatus = issue.getStatus().getStatusCategory().getName();
    			String storyFieldName = issueFieldStory.getName();
    			double storyPoints = 0.0;
    			
    			if(issueFieldStory.getValue()!=null) {
    				storyPoints = (double)issueFieldStory.getValue();
    			}
    			
    			//System.out.println("[DEBUG]:====="+issueFieldSprint.toString());
    			//String sprintFieldName = issueFieldSprint.getValue().toString();//.getName();
    			//System.out.println("[DEBUG]:====="+sprintFieldName.toString());
    			
    			//String sprint = 
    			
    			if(issueFieldSprint.getValue() != null) {
    				String sprintFieldName = issueFieldSprint.getValue().toString();
	    			String sprintDetailsJSONObject = sprintFieldName.toString();
	    			String[] sprintDetails = sprintDetailsJSONObject.split(",");
	    			
	    			for(String sprintu:sprintDetails) {
	    				if(sprintu.contains("name")) {
	    					//System.out.println("[DEBUG-sprintu]:====="+sprintu);
	    					names = sprintu.split("=");
	    				
	    					sName = names[1].replaceAll("\"", "");
	    					
	    				}
	    			}

	    			sprintName = sName;
	    			
	    			sprintNames.add(sprintName);
	    			
	    			//sprintname-story-points-status
	    			sprintDetailsList.add(sprintName+":"+"story"+":"+storyPoints+":"+issueStatus);
	    			
    			}else {
    				sprintName = "NONE";
    			}
    			
    			//System.out.println("[INFO] Issue Type: "+issueType+" - issueKey"+issueKey+" - issue status: "+issueStatus+" - Story Field Name: "+storyFieldName+" - Story Points: "+storyPoints+" - Sprint Field Name: "+sprintFieldName+" - Sprint Field Value: "+sprintName);
    		}
    		/*
    		for(String sprintDetails: sprintDetailsList){
    			totalStories = totalStories+1;
    			
    			if(sprintDetails.contains("Done")) {
    				
    			}else {
    				
    			}
    			
    		}*/
    		

    		//System.out.println("========sprintDetailsList=============");
    		
    		//System.out.println(sprintDetailsList);
    		
    		ArrayList<String> finalSprintDetails = new ArrayList<String>();
    		
    		for(String sprintNam: sprintNames) {
    			finalSprintDetails.add(getFinalSprintDetails(sprintNam,sprintDetailsList));
    		}
    		
    		System.out.println("========Final Sprint Details=============");
    		
    		Collections.sort(finalSprintDetails);   
    		
    		System.out.println(finalSprintDetails);
    		
    		for(String sprintDetails: finalSprintDetails) {
    			writer.writeNext(sprintDetails.split(":"));
    		}
    		
    		writer.close();
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
        	
    }
    
    public static String getFinalSprintDetails(String sprintNam, ArrayList<String> sprintDetailsList) {
    	
    	String finalSprintDetails= "";
    	String sprintName = "";
    	int totalStoryPointsCommitted = 0;
    	int totalStoryPointsDelivered = 0;
    	int totalStories = 0;
    	
    	for(String sprintDetails: sprintDetailsList){
    		//sprintname-story-points-status
    		
    		if(sprintDetails.contains(sprintNam)) {
    			
    			totalStories = totalStories + 1;
    			
    			String[] sprintArray = sprintDetails.split(":");
    			
    			sprintName=sprintArray[0];
    			
    			//System.out.println("[DEBUG]: "+Integer.parseInt(sprintArray[2].replace(".0", "")));
    			
    			totalStoryPointsCommitted = totalStoryPointsCommitted + Integer.parseInt(sprintArray[2].replace(".0", ""));

    			if(sprintDetails.contains("Done")) {
    				
    				totalStoryPointsDelivered = totalStoryPointsDelivered + Integer.parseInt(sprintArray[2].replace(".0", ""));
    			}
    				
    		}
    	}
    	
    	finalSprintDetails = sprintName+":"+totalStories+":"+totalStoryPointsCommitted+":"+totalStoryPointsDelivered;
    	
    	return finalSprintDetails;
    	
    }
}

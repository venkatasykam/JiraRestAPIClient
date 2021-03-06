package com.jira.grafana.integration.JiraAPIClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.atlassian.jira.rest.client.api.AuditRestClient;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.ComponentRestClient;
import com.atlassian.jira.rest.client.api.GroupRestClient;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.ProjectRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.IssueFieldId;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.opencsv.CSVWriter;

import io.atlassian.util.concurrent.Promise;
import net.rcarz.jiraclient.BasicCredentials;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;

import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import net.rcarz.jiraclient.greenhopper.RapidView;
import net.rcarz.jiraclient.greenhopper.Sprint;
import net.rcarz.jiraclient.greenhopper.SprintIssue;
import net.rcarz.jiraclient.greenhopper.SprintReport;
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
	
	public static Set<String> sprintNames = new LinkedHashSet<String>();  
	
    public static void main( String[] args )
    {
    	try {
    		
    		String JIRA_PROJECT_KEY = args[0].toString();
    		//String SPRINT_VALUE = args[1].toString();
    		String RAPID_VIEW_ID = args[1].toString();
    	    String JIRA_URL = args[2].toString();
    	    String JIRA_ADMIN_USERNAME = args[3].toString();
    	    String JIRA_ADMIN_ACCESS_TOKEN = args[4].toString();
    	    //String JIRA_ADMIN_PASSWORD = args[4].toString();
    	    

    		int totalStories = 0;
    		int storyPointsCommitted = 0;
    		int storyPointsDelivered = 0;
    		ArrayList<String> sprintDetailsList = new ArrayList<String>();
    		int rapidViewId = Integer.parseInt(RAPID_VIEW_ID);
    		
    		File file = new File("jira-datasource.csv");
    		// create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);
            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);
            // adding header to csv
            String[] header = {"POD", "Sprint", "Total Stories", "Story Points Committed", "Story points Delivered"};
            writer.writeNext(header);
	        
	        //System.out.println("totalStories1: "+totalStories);
    		
    		URI jiraServerUri = URI.create(JIRA_URL);
    		
    		AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
    		
    		AuthenticationHandler auth = new BasicHttpAuthenticationHandler(JIRA_ADMIN_USERNAME, JIRA_ADMIN_ACCESS_TOKEN);
    		
    		JiraRestClient restClient = factory.create(jiraServerUri, auth);
    		
    		SearchRestClient src = restClient.getSearchClient();
    		
    		//AuditRestClient arc = restClient.getAuditRestClient();
    		//ComponentRestClient crc = restClient.getComponentClient();
    		//GroupRestClient gc = restClient.getGroupClient();
    		//IssueRestClient ic = restClient.getIssueClient();
    		//MetadataRestClient mrc = restClient.getMetadataClient();
    		//ProjectRestClient prc = restClient.getProjectClient();
    		//Promise<Project> project = prc.getProject(JIRA_PROJECT_KEY);
    		//Project pr = project.get();
    		//pr.
    		
    		BasicCredentials creds = new BasicCredentials(JIRA_ADMIN_USERNAME, JIRA_ADMIN_ACCESS_TOKEN);
    		net.rcarz.jiraclient.JiraClient jira = new net.rcarz.jiraclient.JiraClient(JIRA_URL, creds);
    		GreenHopperClient gh = new GreenHopperClient(jira);
    		
    		List<RapidView> allRapidBoards = gh.getRapidViews();
    		List<Sprint> listOfSprints = new ArrayList<Sprint>();
    		List<SprintIssue> sprintIssuesList = new ArrayList<SprintIssue>();
    		//List<SprintIssue> sprintCompletedIssuesList = new ArrayList<SprintIssue>();
    		//List<SprintIssue> sprintInompletedIssuesList = new ArrayList<SprintIssue>();
    		
    		//System.out.println("sprintIssue.getKey(): ");
    		/*
    		for(RapidView rapidView:allRapidBoards) {
    			listOfSprints = rapidView.getSprints();
    			
    			//System.out.println("rapidView.getId(): "+rapidView.getId());
    			//System.out.println("rapidView.toString(): "+rapidView.toString());
    			//System.out.println("listOfSprints: "+listOfSprints.toString());
    			
    			for(Sprint sprint:listOfSprints) {
    				sprintIssuesList = sprint.getIssues();
    				SprintReport sr = rapidView.getSprintReport(sprint);
    				sprintIssuesList.addAll(sr.getCompletedIssues());
    				sprintIssuesList.addAll(sr.getIncompletedIssues());
    				//System.out.println("sprintIssuesList: "+sprintIssuesList.toString());
    				//System.out.println("sprint.toString(): "+sprint.toString());
    				for(SprintIssue sprintIssue:sprintIssuesList) {
    					//System.out.println("sprintIssue.getKey(): "+sprintIssue.getKey());
    					if(sprintIssue.getKey().split("-")[0].equals(JIRA_PROJECT_KEY)){
    						rapidViewId = rapidView.getId();
    						break ;
    					}
    				}
    			}
    		}
    		*/
    		RapidView board = gh.getRapidView(rapidViewId);
    		
    		
    		List<Sprint> sprints = board.getSprints();
    		
    		System.out.println("Total Sprints: "+sprints.size());
    		int sprintsCount = 0;
            for (Sprint sprint : sprints) {
            	sprintsCount = sprintsCount + 1;
                
                //SprintReport sr = board.getSprintReport(sprint);
                //System.out.println("		Sprint getAllIssuesEstimateSum(): "+sr.getAllIssuesEstimateSum().getValue());
                //System.out.println("		Sprint getCompletedIssuesEstimateSum(): "+sr.getCompletedIssuesEstimateSum().getValue());
                //System.out.println("		Sprint getPuntedIssuesEstimateSum(): "+sr.getPuntedIssuesEstimateSum().getValue());
                if(!sprint.getName().isEmpty() && sprint !=null && sprintsCount <=40 ) {
                	System.out.println("Sprint Name-"+sprintsCount+": "+sprint);
                	String JQL = "project = "+JIRA_PROJECT_KEY+" AND issuetype = Story AND Sprint = '"+sprint.getName().toString()+"'";
	            	System.out.println("JQL: "+JQL);
	                SearchResult searchResult = src.searchJql(JQL).get();
	                Iterable<Issue> issueList = searchResult.getIssues();
	                sprintDetailsList.addAll(getSprintDetails(issueList));
                }else {
                	//System.out.println("Empty sprint");
                }
            }
    		//String JQL = "project = "+JIRA_PROJECT_KEY+" AND issuetype = Story AND  Sprint = "+SPRINT_VALUE;
    		
            /*
    		SearchResult searchResult1 = src.searchJql(JQL1).get();
    		SearchResult searchResult2 = src.searchJql(JQL2).get();
    		SearchResult searchResult3 = src.searchJql(JQL3).get();
    		
    		System.out.println("[Debug] searchResult1.getMaxResults(): "+searchResult1.getMaxResults());
    		System.out.println("[Debug] searchResult2.getMaxResults(): "+searchResult2.getMaxResults());
    		System.out.println("[Debug] searchResult3.getMaxResults(): "+searchResult3.getMaxResults());
    		
    		Iterable<Issue> issueList1 = searchResult1.getIssues();
    		Iterable<Issue> issueList2 = searchResult2.getIssues();
    		Iterable<Issue> issueList3 = searchResult3.getIssues();
    		
    		System.out.println("[Debug] totalStories of searchResult1: "+searchResult1.getTotal());
    		System.out.println("[Debug] totalStories of searchResult2: "+searchResult2.getTotal());
    		System.out.println("[Debug] totalStories of searchResult3: "+searchResult3.getTotal());
    		
    		sprintDetailsList.addAll(getSprintDetails(issueList1));
    		sprintDetailsList.addAll(getSprintDetails(issueList2));
    		sprintDetailsList.addAll(getSprintDetails(issueList3));
			*/
    		//System.out.println("========sprintDetailsList=============");
    		
    		//System.out.println(sprintDetailsList);
    		
    		ArrayList<String> finalSprintDetails = new ArrayList<String>();
    		
    		String sprintDetails = "";
    		
    		for(String sprintNam: sprintNames) {
    			sprintDetails = getFinalSprintDetails(sprintNam,sprintDetailsList);
    			finalSprintDetails.add(sprintDetails);
    			writer.writeNext(sprintDetails.split(":"));
    		}
    		
    		System.out.println("========Final Sprint Details=============");
    		
    		Collections.sort(finalSprintDetails);   
    		
    		System.out.println(finalSprintDetails);
    		/*
    		for(String sprintDetails: finalSprintDetails) {
    			writer.writeNext(sprintDetails.split(":"));
    		}
    		*/
    		writer.close();
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
        	
    }
    
    public static ArrayList<String> getSprintDetails(Iterable<Issue> issueList) {
    	
    	ArrayList<String> sprintDetailsList = new ArrayList<String>();
    	
    	try {
    	
	    	String[] names = {""};
			String sName = "NONE";
			String sprintName = "none";
			
		
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
	    					names = sprintu.split("="); //In JIRA cloud, this value comes with colon :
	
	    					sName = names[1].replaceAll("\"", "");
	
	    				}
	    			}
	
	    			sprintName = sName.trim();
	    			
	    			sprintNames.add(sprintName);
	    			
	    			//sprintname-story-points-status
	    			sprintDetailsList.add(sprintName+":"+"story"+":"+storyPoints+":"+issueStatus);
	    			
				}else {
					sprintName = "NONE";
				}
				
				//System.out.println("[INFO] Issue Type: "+issueType+" - issueKey"+issueKey+" - issue status: "+issueStatus+" - Story Field Name: "+storyFieldName+" - Story Points: "+storyPoints+" - Sprint Field Name: "+sprintFieldName+" - Sprint Field Value: "+sprintName);

			}
			
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return sprintDetailsList;
    }
    
    public static String getFinalSprintDetails(String sprintNam, ArrayList<String> sprintDetailsList) {
    	
    	String finalSprintDetails= "";
    	String sprintName = "";
    	String podName = "NONE";
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
    	
    	if(sprintName.contains("_")) {
	    	String[] podSprint = sprintName.split("_");
	    	
	    	podName = podSprint[0];
	    	sprintName = podSprint[1];
   		}
    	finalSprintDetails = podName+":"+sprintName+":"+totalStories+":"+totalStoryPointsCommitted+":"+totalStoryPointsDelivered;
    	
    	return finalSprintDetails;
    	
    }
}

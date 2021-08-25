package com.jira.grafana.integration.JiraAPIClient;

/*import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.Filter;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import java.net.URI;
*/
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
import org.joda.time.DateTime;

import java.net.URI;


/**
 * Hello world!
 *
 */
public class JiraClient 
{
	
    public static final String JIRA_URL = "https://jiraURL.com";
    public static final  String JIRA_ADMIN_USERNAME = "username";
    public static final  String JIRA_ADMIN_PASSWORD = "accessKey-or-personal-access-token";
   
    public static void main( String[] args )
    {
    	try {
    		
    		int totalStories = 0;
    		
    		String[] names = null;
    		String sName = "NONE";
    		String sprintName = "none";
    	
	        System.out.println( "Hello World!" );
	        
	        System.out.println("totalStories1: "+totalStories);
	        /*
	        JiraClient jc = new JiraClient();
	        
	        JiraRestClient jiraRestClient =  jc.getJiraRestClient();
	        
	        final SearchRestClient searchRestClient = jiraRestClient.getSearchClient();
	        
	        SearchResult searchResult = searchRestClient.searchJql("project = SSP AND issuetype = Story").get();
	        
	        //final SearchResult searchResult = searchRestClient.searchJql(filter.getJql()).claim();
	        
	        totalStories = searchResult.getTotal();
	        
	        System.out.println("totalStories2: "+totalStories);
	        
	        Iterable<Issue> issueList = searchResult.getIssues();
	        
	        System.out.println(issueList.toString());*/
    		
    		URI jiraServerUri = URI.create(JIRA_URL);
    		
    		AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
    		
    		AuthenticationHandler auth = new BasicHttpAuthenticationHandler(JIRA_ADMIN_USERNAME, JIRA_ADMIN_PASSWORD);
    		
    		JiraRestClient restClient = factory.create(jiraServerUri, auth);
    		
    		SearchRestClient src = restClient.getSearchClient();
    		
    		SearchResult searchResult = src.searchJql("project = SSP AND issuetype = Story").get();
    		
    		Iterable<Issue> issueList = searchResult.getIssues();
    		
    		System.out.println("totalStories2: "+searchResult.getTotal());
    		
    		//System.out.println(issueList.toString());
    		
    		
    		
    		for(Issue issue:issueList) {
    			
    			IssueField issueFieldStory = issue.getFieldByName("Story Points");
    			IssueField issueFieldSprint = issue.getFieldByName("Sprint");
    			
    			String issueType=issue.getIssueType().getName();
    			String issueKey = issue.getKey();
    			String issueStatus = issue.getStatus().getStatusCategory().getName();
    			String storyFieldName = issueFieldStory.getName();
    			double storyPoints = (double)issueFieldStory.getValue();
    			String sprintFieldName = issueFieldSprint.getName();
	    		
    			if(issueFieldSprint.getValue() != null) {
	    			String sprintDetailsJSONObject = issueFieldSprint.getValue().toString();
	    			String[] sprintDetails = sprintDetailsJSONObject.split(",");
	    			
	    			for(String sprint:sprintDetails) {
	    				if(sprint.contains("name")) {
	    					names = sprint.split(":");
	    					sName = names[1].replaceAll("\"", "");
	    				}
	    			}
	
	    			sprintName = sName;
	    			
    			}else {
    				sprintName = "NONE";
    			}
    			
    			System.out.println("Issue Type: "+issueType+" - issueKey"+issueKey+" - issue status: "+issueStatus+" - Story Field Name: "+storyFieldName+" - Story Points: "+storyPoints+" - Sprint Field Name: "+sprintFieldName+" - Sprint Field Value: "+sprintName);
    		}
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
        	
    }
}


Step-1: Download the zip file `JiraRestAPIClient.zip` form this location [JiraRestAPIClient.zip](JiraRestAPIClient.zip) and unzip it.

Step-2: **java command to execute**: `java -jar JiraRestAPIClient-1.0.0.jar ${JIRA_PROJECT_KEY} ${JIRA_URL} ${JIRA_ADMIN_USERNAME} ${JIRA_ADMIN_ACCESS_TOKEN}`

      `example: java -jar JiraRestAPIClient-1.0.0.jar SSP https://JIRA_SERVER_URL/ jira-admin-user jira-admin-user-personal-access-token`

Step-3: Commit the generated csv file `jira-datasource.csv` to git repo. (the same git repo path which is using for data source in Grafana)



Step-1: Download the Jar file `JiraAPIClient-1.0.0.jar` form this location.

Step-2: **java command to execute**: `java -jar target/JiraAPIClient-1.0.0-SNAPSHOT.jar ${JIRA_PROJECT_KEY} ${JIRA_URL} ${JIRA_ADMIN_USERNAME} ${JIRA_ADMIN_ACCESS_TOKEN}`

      `example: java -jar target/JiraAPIClient-1.0.0-SNAPSHOT.jar SSP https://username.atlassian.net/ jira-admin-user jira-admin-user-personal-access-token`

Step-3: Commit the generated csv file `jira-datasource.csv` to git repo. (the same git repo path which is using for data source in Grafana)


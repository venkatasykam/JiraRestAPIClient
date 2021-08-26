
## Aim of this JiraRestAPIClient

  To generate *jira-datasource.csv* file which will contains the data pulled from the JIRA proejcts with the details - sprint, total stories, total story points and stroy points delivered. See the sample output data in the file [jira-datasource.csv](https://github.com/venkatasykam/JiraRestAPIClient/blob/master/jira-datasource.csv)

### Tools used for development, build and execute

> Java version: jdk1.8.0_301

> maven version: 3.8.1

> git version: git version 2.32.0.windows.2

> JIRA Cloud

> Grafana Cloud (to prepare the dashboard with *jira-datasource.csv* file)

### Steps to execute and generate the jira-datasource.csv file

Step-1: Clone this repo to your workspace (IDE or git bash or any git client)

> Note: Remove *jira-datasource.csv* file from the workspace which may contains the sample data or old data.

Step-2: **maven command to build**: `mvn clean install`

Step-3: **java command to execute**: `java -jar target/JiraAPIClient-1.0.0-SNAPSHOT.jar ${JIRA_PROJECT_KEY} ${JIRA_URL} ${JIRA_ADMIN_USERNAME} ${JIRA_ADMIN_ACCESS_TOKEN}`

      `example: java -jar target/JiraAPIClient-1.0.0-SNAPSHOT.jar SSP https://username.atlassian.net/ admin admin-user-personal-access-token`

Step-4: Commit and push the file *jira-datasource.csv* to this same git repo.

> Note: Configure the above steps in any CI tool (ex: Jenkins) to automatilly execute these steps hourly or everyday once, so that the file *jira-datasource.csv* contains the latest data.

### Prepare dashboard in Grafana Cloud

Step-1: Sign to grafana clound.

Step-2: **Create datasource using CSV plugin**: 

  2.1. Go to Settings >> Data  Sources (under Configuration) >> Click on *Add data source* button.

![image](https://user-images.githubusercontent.com/24622526/130913522-a06d0f99-febd-4834-8e99-192aa4137b7c.png)

  2.2. Search for *csv* and select it.
  
![image](https://user-images.githubusercontent.com/24622526/130913707-123191cb-4d53-4dc6-b28e-7d9ed9b13c11.png)

  2.3. Enter the name and URL
  
  > URL can be raw view git URL, similar to: https://raw.githubusercontent.com/venkatasykam/JiraRestAPIClient/master/jira-datasource.csv
  
![image](https://user-images.githubusercontent.com/24622526/130913957-76919897-fea8-4e52-998e-462fdae9a08d.png)

  2.4. Click on **Save and Test** (if your git repo is *Private* repo, then you need to choose the suitable authentication method for you.
   
![image](https://user-images.githubusercontent.com/24622526/130914323-8814324f-eb07-41db-a00f-1f5a82f8db65.png)

configure credentials for private git repos: (Basic Auth, enter git user name, password should be personal access token)

![image](https://user-images.githubusercontent.com/24622526/130917121-73e93d5c-b9ba-48d6-9ca5-ca748fc2934d.png)


Step-3: **Create Data board**

  3.1. Click on plus(+) symbol >> Create 
  
![image](https://user-images.githubusercontent.com/24622526/130915626-910fee9e-1a8f-4f31-be26-49b5fc4c5bcf.png)

  3.2. Click on "Add an empty panel"
  
![image](https://user-images.githubusercontent.com/24622526/130915866-59710977-360c-45ce-a74b-f27f2ae28096.png)

  3.3. Change the panel view type from "Time series" to "Bar Chart"

![image](https://user-images.githubusercontent.com/24622526/130916090-c3e2c473-f226-498c-a62e-1e0c8672556b.png)

  3.4. Choose the data source which we created *CSV-jira-datasource-jirarestclient* and add the fields.
  
![image](https://user-images.githubusercontent.com/24622526/130916685-ac758bb8-bd92-4bdd-a24d-b507fe15780c.png)

  3.5. Save the dashboard with appropriate name.
  
Final Dashboard view:

![image](https://user-images.githubusercontent.com/24622526/130913232-3396cab5-5e81-4d8c-a966-5fe324c1985e.png)


  3.6. In 3.3, panel view has been selected as "Bar Chart", we can create panel with tabular format also. Click on plus symbol to add new below (to right side)
  

![image](https://user-images.githubusercontent.com/24622526/130927619-76183072-fab0-4585-a504-8f2da5e5a221.png)

  3.7. Select table
  

![image](https://user-images.githubusercontent.com/24622526/130927777-1367a5ea-ad5c-4b64-b444-25d719af0aae.png)

  3.8. Choose the data source which we created *CSV-jira-datasource-jirarestclient* and save the panel and dashboard.
  
![image](https://user-images.githubusercontent.com/24622526/130928278-41deb655-752e-45bd-a2ed-712759632770.png)



  
  





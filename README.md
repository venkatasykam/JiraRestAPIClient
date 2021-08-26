
## AIM of this JiraRestAPIClient

  To generate *jira-datasource.csv* file which will contains the data pulled from the JIRA proejcts with the details - sprint, total stories, total story points and stroy points delivered. See the sample output data in the file [jira-datasource.csv](https://github.com/venkatasykam/JiraRestAPIClient/blob/master/jira-datasource.csv)

### Tools used for development and build

> Java version: jdk1.8.0_301

> maven version: 3.8.1

> git version: git version 2.32.0.windows.2

> JIRA Cloud

> Grafana Cloud (to prepare the dashboard with *jira-datasource.csv* file)

### Steps to execute and generate the jira-datasource.csv file

Step-1: Clone this repo to your workspace (IDE or git bash or any git client)

> Note: Remove *jira-datasource.csv* file from the workspace which may contains the sample data or old data.

Step-2: **maven command to build**: `mvn clean install`

Step-3: **java command to execute**: `java -jar target/JiraAPIClient-1.0.0-SNAPSHOT.jar  ${JIRA_URL} ${JIRA_ADMIN_USERNAME} ${JIRA_ADMIN_PASSWORD}`

Step-4: commit and push the file *jira-datasource.csv* to this same git repo.

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

  2.4. Click on **Save and Test** (if your repo is private repo, 
  
![image](https://user-images.githubusercontent.com/24622526/130914323-8814324f-eb07-41db-a00f-1f5a82f8db65.png)


Final Dashboard view:

![image](https://user-images.githubusercontent.com/24622526/130913232-3396cab5-5e81-4d8c-a966-5fe324c1985e.png)






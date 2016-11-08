#Cloudaholic
Cloudaholic is an Cloud Service that help to manage your cloud services of all your project in one place, using GAE and Azure to communitate to their APIs.

**Cloudaholic(Azure)**: is a Web application, that using a worker role connects to:
 - BitBucket. https://bitbucket.org/ . Version control that works with github.
 - BaseCamp. https://basecamp.com/  . Organize all your projects and the internal communication.
 - Toggl. https://toggl.com/ . Its a time tracker.
 - Hipchat. https://www.hipchat.com/. Its a place to manage all the communication of the company.


**Cloudaholic(GAE)**: has a listener to take all the data from the Azure project and save this data in a Google Spreadsheet and save all the data in Loggly (place to save logs).

# Grinhouse core


*Grinhouse core application including the api, database and the gateway.*

### Use cloud hosted api
+ http://20.52.3.144:8080/
+ http://20.52.3.144:8080/swagger.html

### Connect to Cloud Database
you can also connect to the database directly using Microsoft SQL Server Management Studio
the following image is old, for the server name use: 20.52.3.144
+ [Google Drive](https://drive.google.com/file/d/1vjITBV0ZFGTMP29ZF4AxSNAN1K9QxWCp/view?usp=sharing) login here

### Installation, run gh-core locally
1. Open with [`IntelliJ IDEA`](https://www.jetbrains.com/idea/download/)
    + `git clone https://github.com/via-group-10/gh-core.git`
    + `right click > open as IntelliJ IDEA`
    + that's all if you want to use the shared database. 

2.  
	+ follow the guide in the guides and cheatsheets tab
	+ change the application.properties to localhost

### Guides and Cheatsheets

+ [Google Drive](https://docs.google.com/document/d/1D9RLhvA2nucM9Xn25YckUCAXqgW-W-U-IbtUKs2_Yho/edit?usp=sharing) Zenhub Guide
+ [Google Drive](https://docs.google.com/document/d/1ncDZglOb2xISxwVgWCZtunfQgU5wwQ0wGp7ouzzDlRg/edit?usp=sharing) Git Guide
+ [Google Drive](https://docs.google.com/presentation/d/1XY5o722jOIl-1gF7rECP9ASLa7Z2TDoxz_5BJtcUwhw/edit?usp=sharing) Database Setup Guide
+ [Google Drive](https://drive.google.com/file/d/13jVPOQ1Btf-YqShTESbow78enbkF0VaG/view?usp=sharing) Data Warehouse Setup Guide


### Resources

+ [Database Initialisation SQL](https://github.com/via-group-10/gh-core/blob/main/src/main/resources/RelationalDatabaseCode.sql)

### Links

+ [ZenHub](https://app.zenhub.com/) Check your tasks here
+ [Figma](https://www.figma.com/file/OGHtk0PhJjtHhPoPK8OEhT/Greenhouse?node-id=0%3A1)  App Design
+ [Google Drive](https://drive.google.com/drive/folders/1b_Jb5Cu6ESl89s8uTb6YFx4mEng7EJx8?usp=sharing) SEP4 Folder
+ [Webhook](http://20.52.3.144:8555/hooks/redeploy) Warning: clicking this link will stop the api, do a rebuild and start again.  
+ [Swagger](http://20.52.3.144:8080/swagger.html) Hosted
+ [Swagger](http://localhost:8080/swagger.html) Localhost

---
*No third-party contributions. This repository belongs to the fourth semester group of VIA Software Engineering Program.*

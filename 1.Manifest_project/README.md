🚀 Deployment Guide

This document outlines the essential steps required to build and deploy the application and its associated services to the Cloud Foundry environment.

1. Create the XSUAA Service Instance

You must provision an XSUAA service instance to manage security and authentication for your application. This service reads its configuration from the project's xs-security.json file.

Use the following Cloud Foundry CLI command to create the instance:

```
cf create-service xsuaa application xsuaa-service -c xs-security.json
```


Outcome: A new XSUAA service instance named xsuaa-service will be created and bound to your application during deployment.

2. Build the Application Artifact

Use your project's build tool (e.g., Maven) to clean up old builds and generate the final, deployable artifact (e.g., a JAR or WAR file).

Execute the following command in the root directory of your project:

```
mvn clean install
```


3. Deploy to Cloud Foundry

The cf push command uses the application manifest (typically manifest.yml) to deploy the main application (hello-service) and the associated App Router service to your Cloud Foundry space.

Run the push command:

```
cf push
```


4. Access the Deployed Application

After the cf push process completes and the application shows a "started" status, you can access the service via the App Router's endpoint.

Access the live application here:

https://hello-service.cfapps.us10-001.hana.ondemand.com/

Tip: If the URL changes, always confirm the correct route by running cf apps in your CLI.
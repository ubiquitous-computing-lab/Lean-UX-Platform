# Video-based Body Language detection using Kinect

## 1.1.	Introduction

This developed modules performs following tasks:-

-	Captures human body: It is able to analyze the upper body of human including head, shoulder, hand and arm movements using skeletal joints extracted from depth camera through the Kinect Device. 
-	Emotional Analysis: The extracted limited upper body 3D positions and kinematic joints are used to get the user’s emotion score, which becomes an important input to the Lean UX platform to measure user’s experience.
-	Works independently:  This software can work independently using Kinect devices by tracking human body and extracting emotions in real-time
-	Integrated with any platform: This software can be integrated with any platform measuring user’s experience or human behavior.
Following are the main functionalities of the developed module:-
-	Skeleton Capturing: User can sit or stand in front of Kinect Device, which captures the skeletal data for 15 upper body joints only.
-	Frame per Second:  This software can be adaptive and work with variable frames depending on the requirements. For Lean UX platform we tested for 90 fps, 60 fps and 30 fps.
-	Pre Processing: This software preprocesses the Skeletal joint data and extract features. The machine learning model is trained and persisted. Later on the model is used to classify individual frames in real-time for four type of emotions
  -	Joyful
  - Sadness
  -	Neutral
  -	Anger

## 1.2.	Requirements
-	Java version: JDK 1.8
-	Maven: Apache-maven-3.2.2
-	Eclipse / Netbeans IDE..
-	JAVAFX

## 1.3.	Installation

-Java and JDK setup JDK and JAVA_HOME
  -Make sure JDK is installed, and “JAVA_HOME” variable is added as Windows environment variable
  -	Apache Maven Installation
  -	Download Apache Maven and install it
  -	Visit Maven official website, download the Maven zip file, for example : apache-maven-3.3.9-bin.zip. Unzip it to the folder you want to install Maven. (Assume you unzip to this folder – C:\Program Files\Apache\maven)
  -	Add MAVEN_HOME
  -	Add MAVEN_HOME variables in the Windows environment, and point it to your Maven folder.
  -	Add to PATH
  -	Update PATH variable, append Maven bin folder, so that you can run the Maven’s command everywhere.
  -	Verification by running mvn –version in the command prompt.
-	Build Project
  - once you have Maven installed, verify with: mvn -version Then run (once) this to download all the necessary dependencies:
  - ownload src and pom file into the appropriate project folder
  -	Start Command Prompt
  - hange the directory to your project directory and folder
  - Run “mvn clean install” command
  - You can import the project in Eclipse via File > Import... > Existing Projects into Workspace
  - You can run mvn eclipse:eclipse to re-generate Eclipse .project and .classpath files automatically from your pom.xml file. 

## 1.4.	Usage

Once environment has been setup, Kinect Body Language (KBL) placed in the folder, the user can use KBL. BL_Main_Skeletal_Acquire.java can be executed for acquiring skeleton frames and get four types of emotions.


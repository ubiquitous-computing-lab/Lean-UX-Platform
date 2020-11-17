#Audio-based Emotion Recognition

## 1.1.	Introduction

This developed modules performs following tasks:-
-	Attached Microphone Sensor in PC: It is able to collect the voice data on the PC from USB type of the microphone sensor.
-	Signal Preprocessing: This module remove the silent terms with threshold value which is calculated with the noise-removed decibel. Following formula shows how to get decibel value.
-	Feature Extraction: This module extracts the feature vector from the speech. this thesis employed various basic feature vector in existing methods of speech emotion recognition area.
-	Classification: This module classifies emotions using Kstar algorithm in weka library with extracted feature vector from speech.
-	Works independently:  This software can work independently using Kinect devices by tracking human body and extracting emotions in real-time
-	Integrated with any platform: This software can be integrated with any platform measuring user’s experience or human behavior.
Following are the main working flow of the developed module:-

*1 - Audio Stream data is transferred from sensory data.
*2 - Removing non-speech area in audio stream data
*3 - Window based Segmentation by 3 sec using audio buffer.
*4 - Extract Statistical Features based MFCC, LPC, Energy, Pitch
*5 - Recognize Emotion by Kstar

**o	Joyful
**o	Sadness
**o	Neutral
**o	Anger

##1.2.	Requirements

•	Java version: JDK 1.8
•	Eclipse
•	jAudio-1.0.4.jar
•	comirva-0.36.jar
•	weka-3.7.0.jar

##1.3.	Installation

•	Java and JDK setup JDK and JAVA_HOME
o	Make sure JDK is installed, and “JAVA_HOME” variable is added as Windows environment variable
•	Build Project
o	Open Eclipse
o	Import the AudioBasedER Project
o	Add the dependency libraries in lib folder (Project -> properties -> Java bulid Path -> Libraries -> Add JARs)
o	Find the com.uclab.ER.audio.example package and run the AudioServiceExample.java

##1.4.	Usage

Once environment has been setup, you can easily run by java application from AudioServiceExample.java
If you want to understand detailed thing please find the JAVA Doc.

## 1.5 License

Copyright [2020]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

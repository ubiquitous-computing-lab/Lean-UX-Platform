# LeanUX - Video-based Facial Expression Recognition
[![Version](https://img.shields.io/badge/LeanUX--FER-version%203.0-green.svg)](http://uclab.khu.ac.kr/LeanUX/)
[![License](https://img.shields.io/badge/Apache%20License%20-Version%202.0-yellowgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0)

<!--The LEAN UX platform provides an innovative way of evaluating the User experience with the combined abilities of managing multi-modal, wide range of emotions, real-time synchronization, time spans based experience extraction, multi-device integration and powerful visualization-->

The LEAN UX platform provides an innovative way of evaluating the User experience with the combined abilities of managing multi-modal, wide range of emotions, real-time synchronization, time spans based experience extraction, multi-device integration and powerful visualization. It objectifies the subjective nature of user in evaluating user experience through triangulation methods. UX experts are empowered through powerful real-time analytics visualization to get insight of time spans user experience.

As a core component of the above-mentioned LeanUX platform, we develop a facial expression recognizer with high accuracy by using deep learning algorithms. Particularly, the core technology is the learning of an efficient Dual Neural Network (Spatial Covolutional Neural Network + Temporal Recurrent Neural Network) for tightly comibining high-level facial expression features in spatiotemporal domains to efficiently detect the ultimate emotions.

The architecture of the FER component is manifested as follows
[<img src="https://github.com/ubiquitous-computing-lab/Lean-UX-Platform/blob/master/fer/fer_architecture.png">](http://uclab.khu.ac.kr/LeanUX/)

visit: [LeanUX](http://uclab.khu.ac.kr/LeanUX/) for more details of the LeanUX platform.

# Features
Recognize facial expression via image frames captured from a Webcam on a PC.

##  Inputs
Images captured from a Webcam.

##  Outputs
Emotion labels of Joyful, Sadness, Anger, and Neutral.

## Prerequisites
[Maven Project](https://maven.apache.org/)
[Maven Repository](https://mvnrepository.com/) (Tensorflow, JavaFX).
[OpenCV](https://opencv-java-tutorials.readthedocs.io/en/latest/) for Java.


# Authors

#### Principal Investigator

- Professor Sungyoung Lee (sylee@oslab.khu.ac.kr)

#### Project Managers

- Dr. Jamil Hussain (jamil@oslab.khu.ac.kr)
- Dr. Jaehun Bang (jhb@oslab.khu.ac.kr)

#### Team Lead of FER component

- Mr. Cam-Hao Hua (hao.hua@oslab.khu.ac.kr)

# License
The code is licensed under the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

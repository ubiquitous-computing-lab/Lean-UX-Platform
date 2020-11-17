package BLMain;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JLabel;

import edu.ufl.digitalworlds.j4k.J4KSDK; 
import edu.ufl.digitalworlds.j4k.Skeleton;

/**
 * 
 * @author Asif
 * THis class classfies user context based on the 25 skeleton coordinates data 
 * A Software to get Video-based Body Language
 * Dated Aug 29, 2019
 *
 **
 */
public class BL_Main_Skeletal_Acquire extends J4KSDK{ 

	int a =0; 
	int cont =0;
	int frame = 0;
	
	
	@Override
	public void onColorFrameEvent(byte[] arg0) { 
	}
	
	@Override
	public void onDepthFrameEvent(short[] arg0, byte[] arg1, float[] arg2, float[] arg3) { 
	}
	
	@Override
	public void onSkeletonFrameEvent(boolean[] skeletons, float[] positions, float[] orientations, byte[] state) {
	
		Skeleton skeleton = null;

			for (int i = 0; i < skeletons.length; i++) { 
					if (skeletons[i]) {
							skeleton = Skeleton.getSkeleton(i, skeletons, positions, orientations, state, this);
					} 
			}
	
			if (skeleton != null) { 
				a=1;
		
				try {
 
					if (frame<90)
					{
					getJoints(skeleton, frame);
					}

					if (frame==89)
					{
					KinectBLNotifier KE = new KinectBLNotifier();
					System.out.println("writing Frames now...."+"\n");
					KE.BLClassifier();
					System.out.println("\n"+"Classification done "+"\n");
					Files.deleteIfExists(Paths.get("D:\\BodyLanguageAsif\\CommunicationFiles\\SkeletalData.txt"));
					frame=-1;
					}
					
					
					if (frame>89)
					{
						Files.deleteIfExists(Paths.get("D:\\BodyLanguageAsif\\CommunicationFiles\\SkeletalData.txt"));
						frame =-1;
					}

					frame++;
	} 
	catch(Exception e) {	
		System.out.println("Error for Skeletal Data Acquisition "); 
		System.out.println(e);
	}
	} 
}

public void getJoints(Skeleton skeleton, int frame) throws Exception{

	double SPINE_BASE_cord[]=skeleton.get3DJoint(Skeleton.SPINE_BASE);
	double SPINE_MID_cord[]=skeleton.get3DJoint(Skeleton.SPINE_MID);
	double NECK_cord[]=skeleton.get3DJoint(Skeleton.NECK);
	double HEAD_cord[]=skeleton.get3DJoint(Skeleton.HEAD);
	double SHOULDER_LEFT_cord[]=skeleton.get3DJoint(Skeleton.SHOULDER_LEFT);
	double ELBOW_LEFT_cord[]=skeleton.get3DJoint(Skeleton.ELBOW_LEFT);
	double WRIST_LEFT_cord[]=skeleton.get3DJoint(Skeleton.WRIST_LEFT);
	double HAND_LEFT_cord[]=skeleton.get3DJoint(Skeleton.HAND_LEFT);
	double SHOULDER_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.SHOULDER_RIGHT);
	double ELBOW_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.ELBOW_RIGHT);
	double WRIST_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.WRIST_RIGHT);
	double HAND_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.HAND_RIGHT);
	double HIP_LEFT_cord[]=skeleton.get3DJoint(Skeleton.HIP_LEFT);
	double KNEE_LEFT_cord[]=skeleton.get3DJoint(Skeleton.KNEE_LEFT);
	double ANKLE_LEFT_cord[]=skeleton.get3DJoint(Skeleton.ANKLE_LEFT);
	double FOOT_LEFT_cord[]=skeleton.get3DJoint(Skeleton.FOOT_LEFT);
	double HIP_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.HIP_RIGHT);
	double KNEE_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.KNEE_RIGHT);
	double ANKLE_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.ANKLE_RIGHT);
	double FOOT_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.FOOT_RIGHT);
	double SPINE_SHOULDER_cord[]=skeleton.get3DJoint(Skeleton.SPINE_SHOULDER);
	double HAND_TIP_LEFT_cord[]=skeleton.get3DJoint(Skeleton.HAND_TIP_LEFT);
	double THUMB_LEFT_cord[]=skeleton.get3DJoint(Skeleton.THUMB_LEFT);
	double HAND_TIP_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.HAND_TIP_RIGHT);
	double THUMB_RIGHT_cord[]=skeleton.get3DJoint(Skeleton.THUMB_RIGHT);
	


	
	try (BufferedWriter out = new BufferedWriter(new FileWriter("D:\\BodyLanguageAsif\\CommunicationFiles\\SkeletalData.txt", true));) //false no append
	{
	out.write(SPINE_BASE_cord[0]+","+SPINE_BASE_cord[1]+","+SPINE_BASE_cord[2]
			+","+SPINE_MID_cord[0]+","+SPINE_MID_cord[1]+","+SPINE_MID_cord[2]
			+","+NECK_cord[0]+","+NECK_cord[1]+","+NECK_cord[2]
			+","+HEAD_cord[0]+","+HEAD_cord[1]+","+HEAD_cord[2]
			+","+SHOULDER_LEFT_cord[0]+","+SHOULDER_LEFT_cord[1]+","+SHOULDER_LEFT_cord[2]
			+","+ELBOW_LEFT_cord[0]+","+ELBOW_LEFT_cord[1]+","+ELBOW_LEFT_cord[2]
			+","+WRIST_LEFT_cord[0]+","+WRIST_LEFT_cord[1]+","+WRIST_LEFT_cord[2]
			+","+HAND_LEFT_cord[0]+","+HAND_LEFT_cord[1]+","+HAND_LEFT_cord[2]
			+","+SHOULDER_RIGHT_cord[0]+","+SHOULDER_RIGHT_cord[1]+","+SHOULDER_RIGHT_cord[2]
			+","+ELBOW_RIGHT_cord[0]+","+ELBOW_RIGHT_cord[1]+","+ELBOW_RIGHT_cord[2]
			+","+WRIST_RIGHT_cord[0]+","+WRIST_RIGHT_cord[1]+","+WRIST_RIGHT_cord[2]
			+","+HAND_RIGHT_cord[0]+","+HAND_RIGHT_cord[1]+","+HAND_RIGHT_cord[2]
			+","+HIP_LEFT_cord[0]+","+HIP_LEFT_cord[1]+","+HIP_LEFT_cord[2]
			+","+KNEE_LEFT_cord[0]+","+KNEE_LEFT_cord[1]+","+KNEE_LEFT_cord[2]
			+","+ANKLE_LEFT_cord[0]+","+ANKLE_LEFT_cord[1]+","+ANKLE_LEFT_cord[2]
			+","+FOOT_LEFT_cord[0]+","+FOOT_LEFT_cord[1]+","+FOOT_LEFT_cord[2]
			+","+HIP_RIGHT_cord[0]+","+HIP_RIGHT_cord[1]+","+HIP_RIGHT_cord[2]
			+","+KNEE_RIGHT_cord[0]+","+KNEE_RIGHT_cord[1]+","+KNEE_RIGHT_cord[2]
			+","+ANKLE_RIGHT_cord[0]+","+ANKLE_RIGHT_cord[1]+","+ANKLE_RIGHT_cord[2]
			+","+FOOT_RIGHT_cord[0]+","+FOOT_RIGHT_cord[1]+","+FOOT_RIGHT_cord[2]
			+","+SPINE_SHOULDER_cord[0]+","+SPINE_SHOULDER_cord[1]+","+SPINE_SHOULDER_cord[2]
			+","+HAND_TIP_LEFT_cord[0]+","+HAND_TIP_LEFT_cord[1]+","+HAND_TIP_LEFT_cord[2]
			+","+THUMB_LEFT_cord[0]+","+THUMB_LEFT_cord[1]+","+THUMB_LEFT_cord[2]
			+","+HAND_TIP_RIGHT_cord[0]+","+HAND_TIP_RIGHT_cord[1]+","+HAND_TIP_RIGHT_cord[2]
			+","+THUMB_RIGHT_cord[0]+","+THUMB_RIGHT_cord[1]+","+THUMB_RIGHT_cord[2]);//+", timestamp: "+ System.currentTimeMillis() 
	
	

	out.newLine();
	
	
	out.close();	
	



}
	catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error ");
    } finally {
    }
	
}




public static void main(String[] args) throws Exception
	{ 
	
		BL_Main_Skeletal_Acquire kinect = new BL_Main_Skeletal_Acquire();
		kinect.start(BL_Main_Skeletal_Acquire.DEPTH | BL_Main_Skeletal_Acquire.COLOR | BL_Main_Skeletal_Acquire.SKELETON | BL_Main_Skeletal_Acquire.XYZ | BL_Main_Skeletal_Acquire.PLAYER_INDEX);

		kinect.showViewerDialog(true);
				


	}

}

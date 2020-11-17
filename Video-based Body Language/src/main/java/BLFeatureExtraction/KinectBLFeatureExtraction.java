package BLFeatureExtraction;

import java.util.ArrayList;
/**
 * 
 * @author Asif
 * THis class classfies user context based on the 25 skeleton coordinates data 
 * A Software to get Video-based Body Language
 * Dated Aug 29, 2019
 *
 **
 */
public class KinectBLFeatureExtraction { // This makes 70*2 D 70*2 A features
	double [] inputData;
	double [] x,y,z;

	int[] siD = {8,4,9,5,10,11,23,24,6,7,21,22,9,5,10,11,23,24,6,7,21,22,9,10,11,23,24,5,6,7,21,22,5,10,11,23,24,9,6,7,21,22,6,7,21,22,10,11,23,24,10,11,23,24,6,7,21,22,9,10,11,23,24,5,6,7,21,22,1,9};
	int[] sjD = {3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,8,8,8,8,8,8,8,8,8,8,4,4,4,4,4,4,4,4,4,4,10,10,10,10,6,6,6,6,5,5,5,5,9,9,9,9,20,20,20,20,20,20,20,20,20,20,3,5};
	int[] siA = {8,4,9,5,10,11,23,24,6,7,21,22,9,5,10,11,23,24,6,7,21,22,9,10,11,23,24,5,6,7,21,22,5,10,11,23,24,9,6,7,21,22,6,7,21,22,10,11,23,24,10,11,23,24,6,7,21,22,9,10,11,23,24,5,6,7,21,22,1,9};
	int[] sjA = {3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,8,8,8,8,8,8,8,8,8,8,4,4,4,4,4,4,4,4,4,4,10,10,10,10,6,6,6,6,5,5,5,5,9,9,9,9,20,20,20,20,20,20,20,20,20,20,3,5};
	

	public void setInputData(double[] inputData) {
		this.inputData = inputData;
		separateData();
	}
	
	public void setIndex(ArrayList<ArrayList<Integer>> listIndex) {
		for (int i = 0; i < 70; i++) {
			siD[i] = listIndex.get(0).get(i);
			sjD[i] = listIndex.get(1).get(i);
			siA[i] = listIndex.get(2).get(i);
			sjA[i] = listIndex.get(3).get(i);
		}
	}
	
	public int getAngleFeatureSize(){
		return siA.length;
	}
	
	// 	Get the size of angle feature
	public int getDistanceFeatureSize(){
		return siD.length;
	}
	/** Re-construct the data from DCL for processing
	 *	One vector data with 75 values of x,y, and z to three vectors with 25 values
	 *	25 values corresponding to 25 joints of each complete skeleton
	 */
	private void separateData(){
		x = new double[25];
		y = new double[25];
		z = new double[25];
		for (int i = 0;  i < 25; i++){
			x[i]=inputData[i*3];
			y[i]=inputData[i*3+1];
			z[i]=inputData[i*3+2];
		}
	} 
	
	/** Calculating a distance feature between joint i and j
	 *	This is the Euclidean distance
	 */
	private double distance3d(int i, int j){
		double d= 0;
		try{
			d=Math.sqrt(Math.pow(x[i]-x[j],2)+Math.pow(y[i]-y[j],2)+Math.pow(z[i]-z[j],2));			
		}catch(Exception e){
		}
		return d;
	}
	
	/**	Calculating an angle feature between joint i and j
	 *	This is the angle between vector ij and horizontal axis on the plane z=0
	 */
	private double angle3d(int i,int j){
		double deltax=x[j]-x[i];
        double deltay=y[j]-y[i];
        double a = Math.atan(deltay/deltax);
		//	Normalizing angle from degree to gradian
        if (deltay == 0)
                        a=0;
        else if (deltay > 0 && deltax > 0)
                        a=a/(2*Math.PI);
        else if (deltay > 0 && deltax < 0)
                        a=(a+Math.PI)/(2*Math.PI);
        else if (deltay <0 && deltax < 0)
                        a=(a+Math.PI)/(2*Math.PI);
        else if (deltay <0 && deltax > 0)
                        a=(a+2*Math.PI)/(2*Math.PI);
        return a;
	}
	
	/**	Calculating all distance features 
	 *	These joints are pre-identified based on the feature selection technique to only
	 *	calculate the most important features
	 */
	 public double[] extractDistanceFeature(){
//		 System.out.printf("extractDistanceFeature  " );
		 int numFeatures = siD.length;
		 double[] features = new double[numFeatures];
		 for (int i = 0; i < numFeatures; i++){
			 features[i]=distance3d(siD[i],sjD[i]);
//			 System.out.println("Distance Features " +features[i]);  //gives 70 features
		 }
	 return features;
	 }
	/**Calculating all angle features 
	 *	These joints are pre-identified based on the feature selection technique to only
	 *	calculate the most important features
	 */
	 public double[] extractAngleFeature(){
		 int numFeatures = siA.length;
		 double[] features = new double[numFeatures];
		 for (int i = 0; i < numFeatures; i++){
			 features[i]=angle3d(siA[i],sjA[i]);
//			 System.out.println("Angle Features " +features[i]); //gives 70 features
		 }
		 return features;
	 }

}
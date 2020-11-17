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
public class KinectBLFeatureCalculation {
	KinectBLFeatureExtraction kf = new KinectBLFeatureExtraction();
	

	double inputs[][];
	double mat[][] = new double[90][140];

	public void setInputData(double[][] inputs) {
		this.inputs = inputs;
	}
	
	public void setIndex(ArrayList<ArrayList<Integer>> listIndex) {
		kf.setIndex(listIndex);
	}
	
	public void calcFeatures(){	
		for(int i = 0; i<inputs.length; i++){   // inputs.length = 90
			kf.setInputData(inputs[i]);
			double[] dd = kf.extractDistanceFeature();  // 70 features
			double[] aa = kf.extractAngleFeature();     // 70 features

			for(int j = 0; j<dd.length; j++){
				mat[i][j] = dd[j];  //	System.out.println("mat[i][j] " +mat[i][j]);     // prints 70 features
			}
			for(int j = 0; j<dd.length; j++){
				mat[i][j+aa.length] = aa[j]; //	System.out.println("mat[i][j+aa.length] + " + " [i][j] = "+ i +" "+  j+ " " +mat[i][j+aa.length]);   //prints 70 features

			}

			
			for(int l = 0; l<140; l++) {
				System.out.print(mat[i][l] + ","); //"Frame= "+i+ " "+ 
			}
			System.out.println("");

		}
		
	}

	private double[] calcMean(){     //here  140 features are converted to 140 * 2 (sum, sumsq)
		double[] ret = new double[mat[0].length*2];   // 140 * 2
		int size = mat.length;      // 90
		
		
		for(int i = 0; i<mat[0].length; i++){
			double summ = 0;
			for(int j = 0; j<mat.length; j++){
				summ += mat[j][i];
			}
			ret[i] = summ/size;
			
			System.out.print(ret[i] + ","); //"Frame= "+i+ " "+ 

			
		}
		
		for(int i = 0; i<mat[0].length; i++){
			double sqSumm = 0;
			for(int j = 0; j<mat.length; j++){
				sqSumm += (mat[j][i]-ret[i])*(mat[j][i]-ret[i])/(size-1);
			}	
			ret[mat[0].length+i] = Math.sqrt(sqSumm);

							System.out.print(ret[mat[0].length+i] + ","); //"Frame= "+i+ " "+ 
						
		}	
		System.out.println("");
		return ret;
	}

	
	
	
	
	public double[] extractFeatures() {
		System.out.println("Extract features for each frame ");			
		calcFeatures();

		System.out.println("Now calculate Mean for features");
		return calcMean();// 140 for sum + 140 for sumsq = 280 features
	}
	
}

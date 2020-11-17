
package BLClassification;
/**
 * 
 * @author Asif
 * THis class classfies user context based on the 25 skeleton coordinates data 
 * A Software to get Video-based Body Language
 * Dated Aug 29, 2019
 *
 **
 */
public abstract class Classification<InputType> {
    

    abstract public String Classify(InputType data);
    
   
}

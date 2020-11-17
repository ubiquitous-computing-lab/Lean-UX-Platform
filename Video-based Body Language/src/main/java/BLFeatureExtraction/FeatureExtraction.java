
package BLFeatureExtraction;

/**
 * 
 * @author Asif
 * THis class classfies user context based on the 25 skeleton coordinates data 
 * A Software to get Video-based Body Language
 * Dated Aug 29, 2019
 *
 **
 */
public abstract class FeatureExtraction<InputType, OutputType> {

    abstract public OutputType extractFeature(InputType input);
}

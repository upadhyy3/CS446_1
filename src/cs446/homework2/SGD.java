package cs446.homework2;

import weka.classifiers.Classifier;
import weka.classifiers.Sourcable;
import weka.classifiers.UpdateableClassifier;
import weka.core.Attribute;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.NoSupportForMissingValuesException;
import weka.core.RevisionUtils;
import weka.core.TechnicalInformation;
import weka.core.TechnicalInformationHandler;
import weka.core.Utils;
import weka.core.Capabilities.Capability;
import weka.core.TechnicalInformation.Field;
import weka.core.TechnicalInformation.Type;

import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Random;
import java.util.Arrays;

import javax.swing.Box.Filler;

public class SGD extends Classifier{

	/**
	 * 
	 */
    /** Class attribute of dataset. */
    private Attribute m_ClassAttribute;
	private double alpha;
	private static double[] theta;
	  private double m_ClassValue;
	private int numberOfIterations = 500;
	private double label;
	private static final long serialVersionUID = -5313505361123620399L;

	public SGD (int noOfAttributes)
	{
		this.alpha = 0.0001;
		theta = new double[noOfAttributes];
		Arrays.fill(theta, 0);
	//	theta[0]=1.0;
	}
	
    public Capabilities getCapabilities() {
	Capabilities result = super.getCapabilities();
	result.disableAll();

	// attributes
	result.enable(Capability.NOMINAL_ATTRIBUTES);

	// class
	result.enable(Capability.NOMINAL_CLASS);
	result.enable(Capability.MISSING_CLASS_VALUES);

	// instances
	result.setMinimumNumberInstances(0);

	return result;
    }
	public static void print(int n)
	{
		for(int i =0;i<n;i++)
		{
		System.out.println(theta[i]);
		}
	}
	@Override
	public void buildClassifier(Instances data) throws Exception {
		// TODO Auto-generated method stub
		// can classifier handle the data?
		getCapabilities().testWithFail(data);

		// remove instances with missing class
		data = new Instances(data);
		data.deleteWithMissingClass();
		updateClassifier(data);
		
	}
	public double dotproduct(double[] t, Instance temp)
	{
		double result = 0;
		for(int i=0;i<temp.numAttributes()-1;i++)
		{//System.out.println(temp.value(i));
			result=result+t[i]*temp.value(i);
		}
		//System.out.println(result);
		return result;
	}
	public void updateClassifier(Instances data) throws Exception
	{
//		System.out.println(current);
	//	System.out.println(current.value(current.numAttributes()));

		for(int j=0 ; j<10;j++)
		{
			for(int k=0;k<data.numInstances();k++)
			{
				
				Instance current = data.instance(k);
				//current.insertAttributeAt(0);
				if(current.value(current.numAttributes()-1)==1.0)
				 {
					label = -1.0;
				 }
				else
				 {
					label = 1.0;
				 }
				for(int i =0;i<current.numAttributes()-1;i++)
				{
				//System.out.println(current.value(i));
					theta[i] = theta[i] - alpha*(label-dotproduct(theta,current))*current.value(i);
				//	System.out.println(theta[i]);
					
				}
			//	System.out.println("label");
			//	System.out.println(label);
			}
		
			}
		print(data.numAttributes()-1);
	}
	
    /**
     * Classifies a given test instance using the decision tree.
     * 
     * @param instance
     *            the instance to be classified
     * @return the classification
     * @throws NoSupportForMissingValuesException
     *             if instance has missing values
     */
    public double classifyInstance(Instance instance)
	    throws NoSupportForMissingValuesException {

	if (instance.hasMissingValue()) {
	    throw new NoSupportForMissingValuesException(
		    "ID3: no missing values, " + "please.");
	}
	if (dotproduct(theta,instance)>0) {
	    return 0;
	} else {
	    return 1;
	}
    }


//	 public static void main(String[] args) throws Exception
//	 {
//			// Load the data
//		 String filepath = "C:\\Users\\admin\\CS446\\workspace\\HW2\\Badges123";
//			Instances data = new Instances(new FileReader(new File(filepath)));
//
//			// The last attribute is the class label
//			data.setClassIndex(data.numAttributes() - 1);
//			SGD classifier = new SGD(data.numAttributes()-1);
//			classifier.buildClassifier(data);
//		//	Instance current;
//			int n = data.numInstances();
//		//	System.out.println(n);
//		//	for(int i=0;i<n;i++)
//		//	{
//				classifier.updateClassifier(data);
//				
//		//	}
//
//			print(data.numAttributes()-1);
//	 
//	 }

}



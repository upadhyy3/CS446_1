package cs446.homework2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class StumpFeatures {
	
	  static String[] features;
	  private static FastVector zeroOne;
	  private static FastVector labels;
static{
	features = new String[100];
	List<String> ff = new ArrayList<String>();
	for(int i=0;i<100;i++)
	{
		ff.add("Stump"+i);
		
	}
	features = ff.toArray(new String[ff.size()]);
	for(int i=0;i<100;i++)
	{
		System.out.println(features[i]);
		
	}

	zeroOne = new FastVector(2);
	zeroOne.addElement("0");
	zeroOne.addElement("1");

	labels = new FastVector(2);
	labels.addElement("+");
	labels.addElement("-");
}

private static Instances initializeAttributes() {

	String nameOfDataset = "BadgesStump";

	Instances instances;

	FastVector attributes = new FastVector(100);
	for (String featureName : features) {
	    attributes.addElement(new Attribute(featureName, zeroOne));
	 //   attributes.addElement(new Attribute(featureName));
	}
	Attribute classLabel = new Attribute("Class",labels);
	attributes.addElement(classLabel);

	instances = new Instances(nameOfDataset, attributes, 0);

	instances.setClass(classLabel);

	return instances;

 }

public Instance makeInstance(double[] vector, double classlabel)
{
	String label;
	Instances instances = initializeAttributes();
	Instance instance = new Instance(features.length + 1);
	instance.setDataset(instances);
	for (int featureId = 0; featureId < features.length; featureId++) {
	    Attribute att = instances.attribute(features[featureId]);
	    String featureLabel;
	    if (vector[featureId]==1) {
		featureLabel = "1";
	    } 
	    else
	    {
		featureLabel = "0";
	    }
	   instance.setValue(att, featureLabel);
	}
	if(classlabel==1)
	{
		label = "-";
	}
	else
	{
		label = "+";
	}
	instance.setClassValue(classlabel);
	
	return instance;
}
public static void main(String[] args) throws Exception {
	//StumpFeatures sf = new StumpFeatures();

}

}

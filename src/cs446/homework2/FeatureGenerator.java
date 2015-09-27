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

public class FeatureGenerator {

    static String[] features;
    private static FastVector zeroOne;
    private static FastVector labels;

    static {
	features = new String[] { "firstName0","firstName1","firstName2","firstName3","firstName4","lastName0","lastName1","lastName2","lastName3","lastName4"}; //"firstName1"

	List<String> ff = new ArrayList<String>();

	for (String f : features) {
	    for (char letter = 'a'; letter <= 'z'; letter++) {
		ff.add(f + "=" + letter);
	    }
	}

	features = ff.toArray(new String[ff.size()]);

	zeroOne = new FastVector(2);
	zeroOne.addElement("0");
	zeroOne.addElement("1");

	labels = new FastVector(2);
	labels.addElement("+");
	labels.addElement("-");
    }

    public static Instances readData(String fileName) throws Exception {

	Instances instances = initializeAttributes();
	Scanner scanner = new Scanner(new File(fileName));

	while (scanner.hasNextLine()) {
	    String line = scanner.nextLine();

	    Instance instance = makeInstance(instances, line);

	    instances.add(instance);
	}

	scanner.close();

	return instances;
    }

    private static Instances initializeAttributes() {

	String nameOfDataset = "Badges";

	Instances instances;

	FastVector attributes = new FastVector(10);
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

    private static Instance makeInstance(Instances instances, String inputLine) {
	inputLine = inputLine.trim();

	String[] parts = inputLine.split("\\s+");
	String label = parts[0];
	String firstName = parts[1].toLowerCase();
	String lastName = parts[2].toLowerCase();
	System.out.println(lastName);
	System.out.println(firstName);
	//char temp[] = firstName.toCharArray();
	//firstName.substring(0, 4)
	Instance instance = new Instance(features.length + 1);
	instance.setDataset(instances);
	Set<String> feats = new HashSet<String>();
	if(firstName.length()>=5)
	{
	for(int i=0;i<5;i++)
	{
	feats.add("firstName"+i+"=" + firstName.charAt(i));
	}
	}
	else
	{	int i =0 ;
	
		for(i=0;i<firstName.length();i++)
		{
		feats.add("firstName"+i+"=" + firstName.charAt(i));
		}
		while(i<5)
		{
			feats.add("firstName"+i+"=" + " ");
			i++;
		}
	}
	if(lastName.length()>=5)
	{
	for(int i=0;i<5;i++)
	{
	feats.add("lastName"+i+"=" + lastName.charAt(i));
	}
	}
	else
	{	int i =0 ;
	
		for(i=0;i<lastName.length();i++)
		{
		feats.add("lastName"+i+"=" + lastName.charAt(i));
		}
		while(i<5)
		{
			feats.add("lastName"+i+"=" + " ");
			i++;
		}
	}
	for (int featureId = 0; featureId < features.length; featureId++) {
	    Attribute att = instances.attribute(features[featureId]);

	    String name = att.name();
	    String featureLabel;
	    if (feats.contains(name)) {
		featureLabel = "1";
	   // instance.setValue(att, 1);
	    } 
	    else
	    {
		featureLabel = "0";
	    }
//	    instance.setValue(att, 0);
	   instance.setValue(att, featureLabel);
	}

	instance.setClassValue(label);
//	if(label=="-")
//	{
//	instance.setClassValue(-1);
//	}
//	else if (label == "+")
//	{
//		instance.setClassValue(1);
//	}
	return instance;
    }

    public static void main(String[] args) throws Exception {

//	if (args.length != 2) {
//	    System.err
//		    .println("Usage: FeatureGenerator input-badges-file features-file");
//	    System.exit(-1);
//	}
    String filepath = "C:\\Users\\admin\\CS446\\workspace\\HW2\\badges.modified.data.all";
    String filename = "Badges123";
//	Instances data = readData(args[0]);
	Instances data = readData(filepath);
	ArffSaver saver = new ArffSaver();
	saver.setInstances(data);
	//saver.setFile(new File(args[1]));
	saver.setFile(new File(filename));
	saver.writeBatch();
    }
}

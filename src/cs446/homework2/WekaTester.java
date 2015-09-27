package cs446.homework2;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.core.Instances;
import cs446.homework2.Id3;
import cs446.homework2.SGD;

public class WekaTester {
	
	public static void StumFeatureGenerator(Id3[] stump,Instances data) throws Exception
	{
		for(int i =0; i<data.numInstances();i++)
		{
			System.out.println(stump[i].classifyInstance(data.instance(i)));
		}	
	}

    public static void main(String[] args) throws Exception {

//	if (args.length != 1) {
//	    System.err.println("Usage: WekaTester arff-file");
//	    System.exit(-1);
//	}

	// Load the data
	String filepath = "C:\\Users\\admin\\CS446\\workspace\\HW2\\Badges123";
	Instances data = new Instances(new FileReader(new File(filepath)));
//	int folds=5;
//	long seed = 2;
//	Random rand = new Random(seed );   // create seeded number generator
//	Instances randData = new Instances(data);   // create copy of original data
//	randData.randomize(rand);         // randomize data with number generator
//	Id3[] stumps = new Id3[5];
	// The last attribute is the class label
	data.setClassIndex(data.numAttributes() - 1);
//	randData.setClassIndex(data.numAttributes() - 1);
	


	// Train on 80% of the data and test on 20%
	Instances train = data.trainCV(5,0);
	Instances test = data.testCV(5, 0);

//	for(int n=0;n<folds;n++)
//	{
//	Instances train = randData.trainCV(folds,n);
//	Instances test = randData.testCV(folds, n);
//	Id3 classifier = new Id3();
//	classifier.setMaxDepth(4);
//	classifier.buildClassifier(train);
//	stumps[n] = classifier;
//	Evaluation evaluation = new Evaluation(test);
//	evaluation.evaluateModel(classifier, test);
//	stumps[n] = classifier;
////	System.out.println(evaluation.toSummaryString());
////	System.out.println(train.numInstances());
////	System.out.println(test.numInstances());
////	System.out.println(data.numInstances());
//	}
//	

	Stumps st = new Stumps();
	Instances stumptrainData=st.CreateStumps(train);
	Instances stumptestData=st.CreateStumps(test);
	//Create a new SGD classifier
	
	SGD sgdClassifier = new SGD(stumptrainData.numAttributes()-1);
	System.out.println(stumptrainData.numAttributes()-1);
	//SGD sgdClassifier = new SGD(train.numAttributes()-1);
	sgdClassifier.buildClassifier(stumptrainData);
	//sgdClassifier.buildClassifier(train);
	// Create a new ID3 classifier. This is the modified one where you can
	// set the depth of the tree.
//	Id3 classifier = new Id3();
	
	// An example depth. If this value is -1, then the tree is grown to full
	// depth.
//	classifier.setMaxDepth(4);

	// Train
//	classifier.buildClassifier(train);
	// Print the classfier
	//System.out.println(classifier);
	

	// Evaluate on the test set
	Evaluation evaluation = new Evaluation(stumptestData);
//	evaluation.evaluateModel(classifier, test);
	evaluation.evaluateModel(sgdClassifier, stumptestData);
	System.out.println(evaluation.toSummaryString());
//	System.out.println(data.numInstances());
	

    }
}

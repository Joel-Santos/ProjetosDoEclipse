package testes;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class TesteIris {
	
	
	
	public static void main(String[] args) throws Exception{
	
		DataSource ds = new DataSource("src/teste/iris.arff");
		Instances ins = ds.getDataSet();
		
		//System.out.println(ins.toString());
		
		
		ins.setClassIndex(4);
		//NaiveBayes nb = new NaiveBayes();
		MultilayerPerceptron ml = new MultilayerPerceptron();
		
		//nb.buildClassifier(ins);
		ml.buildClassifier(ins);
		
		Instance novo = new DenseInstance(5);
		novo.setDataset(ins);
		
		novo.setValue(0,5.1);
		novo.setValue(1,3.5);
		novo.setValue(2,4);
		novo.setValue(3,0.2);
	
		double probabilidade[] = ml.distributionForInstance(novo); 
		
		System.out.println("Iris setosa " + probabilidade[0]);
		System.out.println("Iris versicolor " + probabilidade[1]);
		System.out.println("Iris virginica " + probabilidade[2]);
		
		
		
	}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.filters.Filter;
import weka.core.Attribute;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Random;
import java.util.Vector;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.trees.RandomForest;
import weka.core.FastVector;
import weka.core.Instance;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;

/**
 *
 * @author Lohith
 */
public class DClassifier {
    
    Classifier cModel, cModel1;
    //Vector<Double> prev = new Vector<Double>();
    
    
    
    Attribute pid = new Attribute("pid");
    Attribute hbc = new Attribute("hbc");
    Attribute hv = new Attribute("hv");
    Attribute bln = new Attribute("bln");
    Attribute cn = new Attribute("cn");
    Attribute bmi = new Attribute("bmi");
    Attribute ns = new Attribute("ns");
    Attribute age = new Attribute("age");
    
    FastVector fvClassVal = new FastVector(2);
    Attribute ClassAttribute;
    FastVector fvWekaAttributes = new FastVector(9);
    Instances isTrainingSet;
    
    
    DClassifier()
    {
        
         fvClassVal.addElement("1");
         fvClassVal.addElement("0");
         ClassAttribute = new Attribute("theClass", fvClassVal);

         fvWekaAttributes.addElement(pid);
         fvWekaAttributes.addElement(hbc);
         fvWekaAttributes.addElement(hv);
         fvWekaAttributes.addElement(bln);
         fvWekaAttributes.addElement(cn);
         fvWekaAttributes.addElement(bmi);
         fvWekaAttributes.addElement(ns);
         fvWekaAttributes.addElement(age);
         fvWekaAttributes.addElement(ClassAttribute);
         
        
        
        
    }
    
    
     int getSeverity(String line)
    {
        String [] parts = line.split(",");
        
        double v = Double.parseDouble(parts[5]);
        
        if (v>45)
        {
            return 3; //"SEVERE";
        }        
        else if (v<15)
        {
            return 1; //"MILD";
        }
       
            
        return 2; //"AVERAGE";
        
    }
     




     
      public void trainClassifierNB()
    {


        int count = 0;
        
        try
        {
           FileReader fr = new FileReader("training.txt");

           BufferedReader buf=new BufferedReader(fr);

           String s;

           while ((s=buf.readLine())!=null)
           {
                 if (s.length()<2)
                 {
                     continue;
                 }
                 count++;
           }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       
        
        isTrainingSet = new Instances("Rel", fvWekaAttributes, count);
        // Set class index
        isTrainingSet.setClassIndex(8);
         
        try
        {
           FileReader fr = new FileReader("training.txt");

           BufferedReader buf=new BufferedReader(fr);

           String s;

           while ((s=buf.readLine())!=null)
           {
                 if (s.length()<2)
                 {
                     continue;
                 }
                
                 System.out.println(s);
                 Instance iExample = new Instance(9);
                 
                 String [] parts = s.split(",");
            
                
                

                iExample.setValue((Attribute)fvWekaAttributes.elementAt(0),Integer.parseInt(parts[0]));
                
                iExample.setValue((Attribute)fvWekaAttributes.elementAt(1),Integer.parseInt(parts[1]));
                iExample.setValue((Attribute)fvWekaAttributes.elementAt(2),Integer.parseInt(parts[2]));
                iExample.setValue((Attribute)fvWekaAttributes.elementAt(3),Integer.parseInt(parts[3]));
                iExample.setValue((Attribute)fvWekaAttributes.elementAt(4),Integer.parseInt(parts[4]));
                iExample.setValue((Attribute)fvWekaAttributes.elementAt(5),Double.parseDouble(parts[5]));
                iExample.setValue((Attribute)fvWekaAttributes.elementAt(6),Double.parseDouble(parts[6]));
                iExample.setValue((Attribute)fvWekaAttributes.elementAt(7),Integer.parseInt(parts[7]));
                iExample.setValue((Attribute)fvWekaAttributes.elementAt(8),parts[8]);
                isTrainingSet.add(iExample);
                
                
                
             
               
           }
           buf.close();
           fr.close();
           
           
            
                       
            cModel1 = (Classifier)new NaiveBayes();
                    
                    
            cModel1.buildClassifier(isTrainingSet);
           
           
          
            
           
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
      
      
    
    
    boolean isDiseaseNB(String line)
    {
        
        String [] parts = line.split(",");
        
        Instance iExample = new Instance(9);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(0),Integer.parseInt(parts[0]));
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(1),Integer.parseInt(parts[1]));
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(2),Integer.parseInt(parts[2]));
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(3),Integer.parseInt(parts[3]));
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(4),Integer.parseInt(parts[4]));
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(5),Double.parseDouble(parts[5]));
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(6),Double.parseDouble(parts[6]));
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(7),Integer.parseInt(parts[7]));
                
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(8),"1");
        
        
        iExample.setDataset(isTrainingSet);
        
             try
             {
                double[] fDistribution = cModel1.distributionForInstance(iExample);
                 
                 
               
                 
                 double bigval0 = fDistribution[0];
                 double bigval1 = fDistribution[1];
                 
                 
                 
                 
                 
                 if (bigval0 >bigval1)
                 {
                     return true;
                 }
                 
            
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
        
             return false;
        
        
    }
    
    
    public static void main(String []arg)
    {
        DClassifier dc = new DClassifier();

        try
        {
            dc.trainClassifierNB();
            
            System.out.println("Training completed Naive Bayes!!!!");
            
           // dc.trainClassifierRF();
        
           /////////// System.out.println("Training completed !!!!");
            ///////////
           
            
            String ct = "1,106,76,0,0,37.5,0.197,26";
            
           // boolean res=dc.isLungCancerRF(ct);
            
            //System.out.println("Returned result for Naive Bayes:" + res);
            
             boolean res1=dc.isDiseaseNB(ct);
            
            System.out.println("Returned result for Random Forest:" + res1);
            
          
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        
        
    }
    
    
    
    
}

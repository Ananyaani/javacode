/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 *
 * @author Lohith
 */
public class HPredictor {
    
    static int numrecords=0;
    static int TPNB=0, TNNB=0, FPNB=0, FNNB=0;
    static int TPRF=0, TNRF=0, FPRF=0, FNRF=0;
    static int AccNB, AccRF;
    static int num, den, num1, den1;
     
    public  static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

         public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

          
              String va =  value.toString();
              
              if(va.startsWith("#"))
              {
                  
              }
              else
              {
                  HPredictor.numrecords++;
               
              
              
              boolean r1 = AllHadoopInvoke.dc.isDiseaseNB(va);
              
              int level= AllHadoopInvoke.dc.getSeverity(va);
              
             
              
              
              
              if (r1==true)
              {
                    DiseaseRecordView.instance.writetolog("For  Naive Bayes Classifier");
                    
                    output.collect(value, new IntWritable(1));
                    
                    DiseaseRecordView.instance.writetolog(va + "==> Heart Disease ");
                          
              }
              else
              {
                  DiseaseRecordView.instance.writetolog("For  Naive Bayes Classifier");
                  
                  output.collect(value, new IntWritable(0));
                   
                  DiseaseRecordView.instance.writetolog(va + "==>No Disease");
                   FNRF =FNRF+1;
                  
              }
            //   den1 = TPRF+TNRF;
            //   num1 = TPRF+TNRF+FPRF+FNRF;
              
             //  AccRF = (num1*23) / (den1);
              
            //  System.out.println("Accuracy for Random Forest Bayes is" +AccRF);
              
                  
                  
                  
              }
              
              
              
         }
         
         
    
    }
    
      public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

            public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

                
                int sum=0;
                while(values.hasNext())
                {
                    int v = values.next().get();    
                    sum = sum + v;
                    
                
                }                
                
                output.collect(key, new IntWritable(sum));
                
            }
       }
     
     
    
    
}

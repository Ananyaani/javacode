
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
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



public class AllHadoopInvoke {
    
    static DClassifier dc = new DClassifier();
    
    static AllHadoopInvoke instance;
    
    public static void main(String [] arg)  throws Exception
    {
        Vector<Double> prev = new Vector<Double>();
        
           
              
              
  try
        {
           FileReader fr = new FileReader("/home/linux/DiseasePrediction/pinput/test");

           BufferedReader buf=new BufferedReader(fr);

           String s;

           while ((s=buf.readLine())!=null)
           {
                 
                if(s.startsWith("#"))
                {
                    continue;
                }
                else
                {
               //  System.out.println(s);
                
                 
            ///     String [] parts = s.split(",");
                 
                 
         ///double v = Double.parseDouble(parts[5]);
        //// System.out.println(v);
         
        // double v1 = Double.parseDouble(parts[6]);
        // System.out.println(v1);
         
      ///   prev.add(v);
                }  
        }
           buf.close();
           fr.close();
           }
         catch(Exception e)
        {
            e.printStackTrace();
        }
    
  
  
  
            DiseaseRecordView disview = new DiseaseRecordView();
        
            // DosagePredictor dospred = new DosagePredictor(prev);
             
             long st = Calendar.getInstance().getTimeInMillis();
            
             dc.trainClassifierNB();
             
             
            //////////////////////////////////////////////////////////// DosagePredictor er = new DosagePredictor(prev);
           //  er.buildModel();
             
            // System.out.println("Linear Regression Res:" + er.getNextValue());
            // DiseaseRecordView.instance.writetolog("Predicted Value Using Linear Regression is " + er.getNextValue());
            
             JobConf conf = new JobConf(HPredictor.class);
             conf.setJobName("Diseaseprediction");

             conf.setOutputKeyClass(Text.class);
 	     conf.setOutputValueClass(IntWritable.class);

 	     conf.setMapperClass(HPredictor.Map.class);
	     //conf.setCombinerClass(OddEvenCounter.Reduce.class);
             conf.setReducerClass(HPredictor.Reduce.class);
 
             conf.setInputFormat(TextInputFormat.class);
	     conf.setOutputFormat(TextOutputFormat.class);


             String inppath = "/home/linux/DiseasePrediction/pinput";
             String outpath = "/home/linux/DiseasePrediction/poutput";
	     FileInputFormat.setInputPaths(conf, new Path(inppath));
 	     FileOutputFormat.setOutputPath(conf, new Path(outpath));

             System.out.println("About to start app");
 	     JobClient.runJob(conf);
             
             long et = Calendar.getInstance().getTimeInMillis();
             
             long diff = et-st;
             
             String cth = "PT#" + HPredictor.numrecords + "#" + diff;
             
             FileAppender.AppendtoFile("Perfg2.txt", cth);   
             
             
           //  int NBAcc = HPredictor.AccNB;
             
             //int RFAcc = HPredictor.AccRF;
             
             //String cth1 = "L#" + HPredictor.numrecords + "#" + er.getNextValue();
             
             //FileAppender.AppendtoFile("Perfg1.txt", cth1);
             
             //String cth2 = "N#" + HPredictor.numrecords + "#" + RFAcc;
             
             //FileAppender.AppendtoFile("Perfg1.txt", cth2);
             
           
             
             
             
             

            
            
            
       
        
        
        
        
       // PerfView pf = new PerfView();
       // pf.setVisible(true);
        
        disview.setVisible(true);
        
        
        
    }
    
    
    
}

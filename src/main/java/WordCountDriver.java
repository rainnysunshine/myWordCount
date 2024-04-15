import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import javax.security.auth.login.AppConfigurationEntry;
import java.io.File;
import java.io.IOException;


public class WordCountDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        String inputDirect  = "hdfs://master:9000/home/input/";
        String outputDirect = "hdfs://master:9000/home/output";
        String filename = "buyer_favorite.txt";
        String inputPath = inputDirect + '/' + filename;

        String outputPath = outputDirect;
        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf);
        //2.设置jar加载路径
        job.setJarByClass(WordCountDriver.class);
        //3.设置map和reduce类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        //4.设置map输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //5.设置最终的输出
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setOutputFormatClass(ResetOutputFormat.class);

        //6.设置输入和输出路径
        FileInputFormat.setInputPaths(job,new Path(inputPath));
        FileOutputFormat.setOutputPath(job,new Path(outputPath));
        System.exit(job.waitForCompletion(true)?0:1);

//                    MultipleOutputs.addNamedOutput(job,"comment",);
        //7.提交任务执行代码
//        result=job.waitForCompletion(true);
//        File file = new File(inputDirect);
//        if(file.isDirectory()){
//            String[] fileList = file.list();
//            if(fileList!=null){
////                for (String s : fileList) {
////                    init(s, outputPath);
////                }
//                String filename = "comment227020886.txt"; //一个文件时
//                boolean result = true;
//                for(int i=0; i<1;i++){
//                    String inputPath = inputDirect + "/" + filename;
//                    String outputPath = outputDirect;
//
//                    Configuration conf=new Configuration();
//                    Job job=Job.getInstance(conf);
//                    //2.设置jar加载路径
//                    job.setJarByClass(WordCountDriver.class);
//                    //3.设置map和reduce类
//                    job.setMapperClass(WordCountMapper.class);
//                    job.setReducerClass(WordCountReducer.class);
//                    //4.设置map输出
//                    job.setMapOutputKeyClass(Text.class);
//                    job.setMapOutputValueClass(IntWritable.class);
//                    //5.设置最终的输出
//                    job.setOutputKeyClass(Text.class);
//                    job.setOutputValueClass(IntWritable.class);
//
////                    job.setOutputFormatClass(ResetOutputFormat.class);
//
//                    //6.设置输入和输出路径
//                    FileInputFormat.setInputPaths(job,new Path(inputPath));
//                    FileOutputFormat.setOutputPath(job,new Path(outputPath));
//
////                    MultipleOutputs.addNamedOutput(job,"comment",);
//                    //7.提交任务执行代码
//                    result=job.waitForCompletion(true);
//                    //System.exit(result?0:1);
//                }
//                System.exit(result?0:1);
//            }
//
//        }
    }

    private static void init(String inputPath, String outputPath)throws InterruptedException, IOException, ClassNotFoundException{
        //1.获取配置信息以及封装任务
        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf);
        //2.设置jar加载路径
        job.setJarByClass(WordCountDriver.class);
        //3.设置map和reduce类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        //4.设置map输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //5.设置最终的输出
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //6.设置输入和输出路径
        FileInputFormat.setInputPaths(job,new Path(inputPath));
        FileOutputFormat.setOutputPath(job,new Path(outputPath));
        //7.提交任务执行代码
        boolean result=job.waitForCompletion(true);
        System.exit(result?0:1);



        //o正常退出
        //1或者-1  异常退出
    }
}
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.TaskAttemptContext;



import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;

public class ResetOutputFormat extends FileOutputFormat<Text, IntWritable> {
    public RecordWriter<Text,IntWritable> getRecordWriter(TaskAttemptContext job) throws IOException {
        Configuration conf = job.getConfiguration();

        //直接获取outputDir
        Path outputPath = getOutputPath(job);
        FileSystem fs = outputPath.getFileSystem(conf);


//        String inputFileName = conf.get("mapreduce.input.fileinputformat.inputdir");
//        Path outputFilePath = new Path(outputPath, inputFileName);
        String inputFileName = "collection.txt";
        Path outputFilePath = new Path(outputPath,inputFileName);
        FSDataOutputStream fileOut = fs.create(outputFilePath, true);
        return new MyRecordWriter(fileOut);
    }

    public String getOutputName(TaskAttemptContext context, String extension){
        String inputFileName = context.getConfiguration().get("mapreduce.input.fileinputformat.inputdir");
        return inputFileName;
    }


    public static class MyRecordWriter extends RecordWriter<Text, IntWritable> {
        private static final String UTF8 = "UTF-8";
        private static final byte[] newline;

        static{
            try{
                newline = "\n".getBytes(UTF8);
            }catch (UnsupportedEncodingException uee){
                throw new IllegalArgumentException("can't find" + UTF8 + " encoding");
            }
        }

        protected DataOutputStream out;
        private final byte[] keyValueSeparator;

        public MyRecordWriter(DataOutputStream out, String KeyValueSeparator){
            this.out = out;
            try{
                this.keyValueSeparator = KeyValueSeparator.getBytes(UTF8);
            }catch(UnsupportedEncodingException uee){
                throw new IllegalArgumentException("can't find" + UTF8 + " encoding");
            }
        }

        public MyRecordWriter(DataOutputStream out){this(out,"\t");}

        private void writeObject(Object o) throws IOException {
            if(o instanceof Text){
                Text to = (Text) o;
                out.write(to.getBytes(), 0, to.getLength());
            }else{
                out.write(o.toString().getBytes(UTF8));
            }
        }

        @Override
        public synchronized void write(Text key, IntWritable value) throws IOException, InterruptedException {
            boolean nullKey = key == null;
            boolean nullValue = value == null;

            if (nullKey && nullValue){
                return;
            }
            if(!nullKey){
//                out.write(key.getBytes(), 0, key.getLength());
                writeObject(key);
            }
            if(!(nullKey || nullValue)){
                out.write(keyValueSeparator);
            }
            if(!nullValue){
                writeObject(value);
            }
            out.write(newline);

        }

        @Override
        public synchronized void close(TaskAttemptContext context) throws IOException, InterruptedException {
            out.close();
        }
    }
}

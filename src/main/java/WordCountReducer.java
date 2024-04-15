import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReducer extends Reducer<Text, IntWritable,Text, IntWritable>{
    int sum;
    private IntWritable v = new IntWritable();
    @Override
    protected void reduce(Text key,Iterable<IntWritable> value,Reducer<Text,IntWritable,Text,IntWritable>.Context context) throws IOException, InterruptedException {
        //1.对输入的Interator类型进行累加求和
        sum=0;
        for (IntWritable count : value) {
            sum+=count.get();
        }
        //2.输出（word,总次数）

        v.set(sum);
        context.write(key,v);
    }
}

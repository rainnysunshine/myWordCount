import com.amazonaws.services.dynamodbv2.xspec.L;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


import java.io.IOException;

/**
 * <KEYIN,VALEIN,KEYOUT,VALUEOUT>
 * 输入的key(0,helloworld
 * 输入的value       helloworld
 * 输出的key   (hello,1)    hello
 * 输出的value               1
 */
public class WordCountMapper extends Mapper<Object, Text,Text, IntWritable> {
    private Text k = new Text();
    private static final IntWritable v = new IntWritable(1);

    protected void map(Object key,Text value,Mapper<Object,Text,Text,IntWritable>.Context context) throws IOException, InterruptedException {
        //1.获取一行内容，并转换成字符串操作
        String line = value.toString();
        //2.按空格进行切割
        String[] words = line.split(" ");
        // 3.输出(word,1)
        for (String word : words) {
            this.k.set(word);
            context.write(this.k,v);
        }
//        this.k.set(words[0]);
//        context.write(this.k,v);
    }
}
package readFile;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class ProcessData {
    private static String[] removeString = new String[]{"的","有","，","几","要","？","也","[","会","是","看","多","样","去","让","没","。","@","^"};
    public static void main(String[] args) throws IOException {
        String inputFilePath = "src/main/java/readFile/comment227263305old.txt";
        String writeFilePath = "src/main/java/readFile/comment227263305.txt";
        File inputFile = new File(inputFilePath);
        File outputFile = new File(writeFilePath);
        if (outputFile.exists()) outputFile.delete();
        outputFile.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line = "";
        Map<String,Integer> map = new HashMap<>();
        Pattern pattern = Pattern.compile("(\\w{1,2}\\s|\\s*)\\d+");
        while((line=br.readLine())!=null){

            if(pattern.matcher(line).matches()){ continue;}
            else{
                boolean flag = false;
                String[] strings = line.split("\\s");
                for (String s : removeString) {
                    if(strings[0].equals(s)){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    continue;
                }
                map.put(strings[0],Integer.valueOf(strings[1]));
            }
        }
        //将map中排序后写入文件
        if(!map.isEmpty()){
            List<Map.Entry<String,Integer>> list = sortMap(map);
            for (Map.Entry<String, Integer> entry : list) {
                bw.write(entry.getKey()+" "+entry.getValue()+'\n');
            }
        }
        br.close();
        bw.close();
    }
    public static void processData(String inputFilePath, String writeFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(writeFilePath);
        if (outputFile.exists()) outputFile.delete();
        outputFile.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line = "";
        Map<String,Integer> map = new HashMap<>();
        Pattern pattern = Pattern.compile("(\\w{1,2}\\s|\\s*)\\d+");
        while((line=br.readLine())!=null){

            if(pattern.matcher(line).matches()){ continue;}
            else{
                System.out.println(line);
                String[] strings = line.split("\\s");
                map.put(strings[0],Integer.valueOf(strings[1]));
            }
        }
        //将map中排序后写入文件
        if(!map.isEmpty()){
            List<Map.Entry<String,Integer>> list = sortMap(map);
            for (Map.Entry<String, Integer> entry : list) {
                bw.write(entry.getKey()+" "+entry.getValue()+'\n');
            }
        }
        br.close();
        bw.close();
    }

    private static List<Map.Entry<String, Integer>> sortMap(Map<String, Integer> map){
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet() );
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        return entryList;

    }

}

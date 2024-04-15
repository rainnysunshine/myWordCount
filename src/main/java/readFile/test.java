package readFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class test {
    private static final int PICK_NUM = 10;
    private static String titleStr = "";
    private static String commentStr = "";

    private static final Map<String,Integer> map = new HashMap<>();
    public static void main(String[] args) throws IOException {
        String commentInputPath = "src/main/java/readFile/comment227263305.txt";
        String titleInputPath = "src/main/java/readFile/title227263305.txt";


        BufferedReader br1 = new BufferedReader(new FileReader(commentInputPath));
        BufferedReader br2 = new BufferedReader(new FileReader(titleInputPath));
        String line = "";
        List<String> commentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        while((line = br1.readLine())!=null){
            String[] strings = line.split("\\s");
            commentList.add(strings[0]);
            map.put(strings[0],Integer.valueOf(strings[1]));
        }
        while((line = br2.readLine())!=null){
            String[] strings = line.split("\\s");
            titleList.add(strings[0]);
            titleStr += strings[0];
        }


        compare(commentList,titleList);
        System.out.println(titleStr);
        System.out.println(commentStr);

        br1.close();
        br2.close();
    }

    private static void compare(List<String> commentList,List<String> titleList){
        int count = PICK_NUM;
        List<String> list = Arrays.asList(new String[commentList.size()]);

        Collections.copy(list,commentList);
        for (String s : titleList) {
            if(count>0){
                for (String s1 : list) {
                    //当评论中分词与标题简介分词一致且评论分词数大于2时才能算
                    if(s.equals(s1) && map.get(s1)>2){
                        commentStr += s;
                        commentList.remove(s);
                        count--;
                    }
                }
            }else break;

        }

            for (String s : commentList) {
                if(count>0){
                    commentStr+=s;
                    count--;
                }else break;
            }


    }
}

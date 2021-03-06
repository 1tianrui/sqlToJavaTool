package createIbaits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jnkmhbl on 16/5/17.
 */
public class TrStringHelper {
    //将空格作为split的标准进行分组，过滤换行符
    public static String[]  splitBlank(String content){
        List<Character> chars = new ArrayList<Character>();
        chars.add('\r'); chars.add('\n');
        content = filterCharacter(content,chars);
        List<String> result = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        for(char a :content.toCharArray()){
            if(a ==' ' || a=='\r' ||a == '\n'){
                if(builder.length() != 0){
                    result.add(builder.toString());
                    builder = new StringBuilder();
                }
            }else{
                builder.append(a);
            }
        }
        String [] tmp = new String[result.size()];
        int idx = 0;
        for(String a : result){
            tmp[idx] = a;
            idx ++;
        }return tmp;
    }

    //过滤指定的符号
    public static String filterCharacter(String sql,List<Character> chars){
        StringBuilder builder = new StringBuilder();
        for(char a :sql.toCharArray()){
            boolean toSplit = false;
            for(Character c : chars){
                if(c.charValue() == a){
                    toSplit = true ;
                }
            }
            if(!toSplit){
                builder.append(a);
            }
        }
        return builder.toString();
    }

    public static  String convertToTuofeng(String name,boolean firstKey){
        boolean bottomPre = firstKey ;
        StringBuilder className = new StringBuilder();
        for(char a:name.toCharArray()){
            if(a == '\'' || a == '‘'){
                continue;
            }
            if(a !='_'&&(!bottomPre)){
                className.append(String.valueOf(a));
            }
            if(a=='_'){
                bottomPre=true;
                continue;
            }
            if(a!='_'&&bottomPre){
                className.append(String.valueOf(Character.toUpperCase(a)));
                bottomPre=false;
            }
        }
        return className.toString();
    }

    public static  int containsName(String  content ,String type,int targetSequence){
        if(!content.contains(type)){
            return -1 ;
        }
        int sequence = 0 ;
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for(char tmp :content.toCharArray()){
            if(tmp != ' '){
                builder.append(tmp);
            }else{
                String parseType = builder.toString();
                if( parseType.equalsIgnoreCase(type)){
                    return index ;
                }
                builder.delete(0,builder.length());
            }
            index ++;
        }
        return -1;
    }


}

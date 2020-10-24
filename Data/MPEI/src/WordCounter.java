import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class WordCounter {
    HashMap<String,Integer> hmap;
    ArrayList<String> words;
    public WordCounter(){
        hmap = new HashMap<String,Integer>();
    }

    public void analyzeDocument(String document){
        words = new ArrayList<String>(Arrays.asList(document.split(" ")));

        for (String i : words){
            if(hmap.containsKey(i))
                hmap.put(i,hmap.get(i)+1);
            else
                hmap.put(i,1);
        }
    }

    public int numberOfOcurrences(String word){
        int value;
        if(hmap.get(word)!=null)
            value=hmap.get(word);
        else
            value=0;

        return value;
    }

    @Override
    public String toString(){
        Iterator it = hmap.entrySet().iterator();
        while(it.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
           // it.remove();
        }
        return "";
    }
}

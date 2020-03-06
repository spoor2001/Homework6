/**
 * 
 * 
 * Spoorthi Gowda 
 * Homework 6
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MyMiniSearchEngine {
    private Map<String, List<List<Integer>>> indexes;
    // disable default constructor
    private MyMiniSearchEngine() {
        // test test
    }

    public MyMiniSearchEngine(List<String> documents) {
        index(documents);
        //System.out.println(indexes);
    }
    // each item in the List is considered a document.
    // assume documents only contain alphabetical words separated by white spaces.
    private void index(List<String> texts) {
        indexes = new HashMap<>();
        for (int dnum=0; dnum < texts.size(); dnum++){
           String str[] = texts.get(dnum).split(" ");
           for ( int snum = 0; snum < str.length ; snum++){
               if ( !indexes.containsKey(str[snum]) ) { // create new list of list
                   List<List<Integer>> list = new ArrayList<List<Integer>>();
                   // go thru all documents & create a empty list of list
                   for (int dnum2=0; dnum2 < texts.size(); dnum2++){
                       list.add(new ArrayList<Integer>());
                   }
                   indexes.put(str[snum],list);
               }
               indexes.get(str[snum]).get(dnum).add(snum);
           }
        }
        // System.out.println(indexes);
    }
    
    // search(key) return all the document ids where the given key phrase appears.
    // key phrase can have one or two words in English alphabetic characters.
    // return an empty list if search() finds no match in all documents.
    public List<Integer> search(String keyPhrase) {
        // homework
        String[] str= keyPhrase.split(" ");
        str[0] = str[0].toLowerCase();
        List<List<Integer>> result = indexes.get(str[0]);     
        for(int snum = 1; snum < str.length; snum++) {
            str[snum] = str[snum].toLowerCase();
            List<List<Integer>> templist = indexes.get(str[snum]);   
            if(templist == null) return new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).isEmpty()) {
                    templist.get(i).clear();
                } else {
                    for (int j = 0; j < templist.get(i).size(); j++) {
                        int k = 0;
                        while (result.get(i).get(k) < templist.get(i).get(j)) {
                            k++;
                            if(k>=result.get(i).size())
                                break;
                        }
                        if(k > 0) {
                            k--;
                        }
                        if (result.get(i).get(k) != (templist.get(i).get(j) -1)) {
                            templist.get(i).remove(j);
                            j--;
                        }
                    }
                }
            }
            result = templist;
        }
        
        List<Integer> answer = new ArrayList<>();
        if(result==null) return answer;
        for(int i = 0; i < result.size(); i++){
            if(!result.get(i).isEmpty()){
                answer.add(i);
            }
        }
        return answer;
    }
    
}
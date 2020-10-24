import java.util.*;

public class MinHash {
    int numberOfHashFunctions; //number of hashing functions
    int shingleLength;  // length of each shingle
    int totalNumberOfShingles; // total number ( calculated using a small calculation )
    ArrayList<String> documentList; //arraylist of each word that comes in the string
    ArrayList<Shingle> shingles; //arraylist of all the shingles
    int minHashList[];

    //constructor
    public MinHash(int numberOfHashFunctions, String document){
        ArrayList<String> documentList = new ArrayList<String>(Arrays.asList(document.split(" ")));
        this.numberOfHashFunctions=numberOfHashFunctions;
        if(documentList.size()/2==0)
            this.shingleLength=1;
        else
            this.shingleLength=documentList.size()/2;
        totalNumberOfShingles=documentList.size()-shingleLength+1;
        shingles = new ArrayList<Shingle>();
        this.documentList=documentList;
        minHashList = new int[numberOfHashFunctions];

    }
    // main minhash function
    public void populateMinHashList(){
        shingling();
        hashing();
    }

    // takes care of the hashing for each shingle
    private void hashing(){
       for(int i = 0; i <numberOfHashFunctions ; i++){
           minHashList[i]=hashingAux(i);
       }
    }
    //  hashing shingles
    private int hashingAux(int hashingfunction){
        String tmp ;
        int minhash=Integer.MAX_VALUE;
        for(int i = 0; i<shingles.size();i++) {
            tmp = "";
            for(int j = 0 ; j<shingleLength; j++) {
                tmp = tmp + shingles.get(i).words[j] + " ";
            }
            int hashcode = tmp.hashCode()^hashingfunction+20;
            shingles.get(i).hash=Math.abs(hashcode) % 35; // 20 seems like a good number

            if(shingles.get(i).hash<minhash)
                minhash=shingles.get(i).hash;
        }
        return minhash;
    }

    // separating the initial document into shingles
    private void shingling(){
        Shingle temp;
        int documentPointer=0;
        for(int i = 0; i<totalNumberOfShingles; i++){
            temp = new Shingle(shingleLength);
            temp.id=i;
            for(int j = 0 ; j < shingleLength; j++){
                temp.words[j]= documentList.get(documentPointer+j);
            }
            shingles.add(temp);
            documentPointer++;
        }
    }


}

//Aux class
class Shingle {
    String words[];
    int hash;
    int id;
    public Shingle(int size){
        words = new String[size];
    }
}
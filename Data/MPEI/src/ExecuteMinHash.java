import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//Class that takes care of using the minhash class to generate two objects of that type for each document added and has access to functions to compare how similar they are.
public class ExecuteMinHash {
    int numberOfDocuments;
    int currentNumber;
    int numberOfHashFunctions;
    String []documentList;
    ArrayList<ArrayList<Integer>> Matrix;

    //constructor
    public ExecuteMinHash(int numberOfDocuments, int numberOfHashFunctions){
        documentList = new String[numberOfDocuments];
        this.numberOfDocuments=numberOfDocuments;
        currentNumber=0;
        Matrix = new ArrayList<ArrayList<Integer>>();
        this.numberOfHashFunctions=numberOfHashFunctions;
    }

    // adds a document
    public void addDocument(String document){
        documentList[currentNumber]=document;
        currentNumber++;
    }

    //populates the table with each document and shingles with the hash number
    private void populateMatrix(){
        MinHash tmp;
        ArrayList<Integer> tmp2;
        Matrix.clear();
        for(int i=0; i<currentNumber;i++){
            tmp = new MinHash(numberOfHashFunctions, documentList[i]);
            tmp.populateMinHashList();
            tmp2 = new ArrayList<Integer>();
            for (int j : tmp.minHashList){
                tmp2.add(j);
            }
            Matrix.add(tmp2);
        }
    }

    // compares how similar two documents are
    public float similarity(int documentA,int documentB){
        int count = 0;
        populateMatrix();
        for(int i =0; i < Matrix.get(documentA).size();i++){
            if (Matrix.get(documentA).get(i)==Matrix.get(documentB).get(i)){
                count++;
            }

        }
        float estSimilarity = (float) count/numberOfHashFunctions;
        return estSimilarity;
    }

    public double jaccardCoefficient(Set<String> setA, Set<String> setB){

        // intersection
        Set<String> intersection = new HashSet<String>(setA);
        intersection.retainAll(setB);

        // union
        Set<String> union = new HashSet<String>(setA);
        union.addAll(setB);

        // non division by 0
        if(union.isEmpty())
            return 0;

        return (double) intersection.size() / union.size();
    }


}

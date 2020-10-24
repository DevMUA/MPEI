public class BloomFilter {
    private boolean[] filter; // bloom filter
    private int size; // size of bloom filter
    private int k; // number of hash functions
    private int numberOfElements; // number of elements added

    //constructor
    public BloomFilter(int size, int k){
    filter = new boolean[size];
    this.size=size;
    this.k=k;
    this.numberOfElements=0;
    }

    // insert element in the filter
    public void insert(String str){
        str=str.toLowerCase();
        String tmp;
        for(int i=0;i<k;i++) {
            tmp = i + "";
            str = str + tmp;
              filter[Math.abs(hashDjb2(str))]=true;
          }
      numberOfElements++;
    }

    // verify if an element belongs in the filter
    public boolean verify(String str){
        str=str.toLowerCase();
        String tmp;
        for(int i=0;i<k;i++) {
            tmp = i + "";
            str = str + tmp;
            if (filter[Math.abs(hashDjb2(str))]==false)
                return false;
        }
        return true;
    }

    // False positive probability
    public double ProbFalsePositive(){
        return Math.pow((1-Math.exp((double)-k*numberOfElements/size)), k);
    }

    //Djb2 hashing
    private int hashDjb2(String str){
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + ((hash << 5) - hash);
        }
        hash = hash % this.size;
        return hash;
    }
}

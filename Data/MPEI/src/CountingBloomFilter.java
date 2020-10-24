public class CountingBloomFilter {
    private int[] filter;
    public int size;
    public int k;
    private int numberOfElements; // number of elements added

    //constructor
    public CountingBloomFilter(int size, int k){
        filter = new int[size];
        this.size=size;
        this.k=k;
        this.numberOfElements=0;
    }

    //insert element in the filter
    public void insert(String str){
        str=str.toLowerCase();
        String tmp;
        for(int i=0;i<k;i++) {
            tmp = i + "";
            str = str + tmp;
            filter[Math.abs(hashDjb2(str))]++;
        }
        numberOfElements++;
    }

    //verify if element belongs to filter
    public boolean verify(String str){
        str=str.toLowerCase();
        String tmp;
        for(int i=0;i<k;i++) {
            tmp = i + "";
            str = str + tmp;
            if (filter[Math.abs(hashDjb2(str))]==0)
                return false;
        }
        return true;
    }

    public int count(String str){
        str=str.toLowerCase();
        String tmp;
        int min=Integer.MAX_VALUE;
        for(int i=0;i<k;i++) {
            tmp = i + "";
            str = str + tmp;
            if (filter[Math.abs(hashDjb2(str))]<min)
                min=filter[Math.abs(hashDjb2(str))];
        }
        return min;
    }
    // remove element from filter
    public void remove(String str){
        str=str.toLowerCase();
        String tmp;
        if(verify(str)==true) {
            for (int i = 0; i < k; i++) {
                tmp = i + "";
                str = str + tmp;
                if (filter[Math.abs(hashDjb2(str))] != 0)
                    filter[Math.abs(hashDjb2(str))]--;
            }
            numberOfElements--;
        }
    }

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

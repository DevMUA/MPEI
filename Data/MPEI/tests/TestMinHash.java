
import javax.print.DocFlavor;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TestMinHash {
    Scanner scanner;

    public TestMinHash(){
            scanner = new Scanner(System.in);
    }

    public void menuTests(){
        int choice;
        String input;
        int play = 1;

        while(play == 1) {
            System.out.println("+------------------Counting Bloom filter tests---------------------+");
            System.out.println("| 1- Bookname Test                                                 |");
            System.out.println("| 2- Movie Database test                                           |");
            System.out.println("| 3- Exit                                                          |");
            System.out.println("+------------------------------------------------------------------+");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    bookNameTest();
                    break;
                }
                case 2: {
                    movieDatabaseTest();
                    break;
                }
                case 3: {
                    play = 0;
                    break;
                }
                default: {
                    System.out.println("Invalid option");
                    break;
                }
            }
        }
    }

    public void bookNameTest(){

        ExecuteMinHash Um = new ExecuteMinHash(100, 100);

        String tmp = "";
        tmp = tmp +"I Was Told There'd Be Cake ";
        tmp = tmp +"To Kill a Mockingbird ";
        tmp = tmp +"The Man Without Qualities ";
        tmp = tmp +"Where the Wild Things Are ";

        Um.addDocument(tmp);
        tmp = "";


        System.out.println("List 1: \n ");
        System.out.println("    I Was Told There'd Be Cake ");
        System.out.println("    To Kill a Mockingbird ");
        System.out.println("    The Man Without Qualities ");
        System.out.println("    Where the Wild Things Are ");

        tmp = tmp +"I Was Told There'd Be Cake ";
        tmp = tmp +"To Kill a Mockingbird ";
        tmp = tmp +"The Man Without Qualities ";
        tmp = tmp +"Where the Wild Things Are ";

        Um.addDocument(tmp);
        tmp = "";

        System.out.println("List 2: \n");
        System.out.println("    I Was Told There'd Be Cake ");
        System.out.println("    To Kill a Mockingbird ");
        System.out.println("    The Man Without Qualities ");
        System.out.println("    Where the Wild Things Are");


        tmp = tmp +"Me Talk Pretty One Day ";
        tmp = tmp +"One Hundred Years of Solitude ";
        tmp = tmp +"How to Lose Friends and Alienate People ";
        tmp = tmp +"A Thousand Splendid Suns ";

        Um.addDocument(tmp);
        tmp = "";


        System.out.println("List 3:\n");
        System.out.println("    Me Talk Pretty One Day ");
        System.out.println("    One Hundred Years of Solitude ");
        System.out.println("    How to Lose Friends and Alienate People ");
        System.out.println("    A Thousand Splendid Suns ");

        tmp = tmp +"I Was Told There'd Be Cake ";
        tmp = tmp +"To Kill a Mockingbird ";
        tmp = tmp +"The Man Without Qualities ";
        tmp = tmp +"Where the Wild Things Are";


        Um.addDocument(tmp);
        tmp = "";


        System.out.println("List 4:\n");
        System.out.println("    I Was Told There'd Be Cake");
        System.out.println("    To Kill a Mockingbird ");
        System.out.println("    The Man Without Qualities ");
        System.out.println("    Where the Wild Things Are");

        tmp = tmp +"Me Talk Pretty One Day ";
        tmp = tmp +"One Hundred Years of Solitude ";
        tmp = tmp +"How to Lose Friends and Alienate People ";
        tmp = tmp +"A Thousand Splendid Suns";


        Um.addDocument(tmp);
        tmp = "";


        System.out.println("List 5:\n");
        System.out.println("    Me Talk Pretty One Day ");
        System.out.println("    One Hundred Years of Solitude ");
        System.out.println("    How to Lose Friends and Alienate People ");
        System.out.println("    A Thousand Splendid Suns");




        System.out.printf("\nlist 'one' with list 'five' : %.2f de similaridade",Um.similarity(0,4));
        System.out.printf("\nlist 'one' with list 'two'  : %.2f de similaridade",Um.similarity(0,1));
        System.out.printf("\nlist 'one' with list 'four' : %.2f de similaridade",Um.similarity(0,3));
        System.out.printf("\nlist 'one' with list 'tree' : %.2f de similaridade",Um.similarity(0,2));
        System.out.println();
    }

    public void movieDatabaseTest(){
        HashMap<Integer,User> users = new HashMap<Integer,User>();

        //READ FILE AND PUT IT ON LIST
        ArrayList<int[]> list = new ArrayList<int[]>();
        File file = new File("u.data");
        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            int u = 0;
            while ((text = reader.readLine()) != null && u!=200) {
                u++;
                String[] tmp = text.split("\t");
                int[] tmp2 = new int[tmp.length];
                for(int i=0;i<tmp.length;i++){
                    tmp2[i]=Integer.parseInt(tmp[i]);
                }
                list.add(tmp2);
                //list.add(Integer.parseInt(text));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

        //PRINT LIST IF NEEDED
        //for(int i = 0; i<list.size();i++)
        //System.out.println(list.get(i)[0]);

        //SORT THROUGH LIST
        for(int i=0;i<list.size();i++){
            if(!users.containsKey(list.get(i)[0])){
                User tmp = new User(list.get(i)[0]);
                tmp.movieList.add(list.get(i)[1]);
                users.put(list.get(i)[0],tmp);
            }
            else {
                users.get(list.get(i)[0]).movieList.add(list.get(i)[1]);
            }
        }

        Iterator it = users.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            User tmp2 = (User)pair.getValue();
            getSimilarUsers(tmp2,users);

            System.out.println(tmp2.ID + " Similar users:");
            for(int i = 0;i<tmp2.similar_users.size();i++){
                System.out.print(tmp2.similar_users.get(i).ID + " ");
                System.out.println();
            }

        }




    }

    private void getSimilarUsers(User usr, HashMap<Integer,User> mp){
        ExecuteMinHash minhash = new ExecuteMinHash(1000,10);

        //parsing user movie list to string type

        String tmp = "";
        int n = usr.movieList.size();
        for(int i=0;i<n;i++) {
            tmp = tmp + Integer.toString(usr.movieList.remove()) + " ";
        }
        usr.movies = tmp;
        minhash.addDocument(usr.movies);
        //iterate through hashmap of users
        Iterator it = mp.entrySet().iterator();
        int documentNumber = 0;
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            //If it isnt the original user
            if(Integer.valueOf((int)pair.getKey()) != usr.ID){
                documentNumber++;
                tmp = "";
                User tmp2 = (User)pair.getValue();
                n = tmp2.movieList.size();
                for(int i=0;i<n;i++) {
                    tmp = tmp + Integer.toString(tmp2.movieList.remove()) + " ";
                }
                tmp2.movies = tmp;

                minhash.addDocument(tmp);
                if(minhash.similarity(0,documentNumber)>0.4){
                    usr.similar_users.add(tmp2);
                }

            }
       // System.out.println("ola" + documentNumber);
        }


    }

}

class User{
    int ID;
    PriorityQueue<Integer> movieList;
    ArrayList<User> similar_users;
    String movies;

    public User(int ID){
        this.ID=ID;
        movieList = new PriorityQueue<Integer>();
        similar_users = new ArrayList<User>();
    }
}
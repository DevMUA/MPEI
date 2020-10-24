import java.util.Scanner;

public class main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);


        int choice;
        String input;
        int play = 1;

        while(play == 1) {
            System.out.println("+-----------------------------MENU---------------------------------+");
            System.out.println("| 1- Bloom filter tests                                            |");
            System.out.println("| 2- Counting Bloom filter tests                                   |");
            System.out.println("| 3- minhash tests                                                 |");
            System.out.println("| 4- Dating App (All used)                                         |");
            System.out.println("| 5- Exit                                                          |");
            System.out.println("+------------------------------------------------------------------+");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    TestBloomFilter test = new TestBloomFilter();
                    test.menuTests();
                    break;
                }
                case 2: {
                    TestCountingFilter test = new TestCountingFilter();
                    test.menuTests();
                    break;
                }
                case 3: {
                    TestMinHash test = new TestMinHash();
                    test.menuTests();
                    break;
                }
                case 4: {
                    DatingAppTest test = new DatingAppTest();
                    test.useDatabase();
                    break;
                }
                case 5: {
                    play = 0;
                    break;
                }
                default: {
                    System.out.println("Invalid option");
                    break;
                }
            }
        }

       //ExecuteMinHash test = new ExecuteMinHash(2,200);
       //test.addDocument("gaming racing");
       //test.addDocument("gaming baking");
       //System.out.println(test.similarity(0,1));
        //WordCounter test2 = new WordCounter();
        //test2.analyzeDocument("palavra palavra eu sou o o ricardo e gosto de pila");
        //System.out.println(test2.numberOfOcurrences("palavra"));

        //TestBloomFilter test = new TestBloomFilter();
        //test.workShift();
       //test.testCountries(15);
        //test.testRandomStrings(1000,40,8000,3);
        //test.testRandomStrings(10000,40,8000,3);
        //test.defaultTestRandomStrings();

        //TestCountingFilter test = new TestCountingFilter();
        //test.workShift();
        //test.pokedexTest();
       // CountingBloomFilter BF = new CountingBloomFilter(1000,10);
       // BF.insert("pika");
       // System.out.println(BF.verify("pika"));
       // BF.remove("pika");
       // System.out.println(BF.verify("pika"));

        //DatingAppTest test = new DatingAppTest();
        //test.useDatabase();

        //TestMinHash test = new TestMinHash();
        //test.bookNameTest();
        //test.movieDatabaseTest();
    }
}

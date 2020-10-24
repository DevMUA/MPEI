import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TestBloomFilter {
    Scanner scanner;
    public TestBloomFilter(){
         scanner = new Scanner(System.in);
    }

    public void menuTests(){
        int choice;
        String input;
        int play = 1;

        while(play == 1) {
            System.out.println("+---------------------Bloom filter tests---------------------------+");
            System.out.println("| 1- Test random Strings (Give manual input)                       |");
            System.out.println("| 2- Test random Strings (Generated input)                         |");
            System.out.println("| 3- Countries test                                                |");
            System.out.println("| 4- WorkShift test                                                |");
            System.out.println("| 5- Exit                                                          |");
            System.out.println("+------------------------------------------------------------------+");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println("Number of strings?");
                    int n1 = scanner.nextInt();
                    System.out.println("String size?");
                    int n2 = scanner.nextInt();
                    System.out.println("Bloom filter size?");
                    int n3 = scanner.nextInt();
                    System.out.println("K number?");
                    int n4 = scanner.nextInt();
                    testRandomStrings(n1,n2,n3,n4);
                    break;
                }
                case 2: {
                    defaultTestRandomStrings();
                    break;
                }
                case 3: {
                    System.out.println("Number of Countries?");
                    int n1 = scanner.nextInt();
                    testCountries(n1);
                    break;
                }
                case 4: {
                    workShift();
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
    }

    public void testRandomStrings(int numberOfStrings,int stringSize,int bloomFilterSize, int k){
        GenerateString gn = new GenerateString();
        String tmp;
        BloomFilter BF = new BloomFilter(bloomFilterSize,k);
        for(int i = 0 ; i < numberOfStrings; i ++){
            tmp = gn.run(stringSize);
            BF.insert(tmp);
        }


        System.out.println(BF.ProbFalsePositive());
    }

    public void defaultTestRandomStrings(){
        GenerateString gn = new GenerateString();
        String tmp;
        BloomFilter BF = new BloomFilter(1000,3);
        for(int i = 0 ; i < 1000; i ++){
            tmp = gn.run(3);
            BF.insert(tmp);
        }

        int counter = 0;
        for(int i = 0 ; i < 100; i ++){
            tmp = gn.run(3);
            if(BF.verify(tmp)==true) {
                System.out.println(tmp + " belongs to the filter");
                counter++;
            }
            else
                System.out.println(tmp + " does not belong to the filter");
        }

        System.out.println(counter + " of the 100 Strings belonged");
        System.out.println(BF.ProbFalsePositive());
    }

    public void testCountries(int numberOfCountries){
        BloomFilter BF = new BloomFilter(200,4);

        ArrayList<String> countryList = new ArrayList<String>();
        countryList = pickCountries(numberOfCountries);

        for(String i : countryList){
            BF.insert(i);
            System.out.println(i + " added");
        }

        System.out.println();
        System.out.println("------------------------------------------------------");

        countryList = pickCountries(numberOfCountries + 5);

        for(String i : countryList){
            if(BF.verify(i)==true){
                System.out.println(i + " Belongs to the filter");
            }
            else
                System.out.println(i + " Does not belong to the filter");
        }
    }

    public void workShift(){
            BloomFilter BF = new BloomFilter(200,4);
            int choice;
            int play = 1;
            String input;
            while(play == 1) {
                System.out.println("1-Add worker to shift");
                System.out.println("2-Check if worker is on shift");
                System.out.println("3-Stop managing");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1: {
                        System.out.println("Name of the worker you want to add:");
                        scanner.nextLine();
                        input = scanner.nextLine();
                        BF.insert(input);
                        break;
                    }
                    case 2: {
                        System.out.println("Name of the worker you want to verify:");
                        scanner.nextLine();
                        input = scanner.nextLine();
                        if(BF.verify(input)==true){
                            System.out.println("Worker is on the shift!");
                        }
                        else {
                            System.out.println("Worker is not on the shift!");
                        }
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

    private ArrayList<String> pickCountries(int numberOfCountries){
        Countries listOfCountries = new Countries();
        ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
        ArrayList<String> randomCountries = new ArrayList<String>();
        int totalNumberOfCountries = listOfCountries.list.length;

        //generate numbers non repeat
        for(int i = 0; i<numberOfCountries; i ++){
            Random rand = new Random();

            int n = rand.nextInt(totalNumberOfCountries);
            if(randomNumbers.contains(n)==true){
                while(randomNumbers.contains(n)==true){
                    n = rand.nextInt(totalNumberOfCountries);
                }
                randomNumbers.add(n);
            }
            else
                randomNumbers.add(n);


        }

        // get countries
        for(int i = 0 ; i<numberOfCountries; i++){
            randomCountries.add(listOfCountries.list[randomNumbers.get(i)]);
        }

        return randomCountries;
    }
}



class Countries{
    static String[] list = {"Portugal", "Espanha", "França", "Itália", "Alemanha", "Bélgica", "Irlanda", "China","Inglaterra",
            "Escócia", "Japão", "Tailândia", "República Checa", "Argentina", "Brasil", "Austrália", "Nova Zelândia",
            "Canadá", "Estados Unidos", "Grécia", "Paraguai", "Nigéria", "Angola", "Moçambique", "Chile", "Rússia",
            "Israel", "Suécia", "Suíça", "Dinamarca"};
}

class GenerateString{

    public GenerateString(){

    }

    public String run(int stringSize){
        String finalString = "";
        Random rand = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
        for(int i = 0 ; i < stringSize; i++){
            finalString = finalString.concat(alphabet.charAt(rand.nextInt(alphabet.length()))+"");
        }

        return finalString;
    }
}
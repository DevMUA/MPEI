import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DatingAppTest {
    DatingAppMain dap;
    Scanner scanner;
    AppUser myself;
    UserDatabase database;

    public DatingAppTest(){
        dap = new DatingAppMain();
        scanner = new Scanner(System.in);
        myself = new AppUser();
        database = new UserDatabase();
    }

    public void useDatabase(){
        AppUser tmp;
        AppUser []list;
        UserDatabase ud = new UserDatabase();
        int choice;
        int tmp2,tmp3;
        String input,input2,input3;
        int play = 1;
        int registered=0;
        while(play == 1) {
            System.out.println("+-------------------------Dating app ------------------------------+");
            System.out.println("| 1- Register                                                      |");
            System.out.println("| 2- Try to match me                                               |");
            System.out.println("| 3- Exit                                                          |");
            System.out.println("+------------------------------------------------------------------+");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    if(registered==1) {
                        System.out.println("You are already registered!");
                        break;
                    }
                    System.out.println("Your name: ");
                    scanner.nextLine();
                    input = scanner.nextLine();
                    System.out.println("Your gender (1-male,0-female): ");
                    tmp2 = scanner.nextInt();
                    if(tmp2!=1 && tmp2!=0){
                        System.out.println("Has to be equal to 0 or 1");
                        break;
                    }
                    System.out.println("Your preferance (1-male,0-female): ");
                    tmp3 = scanner.nextInt();
                    if(tmp3!=1 && tmp3!=0){
                        System.out.println("Has to be equal to 0 or 1");
                        break;
                    }
                    if(tmp2==1){
                        input2 = "male";
                    }
                    else input2="female";
                    if(tmp3==1){
                        input3 = "male";
                    }
                    else input3="female";
                    tmp = new AppUser(input,0,input2,input3);
                    dap.registerUser(tmp);
                    myself=tmp;
                    System.out.println("Add some interests! (Type stop to exit listing)");
                    scanner.nextLine();
                    while(!input.equals("stop")) {
                        input = scanner.nextLine();
                        if (!input.equals("stop")) {
                            String interest = input;
                            dap.registered_users.get(0).addInterest(interest);
                            //myself.addInterest(interest);
                        }
                    }
                    ud.load(200,dap);
                    registered=1;
                    break;
                }
                case 2: {
                    if(!myself.name.equals("")) {
                        scanner.nextLine();
                        list = dap.catchUserSuggestions(myself, 20);
                        for (int i = 0; i < list.length; i++){
                            dap.showUser(list[i]);
                            System.out.println("Show next? (y/n)");
                            input = scanner.nextLine();
                            //scanner.nextLine();
                            if(input.equals("n"))
                                break;
                            else continue;
                        }
                        System.out.println("You have seen all!");
                    }
                    else{
                        System.out.println("Register first!");
                    }
                    break;
                }
                case 3: {
                    play=0;
                    break;
                }
                default: {
                    System.out.println("Invalid option");
                    break;
                }
            }
        }
    }
}

class UserDatabase{
    String []interests = {"gaming","soccer","Movies","Series","Racing", "Sports", "Traveling", "Photography","Swimming","3D printing", "Baking","Magic","Sewing","Singing","Sketching","Drawing","Reading"
    ,"Yoga","Writting", "Juggling","Dance","Fashion","Painting","Cooking","Programming"};

    String []namesFemale = {"Ema", "Joycelyn", "Shari", "Pa", "Jann", "Winifred", "Goldie", "Crista", "Irish", "Kemberly", "Bonny", "Rosalind", "Twila", "Blythe", "Lanelle", "Cris", "Gracia", "Arleen", "Demetria", "Domonique", "Daniela", "Alla", "Ruthe",
          "Fredericka", "Blondell", "Lita", "Lauryn", "Kaye", "Rosanne", "Amberly", "Aleen", "Ardelia", "Delaine", "Raymonde", "Brittany", "Modesta", "Laila", "Carma",
          "Casandra", "Georgann", "Enola", "Evangelina", "Charleen", "Brandie", "Gretta", "Kaley", "Jacquelyne", "Kyla", "Breanne"};

    String []namesMale = {"Edgardo","Leo","Darius","Andreas","Tim","Lupe"," Cyril","Milford","Grady","Tuan","Cliff","Dino","Ty","Elton","Otis","Scot","Darrel","Reed",
            "Cesar","Charles","Ben","Deshawn","Kip","Boyce","Rodrick","Jewel","Jerrold","Ira","Randall","Teddy"," Tristan","Alton","Sang","Donte","Mitchell","Vernon",
            "Gerald","Eric","Jimmie","Guy","Ahmed","Dennis","Coy","Norbert","Rodger","Isiah","Tory","Donald","Vincenzo"};

    public UserDatabase(){

    }

    public void load(int amountOfUsers,DatingAppMain dap){
        for(int i=0;i<amountOfUsers;i++){
            dap.registerUser(generateUser());
        }
    }

    public AppUser generateUser(){
        ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
    Random rand = new Random();
    AppUser tmp;
    boolean preferance;
    int tmp2 = rand.nextInt(2);

    //female
    if(tmp2==1){
        tmp2 = rand.nextInt(2);
        if(tmp2==1){
            preferance=true;
        }
        else{
            preferance=false;
        }
        tmp = new AppUser();
        tmp.name=namesFemale[rand.nextInt(namesFemale.length)];
        tmp.sex=false;
        tmp.preference=preferance;
    }
    //male
    else{
        tmp2 = rand.nextInt(2);
        if(tmp2==1){
            preferance=true;
        }
        else{
            preferance=false;
        }
        tmp = new AppUser();
        tmp.name=namesMale[rand.nextInt(namesMale.length)];
        tmp.sex=true;
        tmp.preference=preferance;
    }
    int numberOfInterests = rand.nextInt(8)+1;


    //generate numbers non repeat
        for(int i = 0; i<numberOfInterests; i ++){

            int n = rand.nextInt(interests.length);
            if(randomNumbers.contains(n)==true){
                while(randomNumbers.contains(n)==true){
                    n = rand.nextInt(interests.length);
                }
                randomNumbers.add(n);
            }
            else
                randomNumbers.add(n);


        }

        for(int i : randomNumbers){
            tmp.addInterest(interests[i]);
        }

    return tmp;
    }





}

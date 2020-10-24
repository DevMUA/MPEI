import java.util.*;

public class DatingAppMain {
    BloomFilter registered_usersBF; //logs registered users
    CountingBloomFilter users_log;  //logs users and removes/adds people depending if they have already been shown or not
    ArrayList<AppUser> registered_users;


    public DatingAppMain(){
        registered_usersBF = new BloomFilter(1000,10);
        users_log = new CountingBloomFilter(1000,10);
        registered_users = new ArrayList<AppUser>();
    }

    //register user by passing attributes
    public void registerUser(String name,String sex,String preferance){
            int ID;
            if (!registered_users.isEmpty())
                ID = registered_users.get(registered_users.size() - 1).ID+1;
            else
                ID = 0;
            AppUser newUser = new AppUser(name, ID,sex,preferance);
            registered_usersBF.insert(name);
            registered_users.add(newUser);
            users_log.insert(name + "" + ID);

    }

    //register user by passing object
    public void registerUser(AppUser user){
            int ID;
            if (!registered_users.isEmpty())
                ID = registered_users.get(registered_users.size() - 1).ID+1;
            else
                ID = 0;
            user.ID=ID;
            registered_usersBF.insert(user.name);
            registered_users.add(user);
            users_log.insert(user.name + "" + ID);

    }

    //check if a certain user is registered
    public boolean checkIfUserIsRegistered(String name){
        if(registered_usersBF.verify(name)==true)
            return true;
        return false;
    }

    public void showUser(AppUser user){
        users_log.remove(user.name+""+user.ID);
        System.out.println("User name: " + user.name);
        System.out.print("Match percentage:  ");
        System.out.printf("%.2f", user.matchPercentage*100);
        System.out.println();
        System.out.print("Interests: ");
        for(String i : user.interests){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public AppUser[] catchUserSuggestions(AppUser user,int amountOfSuggestions){

        Comparator<AppUser> customComparator = new Comparator<AppUser>(){
            @Override
            public int compare(AppUser s1,AppUser s2){
                if(s1.matchPercentage < s2.matchPercentage)
                    return 1;
                else if ( s1.matchPercentage > s2.matchPercentage)
                    return -1;
                else {
                    return 0;
                }
            }
        };
        //starting minhash
        ExecuteMinHash tmp = new ExecuteMinHash(1000,100);

        //init some needed PQ and array
        AppUser []suggestions = new AppUser[amountOfSuggestions];
        PriorityQueue<AppUser> listByInterestFactor = new PriorityQueue<>(customComparator);

        //tmp string
        String user_interests =  "";

        //collecting own user interests
        for(String i : user.interests){
            user_interests = user_interests + " " + i;
        }
        tmp.addDocument(user_interests);
        //collecting other users interests
        for(AppUser tmp_user : registered_users){
            user_interests =  "";
            if(tmp_user.ID!=user.ID){
                for(String i : tmp_user.interests){
                    user_interests = user_interests + " " + i;

                }
            }
            tmp.addDocument(user_interests);
        }

        //selecting the #AmountOfSuggestions best candidates
        for(int i=1;i<registered_users.size()-1;i++){

            //checking if user has already been shown, if so then doesnt bother adding it
            if(users_log.verify(registered_users.get(i).name + "" + registered_users.get(i).ID)==true && registered_users.get(0).preference==registered_users.get(i).sex) {
                if(tmp.similarity(0, i) >= 0.2){
                    String []setAs = new String[registered_users.get(0).interests.size()];
                    setAs = registered_users.get(0).interests.toArray(setAs);
                    Set<String> setA = new HashSet<String>(Arrays.asList(setAs));
                    String []setBs = new String[registered_users.get(i).interests.size()];
                    setBs = registered_users.get(i).interests.toArray(setBs);
                    Set<String> setB= new HashSet<String>(Arrays.asList(setBs));

                    registered_users.get(i).matchPercentage = (float)tmp.jaccardCoefficient(setA,setB);
                    listByInterestFactor.add(registered_users.get(i));
                }
            }
        }

        //passing the top suggestions to an array
        for(int i=0;i<amountOfSuggestions;i++){
            if(!listByInterestFactor.isEmpty())
            suggestions[i] = listByInterestFactor.remove();
        }

        return suggestions;
    }


}

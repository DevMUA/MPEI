import java.util.ArrayList;

public class AppUser {
    String name;
    int ID;
    boolean sex; //true=male false=female
    boolean preference; //true=male false=female
    ArrayList<String> interests;
    float matchPercentage;

    public AppUser(String name,int ID,String sex,String preferance){
        this.name=name;
        this.ID=ID;
        sex = sex.toLowerCase();
        preferance= preferance.toLowerCase();

        if(sex.equals("male")){
            this.sex=true;
        }
        else{
            this.sex=false;
        }

        if(preferance.equals("male")){
            this.sex=true;
        }
        else{
            this.sex=false;
        }
        interests = new ArrayList<String>();
        matchPercentage=0;
    }
    public AppUser(){
        this.name="";
        this.ID=0;
        this.sex=false;
        this.preference=false;
        interests = new ArrayList<String>();
        matchPercentage=0;
    }

    public void addInterest(String interest){
        interest=interest.toLowerCase();
        interests.add(interest);
    }
}

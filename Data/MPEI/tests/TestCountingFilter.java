import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TestCountingFilter {

    Scanner scanner;

    public TestCountingFilter(){
         scanner = new Scanner(System.in);
    }


    public void menuTests(){
        int choice;
        String input;
        int play = 1;

        while(play == 1) {
            System.out.println("+------------------Counting Bloom filter tests---------------------+");
            System.out.println("| 1- Workshift test (improved)                                     |");
            System.out.println("| 2- Pokedex test                                                  |");
            System.out.println("| 3- Exit                                                          |");
            System.out.println("+------------------------------------------------------------------+");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                   workShift();
                    break;
                }
                case 2: {
                    pokedexTest();
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
    public void workShift() {
        CountingBloomFilter CBF = new CountingBloomFilter(200, 4);
        ArrayList<String> workers = new ArrayList<String>();
        int choice;
        int play = 1;
        String input;
        while (play == 1) {
            System.out.println("1-Add worker to shift");
            System.out.println("2-Check if worker is on shift");
            System.out.println("3-Remove worker from shift");
            System.out.println("4-Check all workers and number of shifts");
            System.out.println("5-Stop managing");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println("Name of the worker you want to add:");
                    scanner.nextLine();
                    input = scanner.nextLine();
                    CBF.insert(input);
                    if(!workers.contains(input))
                        workers.add(input);
                    break;
                }
                case 2: {
                    System.out.println("Name of the worker you want to verify:");
                    scanner.nextLine();
                    input = scanner.nextLine();
                    if (CBF.verify(input) == true) {
                        System.out.println("Worker is on the shift!");
                    } else {
                        System.out.println("Worker is not on the shift!");
                    }
                    break;
                }
                case 3: {
                    System.out.println("Name of the worker you want to remove from shift:");
                    scanner.nextLine();
                    input = scanner.nextLine();
                    CBF.remove(input);
                    break;
                }
                case 4: {
                    System.out.println("Worker - Shift list:");
                    for(String i:workers)
                    System.out.println(i + " has " + CBF.count(i) + " shifts");
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

    public void pokedexTest(){
        Pokedex pd = new Pokedex();
        int choice;
        String input;
        int play = 1;
        while(play == 1) {
            System.out.println("1-Adventure into the wild");
            System.out.println("2-Check if you have X pokemon");
            System.out.println("3-Let a pokemon run free");
            System.out.println("4-Stop playing");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    pd.randomPokemonEncounter();
                    break;
                }
                case 2: {
                    System.out.println("which pokemon?");
                    scanner.nextLine();
                    input = scanner.nextLine();
                    pd.doIownPokemon(input);
                    break;
                }
                case 3: {
                    System.out.println("which pokemon?");
                    scanner.nextLine();
                    input = scanner.nextLine();
                    pd.letPokemonGoFree(input);
                    break;
                }
                case 4: {
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
}

class Pokedex{
    static String[] list = {"Bulbasaur", "Ivysaur", "Venusaur", "Charmander", "Charmeleon", "Charizard", "Squirtle", "Wartortle", "Blastoise", "Caterpie", "Metapod", "Butterfree", "Weedle",
            "Kakuna", "Beedrill", "Pidgey", "Pidgeotto", "Pidgeot", "Rattata", "Raticate", "Spearow", "Fearow", "Ekans", "Arbok", "Pikachu", "Raichu", "Sandshrew", "Sandslash", "Nidoran♀",
            "Nidorina", "Nidoqueen", "Nidoran♂", "Nidorino", "Nidoking", "Clefairy", "Clefable", "Vulpix", "Ninetales", "Jigglypuff", "Wigglytuff", "Zubat", "Golbat", "Oddish", "Gloom",
            "Vileplume", "Paras", "Parasect", "Venonat", "Venomoth", "Diglett", "Dugtrio", "Meowth", "Persian", "Psyduck", "Golduck", "Mankey", "Primeape", "Growlithe", "Arcanine", "Poliwag",
            "Poliwhirl", "Poliwrath", "Abra", "Kadabra", "Alakazam", "Machop", "Machoke", "Machamp", "Bellsprout", "Weepinbell", "Victreebel", "Tentacool", "Tentacruel", "Geodude", "Graveler",
            "Golem", "Ponyta", "Rapidash", "Slowpoke", "Slowbro", "Magnemite", "Magneton", "Farfetch’d", "Doduo", "Dodrio", "Seel", "Dewgong", "Grimer", "Muk", "Shellder", "Cloyster", "Gastly",
            "Haunter", "Gengar", "Onix", "Drowzee", "Hypno", "Krabby", "Kingler", "Voltorb", "Electrode", "Exeggcute", "Exeggutor", "Cubone", "Marowak", "Hitmonlee", "Hitmonchan", "Lickitung", "Koffing",
            "Weezing", "Rhyhorn", "Rhydon", "Chansey", "Tangela", "Kangaskhan", "Horsea", "Seadra", "Goldeen", "Seaking", "Staryu", "Starmie", "Mr. Mime", "Scyther", "Jynx", "Electabuzz", "Magmar",
            "Pinsir", "Tauros", "Magikarp", "Gyarados", "Lapras", "Ditto", "Eevee", "Vaporeon", "Jolteon", "Flareon", "Porygon", "Omanyte", "Omastar", "Kabuto", "Kabutops", "Aerodactyl",
            "Snorlax", "Articuno", "Zapdos", "Moltres", "Dratini", "Dragonair", "Dragonite", "Mewtwo", "Mew", "Chikorita", "Bayleef", "Meganium", "Cyndaquil", "Quilava", "Typhlosion",
            "Totodile", "Croconaw", "Feraligatr", "Sentret", "Furret", "Hoothoot", "Noctowl", "Ledyba", "Ledian", "Spinarak", "Ariados", "Crobat", "Chinchou", "Lanturn", "Pichu",
            "Cleffa", "Igglybuff", "Togepi", "Togetic", "Natu", "Xatu", "Mareep", "Flaaffy", "Ampharos", "Bellossom", "Marill", "Azumarill", "Sudowoodo", "Politoed", "Hoppip", "Skiploom",
            "Jumpluff", "Aipom", "Sunkern", "Sunflora", "Yanma", "Wooper", "Quagsire", "Espeon", "Umbreon", "Murkrow", "Slowking", "Misdreavus", "Unown", "Wobbuffet", "Girafarig",
            "Pineco", "Forretress", "Dunsparce", "Gligar", "Steelix", "Snubbull", "Granbull", "Qwilfish", "Scizor", "Shuckle", "Heracross", "Sneasel", "Teddiursa", "Ursaring",
            "Slugma", "Magcargo", "Swinub", "Piloswine", "Corsola", "Remoraid", "Octillery", "Delibird", "Mantine", "Skarmory", "Houndour", "Houndoom", "Kingdra", "Phanpy", "Donphan", "Porygon2",
            "Stantler", "Smeargle", "Tyrogue", "Hitmontop", "Smoochum", "Elekid", "Magby", "Miltank", "Blissey", "Raikou", "Entei", "Suicune", "Larvitar", "Pupitar", "Tyranitar", "Lugia",
            "Ho-Oh", "Celebi", "Treecko", "Grovyle", "Sceptile", "Torchic", "Combusken", "Blaziken", "Mudkip", "Marshtomp", "Swampert", "Poochyena", "Mightyena", "Zigzagoon", "Linoone",
            "Wurmple", "Silcoon", "Beautifly", "Cascoon", "Dustox", "Lotad", "Lombre", "Ludicolo", "Seedot", "Nuzleaf", "Shiftry", "Taillow", "Swellow", "Wingull", "Pelipper", "Ralts",
            "Kirlia", "Gardevoir", "Surskit", "Masquerain", "Shroomish", "Breloom", "Slakoth", "Vigoroth", "Slaking", "Nincada", "Ninjask", "Shedinja", "Whismur", "Loudred", "Exploud",
            "Makuhita", "Hariyama", "Azurill", "Nosepass", "Skitty", "Delcatty", "Sableye", "Mawile", "Aron", "Lairon", "Aggron", "Meditite", "Medicham", "Electrike", "Manectric", "Plusle", "Minun",
            "Volbeat", "Illumise", "Roselia", "Gulpin", "Swalot", "Carvanha", "Sharpedo", "Wailmer", "Wailord", "Numel", "Camerupt", "Torkoal", "Spoink", "Grumpig", "Spinda", "Trapinch", "Vibrava", "Flygon",
            "Cacnea", "Cacturne", "Swablu", "Altaria", "Zangoose", "Seviper", "Lunatone", "Solrock", "Barboach", "Whiscash", "Corphish", "Crawdaunt", "Baltoy", "Claydol", "Lileep", "Cradily",
            "Anorith", "Armaldo", "Feebas", "Milotic", "Castform", "Kecleon", "Shuppet", "Banette", "Duskull", "Dusclops", "Tropius", "Chimecho", "Absol", "Wynaut", "Snorunt", "Glalie",
            "Spheal", "Sealeo", "Walrein", "Clamperl", "Huntail", "Gorebyss", "Relicanth", "Luvdisc", "Bagon", "Shelgon", "Salamence", "Beldum", "Metang", "Metagross", "Regirock", "Regice",
            "Registeel", "Latias", "Latios", "Kyogre", "Groudon", "Rayquaza", "Jirachi", "Deoxys", "Turtwig", "Grotle", "Torterra", "Chimchar", "Monferno", "Infernape", "Piplup",
            "Prinplup", "Empoleon", "Starly", "Staravia", "Staraptor", "Bidoof", "Bibarel", "Kricketot", "Kricketune", "Shinx", "Luxio", "Luxray", "Budew", "Roserade", "Cranidos", "Rampardos",
            "Shieldon", "Bastiodon", "Burmy", "Wormadam", "Mothim", "Combee", "Vespiquen", "Pachirisu", "Buizel", "Floatzel", "Cherubi", "Cherrim", "Shellos", "Gastrodon", "Ambipom", "Drifloon",
            "Drifblim", "Buneary", "Lopunny", "Mismagius", "Honchkrow", "Glameow", "Purugly", "Chingling", "Stunky", "Skuntank", "Bronzor", "Bronzong", "Bonsly", "Mime Jr.", "Happiny",
            "Chatot", "Spiritomb", "Gible", "Gabite", "Garchomp", "Munchlax", "Riolu", "Lucario", "Hippopotas", "Hippowdon", "Skorupi", "Drapion", "Croagunk", "Toxicroak", "Carnivine", "Finneon", "Lumineon",
            "Mantyke", "Snover", "Abomasnow", "Weavile", "Magnezone", "Lickilicky", "Rhyperior", "Tangrowth", "Electivire", "Magmortar", "Togekiss", "Yanmega", "Leafeon", "Glaceon", "Gliscor",
            "Mamoswine", "Porygon-Z", "Gallade", "Probopass", "Dusknoir", "Froslass", "Rotom", "Uxie", "Mesprit", "Azelf", "Dialga", "Palkia", "Heatran", "Regigigas", "Giratina", "Cresselia",
            "Phione", "Manaphy", "Darkrai", "Shaymin", "Arceus"};
    ArrayList<String> Pokemon;
    Scanner scanner;
    CountingBloomFilter CBF ;

    public Pokedex(){
        Pokemon = new ArrayList<String>();
        scanner = new Scanner(System.in);
        CBF = new CountingBloomFilter(1000,10);
    }

    public void randomPokemonEncounter(){
        Random rand = new Random();
        int n = rand.nextInt(list.length);
        String input;
        System.out.println("A wild " + list[n] + " appeared!");
        Pokemon.add(list[n]);
        System.out.println("Do you want to catch it? (y/n)");
        input = scanner.nextLine();
        if(input.equals("y")){
            System.out.println("You got it!");
            System.out.println(list[n] + " added to the list!");
            CBF.insert(list[n]);
        }
        else{
            System.out.println("You let the pokemon get away!");
        }
    }

    public void doIownPokemon(String pokemon){
        if(CBF.verify(pokemon)==true)
            System.out.println("you already own this pokemon!");
        else{
            System.out.println("you don't own this pokemon yet keep looking!");
        }
    }

    public void letPokemonGoFree(String pokemon){
        if(CBF.verify(pokemon)==true) {
            CBF.remove(pokemon);
            System.out.println(pokemon + "runs happily into the wilderness");
        }
        else {
            System.out.println("You don't own" + pokemon + " !");
        }
    }
}
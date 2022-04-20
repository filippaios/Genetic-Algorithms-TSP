import java.util.*;

public class SalesmanGenome implements Comparable {
    List<Integer> genome;
    int[][] travelPrices;
    int startingCity;
    int numberOfCities = 0;
    int fitness;

    public SalesmanGenome(int numberOfCities, int[][] travelPrices, int startingCity){
        this.travelPrices = travelPrices;
        this.startingCity = startingCity;
        this.numberOfCities = numberOfCities;
        genome = randomSalesman(); //random 4 σημεια ενδιαμεσα
        fitness = this.calculateFitness(); //παει να υπολογισει το κοστος τους
    }

    public SalesmanGenome(List<Integer> permutationOfCities, int numberOfCities, int[][] travelPrices, int startingCity){
        genome = permutationOfCities;
        this.travelPrices = travelPrices;
        this.startingCity = startingCity;
        this.numberOfCities = numberOfCities;
        fitness = this.calculateFitness();
    }
//ΥΠΟΛΟΓΙΣΜΟΣ ΚΟΣΤΟΥΣ
    public int calculateFitness(){
        int fitness = 0;
        int currentCity = startingCity;
        for ( int gene : genome) {
            fitness += travelPrices[currentCity][gene]; //σε κάθε επαναληψη προχωραει 1 στοιχειο στον πινακα και υπολογιζει κοστος
            currentCity = gene;
        }
        fitness += travelPrices[genome.get(numberOfCities-2)][startingCity]; //προσθετει την τελευταια διαδρομη για να φτασει ξανα στην αρχη
        return fitness;
    }
    //ΤΥΧΑΙΑ ΤΟΠΟΘΕΤΗΣΗ ΣΗΜΕΙΩΝ
    private List<Integer> randomSalesman(){ //βαζει τυχαια σειρα τα ενδιαμεσα σημεια
        List<Integer> result = new ArrayList<Integer>();
        for(int i=0; i<numberOfCities; i++) {
            if(i!=startingCity)
                result.add(i);
        }
        Collections.shuffle(result);
        return result;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public int getStartingCity() {
        return startingCity;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Path: ");
        sb.append(startingCity);
        for ( int gene: genome ) {
            sb.append(" ");
            sb.append(gene);
        }
        sb.append(" ");
        sb.append(startingCity);
        sb.append("\nLength: ");
        sb.append(this.fitness);
        return sb.toString();
    }


    @Override
    public int compareTo(Object o) {
        SalesmanGenome genome = (SalesmanGenome) o;
        if(this.fitness > genome.getFitness())
            return 1;
        else if(this.fitness < genome.getFitness())
            return -1;
        else
            return 0;
    }
}

import java.util.*;

public class Main {
    public static void printTravelPrices(int[][] travelPrices, int numberOfCities){
        for(int i = 0; i<numberOfCities; i++){
            for(int j=0; j<numberOfCities; j++){
                System.out.print(travelPrices[i][j]);               
                 System.out.print("  ");               
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        int numberOfCities = 5;
        
        int[][] travelPrices= {  {0,4,4,7,3}, {4,0,2,3,5}, {4,2,0,2,3}, {7,3,2,0,6}, {3,5,3,6,0}  };
        

        printTravelPrices(travelPrices,numberOfCities);

        Random rn = new Random();
        int city = rn.nextInt(5);
        
        //random αρχικο σημειο-πόλη
        Salesmench geneticAlgorithm = new Salesmench(numberOfCities, travelPrices, city, city);
        SalesmanGenome result = geneticAlgorithm.optimize(); 
        System.out.println(result);

    }
}


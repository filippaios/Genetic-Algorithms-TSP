import java.util.*;

public class Main {
    public static void print_kostoi(int[][] travelPrices, int shmeia){
        for(int i = 0; i<shmeia; i++){
            for(int j=0; j<shmeia; j++){
                System.out.print(travelPrices[i][j]);               
                 System.out.print("  ");               
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        int arithmos_shmeiwn = 5;
        
        int[][] kostoi= {  {0,4,4,7,3}, {4,0,2,3,5}, {4,2,0,2,3}, {7,3,2,0,6}, {3,5,3,6,0}  };
        

        print_kostoi(kostoi,arithmos_shmeiwn);

        Random rn = new Random();
        int point = rn.nextInt(5);
        
        
        Salesmench geneticAlgorithm = new Salesmench(arithmos_shmeiwn, kostoi, point, point);
        SalesmanGenome result = geneticAlgorithm.optimize(); 
        System.out.println(result);

    }
}


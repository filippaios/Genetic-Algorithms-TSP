import java.util.*;

public class Main {   

    public static void main(String[] args) {
        int arithmos_shmeiwn = 5;
        
        int[][] kostoi= {  {0,4,4,7,3}, {4,0,2,3,5}, {4,2,0,2,3}, {7,3,2,0,6}, {3,5,3,6,0}  };        

        emfanish_kostwn(kostoi,arithmos_shmeiwn);

        Random rn = new Random();
        int point = rn.nextInt(5);
        
        
        Salesman  algorithmos = new Salesman (arithmos_shmeiwn, kostoi, point, 15);
        SalesmanGenome result = algorithmos.veltistopoihsh(); 
        System.out.println(result);
    }
    
    public static void emfanish_kostwn(int[][] travelPrices, int shmeia){
        for(int i = 0; i<shmeia; i++){
            for(int j=0; j<shmeia; j++){
                System.out.print(travelPrices[i][j]);               
                 System.out.print("  ");               
            }
            System.out.println("");
        }
    }
}


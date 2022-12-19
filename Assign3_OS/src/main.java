import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Pair{
    String name;
    int size;
}

public class main {
    public static void main(String[] args) {
        ArrayList<Pair>partitions=new ArrayList<>();
        ArrayList<Pair>processes=new ArrayList<>();
        //Fill Partitions
//        System.out.println("Enter number of partitions:");
        int partitionNumber , partitionSize;
        String partitionName;
        Scanner sc = new Scanner(System.in);
        partitionNumber = sc.nextInt();
        for (int i=0 ; i<partitionNumber ; i++)
        {
//            System.out.println("Partition name and its size.");
            Pair partition = new Pair();
            partition.name = sc.next();
            partition.size = sc.nextInt();
            partitions.add(partition);
//            partitionsMap.put(partition , "");
        }
        //Fill processes
//        System.out.println("Enter number of partition:");
        int processesNumber , processSize;
        String processName;
        processesNumber = sc.nextInt();
        for (int i=0 ; i<processesNumber ; i++)
        {
//            System.out.println("Process name and its size.");
            Pair process = new Pair();
            process.name = sc.next();
            process.size = sc.nextInt();
            processes.add(process);
        }
        System.out.println("Select the policy you want to apply:");
        System.out.println("1. First fit");
        System.out.println("2. Worst fit");
        System.out.println("3. Best fit");
        int selection;
        selection = sc.nextInt();
        if(selection==1) //First Fit
        {
            FirstFit firstFit = new FirstFit();
            firstFit.firstFit(partitions , processes);
        }
        else if (selection == 2){
            WorstFit worstFit = new WorstFit();
            worstFit.worstFit(partitions , processes);
        }
        else if(selection == 3){
            BestFit bestFit = new BestFit();
            bestFit.bestFit(partitions , processes);
        }
    }
}

/*
6
partition0 90
partition1 20
partition2 5
partition3 30
partition4 120
partition5 80
4
process1 15
process2 90
process3 30
process4 100
 */

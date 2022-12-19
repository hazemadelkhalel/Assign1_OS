import java.util.ArrayList;
import java.util.HashMap;

public class WorstFit {
    //    ArrayList<String>allocation; //hold process names that are allocated
    HashMap<Pair , String> allocation;
    WorstFit(){
        allocation = new HashMap<>();
    }
    void worstFit(ArrayList<Pair> partitions , ArrayList<Pair> processes){
        int lastIndex = partitions.size();
        for(int i=0 ; i<partitions.size() ; i++){
            allocation.put(partitions.get(i) , "");
        }
        for(int i=0 ; i<processes.size() ; i++){
            Pair maxSuitablePartition = new Pair();
            maxSuitablePartition.size = 0;
            int indexMaxPartition=0;
            String processName = processes.get(i).name;
            int processSize = processes.get(i).size;
            int j;
            for(j=0 ; j<partitions.size() ; j++){
                if(j == partitions.size()-1 && processSize>partitions.get(j).size){
                    processes.get(i).size = -1; //can not be allocated
                }
                else if(partitions.get(j).size >= processSize && partitions.get(j).size > maxSuitablePartition.size && allocation.get( partitions.get(j) ) == "" ){
                    maxSuitablePartition = partitions.get(j);
                    indexMaxPartition = j;
                }
            }
            if(maxSuitablePartition.size == processSize){
                allocation.put(partitions.get(indexMaxPartition) , processName);
                processes.get(i).size = 1; // indication that process is allocated
            }
            else if(maxSuitablePartition.size > processSize){
                int remain = partitions.get(indexMaxPartition).size - processSize;
                partitions.get(indexMaxPartition).size = processSize;
//                    allocation.add(j , processName);
                allocation.put(partitions.get(indexMaxPartition) , processName);
                //make a new partition
                Pair partition = new Pair();
                partition.name = "partition"+lastIndex;
                partition.size = remain;
                lastIndex++; //3shan ely gyy b3d kda
                partitions.add(indexMaxPartition+1 , partition);
                allocation.put(partition , "");
                processes.get(i).size = 1; // indication that process is allocated
            }

        }

        //printing
        for(int i=0 ; i<partitions.size() ; i++){
            if( allocation.get( partitions.get(i) )  != "")
                System.out.println(partitions.get(i).name + '(' + partitions.get(i).size + " KB) => " + allocation.get( partitions.get(i) )  );
            else{
                System.out.println(partitions.get(i).name + '(' + partitions.get(i).size + " KB) => External Fragment");
            }
        }
        System.out.println();
        for(int i=0 ;i<processes.size() ; i++){
            if(processes.get(i).size == -1){
                System.out.println(processes.get(i).name + " can not be allocated.");
            }
        }

    }
}

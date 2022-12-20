import java.util.ArrayList;
import java.util.HashMap;

public class WorstFit {
    //    ArrayList<String>allocation; //hold process names that are allocated
    HashMap<Pair , String> allocation;
    WorstFit(){
        allocation = new HashMap<>();
    }
    void worstFit(ArrayList<Pair> partitions , ArrayList<Pair> processes){
        int lastName = partitions.size();
        for(int i = 0; i < processes.size(); i++){
            String processName = processes.get(i).name;
            int processSize = processes.get(i).size;
            Pair maxSuitablePartition = null;
            int indexMaxPartition = -1;
            for(int j = 0; j < partitions.size(); j++){
                if (allocation.containsKey(partitions.get(j)) || processSize > partitions.get(j).size)continue;
                if(maxSuitablePartition != null && partitions.get(j).size <= maxSuitablePartition.size)continue;
                maxSuitablePartition = partitions.get(j);
                indexMaxPartition = j;
            }
            //can not be allocated
            if(maxSuitablePartition == null){
                processes.get(i).size = -1;
                continue;
            }
            allocation.put(partitions.get(indexMaxPartition), processName);
            int remain = partitions.get(indexMaxPartition).size - processSize;
            partitions.get(indexMaxPartition).size = processSize;
            if(remain == 0)continue;
            Pair partition = new Pair();
            partition.name = "Partition-" + lastName++;
            partition.size = remain;
            partitions.add(indexMaxPartition + 1, partition);
        }

        //printing
        for(int i=0 ; i<partitions.size() ; i++){
            if (allocation.containsKey(partitions.get(i))) //process inside
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

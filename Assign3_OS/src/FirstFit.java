import java.util.ArrayList;
import java.util.HashMap;

public class FirstFit {
    //    ArrayList<String>allocation; //hold process names that are allocated
    HashMap<Pair, String> allocation;

    FirstFit() {
        allocation = new HashMap<>();
    }
    void extend(){

    }

    void firstFit(ArrayList<Pair> partitions, ArrayList<Pair> processes) {
        int lastName = partitions.size();
        for(int i = 0; i < processes.size(); i++){
            String processName = processes.get(i).name;
            int processSize = processes.get(i).size;
            boolean isAllocated = false;
            for(int j = 0; j < partitions.size(); j++){
                if (allocation.containsKey(partitions.get(j)) || processSize > partitions.get(j).size)continue;
                allocation.put(partitions.get(j), processName);
                int remain = partitions.get(j).size - processSize;
                partitions.get(j).size = processSize;
                if(remain == 0)continue;
                Pair partition = new Pair();
                partition.name = "Partition-" + lastName++;
                partition.size = remain;
                partitions.add(j + 1, partition);
                isAllocated = true;
                break;
            }
            if(!isAllocated){
                //can not be allocated
                processes.get(i).size = -1;
            }
        }

        //printing
        for (int i = 0; i < partitions.size(); i++) {
            if (allocation.containsKey(partitions.get(i))) //process inside
                System.out.println(partitions.get(i).name + " (" + partitions.get(i).size + " KB) => " + allocation.get(partitions.get(i)));
            else {
                System.out.println(partitions.get(i).name + " (" + partitions.get(i).size + " KB) => External Fragment");
            }
        }
        System.out.println();
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).size == -1) {
                System.out.println(processes.get(i).name + " can not be allocated.");
            }
        }

    }
}

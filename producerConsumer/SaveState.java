package producerConsumer;

import factoryMachines.MachineShape;
import factoryMachines.QueueShape;

import java.util.ArrayList;
import java.util.LinkedList;

public class SaveState {
    private LinkedList<QueueShape> queues;
    private LinkedList<MachineShape> machines;
    private int[] time;
    private ArrayList<Product> products;
    private int timeRate;

    public int[] getTime() {
        return time;
    }

    public void setTime(int[] time) {
        this.time = time;
    }

    public LinkedList<QueueShape> getQueues() {
        return queues;
    }

    public void setQueues(LinkedList<QueueShape> queues) {
        this.queues = queues;
    }

    public LinkedList<MachineShape> getMachines() {
        return machines;
    }

    public void setMachines(LinkedList<MachineShape> machines) {
        this.machines = machines;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getTimeRate() {
        return timeRate;
    }

    public void setTimeRate(int timeRate) {
        this.timeRate = timeRate;
    }
}

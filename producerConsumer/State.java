package producerConsumer;

import java.util.ArrayList;

public class State {
    private ArrayList<LineQueue> queues;
    private ArrayList<Machine> machines;
    private ArrayList<Product> products;
    private int timeRate;

    public ArrayList<LineQueue> getQueues() {
        return queues;
    }

    public void setQueues(ArrayList<LineQueue> queues) {
        this.queues = queues;
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public void setMachines(ArrayList<Machine> machines) {
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

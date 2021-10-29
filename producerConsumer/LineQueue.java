package producerConsumer;

import factoryMachines.QueueShape;
import gui.Controller;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class LineQueue implements IProducer {
    private int id;
    private Queue<Product> products;
    private Deque<Machine> attachedMachines;
    private QueueShape queueShape;
    private Controller controller;



    public LineQueue(QueueShape queueShape) {
        this.queueShape = queueShape;
        this.id = queueShape.getId();
        this.attachedMachines = new ArrayDeque<Machine>();
        this.products = new LinkedList<Product>();
    }
    public QueueShape getQueueShape() {
        return queueShape;
    }

    public synchronized Queue<Product> getProducts() {
        return products;
    }

    public synchronized Product getProduct(){
        if(!products.isEmpty()){
            return products.remove();
        }else {
            return null;
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public void addAttachedMachine(Machine attachedMachine) {
        this.attachedMachines.add(attachedMachine);
    }

    public  void updateMachines(Machine machine){
        this.attachedMachines.remove(machine);
        if(machine.ready){
            this.attachedMachines.addFirst(machine);
        }else{
            this.attachedMachines.addLast(machine);
        }
    }

    public synchronized void addProductToQueue(Product product) {
        this.products.add(product);
        if( id == 0 ){
            QueueShape newShape = queueShape.clone();
            newShape.setProductsInQ(getProductsNumber());
            notifyUI( newShape );
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(product.getId() + " " + product.getColorName() + " added to " + "Q" + id);
        System.out.println(products.size() + " products in " + "Q" + id);
        if( this.attachedMachines.size() != 0 ){
            Machine machine = this.attachedMachines.peek();
            System.out.println("M" + machine.getId() +" on peak " + machine.ready);
            if( machine.ready ){
                produceProduct(machine);
                System.out.println(product.getId() + " " + product.getColorName() + " added to " + "M" + machine.getId());
            }
        }
    }



    public void produceProduct(Machine machine){
        machine.addProductToMachine(this.products.remove(),this);
    }

    public int getProductsNumber(){
        return products.size();
    }

    private void notifyUI( QueueShape newShape){
        controller.updateInitialQueue(newShape);
    }

}

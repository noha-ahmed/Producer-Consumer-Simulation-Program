package producerConsumer;

import factoryMachines.MachineShape;
import factoryMachines.QueueShape;
import gui.Controller;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Random;

public class Machine implements IConsumer{
    private int id;
    private LinkedList<LineQueue> inputQueues;
    private LineQueue outputQueue;
    private String color;
    private int serviceTime;
    boolean ready = true;
    private volatile Product currentProduct;
    private MachineShape machineShape;
    private Thread thread;
    private Controller controller;
    private volatile boolean run = true;

    public Machine(MachineShape machineShape) {
        this.machineShape = machineShape;
        this.id = machineShape.getId();
        Random rand = new Random();
        this.setServiceTime((rand.nextInt(12)+3)*1000);
        this.inputQueues = new LinkedList<LineQueue>();
        thread = new Thread( this , "M" +id);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public MachineShape getMachineShape() {
        return machineShape;
    }



    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int time) {
        this.serviceTime = time;
    }

    public int getId() {
        return id;
    }

    public LinkedList<LineQueue> getInputQueues() {
        return inputQueues;
    }

    public void addInputQueue( LineQueue q ){
        this.inputQueues.add(q);
    }

    public LineQueue getOutputQueue() {
        return outputQueue;
    }

    public void setOutputQueue(LineQueue outputQueue) {
        this.outputQueue = outputQueue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public synchronized void addProductToMachine(Product currentProduct , LineQueue queue ) {
        this.currentProduct = currentProduct;
        this.fromQueueToMachineUpdate(queue);
        this.setBusyState();
        notify();
    }

    public void start(){
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //   this.thread.setDaemon(true);
        this.thread.start();
        System.out.println(thread.getName() + " started");
        System.out.println("machine time : " +  this.getServiceTime());
    }


    public void run() {
        synchronized (this){
            while(!this.thread.isInterrupted()){
                while( this.currentProduct == null ){
                    try { wait(); } catch (InterruptedException e) { break;
                    }
                }
                if(!this.run){
                    break;
                }
                this.consumeProduct();
                this.passProductToQueue();
                this.checkWaitingProducts();
                System.out.println(this.getColor() + " : "+ thread.getName());
            }
        }

    }

    public synchronized void shut(){
        synchronized (this.thread){
            this.run=false;
            System.out.println(this.thread.getName()+" is terminated");
            this.thread.interrupt();
            //this.thread.notify();
        }
    }

    private void checkWaitingProducts(){
        Boolean productFound = false;
        for(int i=0; i<inputQueues.size(); i++){
            LineQueue queue = inputQueues.get(i);
            Product product = queue.getProduct();
            if( product!=null ){
                //System.out.println( queue.getProducts().size() + " products waiting in Q" + queue.getId() );
                this.currentProduct = product;
                this.setColor(currentProduct.getColorName());
                this.fromQueueToMachineUpdate(queue);
                productFound = true;
                break;
            }
        }
        if( !productFound ){
            this.setReadyState();
        }
    }

    private void setBusyState(){
        this.ready=false;
        this.notifyInputQueues();
        this.setColor(currentProduct.getColorName());
    }

    public void consumeProduct(){
        System.out.println(this.getColor() + " processed in " + thread.getName());
        try{this.thread.sleep(this.getServiceTime());}catch (Exception e){}
    }

    private void setReadyState(){
        this.ready = true;
        this.setColor("White");
        this.notifyInputQueues();
    }


    private void passProductToQueue(){
        this.getOutputQueue().addProductToQueue(this.currentProduct);
        this.fromMachineToQueueUpdate(outputQueue);
        currentProduct = null;
    }

    private void notifyInputQueues(){
        for(int i=0; i<inputQueues.size(); i++){
            inputQueues.get(i).updateMachines(this);
        }
    }

    private void fromQueueToMachineUpdate(LineQueue queue){
        QueueShape newQueueShape = queue.getQueueShape().clone();
        newQueueShape.setProductsInQ(queue.getProductsNumber());
        MachineShape newMachineShape = machineShape.clone();
        newMachineShape.setInUse(true,currentProduct.getColor());
        notifyUI(newQueueShape , newMachineShape);
    }

    private void fromMachineToQueueUpdate( LineQueue queue ){
        QueueShape newQueueShape = queue.getQueueShape().clone();
        newQueueShape.setProductsInQ(queue.getProductsNumber());
        MachineShape newMachineShape = machineShape.clone();
        newMachineShape.setInUse(false,Color.FORESTGREEN);
        notifyUI(newQueueShape , newMachineShape);

    }

    private void notifyUI(QueueShape queue , MachineShape machine){
        this.controller.updateShapes( queue , machine );
    }


}

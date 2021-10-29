package producerConsumer;


import factoryMachines.MachineShape;
import factoryMachines.QueueShape;
import gui.*;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import javax.crypto.Mac;
import java.util.*;

public class AssemblyLineManager implements IAssemblyLineManager{
    private static AssemblyLineManager managerInstance = new AssemblyLineManager();
    private Random random = new Random();
    private ArrayList<Product> finishedProducts = new ArrayList<Product>();
    private State currentState = new State();
    private SaveState savedState = new SaveState();
    private SaveState runState;
    private Controller controller ;
    private int productsNumber;
    private Originator originator;
    private CareTaker careTaker;
    private boolean replay;
    private LineQueue lastQueue;

    private AssemblyLineManager(){}

    public static AssemblyLineManager getManagerInstance(){
        return managerInstance;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }


    @Override
    public void setProductsNumber(int productsNumber) {
        this.productsNumber = productsNumber;
    }

    @Override
    public void constructAssemblyLineComponents(LinkedList<QueueShape> queueShapes, LinkedList<MachineShape> machineShapes, boolean replay) {
        this.replay=replay;
        if(!replay){
            originator = new Originator();
            careTaker = new CareTaker();
        }
        savedState.setMachines(machineShapes);
        savedState.setQueues(queueShapes);
        ArrayList<LineQueue> queues = new ArrayList<LineQueue>();
        ArrayList<Machine> machines = new ArrayList<Machine>();
        //instantiate queues and machines
        for( int i = 0 ; i < queueShapes.size() ; i++ ){
            if(queueShapes.get(i).isValidShape()){
                LineQueue lineQueue = new LineQueue(queueShapes.get(i));
                lineQueue.setController(controller);
                queues.add(lineQueue);
            }
        }
        for( int i = 0 ; i < machineShapes.size() ; i++ ){
            if(machineShapes.get(i).isValidShape()){
                Machine machine = new Machine(machineShapes.get(i));
                machine.setController(this.controller);
                machines.add(machine);
            }
        }

        //add attached machines (output machines) to queues
        for( int i = 0 ; i < queues.size() ; i++ ){
            LineQueue lineQueue = queues.get(i);
            LinkedList<Integer> outputMachinesIndex = lineQueue.getQueueShape().getOutputMachine();
            if(outputMachinesIndex.isEmpty()){
                lastQueue = queues.get(i);
            }
            for ( int j = 0 ; j < outputMachinesIndex.size() ; j++ ){
                int machineIndex = outputMachinesIndex.get(j);
                lineQueue.addAttachedMachine(machines.get(machineIndex));
            }
        }

        //add attached queues (output queue and input queues) to machine
        for( int i = 0 ; i < machines.size() ; i++ ){
            Machine machine = machines.get(i);
            MachineShape machineShape = machine.getMachineShape();
            LinkedList<Integer> outputQueueIndex = machineShape.getOutputQueue();
            LinkedList<Integer> inputQueuesIndex = machineShape.getInputQueue();
            machine.setOutputQueue( queues.get(outputQueueIndex.get(0)) );
            for ( int j = 0 ; j < inputQueuesIndex.size() ; j++ ){
                int queueIndex = inputQueuesIndex.get(j);
                machine.addInputQueue(queues.get(queueIndex));
            }
        }
        currentState.setMachines(machines);
        currentState.setQueues(queues);
        System.out.println(queues.size());
        System.out.println(machines.size());
    }

    @Override
    public void startSimulation() {
        this.generateRandomProducts();
        this.runAssemblyLine();
        originator.setState(savedState);
        careTaker.add(originator.saveStateToMemento());
        this.inputProductsToQueue();
        this.waitSimulation();
    }

    @Override
    public void replaySimulation() {
        originator.getStateFromMemento(careTaker.get(0));
        runState = originator.getState();
        this.constructAssemblyLineComponents(runState.getQueues(), runState.getMachines(), true);
        this.runAssemblyLine();
        this.inputProductsToQueue();
        this.waitSimulation();
    }

    private void runAssemblyLine(){
        ArrayList<LineQueue> queues = currentState.getQueues();
        ArrayList<Machine> machines = currentState.getMachines();
        int[] time;
        if(replay){
            time = runState.getTime();
        }else{
            time = new int[machines.size()];
        }
        for( int i = 0 ; i < machines.size() ; i++ ){
            if(this.replay){
                machines.get(i).setServiceTime(time[i]);
            }else{
                time[i] = machines.get(i).getServiceTime();
            }
            machines.get(i).start();
        }
        savedState.setTime(time);
    }

    private void generateRandomProducts() {
        // int inputRate = random.nextInt(10)+2;
        int inputRate = 3;
        currentState.setTimeRate(inputRate);
        savedState.setTimeRate(inputRate);
        Color[] colors = {Color.RED, Color.LAVENDER, Color.BLUE, Color.CYAN,
                Color.MAGENTA, Color.PINK, Color.BLACK, Color.VIOLET};
        String[] colorNames = {"Red", "Lavender", "Blue", "Cyan",
                "Magenta", "Pink", "Black", "Violet"};
        ArrayList<Product> products = new ArrayList<Product>();
        int colorIndex;
        Product product;
        for( int i = 0 ; i < productsNumber ; i++ ){
            colorIndex = random.nextInt(colors.length);
            product = new Product(i,colorNames[colorIndex],colors[colorIndex]);
            products.add(product);
        }
        controller.updateInitialProducts(products);
        currentState.setProducts(products);
        savedState.setProducts(products);
    }

    // a function to input the products inside the input queue (and display them in the list) at the specified input rate
    private void inputProductsToQueue() {
            ArrayList<Product> products = currentState.getProducts();
            int inputRate = currentState.getTimeRate();

        LineQueue inputQueue = currentState.getQueues().get(0);

        Runnable inputProductsRun = () -> {
            for( int i = 0 ; i < products.size() ; i++ ) {
                Product product = products.get(i);
                System.out.println(product.getId() + "" + product.getColorName());
                inputQueue.addProductToQueue(product);
                try {
                    //  controller.updateInitialProducts(product);
                    Thread.currentThread().sleep(inputRate*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread inputThread = new Thread(inputProductsRun,"input thread");
        inputThread.start();

    }

    private void waitSimulation(){
        Runnable waitSimulationRun = () -> {
            while (lastQueue.getProducts().size() < productsNumber){
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            terminate(currentState.getMachines());
        };
        Thread waitSimulationThread = new Thread(waitSimulationRun,"waiting thread");
        waitSimulationThread.start();

    }



    private   void terminate( List<Machine> machines){
        for(int i=0; i<machines.size(); i++){
            machines.get(i).shut();
        }
        controller.endSimulation();
    }






}




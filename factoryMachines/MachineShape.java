package factoryMachines;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class MachineShape implements Shapes , Cloneable {
    private final String shapeKind = "circle";
    private double x_axis;
    private double y_axis;
    private final int id;
    private int globalId;
    private Color colorOfProduct;
    private Boolean inUse = false;
    private LinkedList<Integer> inputQueue = new LinkedList<Integer>();
    private LinkedList<Integer> outputQueue = new LinkedList<Integer>();

    public MachineShape(int globalId, int id, double x, double y) {
        this.globalId = globalId;
        this.id = id;
        this.x_axis = x;
        this.y_axis = y;
    }

    public String getShapeKind() {
        return shapeKind;
    }

    public int getGlobalId() {
        return globalId;
    }

    public void setGlobalId(int globalId) {
        this.globalId = globalId;
    }

    public void setColorOfProduct(Color colorOfProduct) {
        this.colorOfProduct = colorOfProduct;
    }

    public double getX_axis() {
        return x_axis;
    }

    public void setX_axis(double x_axis) {
        this.x_axis = x_axis;
    }

    public void setOutputQueue(LinkedList<Integer> outputQueue) {
        this.outputQueue = outputQueue;
    }

    public void setInputQueue(LinkedList<Integer> inputQueue) {
        this.inputQueue = inputQueue;
    }

    public double getY_axis() {
        return y_axis;
    }

    public void setY_axis(double y_axis) {
        this.y_axis = y_axis;
    }

    public int getId() {
        return id;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse, Color color) {
        this.inUse = inUse;
        this.colorOfProduct = color;
    }

    public void addInputQueue(int q_ID) {
        this.inputQueue.add(q_ID);
    }


    public void addOutputQueue(int q_ID){
        this.outputQueue.add(q_ID);
    }

    public LinkedList<Integer> getInputQueue(){
        return  this.inputQueue;
    }
    public LinkedList<Integer> getOutputQueue(){
        return  this.outputQueue;
    }

    @Override
    public void draw(GraphicsContext ctx) {
        if (!inUse) {
            ctx.setStroke(Color.FORESTGREEN.darker().darker());
            ctx.setFill(Color.FORESTGREEN);

        } else {
            ctx.setStroke(this.colorOfProduct.darker().darker());
            ctx.setFill(this.colorOfProduct);
        }
        ctx.strokeOval(this.x_axis, this.y_axis, 50, 50);
        ctx.fillOval(this.x_axis, this.y_axis, 50, 50);
        ctx.setFill(Color.WHITE);
        ctx.fillText("M" + this.id, this.x_axis + 16, this.y_axis + 30);

    }

    @Override
    public boolean containsPoint(double x, double y) {
        return this.x_axis <= x && x <= this.x_axis + 50 && y >= this.y_axis && y <= this.y_axis + 50;
    }

    public boolean isValidShape(){
        if(this.inputQueue.isEmpty() && this.outputQueue.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public MachineShape clone()  {
        MachineShape newMachine = new MachineShape(globalId,id,x_axis,y_axis);
        newMachine.setInputQueue(inputQueue);
        newMachine.setOutputQueue(outputQueue);
        return newMachine;
    }
}

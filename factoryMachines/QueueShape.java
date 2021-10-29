package factoryMachines;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class QueueShape implements Shapes , Cloneable {
    private final String shapeKind = "rectangle";
    private double x_axis;
    private double y_axis;
    private final int id;
    private int globalId;
    private int productsInQ = 0;
    private LinkedList<Integer> inputMachine = new LinkedList<Integer>();
    private LinkedList<Integer> outputMachine = new LinkedList<Integer>();

    public QueueShape(int globalId, int id, double x, double y) {
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

    public int getId() {
        return id;
    }


    public int getProductsInQ() {
        return productsInQ;
    }

    public void setProductsInQ(int machinesInQ) {
        this.productsInQ = machinesInQ;
    }

    public double getY_axis() {
        return y_axis;
    }

    public void setY_axis(double y_axis) {
        this.y_axis = y_axis;
    }

    public double getX_axis() {
        return x_axis;
    }

    public void setX_axis(double x_axis) {
        this.x_axis = x_axis;
    }

    public void setInputMachine(LinkedList<Integer> inputMachine) {
        this.inputMachine = inputMachine;
    }

    public void addInputMachine(int m_ID) {
        this.inputMachine.add(m_ID);
    }

    public void setOutputMachine(LinkedList<Integer> outputMachine) {
        this.outputMachine = outputMachine;
    }

    public void addOutputMachine(int m_ID){
        this.outputMachine.add(m_ID);
    }

    public LinkedList<Integer> getInputMachine(){
        return  this.inputMachine;
    }
    public LinkedList<Integer> getOutputMachine(){
        return  this.outputMachine;
    }


    @Override
    public void draw(GraphicsContext ctx) {
        ctx.setFill(Color.YELLOW.darker());
        ctx.setStroke(Color.YELLOW.darker().darker().darker());
        ctx.fillRect(this.x_axis, this.y_axis, 80, 40);
        ctx.stroke();
        ctx.setFill(Color.WHITE.brighter().brighter().brighter().brighter());
        ctx.fillText("Q" + this.id, this.x_axis + 30, this.y_axis + 30);
        ctx.fillText("#M = " + this.productsInQ, this.x_axis, this.y_axis + 10);
    }

    @Override
    public boolean containsPoint(double x, double y) {
        return this.x_axis <= x && x <= this.x_axis + 80 && y >= this.y_axis && y <= this.y_axis + 40;
    }

    public boolean isValidShape(){
        if(this.inputMachine.isEmpty() && this.outputMachine.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public QueueShape clone()  {
        QueueShape newShape = new QueueShape( globalId , id , x_axis , y_axis );
        newShape.setInputMachine(inputMachine);
        newShape.setOutputMachine(outputMachine);
        return newShape;
    }
}

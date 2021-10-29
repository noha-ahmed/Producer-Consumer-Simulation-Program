package gui;

import factoryMachines.ArrowShape;
import factoryMachines.MachineShape;
import factoryMachines.QueueShape;
import factoryMachines.Shapes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import producerConsumer.*;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Controller {
    public AssemblyLineManager manager = AssemblyLineManager.getManagerInstance();
    public String shapeKind;
    public Color color;
    public int idQ = 0;
    public LinkedList<QueueShape> queuesList = new LinkedList<>();
    public int idM = 0;
    public LinkedList<MachineShape> machinesList = new LinkedList<>();
    public int globalId = 0;
    public LinkedList<Shapes> shapesList = new LinkedList<>();
    public LinkedList<Shapes> arrowsList = new LinkedList<>();

    public double prevLocationX;
    public double prevLocationY;
    public Shapes shapeDragged;
    public Shapes shape1Clicked;
    public Shapes shape2Clicked;

    //flags
    public boolean firstUsed = false;
    public boolean select = false;
    public boolean clickedShape = false;
    public boolean dragged = false;
    @FXML
    private Button circle;
    @FXML
    private Button rectangle;
    @FXML
    private Button edit;
    @FXML
    private Button arrow;
    @FXML
    private Button replay;
    @FXML
    private Button simulate;
    @FXML
    private Button reset;
    @FXML
    private Canvas canvas;
    private GraphicsContext ctx;
    @FXML
    private ListView<String>initialProducts;
    @FXML
    private TextField number;
    public Controller(){
        this.manager.setController(this);
    }


    public void selectShape() {
        select = true;
        clickedShape = false;
        dragged = false;
        firstUsed = false;
    }

    public void chooseShape(ActionEvent event) {
        if (event.getSource() == circle) {
            shapeKind = "circle";
        } else if (event.getSource() == rectangle) {
            shapeKind = "rectangle";
        }
        clickedShape = true;
        dragged = false;
        firstUsed = false;
        select = false;

    }

    public void mouseClicked(MouseEvent event) {
        double currentX = event.getX();  // x-coordinate of point where mouse was clicked
        double currentY = event.getY();  // y-coordinate of point
        for (int i = shapesList.size() - 1; i >= 0; i--) {  // check shapes from front to back
            Shapes shape = shapesList.get(i);
            if (shape.containsPoint(currentX, currentY)) {
                if (select) {
                    if (!firstUsed) {
                        shape1Clicked = shape;
                        firstUsed = true;
                    } else {
                        shape2Clicked = shape;
                        if (checkShapes(shape1Clicked, shape2Clicked)) {
                            Shapes arrow = new ArrowShape(globalId++,shape1Clicked, shape2Clicked);
                            shapesList.add(arrow);
                            arrowsList.add(arrow);
                            if(shape1Clicked.getShapeKind().equals("circle")){

                                ((MachineShape)shape1Clicked).addOutputQueue(((QueueShape)shape2Clicked).getId());
                                ((QueueShape)shape2Clicked).addInputMachine(((MachineShape)shape1Clicked).getId());

                            }
                            else if(shape1Clicked.getShapeKind().equals("rectangle")){
                                ((QueueShape)shape1Clicked).addOutputMachine(((MachineShape)shape2Clicked).getId());
                                ((MachineShape)shape2Clicked).addInputQueue(((QueueShape)shape1Clicked).getId());

                            }
                            arrow.draw(ctx);
                        }
                        firstUsed = false;
                        select = false;
                        shape1Clicked = null;
                        shape2Clicked = null;
                    }
                }

                return;
            }
        }
    }


    public void mousePressed(MouseEvent event) {
        ctx=canvas.getGraphicsContext2D();
        if (clickedShape) {
            clickedShape = false;
            if (shapeKind == "circle") {
                Shapes shape = new MachineShape(globalId++, idM++, event.getX(), event.getY());
                shapesList.add(shape);
                machinesList.add((MachineShape)shape);
                shape.draw(ctx);
            } else if (shapeKind == "rectangle") {
                Shapes shape = new QueueShape(globalId++, idQ++, event.getX(), event.getY());
                shapesList.add(shape);
                queuesList.add((QueueShape)shape);
                shape.draw(ctx);
            }
        } else if (!dragged && check()) {
            double currentX = event.getX();  // x-coordinate of point where mouse was clicked
            double currentY = event.getY();  // y-coordinate of point
            for (int i = shapesList.size() - 1; i >= 0; i--) {  // check shapes from front to back
                Shapes shape = shapesList.get(i);
                if (shape.containsPoint(currentX, currentY)) {
                    shapeDragged = shape;
                    dragged = true;
                    prevLocationX = currentX;
                    prevLocationY = currentY;
                    return;
                }
            }
        }
    }

    public boolean check() {
        return !select && !firstUsed && !clickedShape;
    }

    public void mouseMove(MouseEvent event) {
        double currentX = event.getX();  // x-coordinate of point where mouse was clicked
        double currentY = event.getY();  // y-coordinate of point
        if (dragged) {
            prevLocationX = currentX;
            prevLocationY = currentY;
            int index = findShape(shapeDragged);
            boolean validDrag=true;
            if (shapesList.get(index).getShapeKind().equals("circle")) {
                MachineShape shape = (MachineShape) shapesList.get(index);
                if(shape.isValidShape()){
                    validDrag=false;
                }
                shape.setX_axis(prevLocationX);
                shape.setY_axis(prevLocationY);
                shapesList.set(index, shape);
            } else if (shapesList.get(index).getShapeKind().equals("rectangle")) {
                QueueShape shape = (QueueShape) shapesList.get(index);
                if(shape.isValidShape()){
                    validDrag=false;
                }
                shape.setX_axis(prevLocationX);
                shape.setY_axis(prevLocationY);
                shapesList.set(index, shape);
            }
            reDraw();    // redraw canvas to show shape in new position

        }
    }
    public void mouseUp() {
        dragged = false;
    }
    public int findShape(Shapes shape) {
        int index = 0;
        for (int j = shapesList.size() - 1; j >= 0; j--) {
            if (shapesList.get(j).equals(shape)) {
                index = j;
                break;
            }
        }
        return index;
    }

    public void clear() {
        ctx=canvas.getGraphicsContext2D();
        ctx.setFill(Color.TRANSPARENT);
        ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        shapeKind = "";
        idQ = 0;
        idM = 0;
        globalId = 0;
        shapesList.clear();
        prevLocationX = 0;
        prevLocationY = 0;
        shapeDragged = null;
        shape1Clicked = null;
        shape2Clicked = null;
        initialProducts.getItems().clear();
        queuesList.clear();
        machinesList.clear();
        firstUsed = false;
        select = false;
        clickedShape = false;
        dragged = false;
        activateButtons();
        edit.setDisable(true);
        replay.setDisable(true);
        canvas.setDisable(false);

    }
    /////////////////////////////////////////////////////////////////////////////////////////////
    public void startSimulation(ActionEvent event) {
        if(!shapesList.isEmpty()){
            clearQueues();
            if(number.getText().equals("")){
                number.setText("5");
            }
            deactivateButtons();
            manager.setProductsNumber(Integer.parseInt(number.getText()));
            manager.constructAssemblyLineComponents(queuesList,machinesList,false);
            manager.startSimulation();
        }
    }

    public void editSimulation(ActionEvent event){
        activateButtons();
        reset.setDisable(true);
        replay.setDisable(true);
        edit.setDisable(true);
        initialProducts.getItems().clear();
        clearQueues();
    }

    public void endSimulation(){
        reset.setDisable(false);
        replay.setDisable(false);
        edit.setDisable(false);
    }

    public void replaySimulation(ActionEvent event){
        edit.setDisable(true);
        reset.setDisable(true);
        replay.setDisable(true);
        clearQueues();
        manager.replaySimulation();
    }

    public void reDraw() {
        ctx.setFill(Color.TRANSPARENT);
        ctx.clearRect(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1);
        //ctx.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        for (int i = 0; i < shapesList.size(); i++) {
            Shapes shape = shapesList.get(i);
            shape.draw(ctx);
        }
    }

    public  synchronized void updateShapes(QueueShape one, MachineShape two){
        System.out.println(" in update shapes ");
        for(int i = 0; i < shapesList.size(); i++){
            if(shapesList.get(i).getShapeKind().equals(one.getShapeKind())) {
                if(((QueueShape)shapesList.get(i)).getId() == one.getId()){
                    shapesList.set(i, one);
                    System.out.println("new queue set");
                }
            }else if(shapesList.get(i).getShapeKind().equals(two.getShapeKind())){
                if(((MachineShape)shapesList.get(i)).getId() == two.getId()){
                    shapesList.set(i, two);
                    System.out.println("new machine set");
                }
            }
        }
        findArrow(one,two);
        reDraw();
    }
    public void findArrow(QueueShape one, MachineShape two){
        for(int i = 0; i < arrowsList.size(); i++){
            ArrowShape temp = (ArrowShape) arrowsList.get(i);
            if(temp.getOne().getShapeKind().equals(one.getShapeKind())){
                QueueShape queue = (QueueShape) temp.getOne();
                if(queue.getId() == one.getId()){
                    temp.setOne(one);
                }
            }
            else if(temp.getOne().getShapeKind().equals(two.getShapeKind())){
                MachineShape machine = (MachineShape) temp.getOne();
                if(machine.getId() == two.getId()){
                    temp.setOne(two);
                }
            }

            if(temp.getTwo().getShapeKind().equals(one.getShapeKind())){
                QueueShape queue = (QueueShape) temp.getTwo();
                if(queue.getId() == one.getId()){
                    temp.setTwo(one);
                }
            }
            else if(temp.getTwo().getShapeKind().equals(two.getShapeKind())){
                MachineShape machine = (MachineShape) temp.getTwo();
                if(machine.getId() == two.getId()){
                    temp.setTwo(two);
                }
            }

        }

    }
    public  synchronized void updateInitialQueue(QueueShape queue){
        for(int i = 0; i < shapesList.size(); i++){
            if(shapesList.get(i).getShapeKind().equals(queue.getShapeKind())) {
                if(((QueueShape)shapesList.get(i)).getId() == queue.getId()){
                    shapesList.set(i, queue);
                    System.out.println("new queue set");
                }
            }
        }
        reDraw();
    }
    public  synchronized void updateInitialProducts(ArrayList<Product> products){
        for( int i = 0 ; i < products.size() ; i++ ){
            Product product = products.get(i);
            initialProducts.getItems().add(product.getId()+" ("+ product.getColorName()+")");
        }
    }

    public void clearQueues(){
        for(int i  = 0; i < shapesList.size(); i++){
            if(shapesList.get(i).getShapeKind().equals("rectangle")){
                ((QueueShape)shapesList.get(i)).setProductsInQ(0);
            }
        }
        reDraw();
    }



    /////////////////////////////////////////////////////////////////////////////////////////////
    public boolean checkShapes(Shapes one, Shapes two) {
        if(one.getShapeKind().equals(two.getShapeKind())){
            return false;
        }
        if(one.getShapeKind().equals("circle")) {
            MachineShape machine = (MachineShape) one;
            QueueShape queue = (QueueShape) two;
            if (machine.getOutputQueue().size() == 1) {
                return false;
            }
            if (machine.getInputQueue().contains(queue.getId())) {
                return false;
            }
            if (machine.getOutputQueue().contains(queue.getId())){
                return false;
            }
        }
        else if(one.getShapeKind().equals("rectangle")){
            MachineShape machine = (MachineShape) two;
            QueueShape queue = (QueueShape) one;
            if (queue.getOutputMachine().contains(machine.getId())){
                return false;
            }
            if (queue.getInputMachine().contains(machine.getId())) {
                return false;
            }
        }
        return true;
    }

    public void deactivateButtons(){
        rectangle.setDisable(true);
        circle.setDisable(true);
        simulate.setDisable(true);
        edit.setDisable(true);
        arrow.setDisable(true);
        reset.setDisable(true);
        replay.setDisable(true);
        number.setDisable(true);
        canvas.setDisable(true);

    }
    public void activateButtons(){
        circle.setDisable(false);
        rectangle.setDisable(false);
        simulate.setDisable(false);
        edit.setDisable(false);
        arrow.setDisable(false);
        reset.setDisable(false);
        number.setDisable(false);
        canvas.setDisable(false);

    }
}





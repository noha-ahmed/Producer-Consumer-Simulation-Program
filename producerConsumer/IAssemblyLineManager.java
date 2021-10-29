package producerConsumer;

import factoryMachines.MachineShape;
import factoryMachines.QueueShape;
import gui.Controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface IAssemblyLineManager {
    public void setController(Controller controller);
    public void setProductsNumber(int productsNumber);
    public void startSimulation();
    public void replaySimulation();
    public void constructAssemblyLineComponents(LinkedList<QueueShape> queueShapes, LinkedList<MachineShape> machineShapes, boolean replay);
}

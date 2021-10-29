package factoryMachines;

import javafx.scene.canvas.GraphicsContext;

public interface Shapes {
    public void draw(GraphicsContext ctx);

    public boolean containsPoint(double x, double y);
    public String getShapeKind();

}

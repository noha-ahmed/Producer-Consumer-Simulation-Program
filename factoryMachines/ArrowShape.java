package factoryMachines;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ArrowShape implements Shapes {
    private Shapes one;
    private Shapes two;
    private String kind = "arrow";
    private int id;
    public ArrowShape(int id,Shapes one, Shapes two) {
        this.one = one;
        this.two = two;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    @Override
    public void draw(GraphicsContext ctx) {
        double x1 = 0;
        double y1 = 0;
        double x2 = 0;
        double y2 = 0;

        if (one.getShapeKind().equals("circle")) {
            x1 = ((MachineShape) one).getX_axis() + 50;
            y1 = ((MachineShape) one).getY_axis();
            x2 = ((QueueShape) two).getX_axis();
            y2 = ((QueueShape) two).getY_axis();
        } else if (one.getShapeKind().equals("rectangle")) {
            x2 = ((MachineShape) two).getX_axis();
            y2 = ((MachineShape) two).getY_axis();
            x1 = ((QueueShape) one).getX_axis() + 80;
            y1 = ((QueueShape) one).getY_axis();
        }
        ctx.beginPath();
        ctx.moveTo(x1, y1);
        ctx.lineTo(x2, y2);
        ctx.setStroke(Color.BLACK);
        ctx.stroke();
        ctx.setFill(Color.BLACK);
        ctx.beginPath();
        ctx.moveTo(x2, y2 - 10);
        ctx.lineTo(x2 + 10, y2);
        ctx.lineTo(x2, y2 + 10);
        ctx.lineTo(x2, y2 - 10);
        ctx.closePath();
        ctx.setStroke(Color.BLACK);
        ctx.fill();
        ctx.stroke();
    }


    @Override
    public boolean containsPoint(double x, double y) {
        return false;
    }

    @Override
    public String getShapeKind() {
        return "arrow";
    }

    public Shapes getOne() {
        return one;
    }

    public void setOne(Shapes one) {
        this.one = one;
    }

    public Shapes getTwo() {
        return two;
    }

    public void setTwo(Shapes two) {
        this.two = two;
    }
}

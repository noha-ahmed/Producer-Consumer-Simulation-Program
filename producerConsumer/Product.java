package producerConsumer;

import javafx.scene.paint.Color;

public class Product {
    private String colorName;
    private Color color;
    private int id;

    public Product(){

    }

    public Product(int id,String colorName, Color color){
        this.id=id;
        this.colorName=colorName;
        this.color=color;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}

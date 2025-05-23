package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Renderer implements  PushFilter{
    private final GraphicsContext gc;
    private Color color;

    public Renderer(GraphicsContext gc, Color color) {
        this.gc = gc;
        this.color = color;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        // Ignore
    }

    @Override
    public void push(Face face) {
        //gc.stroke für face
    }
}

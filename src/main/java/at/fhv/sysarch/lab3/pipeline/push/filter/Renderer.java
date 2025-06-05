package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import com.hackoeur.jglm.Vec4;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Renderer implements  PushFilter<Pair<Face, Color>, Pair<Face, Color>>{

    private final GraphicsContext gc;
    private final RenderingMode rm;

    public Renderer(GraphicsContext gc, RenderingMode rm) {
        this.gc = gc;
        this.rm = rm;
    }

    @Override
    public void setSuccessor(PushFilter<Pair<Face, Color>, ?> successor) {
        // IGNORE
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        Color color = pair.snd();
        gc.setStroke(color);
        gc.setFill(color);

        Face f = pair.fst();
        var cordX = new double[]{ f.getV1().getX(), f.getV2().getX(), f.getV3().getX() };
        var cordY = new double[]{ f.getV1().getY(), f.getV2().getY(), f.getV3().getY() };

        switch (rm) {
            case POINT -> gc.fillOval(cordX[0], cordY[0], 2, 2);
            case WIREFRAME -> gc.strokePolygon(cordX, cordY, 3);
            case FILLED -> {
                gc.strokePolygon(cordX, cordY, 3);
                gc.fillPolygon(cordX, cordY, 3);
            }
        }
    }



}

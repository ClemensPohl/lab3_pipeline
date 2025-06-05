package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import com.hackoeur.jglm.Vec4;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Renderer implements  PushFilter{
    private final GraphicsContext gc;
    private Color color;
    private RenderingMode renderingMode;

    public Renderer(GraphicsContext gc, Color color, RenderingMode renderingMode) {
        this.gc = gc;
        this.color = color;
        this.renderingMode = renderingMode;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        // Ignore
    }

    @Override
    public void push(Face face) {
        Vec4 v1 = face.getV1();
        Vec4 v2 = face.getV2();
        Vec4 v3 = face.getV3();

        if (renderingMode == RenderingMode.FILLED) {

            double[] xPoints = {v1.getX(), v2.getX(), v3.getX()};
            double[] yPoints = {v1.getY(), v2.getY(), v3.getY()};
            gc.setFill(color);
            gc.fillPolygon(xPoints, yPoints, 3);

        } else if(renderingMode == RenderingMode.WIREFRAME){
            gc.setStroke(color);

            gc.strokeLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
            gc.strokeLine(v2.getX(), v2.getY(), v3.getX(), v3.getY());
            gc.strokeLine(v3.getX(), v3.getY(), v1.getX(), v1.getY());

        }else if (renderingMode == RenderingMode.POINT ){
            gc.setFill(color);

            gc.fillOval(v1.getX() - 0.5, v1.getY() - 0.5, 1, 1);
            gc.fillOval(v2.getX() - 0.5, v2.getY() - 0.5, 1, 1);
            gc.fillOval(v3.getX() - 0.5, v3.getY() - 0.5, 1, 1);
        }
    }
}

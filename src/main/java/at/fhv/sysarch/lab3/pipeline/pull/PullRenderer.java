package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;

import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PullRenderer {
    private final PullFilter<Pair<Face, Color>> source;
    private final GraphicsContext gc;
    private final RenderingMode mode;

    public PullRenderer(PullFilter<Pair<Face, Color>> source, GraphicsContext gc, RenderingMode mode) {
        this.source = source;
        this.gc = gc;
        this.mode = mode;
    }

    public void renderAll() {
        Pair<Face, Color> pair;
        while ((pair = source.pull()) != null) {
            Face f = pair.fst();
            Color c = pair.snd();

            gc.setStroke(c);
            gc.setFill(c);

            switch (mode) {
                case FILLED:
                    gc.strokePolygon(
                            new double[]{f.getV1().getX(), f.getV2().getX(), f.getV3().getX()},
                            new double[]{f.getV1().getY(), f.getV2().getY(), f.getV3().getY()},
                            3
                    );
                    gc.fillPolygon(
                            new double[]{f.getV1().getX(), f.getV2().getX(), f.getV3().getX()},
                            new double[]{f.getV1().getY(), f.getV2().getY(), f.getV3().getY()},
                            3
                    );
                    break;
                case WIREFRAME:
                    gc.strokePolygon(
                            new double[]{f.getV1().getX(), f.getV2().getX(), f.getV3().getX()},
                            new double[]{f.getV1().getY(), f.getV2().getY(), f.getV3().getY()},
                            3
                    );
                    break;
                case POINT:
                    gc.fillOval(f.getV1().getX(), f.getV1().getY(), 2, 2);
                    gc.fillOval(f.getV2().getX(), f.getV2().getY(), 2, 2);
                    gc.fillOval(f.getV3().getX(), f.getV3().getY(), 2, 2);
                    break;
            }
        }
    }
}

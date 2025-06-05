package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PullRenderer {
    private final GraphicsContext gc;
    private final RenderingMode rm;
    private final PullFilter<Pair<Face, Color>> input;

    public PullRenderer(GraphicsContext gc, RenderingMode rm, PullFilter<Pair<Face, Color>> input) {
        this.gc = gc;
        this.rm = rm;
        this.input = input;
    }

    public void render() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        Pair<Face, Color> pair;
        while ((pair = input.pull()) != null) {
            Face f = pair.fst();
            Color color = pair.snd();

            gc.setStroke(color);
            gc.setFill(color);

            double[] x = { f.getV1().getX(), f.getV2().getX(), f.getV3().getX() };
            double[] y = { f.getV1().getY(), f.getV2().getY(), f.getV3().getY() };

            switch (rm) {
                case POINT -> gc.fillOval(x[0], y[0], 2, 2);
                case WIREFRAME -> gc.strokePolygon(x, y, 3);
                case FILLED -> {
                    gc.strokePolygon(x, y, 3);
                    gc.fillPolygon(x, y, 3);
                }
            }
        }
    }
}

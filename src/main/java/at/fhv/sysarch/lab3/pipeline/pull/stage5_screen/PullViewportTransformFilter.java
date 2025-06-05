package at.fhv.sysarch.lab3.pipeline.pull.stage5_screen;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PullViewportTransformFilter implements PullFilter<Pair<Face, Color>> {
    private final Mat4 viewportMatrix;
    private PullFilter<Pair<Face, Color>> predecessor;

    public PullViewportTransformFilter(Mat4 viewportMatrix) {
        this.viewportMatrix = viewportMatrix;
    }

    public void setPredecessor(PullFilter<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> input = predecessor.pull();
        if (input == null) return null;

        Face transformed = FilterUtils.multiplyVectorWithMatrix(viewportMatrix, input.fst());
        return new Pair<>(transformed, input.snd());
    }
}

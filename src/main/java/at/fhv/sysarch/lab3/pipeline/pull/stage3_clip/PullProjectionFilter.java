package at.fhv.sysarch.lab3.pipeline.pull.stage3_clip;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PullProjectionFilter implements PullFilter<Pair<Face, Color>> {
    private final Mat4 projectionMatrix;
    private PullFilter<Pair<Face, Color>> predecessor;

    public PullProjectionFilter(Mat4 projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public void setPredecessor(PullFilter<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> input = predecessor.pull();
        if (input == null) return null;

        Face transformed = FilterUtils.multiplyVectorWithMatrix(projectionMatrix, input.fst());
        return new Pair<>(transformed, input.snd());
    }
}

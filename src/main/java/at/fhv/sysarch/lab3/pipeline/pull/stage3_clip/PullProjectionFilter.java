package at.fhv.sysarch.lab3.pipeline.pull.stage3_clip;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PullProjectionFilter implements PullFilter<Pair<Face, Color>> {

    private final PullFilter<Pair<Face, Color>> source;
    private final Mat4 projectionMatrix;

    public PullProjectionFilter(PullFilter<Pair<Face, Color>> source, Mat4 projectionMatrix) {
        this.source = source;
        this.projectionMatrix = projectionMatrix;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> pair = source.pull();
        if (pair == null) return null;

        Face projected = FilterUtils.multiplyVectorWithMatrix(projectionMatrix, pair.fst());
        return new Pair<>(projected, pair.snd());
    }
}

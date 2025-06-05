package at.fhv.sysarch.lab3.pipeline.pull.stage5_screen;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PullViewPortTransformFilter implements PullFilter<Pair<Face, Color>> {

    private final PullFilter<Pair<Face, Color>> source;
    private final Mat4 viewportMatrix;

    public PullViewPortTransformFilter(PullFilter<Pair<Face, Color>> source, Mat4 viewportMatrix) {
        this.source = source;
        this.viewportMatrix = viewportMatrix;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> pair = source.pull();
        if (pair == null) return null;

        Face screenFace = FilterUtils.multiplyVectorWithMatrix(viewportMatrix, pair.fst());
        return new Pair<>(screenFace, pair.snd());
    }
}

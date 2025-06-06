package at.fhv.sysarch.lab3.pipeline.pull.stage2_view;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class PullViewTransformFilter implements PullFilter<Face> {

    private final PullFilter<Face> source;
    private final Mat4 viewMatrix;

    public PullViewTransformFilter(PullFilter<Face> source, Mat4 viewMatrix) {
        this.source = source;
        this.viewMatrix = viewMatrix;
    }

    @Override
    public Face pull() {
        Face face = source.pull();
        if (face == null) return null;
        return FilterUtils.multiplyVectorWithMatrix(viewMatrix, face);
    }
}

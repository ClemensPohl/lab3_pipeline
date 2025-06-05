package at.fhv.sysarch.lab3.pipeline.pull.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class PullScaleFilter implements PullFilter<Face> {

    private final PullFilter<Face> source;
    private final Mat4 scaleMatrix;

    public PullScaleFilter(PullFilter<Face> source, Mat4 scaleMatrix) {
        this.source = source;
        this.scaleMatrix = scaleMatrix;
    }

    @Override
    public Face pull() {
        Face face = source.pull();
        if (face == null) return null;
        return FilterUtils.multiplyVectorWithMatrix(scaleMatrix, face);
    }
}

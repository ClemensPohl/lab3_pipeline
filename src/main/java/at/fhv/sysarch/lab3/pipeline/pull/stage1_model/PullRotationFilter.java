package at.fhv.sysarch.lab3.pipeline.pull.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import com.hackoeur.jglm.Mat4;
import at.fhv.sysarch.lab3.utils.FilterUtils;

public class PullRotationFilter implements PullFilter<Face> {

    private final PullFilter<Face> source;
    private Mat4 rotationMatrix;

    public PullRotationFilter(PullFilter<Face> source, Mat4 rotationMatrix) {
        this.source = source;
        this.rotationMatrix = rotationMatrix;
    }

    public void setRotationMatrix(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    @Override
    public Face pull() {
        Face face = source.pull();
        if (face == null) return null;
        return FilterUtils.multiplyVectorWithMatrix(rotationMatrix, face);
    }
}

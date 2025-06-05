package at.fhv.sysarch.lab3.pipeline.pull.stage2_view;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class PullViewTransformFilter implements PullFilter<Face> {
    private final Mat4 viewMatrix;
    private PullFilter<Face> predecessor;

    public PullViewTransformFilter(Mat4 viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public void setPredecessor(PullFilter<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Face pull() {
        Face input = predecessor.pull();
        return input != null ? FilterUtils.multiplyVectorWithMatrix(viewMatrix, input) : null;
    }
}
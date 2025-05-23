package at.fhv.sysarch.lab3.pipeline.filter.stage3_clip;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class ProjectionFilter implements PushFilter {

    private PushFilter successor;

    private final Mat4 projectionMatrix;

    public ProjectionFilter(Mat4 projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        this.successor.push(FilterUtils.multiplyVectorWithMatrix(projectionMatrix, face));
    }
}

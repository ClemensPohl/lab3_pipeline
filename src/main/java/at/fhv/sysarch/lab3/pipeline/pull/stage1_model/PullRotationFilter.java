package at.fhv.sysarch.lab3.pipeline.pull.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class PullRotationFilter implements PullFilter<Face> {
    private Mat4 rotationMatrix;
    private PullFilter<Face> predecessor;

    public PullRotationFilter(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    public void setRotationMatrix(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    public void setPredecessor(PullFilter<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Face pull() {
        Face input = predecessor.pull();
        return input != null ? FilterUtils.multiplyVectorWithMatrix(rotationMatrix, input) : null;
    }
}
package at.fhv.sysarch.lab3.pipeline.filter.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class RotationFilter implements PushFilter {

    private final Mat4 rotationMatrix;
    private PushFilter successor;

    public RotationFilter(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        this.successor.push(FilterUtils.multiplyVectorWithMatrix(rotationMatrix, face));
    }
}

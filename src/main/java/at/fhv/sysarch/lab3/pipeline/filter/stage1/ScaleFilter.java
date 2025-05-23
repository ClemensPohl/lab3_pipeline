package at.fhv.sysarch.lab3.pipeline.filter.stage1;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class ScaleFilter implements PushFilter {

    Mat4 scaleMatrix;
    private PushFilter successor;

    public ScaleFilter(Mat4 scaleMatrix) {
        this.scaleMatrix = scaleMatrix;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        this.successor.push(FilterUtils.multiplyVectorWithMatrix(scaleMatrix,face));
    }
}

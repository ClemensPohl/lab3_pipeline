package at.fhv.sysarch.lab3.pipeline.filter.stage2_view;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class ViewTransformFilter implements PushFilter {

    private PushFilter successor;

    private Mat4 viewTransformMatrix;

    public ViewTransformFilter(Mat4 viewTransformMatrix) {
        this.viewTransformMatrix = viewTransformMatrix;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        this.successor.push(FilterUtils.multiplyVectorWithMatrix(viewTransformMatrix,face));
    }
}

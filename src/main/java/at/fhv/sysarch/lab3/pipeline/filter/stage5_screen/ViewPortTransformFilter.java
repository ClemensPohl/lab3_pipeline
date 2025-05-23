package at.fhv.sysarch.lab3.pipeline.filter.stage5_screen;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class ViewPortTransformFilter implements PushFilter {

    private PushFilter successor;

    private Mat4 viewPortMatrix;

    public ViewPortTransformFilter(Mat4 viewPortMatrix) {
        this.viewPortMatrix = viewPortMatrix;
    }

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        this.successor.push(FilterUtils.multiplyVectorWithMatrix(viewPortMatrix,face));
    }
}

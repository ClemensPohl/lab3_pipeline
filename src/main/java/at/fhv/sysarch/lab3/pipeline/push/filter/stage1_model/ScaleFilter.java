package at.fhv.sysarch.lab3.pipeline.push.filter.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class ScaleFilter implements PushFilter<Face, Face>{

    Mat4 scaleMatrix;
    private PushFilter<Face, ?> successor;

    public ScaleFilter(Mat4 scaleMatrix) {
        this.scaleMatrix = scaleMatrix;
    }

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        if (face != null) {
            this.successor.push(FilterUtils.multiplyVectorWithMatrix(scaleMatrix,face));
        }
        else {
            this.successor.push(null);
        }
    }
}

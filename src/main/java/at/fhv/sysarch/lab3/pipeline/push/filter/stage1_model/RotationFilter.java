package at.fhv.sysarch.lab3.pipeline.push.filter.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class RotationFilter implements PushFilter<Face, Face>  {

    private Mat4 rotationMatrix;
    private PushFilter<Face, ?> successor;

    public RotationFilter(Mat4 rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    public void setRotationMatrix(Mat4 newRotationMatrix) {
        this.rotationMatrix = newRotationMatrix;
    }

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        if (face != null) {
            this.successor.push(FilterUtils.multiplyVectorWithMatrix(rotationMatrix, face));
        }
        else {
            this.successor.push(null);
        }
    }


}

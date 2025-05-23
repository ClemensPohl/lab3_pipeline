package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;

public class RotationFilter implements PushFilter {

    private Mat4 rotationMatrix;
    private PushFilter successor;

    @Override
    public void setSuccessor(PushFilter successor) {

    }

    @Override
    public void push(Face face) {

        this.successor.push(face);
    }
}

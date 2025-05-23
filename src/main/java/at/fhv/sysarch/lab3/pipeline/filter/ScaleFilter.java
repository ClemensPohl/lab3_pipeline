package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;

public class ScaleFilter implements PushFilter {

    Mat4 scaleMatrix = null;
    private PushFilter successor;

    public ScaleFilter() {
        scaleMatrix = new Mat4();
    }

    @Override
    public void setSuccessor(PushFilter successor) {

    }

    @Override
    public void push(Face face) {
        // Vec4 v1 scalematrix.multiply
        // Vec4 v2 scalematrix.multiply
        // Vec4 v3 scalematrix.multiply

        //this.successor.push(new Face(face));

    }
}

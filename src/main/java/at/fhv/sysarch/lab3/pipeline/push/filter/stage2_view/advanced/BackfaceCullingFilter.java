package at.fhv.sysarch.lab3.pipeline.push.filter.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;

public class BackfaceCullingFilter implements PushFilter {

    private PushFilter successor;

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        this.successor.push(face); // TODO implement backface culling method in utils
    }
}

package at.fhv.sysarch.lab3.pipeline.filter.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.filter.PushFilter;

public class DepthSortingFilter implements PushFilter {

    private PushFilter successor;

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        this.successor.push(face); // TODO implement Depth sorting
    }
}

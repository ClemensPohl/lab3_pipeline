package at.fhv.sysarch.lab3.pipeline.push.filter.stage4_ndc;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;

public class PerspectiveDivisionFilter implements PushFilter {

    private PushFilter successor;

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        this.successor.push(FilterUtils.divideVectorByWeight(face));
    }
}

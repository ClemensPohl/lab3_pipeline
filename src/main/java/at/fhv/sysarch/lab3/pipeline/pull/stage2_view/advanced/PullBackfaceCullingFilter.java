package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;

public class PullBackfaceCullingFilter implements PullFilter<Face> {
    private PullFilter<Face> predecessor;

    public void setPredecessor(PullFilter<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Face pull() {
        Face face;
        while ((face = predecessor.pull()) != null) {
            if (face.getN1().getZ() < 0) return face;
        }
        return null;
    }
}
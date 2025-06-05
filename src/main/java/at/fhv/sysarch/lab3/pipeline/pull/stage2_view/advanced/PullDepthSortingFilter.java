package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PullDepthSortingFilter implements PullFilter<Face> {
    private PullFilter<Face> predecessor;
    private List<Face> sortedFaces = null;
    private int index = 0;

    public void setPredecessor(PullFilter<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Face pull() {
        if (sortedFaces == null) {
            sortedFaces = new ArrayList<>();
            Face f;
            while ((f = predecessor.pull()) != null) {
                sortedFaces.add(f);
            }
            sortedFaces.sort(Comparator.comparingDouble(this::averageZ).reversed());
        }

        return index < sortedFaces.size() ? sortedFaces.get(index++) : null;
    }

    private double averageZ(Face face) {
        return (face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()) / 3.0;
    }
}
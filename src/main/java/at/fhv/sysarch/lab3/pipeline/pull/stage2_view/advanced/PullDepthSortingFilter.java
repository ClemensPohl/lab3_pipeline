package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.pipeline.pull.ResettablePullFilter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PullDepthSortingFilter implements PullFilter<Face>, ResettablePullFilter {
    private final PullFilter<Face> source;
    private List<Face> sortedFaces = null;
    private int currentIndex = 0;

    public PullDepthSortingFilter(PullFilter<Face> source) {
        this.source = source;
    }

    @Override
    public Face pull() {
        if (sortedFaces == null) {
            sortedFaces = new ArrayList<>();
            Face f;
            while ((f = source.pull()) != null) {
                sortedFaces.add(f);
            }
            sortedFaces.sort(Comparator.comparingDouble(
                    face -> face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()
            ));
        }

        if (currentIndex >= sortedFaces.size()) return null;
        return sortedFaces.get(currentIndex++);
    }

    @Override
    public void reset() {
        sortedFaces = null;
        currentIndex = 0;
    }
}
package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PullDepthSortingFilter implements PullFilter<Face> {

    private final PullFilter<Face> source;
    private List<Face> sortedFaces;
    private int currentIndex = 0;

    public PullDepthSortingFilter(PullFilter<Face> source) {
        this.source = source;
    }

    @Override
    public Face pull() {
        if (sortedFaces == null) {
            loadAndSort();
        }

        if (currentIndex >= sortedFaces.size()) {
            return null;
        }

        return sortedFaces.get(currentIndex++);
    }

    private void loadAndSort() {
        sortedFaces = new LinkedList<>();
        Face f;
        while ((f = source.pull()) != null) {
            sortedFaces.add(f);
        }

        sortedFaces.sort(Comparator.comparingDouble(
                face -> face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()
        ));
    }
}

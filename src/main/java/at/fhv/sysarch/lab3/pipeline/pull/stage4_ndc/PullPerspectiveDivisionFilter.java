package at.fhv.sysarch.lab3.pipeline.pull.stage4_ndc;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import javafx.scene.paint.Color;

public class PullPerspectiveDivisionFilter implements PullFilter<Pair<Face, Color>> {
    private PullFilter<Pair<Face, Color>> predecessor;

    public void setPredecessor(PullFilter<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> input = predecessor.pull();
        if (input == null) return null;

        Face divided = FilterUtils.divideVectorByWeight(input.fst());
        return new Pair<>(divided, input.snd());
    }
}

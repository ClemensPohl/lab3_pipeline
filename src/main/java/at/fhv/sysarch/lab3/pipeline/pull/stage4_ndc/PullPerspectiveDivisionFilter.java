package at.fhv.sysarch.lab3.pipeline.pull.stage4_ndc;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import javafx.scene.paint.Color;

public class PullPerspectiveDivisionFilter implements PullFilter<Pair<Face, Color>> {

    private final PullFilter<Pair<Face, Color>> source;

    public PullPerspectiveDivisionFilter(PullFilter<Pair<Face, Color>> source) {
        this.source = source;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> pair = source.pull();
        if (pair == null) return null;

        Face divided = FilterUtils.divideVectorByWeight(pair.fst());
        return new Pair<>(divided, pair.snd());
    }
}

package at.fhv.sysarch.lab3.pipeline.pull.stage4_ndc;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import javafx.scene.paint.Color;

public class PerspectiveDivisionFilter implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private PushFilter<Pair<Face, Color>, ?> successor;

    @Override
    public void setSuccessor(PushFilter<Pair<Face, Color>, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        this.successor.push(new Pair<>(FilterUtils.divideVectorByWeight(pair.fst()), pair.snd()));
    }
}

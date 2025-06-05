package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import javafx.scene.paint.Color;

public class PullColorFilter implements PullFilter<Pair<Face, Color>> {
    private final Color color;
    private final PullFilter<Face> source;

    public PullColorFilter(Color color, PullFilter<Face> source) {
        this.color = color;
        this.source = source;
    }

    @Override
    public Pair<Face, Color> pull() {
        Face face = source.pull();
        return face == null ? null : new Pair<>(face, color);
    }
}
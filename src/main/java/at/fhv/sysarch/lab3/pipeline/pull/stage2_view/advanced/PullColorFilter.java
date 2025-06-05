package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import javafx.scene.paint.Color;

public class PullColorFilter implements PullFilter<Pair<Face, Color>> {
    private final Color color;
    private PullFilter<Face> predecessor;

    public PullColorFilter(Color color) {
        this.color = color;
    }

    public void setPredecessor(PullFilter<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Pair<Face, Color> pull() {
        Face input = predecessor.pull();
        return input != null ? new Pair<>(input, color) : null;
    }
}
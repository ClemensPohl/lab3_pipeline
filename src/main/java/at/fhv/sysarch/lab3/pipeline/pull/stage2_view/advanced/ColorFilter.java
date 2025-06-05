package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;
import javafx.scene.paint.Color;

public class ColorFilter implements PushFilter<Face, Pair<Face, Color>> {

    private final Color faceColor;
    private PushFilter<Pair<Face, Color>, ?> successor;

    public ColorFilter(Color faceColor) {
        this.faceColor = faceColor;
    }

    @Override
    public void setSuccessor(PushFilter<Pair<Face, Color>, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        successor.push(new Pair<>(f, faceColor));
    }
}
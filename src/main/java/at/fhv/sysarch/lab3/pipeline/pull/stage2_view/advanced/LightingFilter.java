package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;
import com.hackoeur.jglm.Vec3;
import javafx.scene.paint.Color;

public class LightingFilter implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private final Vec3 unitVector;
    private PushFilter<Pair<Face, Color>, ?> successor;

    public LightingFilter(Vec3 unitVector) {
        this.unitVector = unitVector;
    }

    @Override
    public void setSuccessor(PushFilter<Pair<Face, Color>, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        Face f = pair.fst();
        successor.push(new Pair<>(f, pair.snd().deriveColor(0, 1, f.getN1().toVec3().dot(unitVector), 1)));
    }
}
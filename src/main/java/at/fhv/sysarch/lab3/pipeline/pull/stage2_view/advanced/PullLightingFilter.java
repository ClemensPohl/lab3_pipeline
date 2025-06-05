package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import com.hackoeur.jglm.Vec3;
import javafx.scene.paint.Color;

public class PullLightingFilter implements PullFilter<Pair<Face, Color>> {

    private final PullFilter<Pair<Face, Color>> source;
    private final Vec3 lightDir;

    public PullLightingFilter(PullFilter<Pair<Face, Color>> source, Vec3 lightDir) {
        this.source = source;
        this.lightDir = lightDir;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> pair = source.pull();
        if (pair == null) return null;

        Face face = pair.fst();
        Color baseColor = pair.snd();

        float brightness = face.getN1().toVec3().dot(lightDir);
        brightness = Math.max(0, brightness); // avoid negative brightness

        Color litColor = baseColor.deriveColor(0, 1, brightness, 1);
        return new Pair<>(face, litColor);
    }
}

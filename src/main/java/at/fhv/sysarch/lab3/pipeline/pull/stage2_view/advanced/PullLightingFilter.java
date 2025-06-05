package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullLightingFilter implements PullFilter<Pair<Face, Color>> {
    private final Vec3 lightDir;
    private PullFilter<Pair<Face, Color>> predecessor;

    public PullLightingFilter(Vec3 lightDir) {
        this.lightDir = lightDir;
    }

    public void setPredecessor(PullFilter<Pair<Face, Color>> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Pair<Face, Color> pull() {
        Pair<Face, Color> input = predecessor.pull();
        if (input == null) return null;

        Face face = input.fst();
        Color baseColor = input.snd();

        Vec3 normal = computeFaceNormal(face);
        float brightness = Math.max(0, normal.dot(lightDir));

        Color shaded = baseColor.deriveColor(0, 1, brightness, 1);
        return new Pair<>(face, shaded);
    }

    private Vec3 computeFaceNormal(Face face) {
        Vec3 a = vec4ToVec3(face.getV2()).subtract(vec4ToVec3(face.getV1()));
        Vec3 b = vec4ToVec3(face.getV3()).subtract(vec4ToVec3(face.getV1()));
        return a.cross(b).getUnitVector();
    }

    private Vec3 vec4ToVec3(Vec4 v) {
        return new Vec3(v.getX(), v.getY(), v.getZ());
    }
}
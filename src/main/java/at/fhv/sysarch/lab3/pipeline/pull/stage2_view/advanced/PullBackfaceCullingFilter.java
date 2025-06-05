package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

public class PullBackfaceCullingFilter implements PullFilter<Face> {

    private final PullFilter<Face> source;

    public PullBackfaceCullingFilter(PullFilter<Face> source) {
        this.source = source;
    }

    @Override
    public Face pull() {
        Face f;
        do {
            f = source.pull();  // Pull from source
            if (f == null) return null;
        } while (f.getV1().dot(f.getN1()) >= 0);  // Cull back-facing
        return f;
    }
}

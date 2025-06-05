package at.fhv.sysarch.lab3.pipeline.pull.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

public class PullBackfaceCullingFilter implements PullFilter<Face> {
    private PullFilter<Face> predecessor;

    public void setPredecessor(PullFilter<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Face pull() {
        Face face;
        while ((face = predecessor.pull()) != null) {
            Vec3 a = vec4ToVec3(face.getV2()).subtract(vec4ToVec3(face.getV1()));
            Vec3 b = vec4ToVec3(face.getV3()).subtract(vec4ToVec3(face.getV1()));
            Vec3 normal = a.cross(b).getUnitVector();

            // Keep only faces facing the camera (negative Z in view space)
            if (normal.getZ() < 0) {
                return face;
            }
        }
        return null;
    }

    private Vec3 vec4ToVec3(Vec4 v) {
        return new Vec3(v.getX(), v.getY(), v.getZ());
    }
}

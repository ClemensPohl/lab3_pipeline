package at.fhv.sysarch.lab3.pipeline.pull.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class PullTransformFilter implements PullFilter<Face> {
    private final PullFilter<Face> predecessor;
    private final Mat4 scaleMatrix;
    private final Mat4 translationMatrix;
    private Mat4 rotationMatrix; // ✅ now mutable

    public PullTransformFilter(PullFilter<Face> predecessor,
                               Mat4 scaleMatrix,
                               Mat4 rotationMatrix,
                               Mat4 translationMatrix) {
        this.predecessor = predecessor;
        this.scaleMatrix = scaleMatrix;
        this.rotationMatrix = rotationMatrix;
        this.translationMatrix = translationMatrix;
    }

    public void updateRotationMatrix(Mat4 newRotationMatrix) {
        this.rotationMatrix = newRotationMatrix;
    }

    @Override
    public Face pull() {
        Face face = predecessor.pull();
        if (face != null) {
            Face scaled = FilterUtils.multiplyVectorWithMatrix(scaleMatrix, face);
            Face rotated = FilterUtils.multiplyVectorWithMatrix(rotationMatrix, scaled);
            return FilterUtils.multiplyVectorWithMatrix(translationMatrix, rotated);
        }
        return null;
    }
}


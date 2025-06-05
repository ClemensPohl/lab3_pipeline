package at.fhv.sysarch.lab3.pipeline.pull.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;

import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import at.fhv.sysarch.lab3.utils.MatrixUtils;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Mat4;

public class PullTranslationFilter implements PullFilter<Face> {

    private final PullFilter<Face> source;
    private final Mat4 translationMatrix;

    public PullTranslationFilter(PullFilter<Face> source, Vec3 translationVec) {
        this.source = source;
        this.translationMatrix = MatrixUtils.translationMatrix(translationVec);
    }

    @Override
    public Face pull() {
        Face face = source.pull();
        if (face == null) return null;
        return FilterUtils.multiplyVectorWithMatrix(translationMatrix, face);
    }
}
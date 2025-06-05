package at.fhv.sysarch.lab3.pipeline.pull.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.pull.PullFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class PullTranslationFilter implements PullFilter<Face> {
    private final Mat4 translationMatrix;
    private PullFilter<Face> predecessor;

    public PullTranslationFilter(Mat4 translationMatrix) {
        this.translationMatrix = translationMatrix;
    }

    public void setPredecessor(PullFilter<Face> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public Face pull() {
        Face input = predecessor.pull();
        return input != null ? FilterUtils.multiplyVectorWithMatrix(translationMatrix, input) : null;
    }
}
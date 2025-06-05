package at.fhv.sysarch.lab3.pipeline.push.filter.stage1_model;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;

public class TranslationFilter implements PushFilter<Face, Face> {

    private PushFilter<Face, ?> successor;
    private Mat4 translationMatrix;

    public TranslationFilter(Mat4 translationMatrix) {
        this.translationMatrix = translationMatrix;
    }

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        if (face != null) {
            this.successor.push(FilterUtils.multiplyVectorWithMatrix(translationMatrix, face));
        }
        else {
            this.successor.push(null);
        }
    }
}

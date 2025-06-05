package at.fhv.sysarch.lab3.pipeline.pull.stage5_screen;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;
import at.fhv.sysarch.lab3.utils.FilterUtils;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class ViewPortTransformFilter implements PushFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private PushFilter<Pair<Face, Color>, ?> successor;

    private Mat4 viewPortMatrix;

    public ViewPortTransformFilter(Mat4 viewPortMatrix) {
        this.viewPortMatrix = viewPortMatrix;
    }

    @Override
    public void setSuccessor(PushFilter<Pair<Face, Color>, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Pair<Face, Color> pair) {
        this.successor.push(new Pair<>(FilterUtils.multiplyVectorWithMatrix(viewPortMatrix, pair.fst()), pair.snd()));
    }
}

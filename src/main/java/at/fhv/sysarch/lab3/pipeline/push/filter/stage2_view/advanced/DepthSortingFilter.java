package at.fhv.sysarch.lab3.pipeline.push.filter.stage2_view.advanced;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.push.filter.PushFilter;

import java.util.Comparator;
import java.util.LinkedList;

public class DepthSortingFilter implements PushFilter<Face, Face> {

    private final LinkedList<Face> faceBuffer = new LinkedList<>();
    private PushFilter<Face, ?> successor;

    @Override
    public void setSuccessor(PushFilter<Face, ?> successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face f) {
        if (f == null) {
            continuePipeline();
        } else {
            faceBuffer.add(f); // Collect all incoming faces
        }
    }

    private void continuePipeline() {
        faceBuffer.sort(Comparator.comparing(face -> face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()));
        while (!faceBuffer.isEmpty()) {
            successor.push(faceBuffer.removeFirst());
        }
    }
}
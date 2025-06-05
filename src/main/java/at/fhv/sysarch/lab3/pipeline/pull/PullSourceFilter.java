package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.Iterator;

public class PullSourceFilter implements PullFilter<Face> {
    private final Model model;
    private Iterator<Face> faceIterator;

    public PullSourceFilter(Model model) {
        this.model = model;
        this.faceIterator = model.getFaces().iterator();
    }

    // Call this manually at the start of every frame
    public void reset() {
        this.faceIterator = model.getFaces().iterator();
    }

    @Override
    public Face pull() {
        return faceIterator.hasNext() ? faceIterator.next() : null;
    }
}

package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.Iterator;

public class PullSourceFilter implements PullFilter<Face>, ResettablePullFilter {
    private final Model model;
    private Iterator<Face> faceIterator;

    public PullSourceFilter(Model model) {
        this.model = model;
        reset();
    }

    @Override
    public Face pull() {
        return faceIterator.hasNext() ? faceIterator.next() : null;
    }

    @Override
    public void reset() {
        faceIterator = model.getFaces().iterator();
    }
}


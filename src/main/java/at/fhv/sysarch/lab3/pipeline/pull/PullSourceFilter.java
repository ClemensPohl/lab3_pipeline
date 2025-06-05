package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.Iterator;

public class PullSourceFilter implements PullFilter<Face> {
    private final Iterator<Face> iterator;

    public PullSourceFilter(Model model) {
        this.iterator = model.getFaces().iterator();
    }

    @Override
    public Face pull() {
        return iterator.hasNext() ? iterator.next() : null;
    }
}
package at.fhv.sysarch.lab3.pipeline.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

import java.util.Iterator;

public class PullSourceFilter implements PullFilter<Face> {
    private Model model;
    private Iterator<Face> iterator;

    public void run(Model model) {
        this.model = model;
        this.iterator = model.getFaces().iterator();
    }

    @Override
    public Face pull() {
        if (iterator == null) {
            throw new IllegalStateException("Call run(model) before pulling.");
        }
        return iterator.hasNext() ? iterator.next() : null;
    }

    public void reset() {
        if (model != null) {
            this.iterator = model.getFaces().iterator();
        }
    }
}

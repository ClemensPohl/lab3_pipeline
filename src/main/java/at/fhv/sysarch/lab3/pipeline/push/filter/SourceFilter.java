package at.fhv.sysarch.lab3.pipeline.push.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class SourceFilter implements PushFilter{

    private PushFilter successor;

    @Override
    public void setSuccessor(PushFilter successor) {
        this.successor = successor;
    }

    @Override
    public void push(Face face) {
        // IGNORE
    }

    public void process(Model model){
        for(Face face : model.getFaces()){
            this.successor.push(face);
        }
    }
}

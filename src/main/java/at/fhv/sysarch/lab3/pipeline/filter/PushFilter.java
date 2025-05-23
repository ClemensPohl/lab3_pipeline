package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;

public interface PushFilter {

    void setSuccessor(PushFilter successor);

    void push(Face face);
}

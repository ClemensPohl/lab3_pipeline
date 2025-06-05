package at.fhv.sysarch.lab3.pipeline.push.filter;

public interface PushFilter<I,O> {

    void setSuccessor(PushFilter<O,?> successor);

    void push(I input);
}

package at.fhv.sysarch.lab3.pipeline.pull;

public interface PullFilter<R> {
    R pull();

    Boolean hasNext();
}

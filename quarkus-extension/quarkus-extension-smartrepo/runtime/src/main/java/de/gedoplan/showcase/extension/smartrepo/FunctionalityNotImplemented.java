package de.gedoplan.showcase.extension.smartrepo;

public class FunctionalityNotImplemented extends RuntimeException {

    public FunctionalityNotImplemented(String className, String methodName) {
        super("Method " + methodName + " of class " + className + " has not yet been implemented");
    }
}

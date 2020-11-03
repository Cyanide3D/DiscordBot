package cyanide3d.exceprtion;

public class UnsupportedActionException extends Exception{
    public UnsupportedActionException(String action) {
        super("Действие [" + action + "] не обнаружено.");
    }
}

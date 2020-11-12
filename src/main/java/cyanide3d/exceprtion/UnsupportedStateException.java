package cyanide3d.exceprtion;

public class UnsupportedStateException extends Exception{
    public UnsupportedStateException(String state) {
        super("Состояние [" + state + "] не поддерживается.");
    }
}

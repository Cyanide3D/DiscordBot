package cyanide3d.exceptions;

public class CommandDuplicateException extends RuntimeException{
    public CommandDuplicateException(String message) {
        super(message);
    }
}

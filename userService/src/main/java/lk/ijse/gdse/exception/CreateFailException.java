package lk.ijse.gdse.exception;

public class CreateFailException extends Exception{

    public CreateFailException(String message, Throwable cause) {
        super(message+" :( "+ cause.getMessage(), cause);
    }

}

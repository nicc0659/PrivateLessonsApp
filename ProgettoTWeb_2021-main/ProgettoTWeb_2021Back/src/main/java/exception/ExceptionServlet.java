package exception;

public class ExceptionServlet extends RuntimeException {

    private int code;
    private String messaggio;

    public ExceptionServlet(int code, String messaggio) {
        this.code = code;
        this.messaggio = messaggio;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.messaggio;
    }
}

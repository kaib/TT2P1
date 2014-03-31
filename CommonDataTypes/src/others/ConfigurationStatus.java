package others;

/**
 * Created by tobi on 31.03.14.
 */
public enum ConfigurationStatus {
    REQUIRED(1), IN_PROGRESS(2), DONE(3);

    private int statusCode;

    ConfigurationStatus(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

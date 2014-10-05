package ak.duorum.ui.message;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/23/14
 */
public enum MessageStatus {

    SUCCESS("alert alert-success"),
    INFO("alert alert-info"),
    WARNING("alert alert-warning"),
    DANGER("alert alert-danger");

    private String cssClass;

    MessageStatus(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

}

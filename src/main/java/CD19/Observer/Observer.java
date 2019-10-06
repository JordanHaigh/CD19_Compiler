package CD19.Observer;

/**
 * Observer Pattern - Observer will watch a Subject for any messages it sends out
 *
 * @author Jordan Haigh c3256730
 * @since 29/9/19
 */
public interface Observer {
    void handleMessage(ObservableMessage message);

}

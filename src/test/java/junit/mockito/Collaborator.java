package junit.mockito;

public class Collaborator {
    public int supply() {
        return 0;
    }

    public void consume(Message message) {
        System.out.printf("message id = %s, value = %d", message.getId(), message.getValue());
    }
}
package hello.core.singleton;

public class SingletonService {

    private static final SingletonService INSTANCE = new SingletonService();

    private SingletonService() {
    }

    public static SingletonService getInstance() {
        return INSTANCE;
    }

    public void logic() {
        System.out.println("Singleton Service");
    }
}

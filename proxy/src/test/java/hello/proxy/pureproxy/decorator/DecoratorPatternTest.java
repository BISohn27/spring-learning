package hello.proxy.pureproxy.decorator;


import hello.proxy.pureproxy.decorator.code.*;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {
    @Test
    void noDecorator() {
        Component component = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(component);
        client.execute();
    }

    @Test
    void decorator1() {
        Component realComponent = new RealComponent();
        Component messageComponent = new MessageDecorator(realComponent);
        DecoratorPatternClient client = new DecoratorPatternClient(messageComponent);
        client.execute();
    }

    @Test
    void decorator2() {
        Component realComponent = new RealComponent();
        Component messageComponent = new MessageDecorator(realComponent);
        Component timeComponent = new TimeDecorator(messageComponent);
        DecoratorPatternClient client = new DecoratorPatternClient(timeComponent);
        client.execute();
    }
}
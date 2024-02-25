package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import hello.advanced.trace.threadlocal.code.ThreadLocalFieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalFieldServiceTest {

    private ThreadLocalFieldService fieldService = new ThreadLocalFieldService();

    @Test
    void field() {
        log.info("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        Thread threadB = new Thread(userB);

        threadA.setName("thread-A");
        threadB.setName("thread-B");

        threadA.start();
        sleep(2000);
        threadB.start();
        sleep(2000);
    }

    @Test
    void field2() {
        log.info("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        Thread threadB = new Thread(userB);

        threadA.setName("thread-A");
        threadB.setName("thread-B");

        threadA.start();
        threadB.start();
        sleep(2000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
    }

    @Test
    void reflection1() throws Exception {
        //클래스 정보 획득
        Class<?> clazz = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서드 정보
        Method methodCallA = clazz.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        //callB 메서드 정보
        Method methodCallB = clazz.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        //클래스 정보 획득
        Class<?> clazz = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서드 정보
        Method methodCallA = clazz.getMethod("callA");
        dynamicCall(methodCallA, target);
        //callB 메서드 정보
        Method methodCallB = clazz.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        //공통 로직2 시작
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Test
    void lambda() {
        Hello target = new Hello();

        call(target::callA);
        call(target::callB);
    }

    private String call(Supplier<String> supplier) {
        log.info("start");
        String result = supplier.get();
        log.info("result={}", result);

        return result;
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");

            return "A";
        }

        public String callB() {
            log.info("callB");

            return "B";
        }
    }
}

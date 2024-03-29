package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
public class CallServiceV2Test {

    @Autowired
    CallServiceV2 call;

    @Test
    public void external() {
        call.external();
    }

    @Test
    public void internal() {
        call.internal();
    }
}

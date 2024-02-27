package hello.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AImpl implements AInterface {

    @Override
    public String call(String arg) {
        log.info("A 호출 : " + arg);
        return "a";
    }
}

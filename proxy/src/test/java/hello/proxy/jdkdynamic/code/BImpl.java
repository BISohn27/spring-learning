package hello.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BImpl implements BInterface {

    @Override
    public String call(String arg) {
        log.info("B 호출 : " + arg);
        return "b";
    }
}

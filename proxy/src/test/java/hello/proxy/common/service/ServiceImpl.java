package hello.proxy.common.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceImpl implements ServiceInterface {

    @Override
    public void save() {
        log.info("Interface Service save");
    }

    @Override
    public void find() {
        log.info("Interface Service find");
    }
}

package ir.msob.jima.crud.sample.kafka;

import ir.msob.jima.core.commons.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {Constants.FRAMEWORK_PACKAGE_PREFIX, "ir.msob.jima.crud.sample.kafka"})
public class JimaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JimaApplication.class, args);
    }

}

package ir.msob.jima.crud.sample.graphql.restful;

import ir.msob.jima.core.commons.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {Constants.FRAMEWORK_PACKAGE_PREFIX})
public class JimaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JimaApplication.class, args);
    }

}

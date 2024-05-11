package ir.msob.jima.crud.api.graphql.restful.service;

import ir.msob.jima.core.commons.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = {Constants.FRAMEWORK_PACKAGE_PREFIX})
public class JimaApplication implements ApplicationRunner {

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(JimaApplication.class, args);
    }

    public void startup() {
        String[] profiles = environment.getActiveProfiles();
        Integer port = environment.getProperty("server.port", Integer.class);
        System.out.println();
        System.out.printf("\t\thttp://localhost:%d%n", port);
        System.out.printf("\t\tProfiles:%s", String.join(", ", profiles));
        System.out.println();
    }

    @Override
    public void run(ApplicationArguments args) {
        startup();
    }

}

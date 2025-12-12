package ir.msob.jima.crud.ral.jpa.it;

import ir.msob.jima.core.commons.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.Environment;

@EntityScan("ir.msob.jima.core.ral.sql.it.test")
@SpringBootApplication(scanBasePackages = {Constants.FRAMEWORK_PACKAGE_PREFIX})
public class TestApplication implements ApplicationRunner {

    @Autowired
    Environment environment;

    static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
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

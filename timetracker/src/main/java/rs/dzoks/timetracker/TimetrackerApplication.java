package rs.dzoks.timetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@EnableConfigurationProperties
public class TimetrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimetrackerApplication.class, args);
    }

}

package com.sakshi1001.configs;

import com.sakshi1001.model.Patient;
import com.sakshi1001.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PatientLoadBase {
    private static final Logger log= LoggerFactory.getLogger(PatientLoadBase.class);

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository repository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                log.info(("Preloading "+ repository.save(new Patient("Sakashi","Dengue"))));
                log.info(("Preloading "+ repository.save(new Patient("Mahesh","Cough"))));
            }
        };
    }
}


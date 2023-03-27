package se.iths.meritwos.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import se.iths.meritwos.ad.Ad;
import se.iths.meritwos.company.Company;
import se.iths.meritwos.student.Student;

@Configuration
@EnableJpaRepositories(basePackageClasses = {Company.class, Ad.class, Student.class})
public class JpaConfig {
}

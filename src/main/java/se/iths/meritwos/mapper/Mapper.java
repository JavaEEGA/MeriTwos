package se.iths.meritwos.mapper;

import org.springframework.stereotype.Component;

import se.iths.meritwos.company.Company;
import se.iths.meritwos.company.CompanyDTO;
import se.iths.meritwos.student.Student;
import se.iths.meritwos.student.StudentDTO;
import se.iths.meritwos.ad.Ad;
import se.iths.meritwos.ad.AdDTO;
import se.iths.meritwos.user.User;
import se.iths.meritwos.user.UserDTO;


import java.util.List;
import java.util.Optional;

@Component
public class Mapper {

    public List<StudentDTO> mapStudentToDto(List<Student> students){
        return students.stream().map(StudentDTO::new).toList();
    }

    public Optional<StudentDTO> mapStudentToDto(Student student){
        return Optional.of(new StudentDTO(student));
    }

    public List<UserDTO> mapUserToDTO(List<User> users) {
        return users.stream().map(UserDTO::new).toList();
    }

    public Optional<UserDTO> mapUserToDTO(User user) {
        return Optional.of(new UserDTO(user));
    }
    public List<AdDTO> mapAdToDTO(List<Ad> ads) {return ads.stream().map(AdDTO::new).toList();}

    public Optional<AdDTO> mapAdToDTO(Ad ad) {return Optional.of((new AdDTO(ad)));}

    public Optional <CompanyDTO> mapCompanyToDTO (Company company) {
        return Optional.of(new CompanyDTO(company));
    }

    public List<CompanyDTO> mapCompanyToDTO (List<Company> companies) {
        return companies.stream().map(CompanyDTO::new).toList();
    }
}

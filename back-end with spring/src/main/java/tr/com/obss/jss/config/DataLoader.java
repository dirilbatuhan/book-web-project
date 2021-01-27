package tr.com.obss.jss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tr.com.obss.jss.entity.Role;
import tr.com.obss.jss.repository.RoleRepository;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean userRoleExists = roleRepository.existsByName("role_user");
        if(!userRoleExists){
            Role userRole = new Role();
            userRole.setName("role_user");
            roleRepository.save(userRole);
        }
        boolean adminRoleExists = roleRepository.existsByName("role_admin");
        if(!userRoleExists){
            Role userRole = new Role();
            userRole.setName("role_admin");
            roleRepository.save(userRole);
        }
    }
}

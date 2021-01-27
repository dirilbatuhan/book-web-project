package tr.com.obss.jss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.obss.jss.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    Role findByName(String name);
}

package tr.com.obss.jss.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tr.com.obss.jss.model.UserDTO;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component("singletonCache")
@Scope(value = "singleton")
public class UserCacheSingleton implements UserCache {
    public Map<String, UserDTO> users;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCacheSingleton.class);


    @PostConstruct
    public void init(){
        LOGGER.info("Singleton bean olusturuldu");
        users = new HashMap<>();
    }

    @Override
    public void put(UserDTO user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public Map<String, UserDTO> getMap() {
        return users;
    }
}

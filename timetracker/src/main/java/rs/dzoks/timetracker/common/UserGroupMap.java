package rs.dzoks.timetracker.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.dzoks.timetracker.model.UserGroup;
import rs.dzoks.timetracker.repository.UserGroupRepository;

import java.util.HashMap;

@Component
public class UserGroupMap {
    private HashMap<String,Integer> map;

    private final UserGroupRepository repository;
    @Autowired
    public UserGroupMap(UserGroupRepository repository){
        this.map = new HashMap<>();
        this.repository = repository;
        for (UserGroup userGroup : repository.findAll()) {
                map.put(userGroup.getKey(),userGroup.getId());
        }
    }

    public boolean addNewGroup(UserGroup userGroup){
        if (map.containsKey(userGroup.getKey())||map.containsValue(userGroup.getId()))
            return false;
        map.put(userGroup.getKey(),userGroup.getId());
        return true;
    }

    public boolean removeGroup(UserGroup userGroup){
        if (!map.containsValue(userGroup.getId()))
            return false;
        map.remove(userGroup.getKey());
            return true;
    }

    public Integer getByKey(String key){
        return map.get(key);
    }
}

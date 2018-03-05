package com.kru.qualibrate.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Component
public class UserConverter implements Converter<UserRecord, UserDTO> {


    @Override
    public UserDTO convert(UserRecord source) {
        UserDTO u = new UserDTO();
        u.setEmail(source.getEmail());
        u.setFirstName(source.getFirstName());
        u.setLastName(source.getLastName());
        u.setLoginAt(source.getLoginAt());
        u.setLogoutAt(source.getLogoutAt());
        u.setProjects(source.getProjects());
        u.setActive(source.isActive());
        u.setProvider(source.getProvider());
        u.setUid(source.getUid());
        u.setUserId(source.getUserId());
        u.setTimestamp(source.getTimestamp());
        u.setActivatedAt(source.getActivatedAt());

        return u;
    }

}

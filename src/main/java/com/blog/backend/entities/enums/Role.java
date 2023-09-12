package com.blog.backend.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.blog.backend.entities.enums.Permissions.*;


@Getter
@RequiredArgsConstructor
public enum Role {
    USER(
            Set.of(

                    USER_DELETE,
                    USER_CREATE,
                    USER_UPDATE,
                    USER_READ
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_READ,
                    USER_DELETE,
                    USER_CREATE,
                    USER_UPDATE,
                    USER_READ
            )
    )
    ;

    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities= getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;

    }
}

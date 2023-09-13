package com.blog.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ReactId implements Serializable {
    private static final long serialVersionUID = 2908773190368750085L;
    @Column(name = "user", nullable = false)
    private Integer user;

    @Column(name = "post", nullable = false)
    private Integer post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReactId entity = (ReactId) o;
        return Objects.equals(this.post, entity.post) &&
                Objects.equals(this.user, entity.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user);
    }

}

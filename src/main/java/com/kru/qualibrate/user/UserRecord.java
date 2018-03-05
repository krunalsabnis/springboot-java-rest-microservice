package com.kru.qualibrate.user;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.kru.qualibrate.project.ProjectRecord;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 * User Entity represents Database Table "user"
 */

@Entity(name = "user")
@Data
@NoArgsConstructor
public class UserRecord {

    @Id
    @NotNull
    @Column(name = "user_id")
    @GeneratedValue
    private long userId;

    @Size(max = 20)
    private String uid;

    @Size(max = 20)
    private String provider;

    @Size(max = 20)
    private String firstName;

    @Size(max = 20)
    private String lastName;

    @Size(max = 50)
    private String email;

    private boolean active;

    private Date activatedAt;

    private Date loginAt;

    private Date logoutAt;

    private Date timestamp;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    Set<ProjectRecord> projects;

    public UserRecord(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email =  user.getEmail();
        this.timestamp = new Date();
    }


    public UserRecord(UserDTO userDto) {
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email =  userDto.getEmail();
        this.userId = userDto.getUserId();
        this.timestamp = new Date();
    }
}

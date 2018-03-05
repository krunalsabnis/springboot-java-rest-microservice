package com.kru.qualibrate.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z]{1,20}")
    @ApiModelProperty(name = "firstName", dataType = "String", required = true, notes = "First name", example = "John")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z]{1,20}")
    @ApiModelProperty(name = "lastName", dataType = "String", required = true, notes = "Last name", example = "Smith")
    private String lastName;

    @NotNull
    @Size(min = 4, max = 50)
    @Pattern(regexp = "[^@]+@[^@]+\\.[^@]+")
    @ApiModelProperty(name = "email", dataType = "String",
        required = true, notes = "Contact email", example = "jsmith@gmail.com")
    private String email;

}

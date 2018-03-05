
package com.kru.qualibrate.project;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Setter
@Getter
public class ProjectDTO extends Project {

    private String icon;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date timestamp;

    private Long userId;

}

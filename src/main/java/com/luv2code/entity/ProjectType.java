

package com.luv2code.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;
import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="project_type")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class ProjectType implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "project_type_id")
 @Schema(example = "1")
 private Integer projectTypeId;

 @Schema(example = "Example ProjectType")
 @Column(name = "title")
 private String title;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "insertion_timestamp")
 private Timestamp insertionTimestamp;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "close_timestamp")
 private Timestamp closeTimestamp;

 @Schema(example = "1")
 @Column(name = "user_id")
 private Integer userId;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_project_type_id")
 private ProjectType rootProjectType;

}

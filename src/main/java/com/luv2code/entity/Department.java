

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
@Table(name="department")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "department_id")
 @Schema(example = "1")
 private Integer departmentId;

 @Schema(example = "1")
 @Column(name = "parent_department_id")
 private Integer parentDepartmentId;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "close_timestamp")
 private Timestamp closeTimestamp;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "insertion_timestamp")
 private Timestamp insertionTimestamp;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "Example Department")
 @Column(name = "title")
 private String title;

 @Schema(example = "Example description of Department")
 @Column(name = "description")
 private String description;

 @ManyToOne
 @JoinColumn(name = "client_id")
 private Client client;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_department_id")
 private Department rootDepartment;

}



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
@Table(name="applications")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class Applications implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "application_id")
 @Schema(example = "1")
 private Integer applicationsId;

 @Schema(example = "Example Applications")
 @Column(name = "title")
 private String title;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "1")
 @Column(name = "user_id")
 private Integer userId;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "insertion_timestamps")
 private Timestamp insertionTimestamps;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "close_timestamps")
 private Timestamp closeTimestamps;

 @Schema(example = "string")
 @Column(name = "app_url")
 private String appUrl;

 @Schema(example = "1")
 @Column(name = "client_id")
 private Integer clientId;

 @Schema(example = "string")
 @Column(name = "short_name")
 private String shortName;

 @Schema(example = "123 Main St")
 @Column(name = "ip_address")
 private String ipAddress;

 @Schema(example = "string")
 @Column(name = "application_icon")
 private String applicationIcon;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_id")
 private Applications root;

 @ManyToOne
 @JoinColumn(name = "project_id")
 private Project project;

}



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
@Table(name="holidays")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class Holidays implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "holidays_id")
 @Schema(example = "1")
 private Integer holidaysId;

 @Schema(example = "2020-01-20")
 @Column(name = "date")
 private Date date;

 @Schema(example = "Example description of Holidays")
 @Column(name = "description")
 private String description;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @ManyToOne
 @JoinColumn(name = "project_id")
 private Project project;

 @ManyToOne
 @JoinColumn(name = "client_id")
 private Client client;

}

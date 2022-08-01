

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
@Table(name="label_constants")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class LabelConstants implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "constants_id")
 @Schema(example = "1")
 private Integer labelConstantsId;

 @Schema(example = "string")
 @Column(name = "value")
 private String value;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "string")
 @Column(name = "constant_code")
 private String constantCode;

 @Schema(example = "1")
 @Column(name = "application_id")
 private Integer applicationId;

}



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
@Table(name="language")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class Language implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "language_id")
 @Schema(example = "1")
 private Integer languageId;

 @Schema(example = "Example Language")
 @Column(name = "title")
 private String title;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "string")
 @Column(name = "code")
 private String code;

 @Schema(example = "1")
 @Column(name = "client_id")
 private Integer clientId;

}



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
@Table(name="login_security_questions")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class LoginSecurityQuestions implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "login_security_question_id")
 @Schema(example = "1")
 private Integer loginSecurityQuestionsId;

 @Schema(example = "Example LoginSecurityQuestions")
 @Column(name = "title")
 private String title;

 @Schema(example = "Example description of LoginSecurityQuestions")
 @Column(name = "description")
 private String description;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

}



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
@Table(name="users")
@JsonInclude(Include.NON_EMPTY)
@Valid
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 @Column(name = "user_id")
 @Schema(example = "1")
 private Integer usersId;

 @Schema(example = "JamesSmith@321")
 @Column(name = "username")
 private String username;

 @Schema(example = "James")
 @Column(name = "first_name")
 private String firstName;

 @Schema(example = "Smith")
 @Column(name = "last_name")
 private String lastName;

 @Schema(example = "James1234")
 @Column(name = "password")
 private String password;

 @Schema(example = "JamesSmith@mailinator.com")
 @Column(name = "email")
 private String email;

 @Schema(example = "Male")
 @Column(name = "gender")
 private String gender;

 @Schema(example = "string")
 @Column(name = "profile_image")
 private String profileImage;

 @Schema(example = "2020-01-20")
 @Column(name = "insertion_timestamp")
 private Date insertionTimestamp;

 @Schema(example = "2020-01-20")
 @Column(name = "close_timestamp")
 private Date closeTimestamp;

 @Schema(example = "1234567890")
 @Column(name = "contact")
 private String contact;

 @Schema(example = "1")
 @Column(name = "user_log_id")
 private Integer userLogId;

 @Schema(example = "string")
 @Column(name = "status")
 private String status;

 @Schema(example = "2020-01-20")
 @Column(name = "date_of_birth")
 private Date dateOfBirth;

 @Schema(example = "123 Main St")
 @Column(name = "address")
 private String address;

 @Schema(example = "1")
 @JsonIgnore
 @Column(name = "dml_status")
 private Integer dmlStatus = 1;

 @Schema(example = "1")
 @Column(name = "employee_id")
 private Integer employeeId;

 @Schema(example = "string")
 @Column(name = "marital_status")
 private String maritalStatus;

 @Schema(example = "James1234")
 @Column(name = "reset_password_hash")
 private String resetPasswordHash;

 @Schema(example = "2022-07-27T16:00:00.000+00:00")
 @Column(name = "hash_expiry")
 private Timestamp hashExpiry;

 @Schema(example = "string")
 @Column(name = "verification_code")
 private String verificationCode;

 @Schema(example = "string")
 @Column(name = "is_verified")
 private String isVerified;

 @Schema(example = "1")
 @Column(name = "login_security_question_id")
 private Integer loginSecurityQuestionId;

 @Schema(example = "string")
 @Column(name = "security_question_answer")
 private String securityQuestionAnswer;

 @Schema(example = "true")
 @Column(name = "stay_sign_in")
 private Boolean staySignIn;

 @Schema(example = "2020-01-20")
 @Column(name = "password_reset_date")
 private Date passwordResetDate;

 @ManyToOne
 @JoinColumn(name = "business_role_id")
 private BusinessRoles businessRole;

 @ManyToOne
 @JoinColumn(name = "client_id")
 private Client client;

 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "root_id")
 private Users root;

 @ManyToOne
 @JoinColumn(name = "department_id")
 private Department department;

 @ManyToOne
 @JoinColumn(name = "user_type_lov_id")
 private UserTypeLov userTypeLov;

}

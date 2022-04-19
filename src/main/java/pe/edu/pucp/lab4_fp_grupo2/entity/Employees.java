package pe.edu.pucp.lab4_fp_grupo2.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="employees")
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private int id;

    @NotBlank(message = "Este campo no debe estar vacio.")
    @Size(max=25)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Este campo no debe estar vacio.")
    @Size(max=25)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @NotBlank(message = "Este campo no debe estar vacio.")
    @Size(max = 25)
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Este campo no debe estar vacio.")
    @Column(name = "password")
    @Size(min = 8, message = "La contraseña debe tener como mínimo 8 caracteres.")
    @Size(max = 65,message = "La contraseña puede tener como máximo 65 caracteres.")
    private String password;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Jobs job;

    @Min(value = 1,message = "El salario debe ser mayor a 0.")
    @Column(name = "salary", precision = 8, scale = 2)
    private BigDecimal salary;

    @Column(name = "commission_pct", precision = 2, scale = 2)
    private BigDecimal commissionPct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employees manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Departments department;

    @Column(name = "enabled")
    private Integer enabled;

}

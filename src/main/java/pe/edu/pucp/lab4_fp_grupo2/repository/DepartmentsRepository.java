package pe.edu.pucp.lab4_fp_grupo2.repository;

import org.springframework.data.jpa.repository.Query;
import pe.edu.pucp.lab4_fp_grupo2.dto.EmpleadosDepartamentoDto;
import pe.edu.pucp.lab4_fp_grupo2.dto.SueldoDepartamentoDto;
import pe.edu.pucp.lab4_fp_grupo2.entity.Departments;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments,Integer> {

    @Query(value = "select  d.department_id as iddep, d.department_name as namedep, round(avg(e.salary)) as promedio\n" +
            "from departments d\n" +
            "join employees e on (d.department_id= e.department_id)\n" +
            "group by d.department_id;",nativeQuery = true)
    List<SueldoDepartamentoDto> listaSueldoDepartamento();

    @Query(value = "select e.employee_id as employeeid, e.first_name as nombre, e.last_name as apellido, j.job_title as cargo, e.salary as sueldo\n" +
            "from employees e\n" +
            "join jobs j on (e.job_id=j.job_id) \n" +
            "where e.department_id = ?1 \n" +
            "order by e.salary desc;" ,nativeQuery = true)
    List<EmpleadosDepartamentoDto> listaEmpleadoDepartmento (int iddep);

}

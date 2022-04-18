package pe.edu.pucp.lab4_fp_grupo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pucp.lab4_fp_grupo2.entity.Employees;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees,Integer> {

    @Query(nativeQuery = true, value="select e.first_name,e.last_name,d.department_name,j.job_title,l.city from employees e inner join jobs j on j.job_id = e.job_id\n" +
            "inner join departments d on d.department_id = e.department_id\n" +
            "inner join locations l on l.location_id = d.location_id\n" +
            "where e.first_name like %?1% or d.department_name like %?1% or e.last_name like %?1%\n" +
            "or j.job_title like %?1% or l.city like %?1%;")
    List<Employees> listarEmpleadosNombreApellidoDepartamentoPuestoCiudad(String searchTxt);

    @Query(nativeQuery = true, value="select e.first_name, e.last_name, jh.start_date as fechainicio, jh.end_date as fechafin, j.job_title as puesto,e.salary from employees e \n" +
            "inner join job_history jh on (jh.employee_id=e.employee_id)\n" +
            "inner join jobs j on (e.job_id=j.job_id) \n" +
            "order by e.salary desc;")
    List<Employees> listarEmpleadosMayorSalario();


}

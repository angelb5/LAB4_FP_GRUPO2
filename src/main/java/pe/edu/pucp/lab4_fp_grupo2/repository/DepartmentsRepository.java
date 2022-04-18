package pe.edu.pucp.lab4_fp_grupo2.repository;

import pe.edu.pucp.lab4_fp_grupo2.entity.Departments;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments,Integer> {


}

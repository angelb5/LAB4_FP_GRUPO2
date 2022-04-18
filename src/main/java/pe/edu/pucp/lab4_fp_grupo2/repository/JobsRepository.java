package pe.edu.pucp.lab4_fp_grupo2.repository;

import pe.edu.pucp.lab4_fp_grupo2.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, String> {
}

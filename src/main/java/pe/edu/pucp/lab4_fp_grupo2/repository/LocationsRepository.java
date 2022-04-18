package pe.edu.pucp.lab4_fp_grupo2.repository;

import com.example.laboratorio4.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationsRepository extends JpaRepository<Locations,Integer> {
}

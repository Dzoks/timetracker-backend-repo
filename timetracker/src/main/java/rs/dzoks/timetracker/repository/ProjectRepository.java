package rs.dzoks.timetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dzoks.timetracker.model.Project;
import rs.dzoks.timetracker.repository.repositoryCustom.ProjectRepositoryCustom;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer>, ProjectRepositoryCustom {


    List<Project> getAllByProjectManagerIdAndActive(Integer projectManagerId,Byte active);
    Project getProjectByIdAndActive(Integer id, Byte active);
}

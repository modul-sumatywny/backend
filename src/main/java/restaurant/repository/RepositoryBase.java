package restaurant.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

@NoRepositoryBean
public interface RepositoryBase<TEntity, ID> extends JpaRepository<TEntity, ID> {
    void deleteById(@Param("id") @NotNull Long id);
}

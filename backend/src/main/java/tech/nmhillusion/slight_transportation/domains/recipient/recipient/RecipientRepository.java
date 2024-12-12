package tech.nmhillusion.slight_transportation.domains.recipient.recipient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.RecipientEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */
public interface RecipientRepository extends JpaRepository<RecipientEntity, Long> {

    @Query(" select s from RecipientEntity s where :name is null or s.fullName like %:name% ")
    Page<RecipientEntity> search(String name, PageRequest pageRequest);

}

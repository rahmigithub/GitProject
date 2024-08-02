package org.rahmi.gitproject.Repository;

import org.rahmi.gitproject.Entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Long> {

    Commit findByHash(String hash);
}

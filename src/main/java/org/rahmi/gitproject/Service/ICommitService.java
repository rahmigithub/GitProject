package org.rahmi.gitproject.Service;

import org.rahmi.gitproject.Entity.Commit;

import java.util.List;


public interface ICommitService {

    void saveCommit(Commit commit);

    Commit getByHash(String hash);

    List<Commit> findAllCommits();

    Commit findById(Long id);
}

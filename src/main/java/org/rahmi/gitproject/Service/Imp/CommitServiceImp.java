package org.rahmi.gitproject.Service.Imp;

import org.rahmi.gitproject.Entity.Commit;
import org.rahmi.gitproject.Repository.CommitRepository;
import org.rahmi.gitproject.Service.ICommitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommitServiceImp implements ICommitService {

    private final CommitRepository commitRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommitServiceImp.class);


    public CommitServiceImp(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    @Override
    public void saveCommit(Commit commit) {
        try {
            logger.info("Saving commit {}", commit.toString());
            commitRepository.save(commit);
        } catch (Exception e) {
            logger.error("Error saving commit: {} Error: {}", commit,e.getMessage());
        }
    }

    @Override
    public Commit getByHash(String hash) {
        logger.info("Getting commit by hash {}", hash);
        return commitRepository.findByHash(hash);
    }

    @Override
    public List<Commit> findAllCommits() {
        logger.info("Finding all commits");
        return commitRepository.findAll();
    }

    @Override
    public Commit findById(Long id) {
        logger.info("Finding commit by id {}", id);

        Optional<Commit> optionalCommit = commitRepository.findById(id);
        if (optionalCommit.isPresent()) {
            return optionalCommit.get();
        } else {
            throw new NoSuchElementException("Commit not found with id " + id);
        }

    }
}

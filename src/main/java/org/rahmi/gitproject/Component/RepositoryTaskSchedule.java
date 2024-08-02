package org.rahmi.gitproject.Component;

import org.rahmi.gitproject.Configuration.AppConfig;
import org.rahmi.gitproject.Dto.CommitDto;
import org.rahmi.gitproject.Dto.CommitResponseDto;
import org.rahmi.gitproject.Dto.RepoDto;
import org.rahmi.gitproject.Entity.Commit;
import org.rahmi.gitproject.Entity.TaskDate;
import org.rahmi.gitproject.Entity.User;
import org.rahmi.gitproject.Service.Imp.CommitServiceImp;
import org.rahmi.gitproject.Service.Imp.TaskServiceImp;
import org.rahmi.gitproject.Service.Imp.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class RepositoryTaskSchedule {

    private final DateTimeFormatter getDateTimeFormatter;
    private final AppConfig appConfig;
    private static final Logger logger = LoggerFactory.getLogger(RepositoryTaskSchedule.class);
    private final TaskServiceImp taskServiceImp;
    private final CommitServiceImp commitServiceImp;
    private final UserServiceImp userServiceImp;

    public RepositoryTaskSchedule(DateTimeFormatter getDateTimeFormatter, AppConfig appConfig, TaskServiceImp taskServiceImp, CommitServiceImp commitServiceImp, UserServiceImp userServiceImp) {
        this.getDateTimeFormatter = getDateTimeFormatter;
        this.appConfig = appConfig;
        this.taskServiceImp = taskServiceImp;
        this.commitServiceImp = commitServiceImp;
        this.userServiceImp = userServiceImp;
    }

    @Value("${git.api.organizationName}")
    private String gitOrganizationName;

    //Her gün sabah 8 de method tetiklenecek.
    @Scheduled(cron = "0 0 8 * * ?")
    //@Scheduled(fixedRate = 30000)
    public void runTask() {

        logger.info("RepositoryTaskSchedule is running.");

        RepoDto[] repositoryNameDtos = getRepos().getBody();
        if (repositoryNameDtos != null) {
            getCommits(repositoryNameDtos);
        }

    }

    private ResponseEntity<RepoDto[]> getRepos() {

        String gitUrl = "https://api.github.com/users/" + gitOrganizationName + "/repos";

        ResponseEntity<RepoDto[]> response = null;

        try {
            response = appConfig.getrestTemplate().exchange(gitUrl, HttpMethod.GET, appConfig.httpEntity(), RepoDto[].class);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving repos. Error message:" + e.getMessage());
        }

        return response;
    }

    //Son 1 ay içindeki komitler alınılıyor ve Db tarafına kayıt ediliyor.
    public void getCommits(RepoDto[] reposDto) {

        //1 ay önceye gitmek için. minusMont'ta kullanılabilinir.
        LocalDateTime sinceWhen = LocalDateTime.now().minusDays(30);
        TaskDate lastCommitDate = taskServiceImp.getTaskDate();

        if (lastCommitDate != null) {
            sinceWhen = lastCommitDate.getDate();
        }

        String formattedDate = sinceWhen.atOffset(ZoneOffset.UTC).format(getDateTimeFormatter);


        for (RepoDto repo : reposDto) {
            String gitUrl = "https://api.github.com/repos/" + gitOrganizationName + "/" + repo.name + "/commits?since=" + formattedDate;
            try {
                ResponseEntity<CommitResponseDto[]> response = appConfig.getrestTemplate().exchange(gitUrl, HttpMethod.GET, appConfig.httpEntity(), CommitResponseDto[].class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    saveCommit(Objects.requireNonNull(response.getBody()), repo.name);
                }
            } catch (Exception e) {
                logger.error("Error, please check your repo, it may be empty or no commits have been made. Repo Name: " + repo.name + ", Error message: " + e.getMessage());
            }

        }

        //İlk kayıt işleminde else sonrakilerde if çalışacak.
        //Uygulamaya taskDate eklenmemin sebebi,en son verilerin çekildiği günü tutmak.
        if (lastCommitDate != null) {
            lastCommitDate.setDate(LocalDateTime.now());
            taskServiceImp.saveTaskDate(lastCommitDate);
        } else {
            TaskDate taskDate = new TaskDate();
            taskDate.setDate(LocalDateTime.now());
            taskServiceImp.saveTaskDate(taskDate);
        }
    }

    //Commitlerin ve comitin user'ının Db ye kayıdı gerçekleştiriliyor.
    private void saveCommit(CommitResponseDto[] commitResponseDtos, String repoName) {
        for (CommitResponseDto commitResponseDto : commitResponseDtos) {

            if (commitServiceImp.getByHash(commitResponseDto.getSha()) == null) {

                User user = userServiceImp.findByName(commitResponseDto.getCommit().getAuthor().getName());

                if (user == null) {
                    user = new User();
                    user.setName(commitResponseDto.getCommit().getAuthor().getName());
                    user.setEmail(commitResponseDto.getCommit().getAuthor().getEmail());
                    userServiceImp.saveUser(user);
                }

                Commit commit = new Commit();
                commit.setMessage(commitResponseDto.getCommit().getMessage());
                commit.setAuthor(user);
                commit.setHash(commitResponseDto.getSha());
                commit.setDate(commitResponseDto.getCommit().getAuthor().getDate());
                commit.setRepoName(repoName);
                commitServiceImp.saveCommit(commit);
            }
        }
    }

}

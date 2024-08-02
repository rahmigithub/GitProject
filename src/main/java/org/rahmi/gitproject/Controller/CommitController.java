package org.rahmi.gitproject.Controller;

import org.rahmi.gitproject.Entity.Commit;
import org.rahmi.gitproject.Service.Imp.CommitServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/commits")
public class CommitController {


    private final CommitServiceImp commitServiceImp;

    public CommitController(CommitServiceImp commitServiceImp) {
        this.commitServiceImp = commitServiceImp;
    }

    @GetMapping
    public String listCommits(Model model) {
        List<Commit> commits = commitServiceImp.findAllCommits();
        model.addAttribute("commits", commits);
        return "commit-list";
    }

    @GetMapping("/{id}")
    public String viewCommit(@PathVariable Long id, Model model) {
        Commit commit = commitServiceImp.findById(id);
        model.addAttribute("commit", commit);
        return "commit-detail";
    }
}
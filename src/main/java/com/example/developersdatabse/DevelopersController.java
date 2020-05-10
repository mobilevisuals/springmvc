package com.example.developersdatabse;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class DevelopersController {

    @Autowired
    DeveloperRepository repository;

    @Autowired
    SkillRepository skillRepository;

    @RequestMapping("/developer/{id}")
    public String developer(@PathVariable Long id, Model model) {
        model.addAttribute("developer", repository.findById(id));
        model.addAttribute("skills", skillRepository.findAll());
        return "developer";
    }

    @RequestMapping(value="/developers",method=RequestMethod.GET)
    public String developersList(Model model) {
        model.addAttribute("developers", repository.findAll());
        return "developers";
    }

    @RequestMapping(value="/developers",method=RequestMethod.POST)
    public String developersAdd(@RequestParam String email,
                                @RequestParam String firstName, @RequestParam String lastName, Model model) {
        Developer newDeveloper = new Developer();
        newDeveloper.setEmail(email);
        newDeveloper.setFirstName(firstName);
        newDeveloper.setLastName(lastName);
        repository.save(newDeveloper);

        model.addAttribute("developer", newDeveloper);
        model.addAttribute("skills", skillRepository.findAll());
        return "redirect:/developer/" + newDeveloper.getId();
    }

    @RequestMapping(value="/developer/{id}/skills", method=RequestMethod.POST)
    public String developersAddSkill(@PathVariable Long id, @RequestParam Long skillId, Model model) {
        Optional<Skill> skill = skillRepository.findById(skillId);
        Optional<Developer> developer = repository.findById(id);
Developer developerFromOptional=developer.get();
Skill skillFromOptional=skill.get();
        if (developerFromOptional != null) {
            if (!developerFromOptional.hasSkill(skillFromOptional)) {
                developerFromOptional.getSkills().add(skillFromOptional);
            }
            repository.save(developerFromOptional);

            model.addAttribute("developer", repository.findById(id));
            model.addAttribute("skills", skillRepository.findAll());
            return "redirect:/developer/" + developerFromOptional.getId();
        }

        model.addAttribute("developers", repository.findAll());
        return "redirect:/developers";
    }

}
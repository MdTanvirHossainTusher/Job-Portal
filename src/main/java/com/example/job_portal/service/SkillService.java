package com.example.job_portal.service;


import com.example.job_portal.dto.SkillDTO;
import com.example.job_portal.entity.Skill;

import java.util.List;

public interface SkillService {

    void save(Skill skill);

    List<SkillDTO> getAllSkillsUnderApp();

    SkillDTO createSkill(SkillDTO skillDTO);

    void deleteSkill(Long skillId);

}

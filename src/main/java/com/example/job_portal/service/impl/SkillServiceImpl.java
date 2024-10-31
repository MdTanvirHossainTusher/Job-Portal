package com.example.job_portal.service.impl;

import com.example.job_portal.entity.Skill;
import com.example.job_portal.repository.SkillRepository;
import com.example.job_portal.service.SkillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional
    public void save(Skill skill) {
        skillRepository.save(skill);
    }
}

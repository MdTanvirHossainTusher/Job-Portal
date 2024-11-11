package com.example.job_portal.service.impl;

import com.example.job_portal.dto.SkillDTO;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.entity.Skill;
import com.example.job_portal.exception.SkillAlreadyExistsException;
import com.example.job_portal.exception.SkillNotFoundException;
import com.example.job_portal.exception.UserNotFoundException;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.repository.SkillRepository;
import com.example.job_portal.service.SkillService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final ProfileRepository profileRepository;

    public SkillServiceImpl(SkillRepository skillRepository, ProfileRepository profileRepository) {
        this.skillRepository = skillRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public void save(Skill skill) {
        skillRepository.save(skill);
    }

    @Override
    public List<SkillDTO> getAllSkillsUnderApp() {
        return EntityToEntityDTOConverter.convertSkillsToSkillsDTO(skillRepository.findAllSkill());
    }

    @Override
    @Transactional
    public SkillDTO createSkill(SkillDTO skillDTO) {

        Optional<Skill> skill = skillRepository.findSkillById(skillDTO.getId());

        if(skill.isEmpty()) {
            Skill newSkill = new Skill();

            newSkill.setSkillName(skillDTO.getSkillName());

            return EntityToEntityDTOConverter.convertSkillToSkillDTO(skillRepository.save(newSkill));
        }
        else {
            throw new SkillAlreadyExistsException("Skill already exists!");
        }
    }

    @Override
    @Transactional
    public void deleteSkill(Long skillId) {
        Skill skill = skillRepository.findSkillById(skillId).orElseThrow(
                () -> new SkillNotFoundException(String.format("Skill with id: %d is not found!", skillId)));

        for(Profile profile: skill.getProfiles()) {
            if(!profile.isDeleted()) {
                for(Skill userSkill: profile.getSkills()) {
                    if(!userSkill.isDeleted() && userSkill.getId().equals(skillId)) {
                        profile.getSkills().remove(userSkill);
                        profileRepository.save(profile);
                    }
                }
            }
        }

        if(!skill.isDeleted()) {
            skillRepository.softDeleteSkillById(skillId);
        }
    }


}

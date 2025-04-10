package com.example.job_portal.service.impl;

import com.example.job_portal.dto.SkillDTO;
import com.example.job_portal.entity.Profile;
import com.example.job_portal.entity.Skill;
import com.example.job_portal.exception.ResourceAlreadyExistsException;
import com.example.job_portal.exception.ResourceNotFoundException;
import com.example.job_portal.repository.ProfileRepository;
import com.example.job_portal.repository.SkillRepository;
import com.example.job_portal.service.SkillService;
import com.example.job_portal.utils.EntityToEntityDTOConverter;
import com.example.job_portal.utils.SortEntityDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

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
        List<SkillDTO> skillDTOs =  EntityToEntityDTOConverter.convertSkillsToSkillsDTO(skillRepository.findAllSkill());
        return SortEntityDTO.sortResponseDTO(skillDTOs, Comparator.comparing(SkillDTO::getId).reversed());
    }

    @Override
    @Transactional
    public SkillDTO createSkill(SkillDTO skillDTO) {
        if (skillDTO.getSkillName() == null || skillDTO.getSkillName().trim().isEmpty()) {
            throw new IllegalArgumentException("Skill name cannot be empty or whitespace-only");
        }
        List<Skill> skills = skillRepository.findAllSkill();
        boolean isSkillExists = false;

        for (Skill existingSkill : skills) {
            if (existingSkill != null &&
                    existingSkill.getSkillName().equalsIgnoreCase(skillDTO.getSkillName().trim())) {
                isSkillExists = true;
                break;
            }
        }
        if (!isSkillExists) {
            Skill newSkill = new Skill();
            if(skillDTO.getSkillName() != null) newSkill.setSkillName(skillDTO.getSkillName());
            return EntityToEntityDTOConverter.convertSkillToSkillDTO(skillRepository.save(newSkill));
        } else {
            throw new ResourceAlreadyExistsException("Skill already exists!");
        }
    }

    @Override
    @Transactional
    public void deleteSkill(Long skillId) {
        Skill skill = skillRepository.findSkillById(skillId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Skill with id: %d is not found!", skillId)));

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

package ua.goit.javaee.group2.dao;

import ua.goit.javaee.group2.model.Skill;

public interface SkillDAO extends AbstractDAO<Skill> {
    Skill getByName(String name);
}

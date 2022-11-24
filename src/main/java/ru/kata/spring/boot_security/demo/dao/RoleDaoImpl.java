package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> getRoleID(int id) {
        return Optional.ofNullable(entityManager.find(Role.class,id));
    }

    @Override
    public void saveRoleName(String name) {
        entityManager.persist(new Role(name));
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        TypedQuery<Role> query = entityManager
                .createQuery("select r from Role r where r.role = :name", Role.class)
                .setParameter("name", name);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Role> getAllRoles() {
        //return entityManager.createQuery("select r FROM Role r", Role.class).getResultList();
        TypedQuery<Role> query = entityManager
                .createQuery("FROM Role", Role.class);
        return query.getResultList();
    }

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

}

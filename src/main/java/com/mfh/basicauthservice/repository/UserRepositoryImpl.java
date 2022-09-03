package com.mfh.basicauthservice.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.mfh.commonmodel.account.Account;
import com.mfh.commonmodel.user.User;

@Repository
public class UserRepositoryImpl implements CustomUserRepository {

  @Autowired
  private EntityManager entityManager;

  @Override
  public User findByUsername(String userName) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
    Root<User> root = criteriaQuery.from(User.class);
    Join<Account, User> join = root.join("account", JoinType.INNER);
    //    criteriaQuery.select(root);
    //    criteriaQuery.multiselect(root)
    Predicate usernamePredicate = criteriaBuilder.equal(join.get("accountName"), userName);
    criteriaQuery.where(usernamePredicate);
    TypedQuery<User> query = entityManager.createQuery(criteriaQuery);

    Optional<User> userOptional = query.getResultList()
        .stream()
        .findFirst();

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Hibernate.initialize(user.getRoles());
      return user;
    }
    return null;
  }

  @Override
  public Optional<User> findByRid(String rid) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
    Root<User> root = criteriaQuery.from(User.class);
    criteriaQuery.select(root);
    criteriaQuery.where(criteriaBuilder.equal(root.get("rid"), rid));
    TypedQuery<User> query = entityManager.createQuery(criteriaQuery);

    List<User> userList = query.getResultList();

    if (CollectionUtils.isEmpty(userList)) {
      return Optional.empty();
    }

    User user = userList.get(0);
    Hibernate.initialize(user.getRoles());
    return Optional.of(user);
  }

  public Class<User> getEntityClass() {
    return User.class;
  }
}

package ru.mts.teta.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mts.teta.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User u " +
                "where u.id not in ( " +
                    "select u.id " +
                    "from User u " +
                    "left join u.courses c " +
                    "where c.id = :courseId)")
    Set<User> findUsersNotAssignedToCourse(@Param("courseId") Long courseId);

    @Query("from User u " +
                "where u.id in ( " +
                    "select u.id " +
                    "from User u " +
                    "left join u.courses c " +
                    "where c.id = :courseId)")
    Set<User> findUsersAssignedToCourse(@Param("courseId") Long courseId);

    List<User> findByUsernameLike(String username);

    Optional<User> findUserByUsername(String username);
}

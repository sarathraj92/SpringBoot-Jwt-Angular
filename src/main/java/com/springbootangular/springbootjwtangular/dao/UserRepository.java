package com.springbootangular.springbootjwtangular.dao;

import com.springbootangular.springbootjwtangular.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    public User findByUsername(String username);

    @Query(value = "SELECT users.user_id,users.email,users.enabled,users.first_name,users.gender,users.image,users.last_name,users.password,users.phone_number,users.username,users.deleted " +
            "FROM users " +
            "JOIN user_role ON users.user_id = user_role.user_user_id " +
            "JOIN roles ON user_role.role_role_id = roles.role_id " +
            "WHERE roles.role_name = 'USER' AND users.deleted = 'false';",nativeQuery = true)
    public List<User> findAllBy();


    User getUserById(long id);
}

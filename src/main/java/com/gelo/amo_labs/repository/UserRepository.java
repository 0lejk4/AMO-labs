package com.gelo.amo_labs.repository;


import com.gelo.amo_labs.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

    User findByEmail(String email);

}

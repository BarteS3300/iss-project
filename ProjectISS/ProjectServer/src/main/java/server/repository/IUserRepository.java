package server.repository;

import common.domain.User;

import java.util.Optional;

public interface IUserRepository extends Repository<Long, User> {

    Optional<User> findOneByUsername(String username);
}

package com.shopngo.auction.user.serice;

import com.shopngo.auction.user.dao.repository.UserRepository;
import com.shopngo.auction.user.domain.UserModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class UserService {

    private final UserConverterService converter;
    private final UserRepository repository;

    public void addUser(UserModel model) {
        repository.save(converter.convert(model));
    }

    public void deleteAllUsers() {
        repository.deleteAll();
    }

    public Optional<UserModel> getUserById(String id) {
        return repository.findById(id).map(converter::convert);
    }

    public Optional<UserModel> getUserByName(String name) {
        return repository.findUserEntityByName(name).map(converter::convert);
    }

    public List<UserModel> getAllUsers() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}

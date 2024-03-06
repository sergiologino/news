package ru.altacod.news.news.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.User;
import ru.altacod.news.news.repository.UserRepository;
import ru.altacod.news.news.service.UserService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(MessageFormat.format("Пользователь с id {0} не найден",id)));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }
}

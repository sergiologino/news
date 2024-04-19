package ru.altacod.news.news.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.altacod.news.news.exception.EntityNotFoundException;
import ru.altacod.news.news.model.User;
import ru.altacod.news.news.repository.DbUserRepository;
import ru.altacod.news.news.service.UserService;
import ru.altacod.news.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbUserService implements UserService {

    private final DbUserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Пользователь с ID {0} не найден", id)));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existingUser = findById(user.getId());
        BeanUtils.copyNonNullProperties(user, existingUser);
        return userRepository.save(user);
    }
}

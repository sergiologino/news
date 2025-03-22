package ru.altacod.news.repository.impl;

import org.springframework.stereotype.Repository;
import ru.altacod.news.exception.EntityNotFoundException;
import ru.altacod.news.model.User;
import ru.altacod.news.repository.UserRepository;
import ru.altacod.news.utils.BeanUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {


    private final Map<Long, User> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<User> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public User save(User user) {

        Long userId = currentId.getAndIncrement();
        user.setId(userId);
        repository.put(userId,user);
        return user;
    }

    @Override
    public User update(User user) {
        Long userId = user.getId();
        User currentUser = repository.get(userId);
        if(currentUser==null){
            throw new EntityNotFoundException(MessageFormat.format("Пользователь с ID {0} не найден", userId));
        }
        BeanUtils.copyNonNullProperties(user,currentUser);
        currentUser.setId(userId);
        repository.put(userId,currentUser);
        return currentUser;
    }

    @Override
    public User delete(User user) {
        if (user != null && user.getId() != null) {
            repository.remove(user.getId());
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        repository.remove(id);
   }
}

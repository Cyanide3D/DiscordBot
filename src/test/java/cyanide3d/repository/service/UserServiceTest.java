package cyanide3d.repository.service;


import cyanide3d.repository.model.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService service;
    private UserEntity emptyEntity;

    @Before
    public void init() {
        service = UserService.getInstance();
        emptyEntity = new UserEntity("123", "123");
    }

    @Test
    public void dao() {
        UserEntity savedEntity = service.incrementExpOrCreate("123", "123");
        assertEquals(emptyEntity, savedEntity);
        UserEntity incrementedEntity = service.incrementExpOrCreate("123", "123");
        assertTrue(incrementedEntity.getLvl() == 0 && incrementedEntity.getExp() == 2);
        UserEntity user = service.getUserById("123", "123");
        assertEquals(user, incrementedEntity);
        List<UserEntity> allUsers = service.getAllUsers("123");
        assertFalse(allUsers.isEmpty());
        service.deleteUserById("123","123");
    }
}
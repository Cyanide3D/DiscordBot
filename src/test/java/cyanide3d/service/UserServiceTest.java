package cyanide3d.service;


import cyanide3d.dto.UserEntity;
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
        UserEntity user = service.getUser("123", "123");
        assertEquals(user, incrementedEntity);
        List<UserEntity> allUsers = service.getAllUsers("123");
        assertFalse(allUsers.isEmpty());
        service.deleteUser("123","123");
    }
}
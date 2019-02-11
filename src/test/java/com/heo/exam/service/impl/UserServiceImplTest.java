package com.heo.exam.service.impl;

import com.heo.exam.form.UserInfoForm;
import com.heo.exam.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserInfo() {
    }

    @Test
    public void updateUserInfo() {
        UserInfoForm userInfoForm = new UserInfoForm();
        userInfoForm.setName("刘康");
        userInfoForm.setAvatar("/avatar");
        userInfoForm.setSex(0);
        userInfoForm.setNid("15131112");
        userInfoForm.setPhone("18021396096");
        userService.updateUserInfo("1", userInfoForm);
    }
}
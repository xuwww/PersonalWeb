package com.personalweb.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.personalweb.demo.dto.UserSignInDTO;
import com.personalweb.demo.exception.CustomizeErrorCode;
import com.personalweb.demo.mapper.UserMapper;
import com.personalweb.demo.model.User;
import com.personalweb.demo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.personalweb.demo.exception.CustomizeErrorCode.CONTENT_IS_EMPTY;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    //更新用户分类表
    public CustomizeErrorCode categoryAdd(User user, JSONObject jsonObject) {
        //检查分类是否重复
        String categories = user.getCategories();
        String category = jsonObject.getString("category");
        if (category.isEmpty()) {
            return CustomizeErrorCode.CONTENT_IS_EMPTY;
        }
        if (categories == null || categories.equals("")) {
            user.setCategories(category);
            userMapper.updateByPrimaryKey(user);
        } else {
            List<String> strings = Arrays.asList(categories.split(","));
            long count = strings.stream().filter(string -> string.equals(category)).count();
            if (count > 0) {
                return CustomizeErrorCode.CATEGORY_REPEAT;
            } else {
                user.setCategories(categories + "," + category);
                userMapper.updateByPrimaryKey(user);
            }
        }
        return null;
    }

    public UserSignInDTO signIn(JSONObject jsonObject) {
        String userId=jsonObject.getString("userId");
        String password = jsonObject.getString("userPassword");
        UserSignInDTO userSignInDTO=new UserSignInDTO();
        if (userId == null || userId.equals("")||password == null || password.equals("")) {
            userSignInDTO.setProperty(0);
            userSignInDTO.setCustomizeErrorCode(CustomizeErrorCode.CONTENT_IS_EMPTY);
            return userSignInDTO;
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(userId);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            userSignInDTO.setProperty(0);
            userSignInDTO.setCustomizeErrorCode(CustomizeErrorCode.NO_USER);
            return userSignInDTO;
        } else if (!password.equals(users.get(0).getPassword())) {
            userSignInDTO.setProperty(0);
            userSignInDTO.setCustomizeErrorCode(CustomizeErrorCode.WRONG_PASSWORD);
            return userSignInDTO;
        }else{
            userSignInDTO.setProperty(1);
            userSignInDTO.setToken(users.get(0).getToken());
            return userSignInDTO;
        }
    }
}

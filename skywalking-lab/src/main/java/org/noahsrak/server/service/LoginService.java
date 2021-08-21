package org.noahsrak.server.service;

import org.springframework.stereotype.Service;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/8/22
 */
@Service
public class LoginService {

    public void checkUser(String name) {

        System.out.println("user login:" + name);

    }
}

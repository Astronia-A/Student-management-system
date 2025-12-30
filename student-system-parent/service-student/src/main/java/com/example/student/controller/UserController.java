package com.example.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Result;
import com.example.student.entity.User;
import com.example.student.service.UserService;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.wf.captcha.SpecCaptcha;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 注册接口
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        // 1. 简单的校验
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.fail("用户名或密码不能为空");
        }

        // 2. 查询用户名是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User existUser = userService.getOne(wrapper);
        if (existUser != null) {
            return Result.fail("用户名已存在");
        }

        // 3. 保存到数据库
        boolean saved = userService.save(user);
        if (saved) {
            return Result.ok("注册成功");
        } else {
            return Result.fail("注册失败");
        }
    }

    // 验证码
    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response) throws Exception {
        // 1. 创建字符验证码（长130，宽48，包含4个字符）
        com.wf.captcha.SpecCaptcha captcha = new com.wf.captcha.SpecCaptcha(130, 48, 4);

        // 2. 获取验证码的文字结果（比如 "ab12"），转为小写存储以便校验
        String result = captcha.text().toLowerCase();

        // 3. 存入 Redis，设置 2 分钟有效期
        redisTemplate.opsForValue().set("CAPTCHA_KEY", result, 2, java.util.concurrent.TimeUnit.MINUTES);

        // 4. 设置响应头，告诉浏览器这是图片
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 5. 输出图片
        captcha.out(response.getOutputStream());
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User loginUser, @RequestParam("code") String userInputCode) {
        // 1. 从 Redis 中获取正确的验证码答案
        String realCode = redisTemplate.opsForValue().get("CAPTCHA_KEY");

        // 2. 校验验证码（忽略大小写）
        if (realCode == null) {
            return Result.fail("验证码已过期，请刷新重试");
        }
        if (!realCode.equalsIgnoreCase(userInputCode)) {
            return Result.fail("验证码错误");
        }

        // 3. 验证码正确后，校验用户名和密码
        // 使用 MyBatis-Plus 的查询构造器
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, loginUser.getUsername())
                .eq(User::getPassword, loginUser.getPassword());

        User user = userService.getOne(wrapper);

        if (user != null) {
            // 登录成功，为了安全，清空返回对象中的密码
            user.setPassword(null);
            // 登录成功后，建议删除 Redis 中的验证码，防止被重复使用
            redisTemplate.delete("CAPTCHA_KEY");
            return Result.ok(user);
        } else {
            return Result.fail("用户名或密码错误");
        }
    }


}


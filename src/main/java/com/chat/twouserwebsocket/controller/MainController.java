package com.chat.twouserwebsocket.controller;

import com.chat.twouserwebsocket.model.ChatDTO;
import com.chat.twouserwebsocket.model.UserDTO;
import com.chat.twouserwebsocket.service.ChatService;
import com.chat.twouserwebsocket.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {

        HttpSession session = request.getSession();
        session.invalidate();

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("""
                <script>
                	alert('로그아웃 되었습니다.');
                    location.href='/login';
                </script>""");
    }


    @PostMapping("/login")
    @ResponseBody
    public String login(UserDTO userDTO, HttpServletRequest request) throws IOException {

        String jsScript = "";
        boolean login = userService.login(userDTO, request);

        if (login) {
            HttpSession session = request.getSession();
            System.out.println(userDTO.getUserNm());
            session.setAttribute("userNm", userDTO.getUserNm());
            session.setMaxInactiveInterval(60 * 30);

            jsScript += """
                    <script>
                       alert('로그인 되었습니다.');
                       location.href='main';
                    </script>
                    """;
            return jsScript;
        } else {
            jsScript += """
                         <script>
                    alert('로그인에 실패하였습니다.');
                             history.go(-1);
                         </script>
                         """;
        }

        return jsScript;
    }

    @GetMapping("/chating")
    public String chating() {
        return "index1";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(UserDTO userDTO) {

        String jsScript = "";
        userService.addUser(userDTO);

        jsScript += """
                 <script>
                alert('회원가입 되었습니다.');
                         location.href='/login';
                     </script>
                 """;


        return jsScript;
    }



    @GetMapping("/main")
    public String main(Model model) {
        List<ChatDTO> chatList = chatService.getChatList();
        model.addAttribute("chatList", chatList);
        return "main";
    }

    @GetMapping("/createChatRoom")
    public String createChatRoom(Model model) {
        model.addAttribute("chatList", chatService.getChatList());
        return "createChatRoom";
    }

    @PostMapping("/createChatRoom")
    @ResponseBody
    public String createChatRoom(ChatDTO chatDTO) {

        String jsScript = "";
        chatService.createChatRoom(chatDTO);

        jsScript = """
                <script>
                	 alert('채팅방이 생성 되었습니다.');
                	location.href='/main';
                </script>""";

        return jsScript;
    }

}

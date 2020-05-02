package com.example.switter.controller;

import com.example.switter.domain.Message;
import com.example.switter.domain.User;
import com.example.switter.repos.MessageRepo;
import com.example.switter.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "-1") String filter,
                       @RequestParam(required = false, name = "author", defaultValue = "-2") String username, Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        if (!filter.equals("-1")) {
            if (filter != null && !filter.isEmpty()) {
                messages = messageRepo.findByTag(filter);
            } else {
                messages = messageRepo.findAll();
            }
            model.addAttribute("filter", filter);
        } else {
            model.addAttribute("filter", "");
        }

        if (!username.equals("-2")) {
            if (username != null && !username.isEmpty()) {
                User author = userRepo.findByUsername(username);
                messages = messageRepo.findByAuthor(author);
            }
            if (username.isEmpty()) {
                messages = messageRepo.findAll();
            }
            model.addAttribute("author", username);
        } else {
            model.addAttribute("author", "");
        }

        model.addAttribute("messages", messages);

        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> model,
                      @RequestParam("file") MultipartFile file
    ) throws IOException {
        Message message = new Message(text, tag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }
}

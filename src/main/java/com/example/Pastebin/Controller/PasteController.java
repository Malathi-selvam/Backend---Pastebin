package com.example.Pastebin.Controller;

import com.example.Pastebin.entity.Paste;
import com.example.Pastebin.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/paste")
@CrossOrigin(origins = "http://localhost:5173")
public class PasteController {

    @Autowired
    private PasteService service;

    @PostMapping
    public Map<String, String> createPaste(@RequestBody Map<String, Object> request) {

        String content = (String) request.get("content");

        Integer maxViews = request.get("maxViews") != null
                ? Integer.parseInt(request.get("maxViews").toString())
                : 5; // default views

        Paste paste = service.createPaste(content, maxViews);

        return Map.of(
            "url", "http://localhost:5173/paste/" + paste.getId()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPaste(@PathVariable String id) {

        Paste paste = service.getPaste(id);

        if (paste == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("error", "Paste not found or expired"));
        }

        return ResponseEntity.ok(
                Map.of(
                        "content", paste.getContent(),
                        "createdAt", paste.getCreatedAt().toString()
                )
        );
    }

    


}

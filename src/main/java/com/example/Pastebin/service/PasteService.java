package com.example.Pastebin.service;

import com.example.Pastebin.entity.Paste;
import com.example.Pastebin.repository.PasteReposiitory;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasteService {

    @Autowired
    private PasteReposiitory repository;

    public Paste createPaste(String content, int maxViews) {

        Paste paste = new Paste();
        paste.setId(UUID.randomUUID().toString().substring(0, 8));
        paste.setContent(content);
        paste.setCreatedAt(LocalDateTime.now());
        paste.setMaxViews(maxViews);
        paste.setViewCount(0);

        return repository.save(paste);
    }

    
    public Paste getPaste(String id) {

        Paste paste = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paste not found"));

        if (paste.getViewCount() >= paste.getMaxViews()) {
            repository.delete(paste);
            throw new RuntimeException("Paste expired (view limit reached)");
        }

        paste.setViewCount(paste.getViewCount() + 1);
        repository.save(paste);

        return paste;
    }

}



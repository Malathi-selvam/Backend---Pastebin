package com.example.Pastebin.repository;

import com.example.Pastebin.entity.Paste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteReposiitory extends JpaRepository<Paste, String> {
}

package ru.mirea.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.chat.model.StickerModel;
import ru.mirea.chat.repository.IStickerRepository;

import java.util.List;

@Service
public class StickerService {
    private final IStickerRepository isr;

    @Autowired
    public StickerService(IStickerRepository isr) {
        this.isr = isr;
    }

    public List<StickerModel> getAllStickers() {
        return isr.getAll();
    }
}
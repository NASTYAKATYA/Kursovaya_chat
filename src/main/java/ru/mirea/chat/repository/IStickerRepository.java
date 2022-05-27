package ru.mirea.chat.repository;

import org.springframework.stereotype.Repository;
import ru.mirea.chat.model.StickerEnum;
import ru.mirea.chat.model.StickerModel;

import java.util.ArrayList;
import java.util.List;

@Repository
public class IStickerRepository {
    public List<StickerModel> getAll() {
        List <StickerModel> stickerList = new ArrayList<>();
        for (StickerEnum stickerEnum : StickerEnum.values()) {
            stickerList.add(new StickerModel(stickerEnum.ordinal(), stickerEnum.name()));
        }
        return stickerList;
    }
}
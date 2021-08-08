package me.astropvp.enums;

import com.cosmicpvp.cosmicutils.utils.CC;

public enum Language {


    CREATE_TOURNAMENT(CC.translate("&a&l(!)&a Tournament has been created. &f/tournament join&a to enter!")),
    NO_ACTIVE_TOURNAMENT(CC.translate("&c&l(!)&c There are currently no tournament running!")),
    FULL_TOURNAMENT(CC.translate("&c&l(!)&c You cannot join this tournament anymore since it's full!")),
    LEAVE_TOURNAMENT(CC.translate("&c&l(!)&c You have left the tournament!")),
    CLOSE_TOURNAMENT("&c&l(!)&c Tournament has ended!");

    private String message;

    Language(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

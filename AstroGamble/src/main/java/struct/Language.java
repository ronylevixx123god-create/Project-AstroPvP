package struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
@Getter
public enum Language {
    COMMAND_IN_COMBAT(CC.translate("&c&l(!)&c You are not allowed to run this command while in combat.")),
    CONFIRMATION_GUI_TITLE(CC.translate("&8Gamble Confirmation Gui")),
    MAIN_GUI_TITLE(CC.translate("&8Main Gamble Gui")),
    ROLLING_GUI(CC.translate("&8Rolling..."));

    public String message;
    Language(String message) {
        this.message = message;
    }


}

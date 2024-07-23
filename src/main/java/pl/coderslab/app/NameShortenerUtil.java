package pl.coderslab.app;

import org.springframework.data.repository.query.ParameterOutOfBoundsException;

public class NameShortenerUtil {
    public static String shortenName(String text, int mode, int maxLength) {
        // mode:    1 = obcina od końca i wstawia trójkropek, 2 = obcina od początku i wstawia trójkropek,
        //          3 = obcina od końca i nie wstawia 3k, 4 = obcina od pocz. i nie wstawia 3k
        // maxLength: ile ma pozostać znaków przed obcięciem

        if (mode < 1 || mode > 4 || maxLength < 0 || maxLength > 35) {
            throw new ParameterOutOfBoundsException("Shortening name went wrong: parameter out of bounds", null);
        }

        if (text != null && text.length() <= maxLength) {
            return text;
        }

        switch (mode) {
            case 1:
                return text.substring(0, maxLength) + "…";
            case 2:
                return "…" + text.substring(text.length() - maxLength);
            case 3:
                return text.substring(0, maxLength);
            case 4:
                return text.substring(text.length() - maxLength);
            default:
                return text;
        }
    }
}


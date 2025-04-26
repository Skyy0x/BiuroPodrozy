package pl.sky0x.travelAgency.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryValidator implements ConstraintValidator<ValidCountry, String> {

    private static final String[] COUNTRIES = {
            "af", "al", "dz", "as", "ad", "ao", "ai", "aq", "ar", "am", "aw", "au", "at", "az",
            "bs", "bh", "bd", "bb", "by", "be", "bz", "bj", "bm", "bt", "bo", "ba", "bw", "bv",
            "br", "io", "bn", "bg", "bf", "bi", "kh", "cm", "ca", "cv", "ky", "cf", "td", "cl",
            "cn", "cx", "cc", "co", "km", "cg", "cd", "ck", "cr", "ci", "hr", "cu", "cy", "cz",
            "dk", "dj", "dm", "do", "ec", "eg", "sv", "gq", "er", "ee", "sz", "et", "fj", "fi",
            "fr", "gf", "pf", "tf", "ga", "gm", "ge", "de", "gh", "gi", "gr", "gl", "gd", "gp",
            "gu", "gt", "gg", "gn", "gw", "gy", "ht", "hm", "hn", "hk", "hu", "is", "in", "id",
            "ir", "iq", "ie", "il", "it", "jm", "jp", "je", "jo", "kz", "ke", "ki", "kp", "kr",
            "kw", "kg", "la", "lv", "lb", "ls", "lr", "ly", "li", "lt", "lu", "mo", "mk", "mg",
            "mw", "my", "mv", "ml", "mt", "mh", "mq", "mr", "mu", "yt", "mx", "fm", "md", "mc",
            "mn", "me", "ms", "ma", "mz", "mm", "na", "nr", "np", "nl", "nc", "nz", "ni", "ne",
            "ng", "nu", "nf", "mp", "no", "om", "pk", "pw", "ps", "pa", "pg", "py", "pe", "ph",
            "pn", "pl", "pt", "pr", "qa", "re", "ro", "ru", "rw", "sa", "sb", "sc", "sd", "sr",
            "ss", "se", "sg", "sx", "sy", "tw", "tj", "tz", "th", "tg", "tk", "to", "tt", "tn",
            "tr", "tm", "tc", "tv", "ug", "ua", "ae", "gb", "us", "uy", "uz", "vu", "ve", "vn",
            "wf", "eh", "ye", "zm", "zw"
    };


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        for (String country : COUNTRIES) {
            if (country.equals(value)) {
                return true;
            }
        }
        return false;
    }
}

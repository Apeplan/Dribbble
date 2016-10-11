package com.simon.dribbble.data;

import android.text.TextUtils;

import com.simon.dribbble.data.model.ImagesEntity;
import com.simon.dribbble.data.model.ShotEntity;
import com.simon.dribbble.data.model.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/10/11 0011 17:30
 */

public class SearchConverter implements Converter<ResponseBody, List<ShotEntity>> {

    private SearchConverter() {
    }

    static final SearchConverter INSTANCE = new SearchConverter();
    private static final String HOST = "https://dribbble.com";
    private static final Pattern PATTERN_PLAYER_ID =
            Pattern.compile("users/(\\d+?)/", Pattern.DOTALL);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM d, yyyy");

    /**
     * Factory for creating converter. We only care about decoding responses.
     **/
    public static final class Factory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                                Annotation[] annotations,
                                                                Retrofit retrofit) {
            return INSTANCE;
        }

    }

    @Override
    public List<ShotEntity> convert(ResponseBody value) throws IOException {
        final Elements shotsElements = Jsoup.parse(value.string(), HOST).select
                ("li[id^=screenshot]");
        final List<ShotEntity> shots = new ArrayList<>(shotsElements.size());
        for (Element element : shotsElements) {
            ShotEntity shot = parseShot(element, DATE_FORMAT);
            if (null != shot) {
                shots.add(shot);
            }
        }

        return shots;
    }

    private ShotEntity parseShot(Element element, SimpleDateFormat dateFormat) {
        final Element descriptionBlock = element.select("a.dribbble-over").first();
        // API responses wrap description in a <p> tag. Do the same for consistent display.
        String description = descriptionBlock.select("span.comment").text().trim();
        if (!TextUtils.isEmpty(description)) {
            description = "<p>" + description + "</p>";
        }
        String imgUrl = element.select("img").first().attr("src");
        if (imgUrl.contains("_teaser.")) {
            imgUrl = imgUrl.replace("_teaser.", ".");
        }
        Date createdAt = null;
        String created_at = null;
        try {
            created_at = descriptionBlock.select("em.timestamp").first().text();
            createdAt = dateFormat.parse(descriptionBlock.select("em.timestamp").first().text());
        } catch (ParseException e) {
        }

        ShotEntity shot = new ShotEntity();
        shot.setId(Long.parseLong(element.id().replace("screenshot-", "")));
        shot.setHtml_url(HOST + element.select("a.dribbble-link").first().attr("href"));
        shot.setTitle(descriptionBlock.select("strong").first().text());
        shot.setDescription(description);
        ImagesEntity imagesEntity = new ImagesEntity();
        imagesEntity.setNormal(imgUrl);
        shot.setImages(imagesEntity);
        shot.setAnimated(element.select("div.gif-indicator").first() != null);
        shot.setCreated_at(created_at);
        shot.setLikes_count(Integer.parseInt(element.select("li.fav").first().child(0).text()
                .replaceAll(",", "")));
        shot.setComments_count(Integer.parseInt(element.select("li.cmnt").first().child(0).text
                ().replaceAll(",", "")));
        shot.setViews_count(Integer.parseInt(element.select("li.views").first().child(0)
                .text().replaceAll(",", "")));
        shot.setUser(parsePlayer(element.select("h2").first()));

        return shot;
    }

    private static User parsePlayer(Element element) {
        final Element userBlock = element.select("a.url").first();
        String avatarUrl = userBlock.select("img.photo").first().attr("src");
        if (avatarUrl.contains("/mini/")) {
            avatarUrl = avatarUrl.replace("/mini/", "/normal/");
        }
        final Matcher matchId = PATTERN_PLAYER_ID.matcher(avatarUrl);
        Long id = -1l;
        if (matchId.find() && matchId.groupCount() == 1) {
            id = Long.parseLong(matchId.group(1));
        }
        final String slashUsername = userBlock.attr("href");
        final String username =
                TextUtils.isEmpty(slashUsername) ? null : slashUsername.substring(1);
        return new User.Builder()
                .setId(id)
                .setName(userBlock.text())
                .setUsername(username)
                .setHtmlUrl(HOST + slashUsername)
                .setAvatarUrl(avatarUrl)
                .setPro(element.select("span.badge-pro").size() > 0)
                .build();
    }
}

package repositories;

import com.google.inject.Inject;
import org.jooq.DSLContext;

import java.util.List;

import static models.Tables.AUTHOR;
import static models.Tables.BOOK;

public class Repository {

    @Inject
    private DSLContext database;

    public Repository() {
    }

    public List<String> trendingTitles() {
        org.jooq.Result<?> result = database.select(BOOK.TITLE)
                .from(AUTHOR)
                .join(BOOK)
                .on(BOOK.AUTHOR_ID.eq(AUTHOR.ID))
                .orderBy(BOOK.ID.asc())
                .fetch();

        return result.getValues(BOOK.TITLE);
    }
}

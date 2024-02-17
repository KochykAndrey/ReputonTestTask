package by.kochyk.ReputonTestTask.service;

import by.kochyk.ReputonTestTask.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final WebClient webClient;

    @Override
    @Cacheable(value = "ReviewServiceImpl.getReviewByDomain", key = "#domain")
    public Mono<Review> getReviewByDomain(String domain) {
        return webClient
                .get()
                .uri(String.join("", "/review/", domain))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Domain " + domain + " not found")))
                .bodyToMono(String.class)
                .map(this::parseReviews);
    }

    private Review parseReviews(String html) {
        Document doc = Jsoup.parse(html);

        Element reviewsCountElement = doc.selectFirst(".styles_text__W4hWi");
        Element ratingElement = doc.selectFirst(".styles_rating__uyC6m > p:nth-child(2)");

        if (ratingElement == null || reviewsCountElement == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error parsing data");
        }

        int reviewsCount = Integer.parseInt(reviewsCountElement.text().replaceAll("[^0-9]", ""));
        double rating = Double.parseDouble(ratingElement.text());

        return new Review(reviewsCount, rating);
    }

    @CacheEvict(value = "ReviewServiceImpl.getReviewByDomain", allEntries = true)
    @Scheduled(fixedDelay = 60000)
    public void clearCache() {
        //Simple example how clear cache
    }

}

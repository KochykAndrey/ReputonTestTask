package by.kochyk.ReputonTestTask.service;

import by.kochyk.ReputonTestTask.domain.review.Review;
import reactor.core.publisher.Mono;

public interface ReviewService {

    Mono<Review> getReviewByDomain(String domain);

}

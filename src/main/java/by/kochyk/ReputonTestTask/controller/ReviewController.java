package by.kochyk.ReputonTestTask.controller;

import by.kochyk.ReputonTestTask.domain.review.Review;
import by.kochyk.ReputonTestTask.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{domain}")
    public Mono<Review> getReviewByDomain(@PathVariable final String domain) {
        return reviewService.getReviewByDomain(domain);
    }

}

//
//  ReviewFormViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 1/30/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class ReviewFormViewModel: ViewModel, ObservableObject {
    @Published var review: Review?
    
    let reviewRepository: ReviewRepository
    
    init(reviewRepository: ReviewRepository) {
        self.reviewRepository = reviewRepository
    }
    
    func createReview(dessertId: String, newReview: Review) {
        reviewRepository.doNewReview(dessertId: dessertId, reviewInput: ReviewInput(rating: Int32(newReview.rating), text: newReview.text)) { [weak self] (data, error) in
            guard let self = self,
                  let newDessert = data else { return }
            self.review = newDessert
        }
    }
    
    func updateReview(review: Review) {
        reviewRepository.updateReview(reviewId: review.id, reviewInput: ReviewInput(rating: Int32(review.rating), text: review.text)) { [weak self] (data, error) in
            guard let self = self,
                  let updatedDessert = data else { return }
            self.review = updatedDessert
        }
    }
    
    func deleteReview(reviewId: String) {
        reviewRepository.deleteReview(reviewId: reviewId) { [weak self] (deleted, error) in
            guard let self = self else { return }
            self.review = nil
        }
    }
}

//
//  ReviewFormViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 1/30/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class ReviewFormViewModel: ObservableObject {
    @Published var review: Review?
    
    let repository: ReviewRepository = ReviewRepository(apolloProvider: Apollo.shared.apolloProvider)
    
    func createReview(dessertId: String, newReview: Review) {
        repository.doNewReview(dessertId: dessertId, reviewInput: ReviewInput(rating: Int32(newReview.rating), text: newReview.text)) { [weak self] (data, error) in
            guard let self = self,
                  let newDessert = data else { return }
            self.review = newDessert
        }
    }
    
    func updateReview(review: Review) {
        repository.updateReview(reviewId: review.id, reviewInput: ReviewInput(rating: Int32(review.rating), text: review.text)) { [weak self] (data, error) in
            guard let self = self,
                  let updatedDessert = data else { return }
            self.review = updatedDessert
        }
    }
    
    func deleteReview(reviewId: String) {
        repository.deleteReview(reviewId: reviewId) { [weak self] (deleted, error) in
            guard let self = self else { return }
            self.review = nil
        }
    }
}

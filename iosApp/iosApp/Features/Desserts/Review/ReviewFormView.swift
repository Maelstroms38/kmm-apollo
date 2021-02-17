//
//  ReviewCreateView.swift
//  iosApp
//
//  Created by Michael Stromer on 1/30/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

@available(iOS 14.0, *)
struct ReviewFormView: View {
    
    @StateObject var viewModel = ViewModelFactory.viewModel(forType: ReviewFormViewModel.self)
    
    let review: Review?
    
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        VStack {
            ReviewForm(handler: { review, action in
                switch action {
                case .create:
                    viewModel.createReview(dessertId: review.dessertId, newReview: review)
                case .update:
                    viewModel.updateReview(review: review)
                case .delete_:
                    viewModel.deleteReview(reviewId: review.id)
                    
                default:
                    break
                }
                presentationMode.wrappedValue.dismiss()
            },
            reviewId: review?.id ?? "new", dessertId: review?.dessertId ?? "new", text: review?.text ?? "", rating: review?.rating ?? 0)
        }
        .navigationBarTitle("", displayMode: .inline)
        .onAppear() {
            self.viewModel.review = review
        }
    }
}

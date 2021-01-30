//
//  DessertReviewRowView.swift
//  iosApp
//
//  Created by Michael Stromer on 12/30/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import shared
import KingfisherSwiftUI

@available(iOS 14.0, *)
struct DessertReviewRowView: View {
    let review: Review
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(review.text)
                    .font(.title3)
                    .foregroundColor(.accentColor)
                VStack {
                    HStack {
                        ForEach(0..<5) { index in
                            if (index <= review.rating) {
                                Image(systemName: "star.fill")
                            } else {
                                Image(systemName: "star")
                            }
                        }
                    }
                }
            }
        }
    }
}

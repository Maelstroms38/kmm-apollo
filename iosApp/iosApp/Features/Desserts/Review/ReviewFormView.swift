//
//  ReviewFormView.swift
//  iosApp
//
//  Created by Michael Stromer on 1/30/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

@available(iOS 14.0, *)
struct ReviewFormView: View {
    var handler: (Review, ReviewAction) -> Void
    
    let reviewId: String
    let dessertId: String
    @State var text: String
    @State var rating: Int64
    
    private var isEditing: Bool {
        return reviewId != "new"
    }
    
    private var label: String {
        return isEditing ? "Edit" : "Create"
    }
    
    var body: some View {
        Form {
            Section(header: Text("\(label) Review")) {
                TextField("Description", text: $text)
                
                VStack {
                    HStack {
                        ForEach(0..<5) { index in
                            if (index <= rating) {
                                Image(systemName: "star.fill").onTapGesture {
                                    self.rating = Int64(index)
                                }
                            } else {
                                Image(systemName: "star").onTapGesture {
                                    self.rating = Int64(index)
                                }
                            }
                        }
                    }
                }
            }
            Section {
                Button(
                    action: {
                        let action: ReviewAction = isEditing ? .update : .create
                        self.handler(Review(id: reviewId, dessertId: dessertId, userId: "", text: text, rating: rating), action)
                    },
                    label: { Text(label) }
                )
                if isEditing {
                    Button(
                        action: {
                            self.handler(Review(id: reviewId, dessertId: dessertId, userId: "", text: text, rating: rating), ReviewAction.delete_)
                        },
                        label: { Text("Delete") }
                    )
                }
            }
        }
    }
}

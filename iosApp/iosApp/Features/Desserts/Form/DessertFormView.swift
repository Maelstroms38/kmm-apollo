//
//  DessertFormView.swift
//  iosApp
//
//  Created by Michael Stromer on 12/31/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

@available(iOS 14.0, *)
struct DessertFormView: View {
    var handler: (GetDessertQuery.Dessert) -> Void
    
    let dessertId: String
    @State var name: String
    @State var description: String
    @State var imageUrl: String
    
    private var isEditing: Bool {
        return !dessertId.isEmpty
    }
    
    private var label: String {
        return isEditing ? "Edit" : "Create"
    }
    
    var body: some View {
        Form {
            Section(header: Text(label)) {
                TextField("Name", text: $name)
                TextField("Description", text: $description)
                TextField("Image URL", text: $imageUrl)
            }
            Section {
                Button(
                    action: {
                        if (isEditing) {
                            self.handler(GetDessertQuery.Dessert(
                            __typename: "updateDessert", id: dessertId, name: name, description: description, imageUrl: imageUrl, reviews: []
                            ))
                        } else {
                            self.handler(GetDessertQuery.Dessert(
                            __typename: "newDessert", id: dessertId, name: name, description: description, imageUrl: imageUrl, reviews: []
                            ))
                        }
                    },
                    label: { Text(label) }
                )
                if isEditing {
                    Button(
                        action: {
                            self.handler(GetDessertQuery.Dessert(__typename: "deleteDessert", id: dessertId, name: "", description: "", imageUrl: "", reviews: []))
                        },
                        label: { Text("Delete") }
                    )
                }
            }
        }
    }
}

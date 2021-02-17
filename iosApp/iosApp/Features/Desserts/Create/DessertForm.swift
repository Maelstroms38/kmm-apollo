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
struct DessertForm: View {
    var handler: (Dessert, DessertAction) -> Void
    
    let dessertId: String
    @State var name: String
    @State var description: String
    @State var imageUrl: String
    
    private var isEditing: Bool {
        return dessertId != "new"
    }
    
    private var label: String {
        return isEditing ? "Edit" : "Create"
    }
    
    var body: some View {
        Form {
            Section(header: Text("\(label) Dessert")) {
                TextField("Name", text: $name)
                TextField("Description", text: $description)
                TextField("Image URL", text: $imageUrl)
            }
            Section {
                Button(
                    action: {
                        let action: DessertAction = isEditing ? .update : .create
                        self.handler(Dessert(id: dessertId, userId: "", name: name, description: description, imageUrl: imageUrl
                        ), action)
                    },
                    label: { Text(label) }
                )
                if isEditing {
                    Button(
                        action: {
                            self.handler(Dessert(id: dessertId, userId: "", name: "", description: "", imageUrl: ""), DessertAction.delete_)
                        },
                        label: { Text("Delete") }
                    )
                }
            }
        }
    }
}

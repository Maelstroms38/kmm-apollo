//
//  DessertCreateView.swift
//  iosApp
//
//  Created by Michael Stromer on 1/3/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

@available(iOS 14.0, *)
struct DessertCreateView: View {
    
    @StateObject var createViewModel = DessertCreateViewModel()
    
    let dessert: Dessert?
    
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        VStack {
            DessertFormView(handler: { dessert, action in
                switch action {
                case .create:
                    createViewModel.createDessert(newDessert: dessert)
                case .update:
                    createViewModel.updateDessert(dessert: dessert)
                case .delete_:
                    createViewModel.deleteDessert(dessertId: dessert.id)
                    
                default:
                    break
                }
                presentationMode.wrappedValue.dismiss()
            },
            dessertId: dessert?.id ?? "new", name: dessert?.name ?? "", description: dessert?.description_ ?? "", imageUrl: dessert?.imageUrl ?? "")
        }
        .navigationBarTitle("", displayMode: .inline)
        .onAppear() {
            self.createViewModel.dessert = dessert
        }
    }
}

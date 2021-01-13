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
    
    let delegate: DessertDelegate
    
    let dessert: Dessert?
    
    @StateObject private var viewModel = DessertCreateViewModel()
    
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        VStack {
            DessertFormView(handler: { dessert in
                switch dessert.action {
                case .CREATE:
                    viewModel.createDessert(newDessert: dessert)
                case .UPDATE:
                    viewModel.updateDessert(dessert: dessert)
                case .DELETE:
                    viewModel.deleteDessert(dessertId: dessert.dessertId)
                    
                default:
                    break
                }
                presentationMode.wrappedValue.dismiss()
            },
            dessertId: dessert?.dessertId ?? "new", name: dessert?.name ?? "", description: dessert?.description ?? "", imageUrl: dessert?.imageUrl ?? "")
        }
        .navigationBarTitle("", displayMode: .inline)
        .onAppear() {
            self.viewModel.dessert = dessert
            self.viewModel.delegate = delegate
        }
    }
}

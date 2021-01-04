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
    
    let delegate: DessertListViewDelegate
    
    @StateObject private var viewModel = DessertCreateViewModel()
    
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        VStack {
            DessertFormView(handler: { dessert in
                viewModel.createDessert(newDessert: dessert)
                presentationMode.wrappedValue.dismiss()
            },
            dessertId: "new", name: "", description: "", imageUrl: "")
        }
        Spacer()
        .onAppear() {
            self.viewModel.delegate = delegate
        }
    }
}

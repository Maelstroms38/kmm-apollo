//
//  DessertListRowView.swift
//  iosApp
//
//  Created by Michael Stromer on 12/29/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import shared
import KingfisherSwiftUI

@available(iOS 14.0, *)
struct DessertListRowView: View {
    let dessert: GetDessertsQuery.Result
    
    var body: some View {
        HStack {
            if let image = dessert.imageUrl,
               let url = URL(string: image) {
                KFImage(url)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 50, height: 50)
                    .cornerRadius(25)
            } else {
                RoundedRectangle(cornerRadius: 25)
                    .frame(width: 50, height: 50)
                    .foregroundColor(.gray)
            }
            VStack(alignment: .leading) {
                Text(dessert.name ?? "Loading...")
                    .font(.title3)
                    .foregroundColor(.accentColor)
                Text(dessert.description_ ?? "")
                    .font(.footnote)
                    .foregroundColor(.gray)
            }
        }
    }
}

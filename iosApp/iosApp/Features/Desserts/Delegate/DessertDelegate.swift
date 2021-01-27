//
//  DessertDelegate.swift
//  iosApp
//
//  Created by Michael Stromer on 1/12/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared

protocol DessertDelegate {
    func onCreateDessert(newDessert: Dessert)
    func onUpdateDessert(updatedDessert: Dessert)
    func onDeleteDessert(dessertId: String)
}

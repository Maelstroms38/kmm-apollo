//
//  ModuleProvider.swift
//  iosApp
//
//  Created by Michael Stromer on 2/17/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

/// App Modules Provider - provides all the modues for injection - i-e: ViewModel injections
final class ModuleProvider
{
    let dessertRepository: DessertRepository
    let authRepository: AuthRepository
    let reviewRepository: ReviewRepository
    
    private let apolloProvider = ApolloProvider(databaseDriverFactory: DatabaseDriverFactory())
    
    init()
    {
        self.dessertRepository = DessertRepository(apolloProvider: apolloProvider)
        self.authRepository = AuthRepository(apolloProvider: apolloProvider)
        self.reviewRepository = ReviewRepository(apolloProvider: apolloProvider)
        
        self.commonInit()
    }
    
    func commonInit()
    {
      // add any configuration logic here
    }
    
    func start()
    {
        
    }
}

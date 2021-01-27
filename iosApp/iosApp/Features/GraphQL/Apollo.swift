//
//  Apollo.swift
//  iosApp
//
//  Created by Michael Stromer on 1/27/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared

class Apollo {
    static let shared = Apollo()
    private(set) lazy var apolloProvider: ApolloProvider = {
        return ApolloProvider(databaseDriverFactory: DatabaseDriverFactory())
    }()
}

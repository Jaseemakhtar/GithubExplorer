package com.jaseem.githubexplorer.domain.usecase

class SearchQuerySanitizerUseCase {
    fun invoke(input: String): String {
        return input
            .replace(Regex("[^a-zA-Z0-9_\\- ]"), "")
            .replace(Regex("\\s+"), " ")
    }
}

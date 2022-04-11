package kaczmarek.moneycalculator.core.error_handling

abstract class ApplicationException(cause: Throwable? = null) : Exception(cause)

class MatchingAppNotFoundException(cause: Throwable) : ApplicationException(cause)

class UnknownException(cause: Throwable, override val message: String) : ApplicationException(cause)
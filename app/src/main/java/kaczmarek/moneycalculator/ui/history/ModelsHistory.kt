package kaczmarek.moneycalculator.ui.history

import kaczmarek.moneycalculator.di.services.database.models.Session
import kaczmarek.moneycalculator.ui.base.BaseItem

class DateItem(val date: String) : BaseItem

class SessionItem(val session: Session, var isShow: Boolean) : BaseItem
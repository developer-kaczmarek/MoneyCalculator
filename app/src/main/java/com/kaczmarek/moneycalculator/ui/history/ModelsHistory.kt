package com.kaczmarek.moneycalculator.ui.history

import com.kaczmarek.moneycalculator.di.services.database.models.Session
import com.kaczmarek.moneycalculator.ui.base.BaseItem

class DateItem(val date: String) : BaseItem

class SessionItem(val session: Session) :
    BaseItem
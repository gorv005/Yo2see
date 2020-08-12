package com.appiness.yo2see.interfaces

import androidx.annotation.IntDef
import com.appiness.yo2see.common.CommonInt.BLOCKED_OR_NEVER_ASKED
import com.appiness.yo2see.common.CommonInt.DENIED
import com.appiness.yo2see.common.CommonInt.GRANTED


import java.lang.annotation.RetentionPolicy
import kotlin.annotation.Retention

@Retention(AnnotationRetention.SOURCE)
@IntDef(GRANTED, DENIED, BLOCKED_OR_NEVER_ASKED)
annotation class PermissionStatus


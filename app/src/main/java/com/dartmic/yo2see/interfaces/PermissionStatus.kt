package com.dartmic.yo2see.interfaces

import androidx.annotation.IntDef
import com.dartmic.yo2see.common.CommonInt.BLOCKED_OR_NEVER_ASKED
import com.dartmic.yo2see.common.CommonInt.DENIED
import com.dartmic.yo2see.common.CommonInt.GRANTED


import kotlin.annotation.Retention

@Retention(AnnotationRetention.SOURCE)
@IntDef(GRANTED, DENIED, BLOCKED_OR_NEVER_ASKED)
annotation class PermissionStatus


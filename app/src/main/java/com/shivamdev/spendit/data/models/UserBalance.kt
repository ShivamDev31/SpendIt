package com.shivamdev.spendit.data.models

/**
 * Created by shivam on 04/02/18.
 */

data class UserBalance(val userId: String = "", val userLent: Int = 0,
                       val userBorrow: Int = 0)
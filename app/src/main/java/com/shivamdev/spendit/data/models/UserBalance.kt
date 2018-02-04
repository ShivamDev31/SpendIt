package com.shivamdev.spendit.data.models

/**
 * Created by shivam on 04/02/18.
 */

data class UserBalance(val userId: String, val userBal: Float, val userLend: Float,
                       val userBorrow: Float)
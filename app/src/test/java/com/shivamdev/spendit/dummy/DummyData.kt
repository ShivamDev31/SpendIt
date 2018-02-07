package com.shivamdev.spendit.dummy

import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.data.models.User

/**
 * Created by shivam on 06/02/18.
 */

const val SELECTED_USER_ID = "2"
const val USER_ID = "3"
const val UNCOMMON_USER_ID = "4"

fun getUser(): User {
    return User(USER_ID, "Shivam", 100, 50, timeStamp = 123)
}

fun getUnCommonUser(): User {
    return User(UNCOMMON_USER_ID, "Shivam", 50, 100, timeStamp = 123)
}

fun getUsers(): List<User> {
    val users = mutableListOf<User>()
    (1..3).mapTo(users) {
        if (it == 2) {
            User("$it", "Shivam", 100, 50, true, timeStamp = 123)
        } else {
            User("$it", "Shivam", 100, 50, false, timeStamp = 123)
        }
    }
    return users
}

fun getUnCommonUsers(): List<User> {
    val users = mutableListOf<User>()
    User(UNCOMMON_USER_ID, "Shivam", 50, 100, timeStamp = 123)
    return users
}

fun getSelectedUsers(): List<User> {
    val users = mutableListOf<User>()
    val user = User(SELECTED_USER_ID, "Shivam", 100, 50, timeStamp = 123)
    users.add(user)
    return users
}


fun getFilteredUsers(): List<User> {
    val users = mutableListOf<User>()
    (1..2).mapTo(users) {
        if (it == 2) {
            User("$it", "Shivam", 100, 50, true, timeStamp = 123)
        } else {
            User("$it", "Shivam", 100, 50, false, timeStamp = 123)
        }
    }
    return users
}

fun getExpense(): Expense {
    return Expense(USER_ID, "Shivam", 1500, "For test purpose",
            500, getExpenseFriendsMap(getUsers().toMutableList()), "You lent", 123)
}

fun getExpensesWithText(): List<Expense> {
    val expenses = mutableListOf<Expense>()
    (1..3).mapTo(expenses) {
        if (it == 3) {
            Expense("$it", "Shivam", 1500, "For test purpose $it",
                    500, getExpenseFriendsMap(getUsers().toMutableList()),
                    "You lent", 123)
        } else {
            Expense("$it", "Shivam", 1500, "For test purpose $it",
                    500, getExpenseFriendsMap(getUsers().toMutableList()),
                    "You borrowed", 123)
        }
    }

    return expenses
}

fun getExpenseFriendsMap(selectedUsers: MutableList<User>): MutableMap<String, String> {
    val expenseFriendsMap = mutableMapOf<String, String>()
    selectedUsers.forEach {
        expenseFriendsMap[it.userId!!] = it.name!!
    }
    return expenseFriendsMap
}


package com.example.finalproject

import android.content.Intent

interface OnAuthLaunch {
    fun launch(intent: Intent)
    fun showListFragment()
}
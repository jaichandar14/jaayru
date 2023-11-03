package com.example.jaayr.baseactivity

import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity<T : BaseViewModel> :AppCompatActivity() {

    lateinit var viewModel: T
}
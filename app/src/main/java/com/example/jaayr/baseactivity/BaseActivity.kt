package com.example.jaayr.baseactivity

import androidx.appcompat.app.AppCompatActivity
import com.example.jaayr.dashboard.MainViewModel

abstract class BaseActivity<T : BaseViewModel> :AppCompatActivity() {

    lateinit var viewModel: T
}
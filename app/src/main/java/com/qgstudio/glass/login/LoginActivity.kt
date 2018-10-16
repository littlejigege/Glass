package com.qgstudio.glass.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.qgstudio.glass.R
import com.qgstudio.glass.home.HomeActivity
import com.qgstudio.glass.showToast
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private val tipDialog by lazy {
        QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在登录...")
                .create()
                .apply { setCancelable(false) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        initView()

    }

    private fun initView() {
        //验证码Button
        btnVeCode.setOnClickListener {
            veCodeOnClick(30) {
                //TODO 请求发送发送验证码
                viewModel.requestVeCode(textInputLayoutPhone.editText?.text.toString())
            }
        }
        //长按后门直入
        btnVeCode.setOnLongClickListener {
            //跳过登录
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            true
        }

        setTimingOfLogin()
    }

    /**
     *设置何时需要触发登录
     */
    private fun setTimingOfLogin() {
        textInputLayoutVeCode.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable) {
                val veCode = p0.toString()
                val phone = textInputLayoutPhone.editText?.text.toString()
                if (p0.length == 4) {
                    if (phone.isEmpty()) {
                        showToast("请填写正确的电话好吗?")
                        return
                    }
                    tipDialog.show()
                    viewModel.login(phone, veCode).observe(this@LoginActivity, Observer {
                        if (it == 200) {
                            // TODO 登录成功
                        } else {
                            //TODO 登录失败
                        }
                    })
                }
            }
        })
    }

    private fun veCodeOnClick(timeMillis: Long, action: () -> Unit) {
        if (timeMillis <= 0) return // don't delay
        action()
        GlobalScope.launch(Dispatchers.Main) {
            btnVeCode.isEnabled = false
            for (i in timeMillis downTo 1) {
                btnVeCode.text = "${i}秒后重试"
                delay(1000)
            }
            btnVeCode.isEnabled = true
            btnVeCode.text = "发送验证码"
        }
    }

    private fun loginSuccess(){

    }
}

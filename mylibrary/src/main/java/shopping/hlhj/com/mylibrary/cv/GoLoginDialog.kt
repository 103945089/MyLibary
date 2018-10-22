package shopping.hlhj.com.mylibrary.cv

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import shopping.hlhj.com.mylibrary.R

/**
 * Created by Never Fear   on 2018\10\22 0022.
 * Never More....
 */

class GoLoginDialog(context: Context) : Dialog(context, R.style.CustomDialog){
    private var listener:OnCancelListener?=null
    private var btNo:View?=null
    private var btYes:View?=null
    public fun setListener(listener:OnCancelListener){
        this.listener=listener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.go_logindialog)
        btNo=findViewById(R.id.btNo)
        btYes=findViewById(R.id.btYes)
        btNo?.setOnClickListener {
            if (listener==null) run {
                dismiss()
            }else{
                listener!!.cancle()
                dismiss()
            }
        }
        btYes?.setOnClickListener {
            //            TMSharedPUtil.saveTMToken(context,"ADCB12389F971DD3AD5860AAB7A5A2A9")

            val action = context.packageName + ".usercenter.login"
            val intent = Intent(action)
            context.startActivity(intent)
            dismiss()
        }



    }

    interface OnCancelListener{
        fun cancle()
    }

}

package at.abraxas.amarino.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import at.abraxas.amarino.R;
import at.abraxas.amarino.intent.DefaultAmarinoServiceIntentConfig;
import at.abraxas.amarino.message.ControlMessage;
import at.abraxas.amarino.service.AmarinoService;


public class ControlActivity extends Activity implements View.OnClickListener {


        @ViewInject(R.id.FORWARD)
        Button FORWARD ;
        @ViewInject(R.id.BACKWARD)
        Button BACKWARD;
        @ViewInject(R.id.TurnLeft)
        Button TurnLeft ;
        @ViewInject(R.id.TurnRight)
        Button TurnRight;

        @ViewInject(R.id.TurnOff)
        Button TurnOff;


    String[] addresses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        ViewUtils.inject(this);

        FORWARD.setOnClickListener(this);
        BACKWARD.setOnClickListener(this);
        TurnLeft.setOnClickListener(this);
        TurnRight.setOnClickListener(this);
        TurnOff.setOnClickListener(this);


        /*
			注册蓝牙地址监听器  获得已经连接的蓝牙地址信息
		 */
        // we want to know which devices are connected in order to send data to a device
        IntentFilter intentFilter = new IntentFilter(DefaultAmarinoServiceIntentConfig.ACTION_CONNECTED_DEVICES);
        registerReceiver(receiver, intentFilter);

        Intent intent = new Intent(ControlActivity.this, AmarinoService.class);
        intent.setAction(DefaultAmarinoServiceIntentConfig.ACTION_GET_CONNECTED_DEVICES);
        ControlActivity.this.startService(intent);

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action == null) return;

            if (DefaultAmarinoServiceIntentConfig.ACTION_CONNECTED_DEVICES.equals(action)){
                //获得连接的设备信息
                addresses = intent.getStringArrayExtra(DefaultAmarinoServiceIntentConfig.EXTRA_CONNECTED_DEVICE_ADDRESSES);
            }
        }
    };


    @Override
    public void onClick(View view) {
        if (view == FORWARD)
        {
            sendDirControlData(ControlMessage.FORWARD);

        }else if (view == BACKWARD)
        {
            sendDirControlData(ControlMessage.BACKWARD);
        }
        else if (view == TurnLeft)
        {
            sendDirControlData(ControlMessage.TurnLeft);
        }
        else if (view == TurnRight)
        {
            sendDirControlData(ControlMessage.TurnRight);
        }
        else if (view == TurnOff){
            sendDirControlData(ControlMessage.TurnOff);
        }
    }

    /*
    给arduino 设备发送数据
 */
    private void sendDirControlData(int msg){

        String address = "";

        if (addresses == null){
            // no device is connected
            Toast.makeText(ControlActivity.this, "No connected device found!\n\nData not sent.", Toast.LENGTH_SHORT).show();
        }
        else if (addresses.length == 1){
            // no need to ask just send data to the connected device
            address = addresses[0];
//            sendData(addresses[0], dataToSendET.getText().toString());
        }
        else {
            // several connected devices, we need to show a dialog and ask where to send the data
            //todo: 提供选择设备的jie接口  这样就可以 控制多个设备 发送数据
//            showDialog(DIALOEVICES);
            address = addresses[0];
        }


        Intent intent = new Intent(ControlActivity.this, AmarinoService.class);
        intent.setAction(DefaultAmarinoServiceIntentConfig.ACTION_SEND);
        intent.putExtra(DefaultAmarinoServiceIntentConfig.EXTRA_DEVICE_ADDRESS, address);	//mac地址
        intent.putExtra(DefaultAmarinoServiceIntentConfig.EXTRA_FLAG, ControlMessage.directionFlag);		//选择模式
        intent.putExtra(DefaultAmarinoServiceIntentConfig.EXTRA_DATA_TYPE, DefaultAmarinoServiceIntentConfig.INT_EXTRA); //数据类型
        intent.putExtra(DefaultAmarinoServiceIntentConfig.EXTRA_DATA, msg);	//具体数据
        ControlActivity.this.startService(intent);


    }


}

package com.orange_infinity.onlinepay.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.iposprinter.iposprinterservice.*;
import com.iposprinter.printerhelper.*;
import com.iposprinter.printerhelper.Utils.*;

public class PrintHelper {

    private static final String TAG = "PrintHelper";

    private int printerStatus = 0;

    private IPosPrinterService mIPosPrinterService;
    private IPosPrinterCallback callback;

    public PrintHelper() {
        callback = new IPosPrinterCallback.Stub() {

            @Override
            public void onRunResult(final boolean isSuccess) throws RemoteException {
                Log.i(TAG,"result:" + isSuccess + "\n");
            }

            @Override
            public void onReturnString(final String value) throws RemoteException {
                Log.i(TAG,"result:" + value + "\n");
            }
        };
    }

    public PrintHelper createHelper(Context context) {

        Intent intent=new Intent();
        intent.setPackage("com.iposprinter.iposprinterservice");
        intent.setAction("com.iposprinter.iposprinterservice.IPosPrintService");
        //startService(intent);
        context.bindService(intent, connectService, Context.BIND_AUTO_CREATE);

        return this;
    }

    public void removeHelper(Context context) {
        context.unbindService(connectService);
    }

    private ServiceConnection connectService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIPosPrinterService = IPosPrinterService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIPosPrinterService = null;
        }
    };

    public int getPrinterStatus(){

        Log.i(TAG,"***** printerStatus"+printerStatus);
        try{
            printerStatus = mIPosPrinterService.getPrinterStatus();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        Log.i(TAG,"#### printerStatus"+printerStatus);
        return  printerStatus;
    }

    public void printerInit(){
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try{
                    mIPosPrinterService.printerInit(callback);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void printText()
    {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] cmd = new byte[3];
                    cmd[0]=0x1B;
                    cmd[1]=0x45;
                    cmd[2]=125;
                    mIPosPrinterService.sendUserCMDData(cmd,callback);
                    byte[] cmd1= {27, 45, 0};

                    mIPosPrinterService.printSpecifiedTypeText("    Проверка печати\n", "ST", 24, callback);
                    mIPosPrinterService.sendUserCMDData(cmd1,callback);
                    mIPosPrinterService.printSpecifiedTypeText("    дальше пойдёт текст на китайском\n", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("    智能POS机数据终端\n", "ST", 16, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.printSpecifiedTypeText("      欢迎使智能POS机数据终端\n", "ST", 24, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.printSpecifiedTypeText("智能POS 数据终端 智能POS\n", "ST", 16, callback);
                    mIPosPrinterService.printBlankLines(1, 8, callback);
                    mIPosPrinterService.printSpecifiedTypeText("#POS POS ipos POS POS POS POS ipos POS POS ipos#\n", "ST", 16, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                    mIPosPrinterService.PrintSpecFormatText("开启打印测试\n", "ST", 16, 1, callback);
                    mIPosPrinterService.printSpecifiedTypeText("********************************", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("这是一行16号字体\n", "ST", 16, callback);
                    mIPosPrinterService.printSpecifiedTypeText("这是一行24号字体\n", "ST", 24, callback);
                    mIPosPrinterService.PrintSpecFormatText("这是一行24号字体\n", "ST", 24, 2, callback);
                    mIPosPrinterService.printSpecifiedTypeText("这是一行16号字体\n", "ST", 16, callback);
                    mIPosPrinterService.PrintSpecFormatText("这是一行16号字体\n", "ST", 16, 2, callback);
                    mIPosPrinterService.printSpecifiedTypeText("这是一行24号字体\n", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("ABCDEFGHIJKLMNOPQRSTUVWXYZ01234\n", "ST", 16, callback);
                    mIPosPrinterService.printSpecifiedTypeText("abcdefghijklmnopqrstuvwxyz56789\n", "ST", 24, callback);
                    mIPosPrinterService.printSpecifiedTypeText("κρχκμνκλρκνκνμρτυφ\n", "ST", 24, callback);
                    mIPosPrinterService.setPrinterPrintAlignment(0,callback);
                    mIPosPrinterService.printQRCode("http://www.google.com\n", 10, 1, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                    mIPosPrinterService.printBlankLines(1, 16, callback);
                    for (int i = 0; i < 12; i++)
                    {
                        mIPosPrinterService.printRawData(BytesUtil.initLine1(384, i),callback);
                    }
                    mIPosPrinterService.PrintSpecFormatText("打印测试完成\n", "ST", 16, 1, callback);
                    mIPosPrinterService.printSpecifiedTypeText("**********END***********\n\n", "ST", 16, callback);
                    mIPosPrinterService.printerPerformPrint(160,  callback);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
    }
}

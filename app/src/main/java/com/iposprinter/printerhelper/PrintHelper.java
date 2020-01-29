package com.iposprinter.printerhelper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.iposprinter.iposprinterservice.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PrintHelper {

    private static final String TAG = "PrintHelper";

    private final int PRINTER_NORMAL = 0;
    private final int PRINTER_PAPERLESS = 1;
    private final int PRINTER_THP_HIGH_TEMPERATURE = 2;
    private final int PRINTER_MOTOR_HIGH_TEMPERATURE = 3;
    private final int PRINTER_IS_BUSY = 4;
    private final int PRINTER_ERROR_UNKNOWN = 5;

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

    public String getCurrentTimeStamp(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void printReceipt(final int receiptNumber, final String receiptTime, final int turnNumber, final String[] OFD, final Bitmap QR)
    {
        if (getPrinterStatus() == PRINTER_NORMAL) {
            ThreadPoolManager.getInstance().executeTask(new Runnable() {
                @Override
                public void run() {
                    try {

                        mIPosPrinterService.PrintSpecFormatText("ООО «Ружавто»\n", "ST", 32, 1, callback);
                        mIPosPrinterService.PrintSpecFormatText("634050, Томск, ул.Алексея Беленца,8\n", "ST", 16, 1, callback);
                        mIPosPrinterService.PrintSpecFormatText("ИНН 7017329780\n", "ST", 32, 1, callback);

                        mIPosPrinterService.printBlankLines(2, 8, callback);

                        mIPosPrinterService.PrintSpecFormatText("Место расчетов: автобус\n", "ST", 24, 1, callback);

                        mIPosPrinterService.printBlankLines(2, 8, callback);

                        mIPosPrinterService.PrintSpecFormatText("КАССОВЫЙ ЧЕК №" + receiptNumber + "\n", "ST", 24, 1, callback);

                        mIPosPrinterService.printBlankLines(2, 8, callback);

                        mIPosPrinterService.PrintSpecFormatText("Приход" + "          " + receiptTime + "\n", "ST", 24, 1, callback);
                        mIPosPrinterService.PrintSpecFormatText("Номер смены" + "                " + turnNumber + "\n", "ST", 24, 1, callback);
                        mIPosPrinterService.PrintSpecFormatText("СНО" + "                       " + "ОСН" + "\n", "ST", 24, 1, callback);

                        mIPosPrinterService.printBlankLines(2, 8, callback);

                        mIPosPrinterService.PrintSpecFormatText("Оплата за проезд в автобусе" + "22.00" + "\n", "ST", 24, 1, callback);
                        mIPosPrinterService.PrintSpecFormatText("ИТОГ" + "              " + "22.00" + "\n", "ST", 32, 1, callback);

                        mIPosPrinterService.printBlankLines(2, 8, callback);

                        mIPosPrinterService.PrintSpecFormatText("Наличными" + "              " + "22.00" + "\n", "ST", 24, 1, callback);
                        mIPosPrinterService.PrintSpecFormatText("Электронными" + "              " + "0.00" + "\n", "ST", 24, 1, callback);
                        mIPosPrinterService.PrintSpecFormatText("НДС" + "                      " + "0%" + "\n", "ST", 24, 1, callback);

                        mIPosPrinterService.printBlankLines(2, 8, callback);

                        /*
                        if (OFD != null && OFD.length >= 5) {
                            mIPosPrinterService.PrintSpecFormatText("заводской номер фискального накопителя" + "    " + OFD[0] + "\n", "ST", 16, 1, callback);
                            mIPosPrinterService.PrintSpecFormatText("регистрационный номер ККТ" + "           " + OFD[0] + "\n", "ST", 16, 1, callback);
                            mIPosPrinterService.PrintSpecFormatText("порядковый номер фискального документа" + "    " + OFD[0] + "\n", "ST", 16, 1, callback);
                            mIPosPrinterService.PrintSpecFormatText("фискальный признак документа" + "              " + OFD[0] + "\n", "ST", 16, 1, callback);
                            mIPosPrinterService.PrintSpecFormatText("версия ФФД" + "                     " + OFD[0] + "\n", "ST", 16, 1, callback);
                        }
                        */

                        mIPosPrinterService.printBlankLines(2, 8, callback);

                        mIPosPrinterService.PrintSpecFormatText("Адрес сайта для просмотра чека:\n", "ST", 16, 1, callback);
                        mIPosPrinterService.PrintSpecFormatText("Адрес сайта для проверки чека: nalog.ru\n", "ST", 16, 1, callback);

                        mIPosPrinterService.printBlankLines(2, 8, callback);

                        mIPosPrinterService.printBitmap(1, 10, QR, callback);

                        //mIPosPrinterService.PrintSpecFormatText("ТЕСТОВАЯ ПЕЧАТЬ\n", "ST", 24, 1, callback); //TODO remove!

                        mIPosPrinterService.printerPerformPrint(160, callback);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Log.i(TAG, "Printer is busy");
        }
    }
}

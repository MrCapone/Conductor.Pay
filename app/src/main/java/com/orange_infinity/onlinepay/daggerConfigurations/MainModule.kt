package com.orange_infinity.onlinepay.daggerConfigurations

import com.orange_infinity.onlinepay.data.db.CashChequeDao
import com.orange_infinity.onlinepay.data.network.PayInfoService
import com.orange_infinity.onlinepay.ui.presenter.MainActivityPresenter
import com.orange_infinity.onlinepay.ui.presenter.RegistrationPresenter
import com.orange_infinity.onlinepay.useCase.*
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainActivityPresenter(
        cashChequeManager: CashChequeManager
    ): MainActivityPresenter = MainActivityPresenter(cashChequeManager = cashChequeManager)

    @Provides
    fun provideRegistrationActivityPresenter(serverEntryController: ServerEntryController): RegistrationPresenter
            = RegistrationPresenter(serverEntryController = serverEntryController)

    @Provides
    fun provideTicketManager(): CashChequeManager = CashChequeManager()

    @Provides
    fun provideUpdateController(programUpdater: ProgramUpdater): ServerEntryController = ServerEntryController(programUpdater)

    @Provides
    fun provideServerController(payInfoService: PayInfoService): ServerPayController = ServerPayController(payInfoService)

    @Provides
    fun provideProgramUpdater(): ProgramUpdater = ProgramUpdater()

    @Provides
    fun provideYandexSdkManager(): YandexSdkManager = YandexSdkManager()

    @Provides
    fun provideNfcController(): NfcController = NfcController()

    @Provides
    fun providePayInfoService(): PayInfoService = PayInfoService()
}
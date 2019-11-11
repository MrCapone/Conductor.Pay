package com.orange_infinity.onlinepay.daggerConfigurations

import com.orange_infinity.onlinepay.data.db.TicketRepository
import com.orange_infinity.onlinepay.data.network.PayInfoService
import com.orange_infinity.onlinepay.ui.presenter.MainActivityPresenter
import com.orange_infinity.onlinepay.useCase.*
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainActivityPresenter(
        ticketManager: TicketManager,
        serverEntryController: ServerEntryController
    ): MainActivityPresenter = MainActivityPresenter(ticketManager = ticketManager, serverEntryController = serverEntryController)

    @Provides
    fun provideTicketManager(ticketRepository: TicketRepository): TicketManager = TicketManager(ticketRepository)

    @Provides
    fun provideUpdateController(programUpdater: ProgramUpdater): ServerEntryController = ServerEntryController(programUpdater)

    @Provides
    fun provideServerController(payInfoService: PayInfoService): ServerPayController = ServerPayController(payInfoService)

    @Provides
    fun provideTicketRepository(): TicketRepository = TicketRepository()

    @Provides
    fun provideProgramUpdater(): ProgramUpdater = ProgramUpdater()

    @Provides
    fun provideYandexSdkManager(): YandexSdkManager = YandexSdkManager()

    @Provides
    fun provideNfcController(): NfcController = NfcController()

    @Provides
    fun providePayInfoService(): PayInfoService = PayInfoService()
}